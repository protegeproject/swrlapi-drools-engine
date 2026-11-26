package org.swrlapi.drools.reasoner;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.AxiomNotInProfileException;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.ClassExpressionNotInProfileException;
import org.semanticweb.owlapi.reasoner.FreshEntitiesException;
import org.semanticweb.owlapi.reasoner.InconsistentOntologyException;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.ReasonerInterruptedException;
import org.semanticweb.owlapi.reasoner.TimeOutException;
import org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException;
import org.semanticweb.owlapi.reasoner.impl.OWLClassNode;
import org.semanticweb.owlapi.reasoner.impl.OWLClassNodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLDataPropertyNode;
import org.semanticweb.owlapi.reasoner.impl.OWLDataPropertyNodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLNamedIndividualNode;
import org.semanticweb.owlapi.reasoner.impl.OWLNamedIndividualNodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLObjectPropertyNode;
import org.semanticweb.owlapi.reasoner.impl.OWLObjectPropertyNodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLReasonerBase;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.Version;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.drools.converters.oo.DroolsOWLClassExpressionHandler;
import org.swrlapi.drools.converters.oo.DroolsOWLEntity2OEConverter;
import org.swrlapi.drools.converters.oo.DroolsOWLIndividual2IConverter;
import org.swrlapi.drools.converters.oo.DroolsOWLPropertyExpressionHandler;
import org.swrlapi.drools.owl.classes.C;
import org.swrlapi.drools.owl.classes.CE;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.drools.owl.literals.L;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.DPE;
import org.swrlapi.drools.owl.properties.OPE;
import org.swrlapi.factory.OWLLiteralFactory;

import java.util.HashSet;
import java.util.Collections;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.FreshEntityPolicy;
import org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy;
import org.semanticweb.owlapi.reasoner.ReasonerInternalException;
import org.swrlapi.bridge.SWRLBridge;
import org.swrlapi.drools.core.DroolsSWRLRuleEngine;
import org.kie.api.runtime.KieSession;
import java.util.Set;

/**
 * A Drools-based reasoner interface implementing the OWLAPI {@link org.semanticweb.owlapi.reasoner.OWLReasoner}
 * interface.
 *
 * @see org.semanticweb.owlapi.reasoner.OWLReasoner
 */
public class DroolsOWLReasoner extends OWLReasonerBase implements OWLReasoner
{
  private static final String REASONER_NAME = "DroolsOWL2RLReasoner";
  private static final Version REASONER_VERSION = new Version(0, 0, 0, 0);

  private static final String OWLThingPrefixedName = OWLRDFVocabulary.OWL_THING.getPrefixedName();
  private static final String OWLNothingPrefixedName = OWLRDFVocabulary.OWL_NOTHING.getPrefixedName();
  private static final String OWLTopObjectPropertyPrefixedName = OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY
    .getPrefixedName();
  private static final String OWLBottomObjectPropertyPrefixedName = OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY
    .getPrefixedName();
  private static final String OWLTopDataPropertyPrefixedName = OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getPrefixedName();
  private static final String OWLBottomDataPropertyPrefixedName = OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY
    .getPrefixedName();

  private Materialization state;
  private boolean prepared;
  private volatile boolean interrupted;
  private boolean disposed;
  private long started;
  private final Set<OWLClassExpression> queryExpressions = new HashSet<>();

  public DroolsOWLReasoner(OWLOntology rootOntology, OWLReasonerConfiguration configuration, BufferingMode bufferingMode)
  {
    super(rootOntology, configuration, bufferingMode);
  }

  /**
   * Compatibility constructor for clients of the earlier API. Converter state is now owned by
   * an isolated ontology snapshot, so the supplied handlers and factories are no longer retained.
   * @deprecated use the ontology/configuration/buffering-mode constructor
   */
  @Deprecated
  public DroolsOWLReasoner(OWLOntology rootOntology, OWLReasonerConfiguration configuration,
    BufferingMode bufferingMode, OWLLiteralFactory literalFactory, DroolsOWLAxiomHandler axiomHandler,
    DroolsOWLClassExpressionHandler classHandler, DroolsOWLPropertyExpressionHandler propertyHandler,
    DroolsOWLIndividual2IConverter individualConverter, DroolsOWLEntity2OEConverter entityConverter)
  {
    this(rootOntology, configuration, bufferingMode);
  }

  /** An isolated materialization, owned and disposed by this reasoner. */
  public static final class Materialization implements AutoCloseable
  {
    final DroolsOWLAxiomHandler axioms;
    final DroolsOWLClassExpressionHandler classes;
    final DroolsOWLPropertyExpressionHandler properties;
    final DroolsOWLIndividual2IConverter individuals;
    final DroolsOWLEntity2OEConverter entities;
    final SWRLBridge bridge;
    final Set<OWLAxiom> closure;
    private final KieSession session;

