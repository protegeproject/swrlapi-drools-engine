package org.swrlapi.drools.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.Activation;
import org.drools.runtime.rule.AgendaFilter;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.drools.converters.drl.DroolsOWLClassExpression2DRLConverter;
import org.swrlapi.drools.converters.drl.DroolsOWLPropertyExpression2DRLConverter;
import org.swrlapi.drools.converters.drl.DroolsSQWRLQuery2DRLConverter;
import org.swrlapi.drools.converters.oo.DroolsOWLAxiom2AConverter;
import org.swrlapi.drools.core.resolvers.DroolsObjectResolver;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.factory.DroolsFactory;
import org.swrlapi.drools.owl.axioms.A;
import org.swrlapi.drools.owl2rl.DroolsOWL2RLEngine;
import org.swrlapi.drools.reasoner.DefaultDroolsOWLAxiomHandler;
import org.swrlapi.drools.sqwrl.DroolsSQWRLCollectionHandler;
import org.swrlapi.exceptions.SWRLRuleEngineBridgeException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.sqwrl.SQWRLQuery;

import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class provides a Drools implementation of a rule engine for SWRL using the SWRLAPI's Rule Engine Bridge
 * mechanism. The underlying reasoner is supplied by the {@link org.swrlapi.drools.owl2rl.DroolsOWL2RLEngine}.
 *
 * @see org.swrlapi.bridge.TargetSWRLRuleEngine
 * @see org.swrlapi.drools.owl2rl.DroolsOWL2RLEngine
 */
public class DroolsSWRLRuleEngine implements TargetSWRLRuleEngine
{
  @NonNull private final SWRLRuleEngineBridge bridge;

  @NonNull private final DroolsOWLAxiom2AConverter axiomConverter;
  @NonNull private final DroolsSQWRLQuery2DRLConverter queryConverter;
  @NonNull private final DroolsOWLAxiomExtractor axiomExtractor;
  @NonNull private final DroolsSWRLBuiltInInvoker builtInInvoker;
  @NonNull private final DroolsSQWRLCollectionHandler sqwrlCollectionInferrer;
  @NonNull private final DroolsOWL2RLEngine owl2RLEngine;
  @NonNull private final DefaultDroolsOWLAxiomHandler axiomInferrer;

  // We keep track of axioms supplied to and inferred by Drools so that we do not redundantly assert them.
  @NonNull private final Set<@NonNull OWLAxiom> assertedAndInferredOWLAxioms;
  @NonNull private final Set<@NonNull String> allSQWRLQueryNames; // Drools is supplied with all currently enabled SQWRL queries.
  // Typically, only one query is active so we use an agenda filter to ignore the ones that are not active.
  @NonNull private final Set<@NonNull String> activeSQWRLQueryNames;
  @NonNull private final Set<@NonNull String> phase1SQWRLRuleNames;
  @NonNull private final Set<@NonNull String> phase2SQWRLRuleNames;
  @NonNull private final Map<@NonNull String, String> ruleName2SQWRLQueryNameMap;
  @NonNull private final SQWRLPhase1AgendaFilter sqwrlPhase1AgendaFilter;
  @NonNull private final SQWRLPhase2AgendaFilter sqwrlPhase2AgendaFilter;

  private KnowledgeBase knowledgeBase;
  private KnowledgeBuilder knowledgeBuilder;
  private StatefulKnowledgeSession knowledgeSession;
  private DroolsResourceHandler resourceHandler;
  private boolean knowledgePackagesAdditionRequired;

