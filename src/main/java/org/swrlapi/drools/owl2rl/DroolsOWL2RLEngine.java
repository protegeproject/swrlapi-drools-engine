package org.swrlapi.drools.owl2rl;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.core.DroolsRuleDefinition;
import org.swrlapi.owl2rl.AbstractOWL2RLEngine;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Class providing a Drools implementation of an OWL 2 RL-based reasoner.
 * <p>
 * This engine is created by a {@link org.swrlapi.drools.core.DroolsSWRLRuleEngine}. The OWL 2 RL rules are defined in
 * the {@link org.swrlapi.drools.owl2rl.DroolsOWL2RLRules} class. All axioms inferred by Drools rules are handled by a
 * {@link org.swrlapi.drools.reasoner.DefaultDroolsOWLAxiomHandler}.
 *
 * @see org.swrlapi.owl2rl.AbstractOWL2RLEngine
 * @see org.swrlapi.drools.owl2rl.DroolsOWL2RLRules
 * @see org.swrlapi.drools.core.DroolsSWRLRuleEngine
 * @see org.swrlapi.drools.reasoner.DefaultDroolsOWLAxiomHandler
 */
public class DroolsOWL2RLEngine extends AbstractOWL2RLEngine
{
  @NonNull private final DroolsOWL2RLRules droolsOWL2RLRules;

  public DroolsOWL2RLEngine(@NonNull OWL2RLPersistenceLayer persistenceLayer)
  {
    super(persistenceLayer, generateUnsupportedRules(), generatePermanentlyOnRules(), generateGroupedRuleSets());

    this.droolsOWL2RLRules = new DroolsOWL2RLRules();

    this.droolsOWL2RLRules.defineRules();
  }

  @NonNull public Set<DroolsRuleDefinition> getEnabledRuleDefinitions()
  {
    Set<DroolsRuleDefinition> enabledRuleDefinitions = new HashSet<>();

    getEnabledRules().stream().filter(this.droolsOWL2RLRules::hasRule)
        .forEach(rule -> enabledRuleDefinitions.addAll(this.droolsOWL2RLRules.getRules(rule)));

    return enabledRuleDefinitions;
  }

  /**
   * These are rules that are always enabled and that cannot be disabled.
   */
  @NonNull private static Set<OWL2RLRule> generatePermanentlyOnRules()
  {
    return EnumSet.of(OWL2RLRule.CLS_THING, OWL2RLRule.CLS_NOTHING1, OWL2RLRule.CLS_NOTHING2, OWL2RLRule.DT_TYPE1,
        OWL2RLRule.DT_TYPE2, OWL2RLRule.DT_EQ, OWL2RLRule.DT_DIFF);
  }

  /**
   * These are rules that must be enabled or disabled in groups.
   */
  @NonNull private static Set<Set<OWL2RLRule>> generateGroupedRuleSets()
  {
    Set<Set<OWL2RLRule>> groupedRuleSets = new HashSet<>();

    groupedRuleSets.add(EnumSet.of(OWL2RLRule.EQ_DIFF1, OWL2RLRule.EQ_DIFF2, OWL2RLRule.EQ_DIFF3));
    groupedRuleSets.add(EnumSet.of(OWL2RLRule.PRP_EQP1, OWL2RLRule.PRP_EQP2, OWL2RLRule.EQ_REP_P));
    groupedRuleSets.add(EnumSet.of(OWL2RLRule.PRP_PDW, OWL2RLRule.PRP_ADP));
    groupedRuleSets.add(EnumSet.of(OWL2RLRule.CAX_ADC, OWL2RLRule.CAX_DW));

    return groupedRuleSets;
  }

  @NonNull private static Set<OWL2RLRule> generateUnsupportedRules()
  {
    return EnumSet.of(OWL2RLRule.PRP_SPO2, OWL2RLRule.PRP_KEY, OWL2RLRule.DT_NOT_TYPE);

    // TODO OWL2RLRule.PRP_SPO2 - Property chains
    // See DroolsOWLClassExpressionConverter conversion of OWLObjectOnOf for pairwise extraction
    // T(?p, owl:propertyChainAxiom, ?x) LIST[c?x, ?p1,...,?pn] T(?u1, ?p1, ?u2) T(?u2, ?p2, ?u3) ...
    // T( ? un,?pn,?un + 1) -> T(?u1, ?p, ?un+1)

    // TODO OWL2RLRule.PRP_KEY - Keys
    // T(?c, owl:hasKey, ?u) LIST[?u, ?p1, ..., ?pn] T(?x, rdf:type, ?c) T(?x, ?p1, ?z1) ...
    // T(?x, ?pn, ?zn) T(?y, rdf:type, ?c) T(?y, ?p1, ?z1) ...
    // T(?y, ?pn, ?zn) -> T(?x, owl:sameAs, ?y)

    // Table 8
    // TODO DT_NOT_TYPE - Basically verify that the raw value of each literal is valid for its datatype
    // T(lt, rdf:type, dt) -> false
    // "For each literal lt and each datatype dt supported in OWL 2 RL such that the data value of lt is not
    // contained in the value space of dt." L(l., dt) ^ notValid(l, dt) -> explode?
  }
}