    public Materialization(DroolsOWLAxiomHandler axioms, DroolsOWLClassExpressionHandler classes,
      DroolsOWLPropertyExpressionHandler properties, DroolsOWLIndividual2IConverter individuals,
      DroolsOWLEntity2OEConverter entities, SWRLBridge bridge, Set<OWLAxiom> closure, KieSession session)
    {
      this.axioms = axioms;
      this.classes = classes;
      this.properties = properties;
      this.individuals = individuals;
      this.entities = entities;
      this.bridge = bridge;
      this.closure = closure;
      this.session = session;
    }

    @Override public void close() { this.session.dispose(); }
  }

  @NonNull @Override public String getReasonerName()
  {
    return REASONER_NAME;
  }

  @NonNull @Override public Version getReasonerVersion()
  {
    return REASONER_VERSION;
  }

  @Override public void interrupt()
  {
    this.interrupted = true;
  }

  @Override public void precomputeInferences(InferenceType... inferenceTypes)
    throws ReasonerInterruptedException, TimeOutException, InconsistentOntologyException
  {
    prepareReasoner();
  }

  @Override public boolean isPrecomputed(InferenceType inferenceType)
  {
    return this.prepared && getPrecomputableInferenceTypes().contains(inferenceType);
  }

  @NonNull @Override public Set<@NonNull InferenceType> getPrecomputableInferenceTypes()
  {
    return CollectionFactory.createSet(InferenceType.CLASS_HIERARCHY, InferenceType.OBJECT_PROPERTY_HIERARCHY,
      InferenceType.DATA_PROPERTY_HIERARCHY, InferenceType.CLASS_ASSERTIONS, InferenceType.OBJECT_PROPERTY_ASSERTIONS,
      InferenceType.DATA_PROPERTY_ASSERTIONS, InferenceType.SAME_INDIVIDUAL, InferenceType.DIFFERENT_INDIVIDUALS);
  }

  @Override public boolean isConsistent() throws ReasonerInterruptedException, TimeOutException
  {
    ensurePrepared();
    return !getDroolsOWLAxiomHandler().isInconsistent();
  }

  @Override public boolean isSatisfiable(@NonNull OWLClassExpression classExpression)
    throws ReasonerInterruptedException, TimeOutException, ClassExpressionNotInProfileException, FreshEntitiesException,
    InconsistentOntologyException
  {
    ensureExpression(classExpression);
    ensureConsistent();
    if (classExpression.isOWLNothing())
      return false;
    String id = resolveCE(classExpression).getceid();
    if (getDroolsOWLAxiomHandler().getEquivalentClasses(OWLNothingPrefixedName).contains(id)
      || getDroolsOWLAxiomHandler().strictSubClassOf(id, OWLNothingPrefixedName))
      return false;
    // A class below two disjoint classes is empty even if no individual instantiates it.
    Set<String> parents = getDroolsOWLAxiomHandler().getSuperClasses(id, false);
    parents.add(id);
    for (String parent : parents)
      if (!Collections.disjoint(parents, getDroolsOWLAxiomHandler().getDisjointClasses(parent)))
        return false;
    return true;
  }

  @NonNull @Override public Node<@NonNull OWLClass> getUnsatisfiableClasses()
    throws ReasonerInterruptedException, TimeOutException, InconsistentOntologyException
  {
    ensureConsistent();
    return getBottomClassNode();
  }

  @Override public boolean isEntailed(@NonNull OWLAxiom axiom)
    throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException,
    AxiomNotInProfileException, FreshEntitiesException, InconsistentOntologyException
  {
    ensureConsistent();
    if (!isEntailmentCheckingSupported(axiom.getAxiomType()))
      throw new UnsupportedEntailmentTypeException(axiom);
    for (OWLAxiom known : this.state.closure)
      if (known.equalsIgnoreAnnotations(axiom))
        return true;
    if (axiom instanceof OWLSubClassOfAxiom) {
      OWLSubClassOfAxiom sub = (OWLSubClassOfAxiom)axiom;
      return sub.getSubClass().equals(sub.getSuperClass()) || sub.getSuperClass().isOWLThing()
        || sub.getSubClass().isOWLNothing();
    }
    return false;
  }

  @Override public boolean isEntailed(@NonNull Set<? extends @NonNull OWLAxiom> axioms)
    throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException,
    AxiomNotInProfileException, FreshEntitiesException, InconsistentOntologyException
  {
    ensureConsistent();
    for (OWLAxiom axiom : axioms) {
      if (!isEntailed(axiom)) {
        return false;
      }
    }
    return true;
  }