  public DroolsSWRLRuleEngine(@NonNull SWRLRuleEngineBridge bridge) throws TargetSWRLRuleEngineException
  {
    this.bridge = bridge;

    DroolsObjectResolver resolver = new DroolsObjectResolver();
    DroolsOWLPropertyExpression2DRLConverter propertyExpressionConverter = new DroolsOWLPropertyExpression2DRLConverter(
      bridge, resolver);
    DroolsOWLClassExpression2DRLConverter classExpressionConverter = new DroolsOWLClassExpression2DRLConverter(bridge,
      resolver, propertyExpressionConverter);
    this.axiomConverter = new DroolsOWLAxiom2AConverter(bridge, this, classExpressionConverter,
      propertyExpressionConverter);
    this.queryConverter = new DroolsSQWRLQuery2DRLConverter(bridge, this, classExpressionConverter,
      propertyExpressionConverter);

    this.axiomExtractor = DroolsFactory.getDroolsOWLAxiomExtractor(bridge);
    this.builtInInvoker = new DroolsSWRLBuiltInInvoker(bridge);
    this.owl2RLEngine = new DroolsOWL2RLEngine(bridge.getOWL2RLPersistenceLayer());
    this.axiomInferrer = new DefaultDroolsOWLAxiomHandler();
    this.sqwrlCollectionInferrer = new DroolsSQWRLCollectionHandler();

    this.assertedAndInferredOWLAxioms = new HashSet<>();

    this.allSQWRLQueryNames = new HashSet<>();
    this.activeSQWRLQueryNames = new HashSet<>();
    this.phase1SQWRLRuleNames = new HashSet<>();
    this.phase2SQWRLRuleNames = new HashSet<>();
    this.ruleName2SQWRLQueryNameMap = new HashMap<>();

    this.sqwrlPhase1AgendaFilter = new SQWRLPhase1AgendaFilter();
    this.sqwrlPhase2AgendaFilter = new SQWRLPhase2AgendaFilter();

    this.knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase(createKnowledgeBaseConfiguration());
    this.knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    this.resourceHandler = new DroolsResourceHandler(this.knowledgeBuilder);

    // Import OWL and SWRL Java classes
    this.resourceHandler.defineJavaResources();

    // Add the globals and OWL and SWRL Java classes to knowledge base
    addKnowledgePackages(this.knowledgeBase, this.knowledgeBuilder);

    // OWL 2 RL rules are not added to knowledge base until the runRuleEngine method is invoked
    for (DroolsRuleDefinition ruleDefinition : this.owl2RLEngine.getEnabledRuleDefinitions())
      this.resourceHandler.defineDRLRule(ruleDefinition.getRuleText());

    this.knowledgePackagesAdditionRequired = true;
    this.builtInInvoker.reset();

    this.knowledgeSession = this.knowledgeBase.newStatefulKnowledgeSession();
    this.knowledgeSession.setGlobal("invoker", this.builtInInvoker);
    this.knowledgeSession.setGlobal("inferrer", this.axiomInferrer);
    this.knowledgeSession.setGlobal("sqwrlInferrer", this.sqwrlCollectionInferrer);

    // Supply the inferrer with the knowledge session is so that it can insert new facts as inference is performed.
    this.axiomInferrer.reset(this.knowledgeSession);
  }

  @Override public void resetRuleEngine() throws TargetSWRLRuleEngineException
  {
    // The bridge resets the ontology changed status after each reset so we can determine if the ontology has changed
    // since the last reset and decide if we want to rebuild the knowledge base. A change in OWL 2 RL rule selection
    // will also require a rebuild.
    // TODO This could be optimized so that we do not repeat addition of the Java classes representing OWL and SWRL
    // concepts on a knowledge base rebuild.

    if (getBridge().hasOntologyChanged() || getOWL2RLEngine().hasRuleSelectionChanged()) {

      this.knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase(createKnowledgeBaseConfiguration());
      this.knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
      this.resourceHandler = new DroolsResourceHandler(this.knowledgeBuilder);

      // Import OWL and SWRL Java classes
      this.resourceHandler.defineJavaResources();

      // Add the globals and OWL and SWRL Java classes to knowledge base
      addKnowledgePackages(this.knowledgeBase, this.knowledgeBuilder);

      // OWL 2 RL rules are not added to knowledge base until the runRuleEngine method is invoked
      for (DroolsRuleDefinition ruleDefinition : this.owl2RLEngine.getEnabledRuleDefinitions())
        this.resourceHandler.defineDRLRule(ruleDefinition.getRuleText());

      this.knowledgePackagesAdditionRequired = true;
    }
    this.builtInInvoker.reset();
    resetKnowledgeSession();
  }

  @Override public void runRuleEngine() throws TargetSWRLRuleEngineException
  {
    ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
    Thread.currentThread().setContextClassLoader(DroolsSWRLRuleEngine.class.getClassLoader());

    if (this.knowledgePackagesAdditionRequired) {
      try { // Add the (OWL 2 RL and SWRL-translated) rules to the knowledge base.
        this.knowledgeBase.addKnowledgePackages(this.knowledgeBuilder.getKnowledgePackages());
      } catch (Exception e) {
        Thread.currentThread().setContextClassLoader(oldClassLoader);
        throw new TargetSWRLRuleEngineException("error transferring rules to Drools rule engine:\n" + e.getMessage(),
          e);
      }
      this.knowledgePackagesAdditionRequired = false;
    }

    try { // Asserted OWL axioms and class expressions must be added after rules are added to knowledge base.
      getDroolsOWLAxiomConverter().getAssertedOWLAxioms().forEach(this.knowledgeSession::insert);
      getDroolsOWLAxiomConverter().getOWLClassExpressions().forEach(this.knowledgeSession::insert);
    } catch (Exception e) { // Note: SWRL built-ins can be called during this insertion process
      Thread.currentThread().setContextClassLoader(oldClassLoader);
      String errorMessage = (e.getCause() == null) ?
        (e.getMessage() == null ? e.toString() : e.getMessage()) :
        (e.getCause().getMessage() == null ? e.toString() : e.getCause().getMessage());
      throw new TargetSWRLRuleEngineException("error inserting asserted OWL axioms into Drools:\n" + errorMessage, e);
    }

    // Supply the inferrer with the set of asserted OWL axioms so that it does not redundantly put inferred axioms into
    // the knowledge session that are identical to asserted knowledge.
    this.axiomInferrer.addAssertOWLAxioms(getDroolsOWLAxiomConverter().getAssertedOWLAxioms());

    try { // Fire the rules
      this.knowledgeSession.fireAllRules(this.sqwrlPhase1AgendaFilter);
      if (!this.phase2SQWRLRuleNames.isEmpty() && this.sqwrlCollectionInferrer.hasSQWRLCollections()) {
        this.sqwrlCollectionInferrer.getSQWRLCollections().forEach(this.knowledgeSession::insert);
        this.knowledgeSession.fireAllRules(this.sqwrlPhase2AgendaFilter);
      }
    } catch (Exception e) {
      Thread.currentThread().setContextClassLoader(oldClassLoader);
      String errorMessage = e.getMessage() != null ? e.getMessage() : "";
      throw new TargetSWRLRuleEngineException("error running Drools rule engine:\n" + errorMessage, e);
    }
    Thread.currentThread().setContextClassLoader(oldClassLoader);

    writeInferredOWLAxiomsToBridge(); // Supply the inferred OWL axioms back to the bridge.
  }

  @Override public void defineOWLAxiom(@NonNull OWLAxiom axiom) throws TargetSWRLRuleEngineException
  {
    if (!this.assertedAndInferredOWLAxioms.contains(axiom)) {
      getDroolsOWLAxiomConverter().convert(axiom); // Put the axiom into the Drools knowledge base.
      this.assertedAndInferredOWLAxioms.add(axiom);
    }
  }

  @Override public void defineSQWRLQuery(@NonNull SQWRLQuery query) throws TargetSWRLRuleEngineException
  {
    this.allSQWRLQueryNames.add(query.getQueryName());

    if (query.isActive()) // If a query is not active, we convert it but recordOWLClassExpression it as inactive.
      this.activeSQWRLQueryNames.add(query.getQueryName());

    getDroolsSQWRLQueryConverter().convert(query); // Will call local defineSQWRLPhase{1,2}Rule.
  }

  /*
   * A target rule engine must also define an OWL reasoner implementation.
   */
  @NonNull @Override public OWLReasoner getOWLReasoner()
  {
    return null; // TODO Return Drools implementation of an OWL reasoner here
  }