  @Override public boolean isEntailmentCheckingSupported(AxiomType<?> axiomType)
  {
    return axiomType.equals(AxiomType.SUBCLASS_OF) || axiomType.equals(AxiomType.CLASS_ASSERTION)
      || axiomType.equals(AxiomType.OBJECT_PROPERTY_ASSERTION) || axiomType.equals(AxiomType.DATA_PROPERTY_ASSERTION)
      || axiomType.equals(AxiomType.SAME_INDIVIDUAL) || axiomType.equals(AxiomType.DIFFERENT_INDIVIDUALS)
      || axiomType.equals(AxiomType.EQUIVALENT_CLASSES) || axiomType.equals(AxiomType.DISJOINT_CLASSES)
      || axiomType.equals(AxiomType.SUB_OBJECT_PROPERTY) || axiomType.equals(AxiomType.SUB_DATA_PROPERTY)
      || axiomType.equals(AxiomType.EQUIVALENT_OBJECT_PROPERTIES) || axiomType.equals(AxiomType.EQUIVALENT_DATA_PROPERTIES)
      || axiomType.equals(AxiomType.OBJECT_PROPERTY_DOMAIN) || axiomType.equals(AxiomType.OBJECT_PROPERTY_RANGE)
      || axiomType.equals(AxiomType.DATA_PROPERTY_DOMAIN);
  }

  @NonNull @Override public Node<@NonNull OWLClass> getTopClassNode()
  {
    ensureConsistent();
    Set<@NonNull OWLClass> classes = new HashSet<>();
    classes.add(getOWLDataFactory().getOWLThing());

    for (String classID : getDroolsOWLAxiomHandler().getEquivalentClasses(OWLThingPrefixedName)) {
      OWLClassExpression expression = resolveOWLClassExpression(classID);
      if (!expression.isAnonymous())
        classes.add(expression.asOWLClass());
    }
    return new OWLClassNode(classes);
  }

  @NonNull @Override public Node<@NonNull OWLClass> getBottomClassNode()
  {
    ensureConsistent();
    Set<@NonNull OWLClass> classes = new HashSet<>();
    classes.add(getOWLDataFactory().getOWLNothing());

    for (String classID : getDroolsOWLAxiomHandler().getEquivalentClasses(OWLNothingPrefixedName)) {
      OWLClassExpression expression = resolveOWLClassExpression(classID);
      if (!expression.isAnonymous())
        classes.add(expression.asOWLClass());
    }
    for (OWLClass cls : namedClasses())
      if (!isSatisfiable(cls))
        classes.add(cls);
    return new OWLClassNode(classes);
  }

  @NonNull @Override public NodeSet<@NonNull OWLClass> getSubClasses(@NonNull OWLClassExpression classExpression,
    boolean direct)
    throws ReasonerInterruptedException, TimeOutException, FreshEntitiesException, InconsistentOntologyException,
    ClassExpressionNotInProfileException
  {
    ensureConsistent();
    OWLClassNodeSet ns = new OWLClassNodeSet();

    {
      ensureExpression(classExpression);
      CE c = resolveCE(classExpression);
      String classID = c.getceid();
      for (String subClassID : getDroolsOWLAxiomHandler().getSubClasses(classID, direct)) {
        OWLClassExpression subClassExpression = resolveOWLClassExpression(subClassID);
        Node<OWLClass> cNode = getEquivalentClasses(subClassExpression);
        ns.addNode(cNode);
      }
    }
    return ns;
  }

  @NonNull @Override public NodeSet<@NonNull OWLClass> getSuperClasses(@NonNull OWLClassExpression classExpression,
    boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException,
    ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    OWLClassNodeSet ns = new OWLClassNodeSet();

    {
      ensureExpression(classExpression);
      CE c = resolveCE(classExpression);
      String classID = c.getceid();
      for (String superClassID : getDroolsOWLAxiomHandler().getSuperClasses(classID, direct)) {
        OWLClassExpression subClassExpression = resolveOWLClassExpression(superClassID);
        Node<OWLClass> cNode = getEquivalentClasses(subClassExpression);
        ns.addNode(cNode);
      }
    }
    return ns;
  }

  @NonNull @Override public Node<@NonNull OWLClass> getEquivalentClasses(OWLClassExpression classExpression)
    throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException,
    ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    Set<@NonNull OWLClass> classes = new HashSet<>();