  @NonNull @Override public Icon getTargetRuleEngineIcon()
  {
    return DroolsFactory.getSWRLRuleEngineIcon();
  }

  @NonNull @Override public OWL2RLEngine getOWL2RLEngine()
  {
    return this.owl2RLEngine;
  }

  @NonNull @Override public String getTargetRuleEngineName()
  {
    return DroolsNames.RULE_ENGINE_NAME;
  }

  @NonNull @Override public String getTargetRuleEngineVersion()
  {
    return DroolsNames.VERSION_STRING;
  }

  /**
   * Define a Drools representation of a SWRL rule or a SQWRL query. This method will be called by Drools converters
   * after they have translated SWRL rules and SQWRL queries into their Drools equivalent.
   *
   * @param ruleText The rule
   */
  public void defineDRLRule(@NonNull String ruleText)
  {
    if (this.knowledgePackagesAdditionRequired)
      this.resourceHandler.defineDRLRule(ruleText);
  }

  /**
   * Define a Drools representation of a SWRL rule representing phase 2 of a SQWRL query. This method will be called by
   * the {@link DroolsSQWRLQuery2DRLConverter}.
   *
   * @param queryName The name of the query
   * @param ruleName  The name of the rule
   * @param ruleText  The rule text
   * @throws TargetSWRLRuleEngineException If an exception occurs during rule creation
   */
  public void defineDRLSQWRLPhase1Rule(@NonNull String queryName, @NonNull String ruleName, @NonNull String ruleText)
    throws TargetSWRLRuleEngineException
  {
    this.phase1SQWRLRuleNames.add(ruleName);
    this.ruleName2SQWRLQueryNameMap.put(ruleName, queryName);

    if (this.knowledgePackagesAdditionRequired)
      defineDRLRule(ruleText);
  }

  /**
   * Define a Drools representation of a SWRL rule representing phase 2 of a SQWRL query. This method will be called by
   * {@link DroolsSQWRLQuery2DRLConverter}.
   *
   * @param queryName The name of the query
   * @param ruleName  The name of the rule
   * @param ruleText  The rule text
   * @throws TargetSWRLRuleEngineException If an exception occurs during rule creation
   */
  public void defineDRLSQWRLPhase2Rule(@NonNull String queryName, @NonNull String ruleName, @NonNull String ruleText)
    throws TargetSWRLRuleEngineException
  {
    this.phase2SQWRLRuleNames.add(ruleName);
    this.ruleName2SQWRLQueryNameMap.put(ruleName, queryName);

    if (this.knowledgePackagesAdditionRequired)
      defineDRLRule(ruleText);
  }

  private void resetKnowledgeSession()
  {
    if (this.knowledgeSession != null)
      this.knowledgeSession.dispose();

    this.knowledgeSession = this.knowledgeBase.newStatefulKnowledgeSession();
    this.knowledgeSession.setGlobal("invoker", this.builtInInvoker);
    this.knowledgeSession.setGlobal("inferrer", this.axiomInferrer);
    this.knowledgeSession.setGlobal("sqwrlInferrer", this.sqwrlCollectionInferrer);

    // Supply the inferrer with the knowledge session is so that it can insert new facts as inference is performed.
    this.axiomInferrer.reset(this.knowledgeSession);

    this.sqwrlCollectionInferrer.reset();
    this.assertedAndInferredOWLAxioms.clear();
    this.allSQWRLQueryNames.clear();
    this.activeSQWRLQueryNames.clear();
    this.phase1SQWRLRuleNames.clear();
    this.phase2SQWRLRuleNames.clear();
    this.ruleName2SQWRLQueryNameMap.clear();
    this.axiomConverter.reset();
    this.queryConverter.reset();
  }

  /**
   * Converts a Drools representation of an OWL axiom to an OWLAPI equivalent and pass it back to the bridge.
   */
  private void writeInferredOWLAxiomsToBridge() throws TargetSWRLRuleEngineException
  {
    try {
      for (A a : this.axiomInferrer.getInferredOWLAxioms()) {
        OWLAxiom axiom = a.extract(getDroolsOWLAxiomExtractor());
        getBridge().inferOWLAxiom(axiom);
      }
    } catch (SWRLRuleEngineBridgeException e) {
      throw new TargetSWRLRuleEngineException(
        "error writing inferred OWL axioms to bridge: " + (e.getMessage() != null ? e.getMessage() : ""), e);
    }
  }

  private static void addKnowledgePackages(@NonNull KnowledgeBase knowledgeBase,
    @NonNull KnowledgeBuilder knowledgeBuilder) throws TargetSWRLRuleEngineException
  {
    if (knowledgeBuilder.hasErrors())
      throw new TargetSWRLRuleEngineException(
        "error configuring Drools rule engine: " + knowledgeBuilder.getErrors().toString());

    try {
      knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());
    } catch (Exception e) {
      throw new TargetSWRLRuleEngineException(
        "error configuring Drools rule engine: " + (e.getMessage() != null ? e.getMessage() : ""), e);
    }
  }

  private static KnowledgeBaseConfiguration createKnowledgeBaseConfiguration()
  {
    KnowledgeBaseConfiguration config = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
    config.setProperty("drools.assertBehaviour", "equality");
    config.setProperty("drools.dialect.mvel.strict", "false");

    return config;
  }

  /**
   * Drools is supplied with all currently enabled SQWRL queries. Typically, only one query is active so we use an
   * agenda filter to ignore the ones that are not active.
   */
  private class SQWRLPhase1AgendaFilter implements AgendaFilter
  {
    @Override public boolean accept(@NonNull Activation activation)
    {
      String ruleName = activation.getRule().getName();

      if (DroolsSWRLRuleEngine.this.phase1SQWRLRuleNames.contains(ruleName)) {
        // A rule representing phase 1 of a SQWRL query
        if (DroolsSWRLRuleEngine.this.ruleName2SQWRLQueryNameMap.containsKey(ruleName)) {
          String sqwrlQueryName = DroolsSWRLRuleEngine.this.ruleName2SQWRLQueryNameMap.get(ruleName);
          return DroolsSWRLRuleEngine.this.activeSQWRLQueryNames.contains(sqwrlQueryName);
        } else
          throw new TargetSWRLRuleEngineInternalException(
            "phase 1 SQWRL rule " + ruleName + " not correctly recorded as query");
      } else
        return !DroolsSWRLRuleEngine.this.phase2SQWRLRuleNames.contains(ruleName);
    }
  }

  private class SQWRLPhase2AgendaFilter implements AgendaFilter
  {
    @Override public boolean accept(@NonNull Activation activation)
    {
      String ruleName = activation.getRule().getName();

      if (DroolsSWRLRuleEngine.this.phase2SQWRLRuleNames.contains(ruleName)) {
        // A rule representing phase 1 of a SQWRL query
        if (DroolsSWRLRuleEngine.this.ruleName2SQWRLQueryNameMap.containsKey(ruleName)) {
          String sqwrlQueryName = DroolsSWRLRuleEngine.this.ruleName2SQWRLQueryNameMap.get(ruleName);
          return DroolsSWRLRuleEngine.this.activeSQWRLQueryNames.contains(sqwrlQueryName);
        } else
          throw new TargetSWRLRuleEngineInternalException(
            "phase 2 rule " + ruleName + " not correctly recorded as SQWRL query");
      } else
        return false;
    }
  }

  @NonNull private SWRLRuleEngineBridge getBridge()
  {
    return this.bridge;
  }

  @NonNull private DroolsOWLAxiom2AConverter getDroolsOWLAxiomConverter()
  {
    return this.axiomConverter;
  }

  @NonNull private DroolsOWLAxiomExtractor getDroolsOWLAxiomExtractor()
  {
    return this.axiomExtractor;
  }

  @NonNull private DroolsSQWRLQuery2DRLConverter getDroolsSQWRLQueryConverter()
  {
    return this.queryConverter;
  }
}