    ensureExpression(classExpression);
    if (!classExpression.isAnonymous())
      classes.add(classExpression.asOWLClass());
    CE ce = resolveCE(classExpression);
    String classID = ce.getceid();
    for (String equivalentClassID : getDroolsOWLAxiomHandler().getEquivalentClasses(classID)) {
      OWLClassExpression equivalent = resolveOWLClassExpression(equivalentClassID);
      if (!equivalent.isAnonymous())
        classes.add(equivalent.asOWLClass());
    }
    return new OWLClassNode(classes);
  }

  @NonNull @Override public NodeSet<@NonNull OWLClass> getDisjointClasses(@NonNull OWLClassExpression classExpression)
    throws ReasonerInterruptedException, TimeOutException, FreshEntitiesException, InconsistentOntologyException
  {
    ensureConsistent();
    OWLClassNodeSet nodeSet = new OWLClassNodeSet();

    {
      ensureExpression(classExpression);
      CE c = resolveCE(classExpression);
      String classID = c.getceid();
      for (String disjointClassID : getDroolsOWLAxiomHandler().getDisjointClasses(classID)) {
        OWLClassExpression disjointClassExpression = resolveOWLClassExpression(disjointClassID);
        Node<OWLClass> cNode = getEquivalentClasses(disjointClassExpression);
        nodeSet.addNode(cNode);
      }
    }
    return nodeSet;
  }

  @NonNull @Override public Node<@NonNull OWLObjectPropertyExpression> getTopObjectPropertyNode()
  {
    ensureConsistent();
    Set<@NonNull OWLObjectPropertyExpression> properties = new HashSet<>();
    properties.add(getOWLDataFactory().getOWLTopObjectProperty());

    for (String propertyID : getDroolsOWLAxiomHandler()
      .getEquivalentObjectProperties(resolveOPE(getOWLDataFactory().getOWLTopObjectProperty()).getid())) {
      OWLObjectPropertyExpression property = resolveOWLObjectPropertyExpression(propertyID);
      properties.add(property);
    }
    return new OWLObjectPropertyNode(properties);
  }

  @NonNull @Override public Node<@NonNull OWLObjectPropertyExpression> getBottomObjectPropertyNode()
  {
    ensureConsistent();
    Set<@NonNull OWLObjectPropertyExpression> properties = new HashSet<>();
    properties.add(getOWLDataFactory().getOWLBottomObjectProperty());

    for (String propertyID : getDroolsOWLAxiomHandler()
      .getEquivalentObjectProperties(resolveOPE(getOWLDataFactory().getOWLBottomObjectProperty()).getid())) {
      OWLObjectPropertyExpression property = resolveOWLObjectPropertyExpression(propertyID);
      properties.add(property);
    }
    return new OWLObjectPropertyNode(properties);
  }

  @NonNull @Override public NodeSet<@NonNull OWLObjectPropertyExpression> getSubObjectProperties(
    @NonNull OWLObjectPropertyExpression objectPropertyExpression, boolean direct)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    OWLObjectPropertyNodeSet ns = new OWLObjectPropertyNodeSet();

    ensurePrepared();
    OPE pe = resolveOPE(objectPropertyExpression);
    String propertyID = pe.getid();
    for (String subPropertyID : getDroolsOWLAxiomHandler().getSubObjectProperties(propertyID, direct)) {
      OWLObjectPropertyExpression subPropertyExpression = resolveOWLObjectPropertyExpression(subPropertyID);
      Node<OWLObjectPropertyExpression> opNode = getEquivalentObjectProperties(subPropertyExpression);
      ns.addNode(opNode);
    }
    return ns;
  }

  @NonNull @Override public NodeSet<@NonNull OWLObjectPropertyExpression> getSuperObjectProperties(
    @NonNull OWLObjectPropertyExpression objectPropertyExpression, boolean direct)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    OWLObjectPropertyNodeSet ns = new OWLObjectPropertyNodeSet();

    ensurePrepared();
    OPE pe = resolveOPE(objectPropertyExpression);
    String propertyID = pe.getid();
    for (String superPropertyID : getDroolsOWLAxiomHandler().getSuperObjectProperties(propertyID, direct)) {
      OWLObjectPropertyExpression superPropertyExpression = resolveOWLObjectPropertyExpression(superPropertyID);
      Node<OWLObjectPropertyExpression> opNode = getEquivalentObjectProperties(superPropertyExpression);
      ns.addNode(opNode);
    }
    return ns;
  }

  @NonNull @Override public Node<@NonNull OWLObjectPropertyExpression> getEquivalentObjectProperties(
    @NonNull OWLObjectPropertyExpression objectPropertyExpression)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    Set<@NonNull OWLObjectPropertyExpression> properties = new HashSet<>();

    properties.add(objectPropertyExpression);
    if (objectPropertyExpression.isAnonymous()) {
      for (String inverseID : getDroolsOWLAxiomHandler().getInverseObjectProperties(
        resolveOPE(objectPropertyExpression.getNamedProperty()).getid()))
        properties.addAll(getEquivalentObjectProperties(resolveOWLObjectPropertyExpression(inverseID)).getEntities());
    }
    OPE ope = resolveOPE(objectPropertyExpression);
    String propertyID = ope.getid();
    for (String equivalentPropertyID : getDroolsOWLAxiomHandler().getEquivalentObjectProperties(propertyID)) {
      OWLObjectPropertyExpression p = resolveOWLObjectPropertyExpression(equivalentPropertyID);
      properties.add(p);
    }
    return new OWLObjectPropertyNode(properties);
  }

  @NonNull @Override public NodeSet<@NonNull OWLObjectPropertyExpression> getDisjointObjectProperties(
    @NonNull OWLObjectPropertyExpression objectPropertyExpression)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    OWLObjectPropertyNodeSet nodeSet = new OWLObjectPropertyNodeSet();

    ensurePrepared();
    OPE ope = resolveOPE(objectPropertyExpression);
    String propertyID = ope.getid();
    for (String disjointPropertyID : getDroolsOWLAxiomHandler().getDisjointObjectProperties(propertyID)) {
      OWLObjectPropertyExpression disjointProperty = resolveOWLObjectPropertyExpression(disjointPropertyID);
      Node<OWLObjectPropertyExpression> opNode = getEquivalentObjectProperties(disjointProperty);
      nodeSet.addNode(opNode);
    }
    return nodeSet;
  }

  @NonNull @Override public Node<@NonNull OWLObjectPropertyExpression> getInverseObjectProperties(
    @NonNull OWLObjectPropertyExpression objectPropertyExpression)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    Set<OWLObjectPropertyExpression> inverses = new HashSet<>();
    inverses.add(objectPropertyExpression.getInverseProperty());
    for (String id : getDroolsOWLAxiomHandler().getInverseObjectProperties(resolveOPE(objectPropertyExpression).getid()))
      inverses.addAll(getEquivalentObjectProperties(resolveOWLObjectPropertyExpression(id)).getEntities());
    return new OWLObjectPropertyNode(inverses);
  }

  @NonNull @Override public NodeSet<@NonNull OWLClass> getObjectPropertyDomains(
    @NonNull OWLObjectPropertyExpression objectPropertyExpression, boolean direct)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    OWLClassNodeSet ns = new OWLClassNodeSet();
    OPE ope = resolveOPE(objectPropertyExpression);
    String propertyID = ope.getid();
    for (String classID : getDroolsOWLAxiomHandler().getObjectPropertyDomains(propertyID, direct)) {
      OWLClassExpression domainClass = resolveOWLClassExpression(classID);
      Node<OWLClass> cNode = getEquivalentClasses(domainClass);
      ns.addNode(cNode);
    }
    return ns;
  }

  @NonNull @Override public NodeSet<@NonNull OWLClass> getObjectPropertyRanges(
    @NonNull OWLObjectPropertyExpression objectPropertyExpression, boolean direct)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    OWLClassNodeSet ns = new OWLClassNodeSet();
    OPE ope = resolveOPE(objectPropertyExpression);
    String propertyID = ope.getid();
    for (String classID : getDroolsOWLAxiomHandler().getObjectPropertyRanges(propertyID, direct)) {
      OWLClassExpression rangeClass = resolveOWLClassExpression(classID);
      Node<OWLClass> cNode = getEquivalentClasses(rangeClass);
      ns.addNode(cNode);
    }
    return ns;
  }

  @NonNull @Override public Node<@NonNull OWLDataProperty> getTopDataPropertyNode()
  {
    ensureConsistent();
    Set<@NonNull OWLDataProperty> properties = new HashSet<>();
    properties.add(getOWLDataFactory().getOWLTopDataProperty());

    for (String propertyID : getDroolsOWLAxiomHandler().getEquivalentDataProperties(resolveDP(getOWLDataFactory().getOWLTopDataProperty()).getid())) {
      OWLDataProperty property = resolveOWLDataProperty(propertyID);
      properties.add(property);
    }
    return new OWLDataPropertyNode(properties);
  }

  @NonNull @Override public Node<@NonNull OWLDataProperty> getBottomDataPropertyNode()
  {
    ensureConsistent();
    Set<@NonNull OWLDataProperty> properties = new HashSet<>();
    properties.add(getOWLDataFactory().getOWLBottomDataProperty());

    for (String propertyID : getDroolsOWLAxiomHandler()
      .getEquivalentDataProperties(resolveDP(getOWLDataFactory().getOWLBottomDataProperty()).getid())) {
      OWLDataProperty property = resolveOWLDataProperty(propertyID);
      properties.add(property);
    }
    return new OWLDataPropertyNode(properties);
  }

  @NonNull @Override public NodeSet<@NonNull OWLDataProperty> getSubDataProperties(
    @NonNull OWLDataProperty dataProperty, boolean direct)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    OWLDataPropertyNodeSet ns = new OWLDataPropertyNodeSet();

    ensurePrepared();
    DPE dp = resolveDP(dataProperty);
    String propertyID = dp.getid();
    for (String subPropertyID : getDroolsOWLAxiomHandler().getSubDataProperties(propertyID, direct)) {
      OWLDataProperty subProperty = resolveOWLDataProperty(subPropertyID);
      Node<OWLDataProperty> opNode = getEquivalentDataProperties(subProperty);
      ns.addNode(opNode);
    }
    return ns;
  }

  @NonNull @Override public NodeSet<@NonNull OWLDataProperty> getSuperDataProperties(
    @NonNull OWLDataProperty dataProperty, boolean direct)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    OWLDataPropertyNodeSet ns = new OWLDataPropertyNodeSet();

    ensurePrepared();
    DPE dp = resolveDP(dataProperty);
    String propertyID = dp.getid();
    for (String subPropertyID : getDroolsOWLAxiomHandler().getSuperDataProperties(propertyID, direct)) {
      OWLDataProperty subProperty = resolveOWLDataProperty(subPropertyID);
      Node<OWLDataProperty> opNode = getEquivalentDataProperties(subProperty);
      ns.addNode(opNode);
    }
    return ns;
  }

  @NonNull @Override public Node<@NonNull OWLDataProperty> getEquivalentDataProperties(
    @NonNull OWLDataProperty dataProperty)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    Set<@NonNull OWLDataProperty> properties = new HashSet<>();

    properties.add(dataProperty);
    DPE dp = resolveDP(dataProperty);
    String propertyID = dp.getid();
    for (String equivalentPropertyID : getDroolsOWLAxiomHandler().getEquivalentDataProperties(propertyID)) {
      OWLDataProperty p = resolveOWLDataProperty(equivalentPropertyID);
      properties.add(p);
    }
    return new OWLDataPropertyNode(properties);
  }

  @NonNull @Override public NodeSet<@NonNull OWLDataProperty> getDisjointDataProperties(
    @NonNull OWLDataPropertyExpression dataPropertyExpression)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    OWLDataPropertyNodeSet nodeSet = new OWLDataPropertyNodeSet();

    ensurePrepared();
    DPE dpe = resolveDPE(dataPropertyExpression);
    String propertyID = dpe.getid();
    for (String disjointPropertyID : getDroolsOWLAxiomHandler().getDisjointDataProperties(propertyID)) {
      OWLDataProperty disjointProperty = resolveOWLDataProperty(disjointPropertyID);
      Node<OWLDataProperty> dpNode = getEquivalentDataProperties(disjointProperty);
      nodeSet.addNode(dpNode);
    }
    return nodeSet;
  }

  @NonNull @Override public NodeSet<OWLClass> getDataPropertyDomains(@NonNull OWLDataProperty dataProperty,
    boolean direct)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    OWLClassNodeSet ns = new OWLClassNodeSet();
    DPE dp = resolveDP(dataProperty);
    String propertyID = dp.getid();
    for (String classID : getDroolsOWLAxiomHandler().getDataPropertyDomains(propertyID, direct)) {
      OWLClassExpression domainClass = resolveOWLClassExpression(classID);
      Node<OWLClass> cNode = getEquivalentClasses(domainClass);
      ns.addNode(cNode);
    }
    return ns;
  }

  @NonNull @Override public NodeSet<OWLClass> getTypes(@NonNull OWLNamedIndividual namedIndividual, boolean direct)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    Set<OWLClass> types = new HashSet<>();
    types.add(getOWLDataFactory().getOWLThing());
    String individual = resolveI(namedIndividual).getid();
    for (OWLClass cls : namedClasses())
      if (getDroolsOWLAxiomHandler().getClassAssertions(resolveC(cls).getceid()).contains(individual))
        types.add(cls);
    if (direct) {
      Set<OWLClass> all = new HashSet<>(types);
      types.removeIf(parent -> all.stream().anyMatch(child -> getDroolsOWLAxiomHandler()
        .strictSubClassOf(resolveC(child).getceid(), resolveC(parent).getceid())));
    }
    OWLClassNodeSet result = new OWLClassNodeSet();
    for (OWLClass type : types)
      result.addNode(getEquivalentClasses(type));
    return result;
  }

  @NonNull @Override public NodeSet<OWLNamedIndividual> getInstances(@NonNull OWLClassExpression classExpression,
    boolean direct) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException,
    ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    ensureExpression(classExpression);
    OWLNamedIndividualNodeSet result = new OWLNamedIndividualNodeSet();
    String classID = resolveCE(classExpression).getceid();
    Set<String> individuals = new HashSet<>(getDroolsOWLAxiomHandler().getClassAssertions(classID));
    if (classExpression.isOWLThing())
      for (OWLNamedIndividual individual : namedIndividuals())
        individuals.add(resolveI(individual).getid());
    for (String id : individuals) {
      OWLNamedIndividual individual = resolveOWLNamedIndividual(id);
      if (!direct || isDirectInstance(individual, classID))
        addIndividual(result, individual);
    }
    return result;
  }

  @NonNull @Override public NodeSet<OWLNamedIndividual> getObjectPropertyValues(
    @NonNull OWLNamedIndividual namedIndividual, @NonNull OWLObjectPropertyExpression owlObjectPropertyExpression)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    OWLNamedIndividualNodeSet ns = new OWLNamedIndividualNodeSet();
    if (owlObjectPropertyExpression.isOWLTopObjectProperty()) {
      for (OWLNamedIndividual individual : namedIndividuals())
        addIndividual(ns, individual);
      return ns;
    }
    if (owlObjectPropertyExpression.isAnonymous()) {
      String target = resolveI(namedIndividual).getid();
      String namedProperty = resolveOPE(owlObjectPropertyExpression.getNamedProperty()).getid();
      for (java.util.Map.Entry<String, Set<String>> entry : getDroolsOWLAxiomHandler().getObjectPropertyAssertions(namedProperty).entrySet())
        if (entry.getValue().contains(target))
          addIndividual(ns, resolveOWLNamedIndividual(entry.getKey()));
      return ns;
    }
    I i = resolveI(namedIndividual);
    OPE ope = resolveOPE(owlObjectPropertyExpression);
    String individualID = i.getid();
    String propertyID = ope.getid();
    for (String valueIndividualID : getDroolsOWLAxiomHandler()
      .getObjectPropertyValuesForIndividual(individualID, propertyID)) {
      OWLNamedIndividual valueIndividual = resolveOWLNamedIndividual(valueIndividualID);
      addIndividual(ns, valueIndividual);
    }
    return ns;
  }

  @NonNull @Override public Set<@NonNull OWLLiteral> getDataPropertyValues(@NonNull OWLNamedIndividual namedIndividual,
    OWLDataProperty owlDataProperty)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    Set<@NonNull OWLLiteral> values = new HashSet<>();
    I i = resolveI(namedIndividual);
    DPE dp = resolveDP(owlDataProperty);
    String individualID = i.getid();
    String propertyID = dp.getid();
    for (L l : getDroolsOWLAxiomHandler().getDataPropertyValuesForIndividual(individualID, propertyID)) {
      OWLLiteral literal = l2OWLLiteral(l);
      values.add(literal);
    }
    return values;
  }

  @NonNull @Override public Node<@NonNull OWLNamedIndividual> getSameIndividuals(
    @NonNull OWLNamedIndividual namedIndividual)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    Set<@NonNull OWLNamedIndividual> individuals = new HashSet<>();
    individuals.add(namedIndividual);
    I i = resolveI(namedIndividual);
    String individualID = i.getid();
    for (String sameIndividualID : getDroolsOWLAxiomHandler().getSameIndividual(individualID)) {
      OWLNamedIndividual individual = resolveOWLNamedIndividual(sameIndividualID);
      individuals.add(individual);
    }
    return new OWLNamedIndividualNode(individuals);
  }

  @NonNull @Override public NodeSet<OWLNamedIndividual> getDifferentIndividuals(OWLNamedIndividual namedIndividual)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensureConsistent();
    OWLNamedIndividualNodeSet ns = new OWLNamedIndividualNodeSet();
    I i = resolveI(namedIndividual);
    String individualID = i.getid();
    for (String sameIndividualID : getDroolsOWLAxiomHandler().getDifferentIndividuals(individualID)) {
      OWLNamedIndividual sameIndividual = resolveOWLNamedIndividual(sameIndividualID);
      addIndividual(ns, sameIndividual);
    }
    return ns;
  }

  public synchronized void prepareReasoner() throws ReasonerInterruptedException, TimeOutException
  {
    if (this.disposed)
      throw new IllegalStateException("Reasoner has been disposed");
    throwExceptionIfInterrupted();
    this.started = System.nanoTime();
    if (this.state != null) {
      this.state.close();
      this.state = null;
    }
    this.prepared = false;
    Materialization materialized = DroolsSWRLRuleEngine.materialize(new HashSet<>(getReasonerAxioms()),
      this.queryExpressions, this::checkCancellation);
    try {
      checkCancellation();
      this.state = materialized;
      this.prepared = true;
    } catch (RuntimeException e) {
      materialized.close();
      throw e;
    }
  }

  @Override protected void handleChanges(Set<OWLAxiom> additions, Set<OWLAxiom> removals)
  {
    this.prepared = false;
  }

  public boolean isDisposed() { return this.disposed; }

  @Override public synchronized void dispose()
  {
    if (!this.disposed) {
      super.dispose();
      if (this.state != null) {
        this.state.close();
        this.state = null;
      }
      this.prepared = false;
      this.disposed = true;
    }
  }

  private void ensurePrepared()
  {
    if (this.disposed)
      throw new IllegalStateException("Reasoner has been disposed");
    throwExceptionIfInterrupted();
    if (!this.prepared)
      prepareReasoner();
  }

  private void ensureConsistent()
  {
    ensurePrepared();
    if (this.state.axioms.isInconsistent())
      throw new InconsistentOntologyException();
  }

  private void ensureExpression(OWLClassExpression expression)
  {
    ensureConsistent();
    checkFresh(expression);
    boolean known = !expression.isAnonymous() && namedClasses().contains(expression.asOWLClass());
    if (!known && this.queryExpressions.add(expression)) {
      this.prepared = false;
      ensureConsistent();
    }
  }

  private void checkCancellation()
  {
    throwExceptionIfInterrupted();
    if (getTimeOut() != Long.MAX_VALUE && (System.nanoTime() - this.started) / 1000000 >= getTimeOut())
      throw new TimeOutException();
  }

  protected void throwExceptionIfInterrupted()
  {
    if (this.interrupted) {
      this.interrupted = false;
      throw new ReasonerInterruptedException();
    }
  }

  private Set<OWLClass> namedClasses()
  {
    Set<OWLClass> result = new HashSet<>();
    for (OWLAxiom axiom : this.state.closure)
      result.addAll(axiom.getClassesInSignature());
    return result;
  }

  private Set<OWLNamedIndividual> namedIndividuals()
  {
    Set<OWLNamedIndividual> result = new HashSet<>();
    for (OWLAxiom axiom : this.state.closure)
      result.addAll(axiom.getIndividualsInSignature());
    return result;
  }

  private boolean isDirectInstance(OWLNamedIndividual individual, String classID)
  {
    for (OWLClass type : getTypes(individual, false).getFlattened())
      if (getDroolsOWLAxiomHandler().strictSubClassOf(resolveC(type).getceid(), classID))
        return false;
    return true;
  }

  private void addIndividual(OWLNamedIndividualNodeSet result, OWLNamedIndividual individual)
  {
    Node<OWLNamedIndividual> same = getSameIndividuals(individual);
    if (getIndividualNodeSetPolicy() == IndividualNodeSetPolicy.BY_SAME_AS)
      result.addNode(same);
    else
      for (OWLNamedIndividual value : same.getEntities())
        result.addNode(new OWLNamedIndividualNode(value));
  }

  private void checkFresh(OWLObject object)
  {
    if (getFreshEntityPolicy() == FreshEntityPolicy.DISALLOW) {
      Set<OWLEntity> known = new HashSet<>();
      for (OWLAxiom axiom : getReasonerAxioms())
        known.addAll(axiom.getSignature());
      Set<OWLEntity> fresh = new HashSet<>(object.getSignature());
      fresh.removeAll(known);
      fresh.removeIf(OWLEntity::isBuiltIn);
      if (!fresh.isEmpty())
        throw new org.semanticweb.owlapi.reasoner.FreshEntitiesException(fresh);
    }
  }

  private IRI resolveIRI(String id)
  {
    return this.state.bridge.getIRIResolver().prefixedName2IRI(id)
      .orElseThrow(() -> new ReasonerInternalException("Cannot resolve " + id));
  }

  private OWLClass resolveOWLClass(String id)
  {
    return getOWLDataFactory().getOWLClass(resolveIRI(id));
  }

  private OWLClassExpression resolveOWLClassExpression(String id)
  {
    return this.state.classes.resolveOWLClassExpression(id);
  }

  private OWLNamedIndividual resolveOWLNamedIndividual(String id)
  {
    return getOWLDataFactory().getOWLNamedIndividual(resolveIRI(id));
  }

  private OWLObjectPropertyExpression resolveOWLObjectPropertyExpression(String id)
  {
    return this.state.properties.resolveOWLObjectPropertyExpression(id);
  }

  private OWLDataProperty resolveOWLDataProperty(String id)
  {
    return this.state.properties.resolveOWLDataPropertyExpression(id).asOWLDataProperty();
  }

  private C resolveC(OWLClass cls) { checkFresh(cls); return this.state.classes.convert(cls); }
  private CE resolveCE(OWLClassExpression expression) { checkFresh(expression); return this.state.classes.convert(expression); }
  private I resolveI(OWLNamedIndividual individual) { checkFresh(individual); return this.state.individuals.convert(individual); }
  private OPE resolveOPE(OWLObjectPropertyExpression expression) { checkFresh(expression); return this.state.properties.convert(expression); }
  private DPE resolveDP(OWLDataProperty property) { checkFresh(property); return this.state.properties.convert(property); }
  private DPE resolveDPE(OWLDataPropertyExpression expression) { checkFresh(expression); return this.state.properties.convert(expression); }

  private OWLLiteral l2OWLLiteral(L literal)
  {
    return literal.languageTag.isEmpty()
      ? getOWLDataFactory().getOWLLiteral(literal.getValue(), getOWLDataFactory().getOWLDatatype(resolveIRI(literal.datatypeName)))
      : getOWLDataFactory().getOWLLiteral(literal.getValue(), literal.languageTag);
  }

  private DroolsOWLAxiomHandler getDroolsOWLAxiomHandler() { return this.state.axioms; }
}
