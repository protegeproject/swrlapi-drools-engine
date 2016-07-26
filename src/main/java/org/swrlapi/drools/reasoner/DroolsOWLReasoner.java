package org.swrlapi.drools.reasoner;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDatatype;
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
import org.swrlapi.core.OWLObjectResolver;
import org.swrlapi.drools.converters.oo.DroolsOWLClassExpression2CEConverter;
import org.swrlapi.drools.converters.oo.DroolsOWLEntity2OEConverter;
import org.swrlapi.drools.converters.oo.DroolsOWLIndividual2IConverter;
import org.swrlapi.drools.converters.oo.DroolsOWLPropertyExpression2PEConverter;
import org.swrlapi.drools.owl.classes.C;
import org.swrlapi.drools.owl.classes.CE;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.drools.owl.literals.L;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.DPE;
import org.swrlapi.drools.owl.properties.OPE;
import org.swrlapi.factory.OWLLiteralFactory;

import java.util.HashSet;
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

  private static final String OWLThingPrefixedName = OWLRDFVocabulary.OWL_NOTHING.getPrefixedName();
  private static final String OWLNothingPrefixedName = OWLRDFVocabulary.OWL_NOTHING.getPrefixedName();
  private static final String OWLTopObjectPropertyPrefixedName = OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY
    .getPrefixedName();
  private static final String OWLBottomObjectPropertyPrefixedName = OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY
    .getPrefixedName();
  private static final String OWLTopDataPropertyPrefixedName = OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getPrefixedName();
  private static final String OWLBottomDataPropertyPrefixedName = OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY
    .getPrefixedName();

  @NonNull private final DroolsOWLAxiomHandler droolsOWLAxiomHandler;
  @NonNull private final DroolsOWLClassExpression2CEConverter droolsOWLClassExpression2CEConverter;
  @NonNull private final DroolsOWLPropertyExpression2PEConverter droolsOWLPropertyExpression2PEConverter;
  @NonNull private final DroolsOWLIndividual2IConverter droolsOWLIndividual2IConverter;
  @NonNull private final DroolsOWLEntity2OEConverter droolsOWLEntity2OEConverter;
  @NonNull private final OWLObjectResolver owlObjectResolver;
  @NonNull private final OWLLiteralFactory owlLiteralFactory;

  private boolean prepared = false;
  private boolean interrupted = false;

  public DroolsOWLReasoner(@NonNull OWLOntology rootOntology, @NonNull OWLReasonerConfiguration configuration,
    @NonNull BufferingMode bufferingMode, @NonNull OWLObjectResolver owlObjectResolver,
    @NonNull OWLLiteralFactory owlLiteralFactory, @NonNull DroolsOWLAxiomHandler droolsOWLAxiomHandler,
    @NonNull DroolsOWLClassExpression2CEConverter droolsOWLClassExpression2CEConverter,
    @NonNull DroolsOWLPropertyExpression2PEConverter droolsOWLPropertyExpression2PEConverter,
    @NonNull DroolsOWLIndividual2IConverter droolsOWLIndividual2IConverter,
    @NonNull DroolsOWLEntity2OEConverter droolsOWLEntity2OEConverter)
  {
    super(rootOntology, configuration, bufferingMode);

    this.droolsOWLAxiomHandler = droolsOWLAxiomHandler;
    this.droolsOWLClassExpression2CEConverter = droolsOWLClassExpression2CEConverter;
    this.droolsOWLPropertyExpression2PEConverter = droolsOWLPropertyExpression2PEConverter;
    this.droolsOWLIndividual2IConverter = droolsOWLIndividual2IConverter;
    this.droolsOWLEntity2OEConverter = droolsOWLEntity2OEConverter;
    this.owlObjectResolver = owlObjectResolver;
    this.owlLiteralFactory = owlLiteralFactory;
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
    return true;
  }

  @NonNull @Override public Set<@NonNull InferenceType> getPrecomputableInferenceTypes()
  {
    return CollectionFactory.createSet(InferenceType.CLASS_HIERARCHY, InferenceType.OBJECT_PROPERTY_HIERARCHY,
      InferenceType.DATA_PROPERTY_HIERARCHY);
  }

  @Override public boolean isConsistent() throws ReasonerInterruptedException, TimeOutException
  {
    return !getDroolsOWLAxiomHandler().isInconsistent();
  }

  @Override public boolean isSatisfiable(@NonNull OWLClassExpression classExpression)
    throws ReasonerInterruptedException, TimeOutException, ClassExpressionNotInProfileException, FreshEntitiesException,
    InconsistentOntologyException
  {
    if (!classExpression.isAnonymous()) {
      CE ce = resolveCE(classExpression);
      return getDroolsOWLAxiomHandler().getEquivalentClasses(OWLNothingPrefixedName).contains(ce.getceid());
    } else
      return false;
  }

  @NonNull @Override public Node<@NonNull OWLClass> getUnsatisfiableClasses()
    throws ReasonerInterruptedException, TimeOutException, InconsistentOntologyException
  {
    return getBottomClassNode();
  }

  @Override public boolean isEntailed(@NonNull OWLAxiom axiom)
    throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException,
    AxiomNotInProfileException, FreshEntitiesException, InconsistentOntologyException
  {
    return EntitySearcher.containsAxiomIgnoreAnnotations(axiom, getRootOntology(), true);
  }

  @Override public boolean isEntailed(@NonNull Set<? extends @NonNull OWLAxiom> axioms)
    throws ReasonerInterruptedException, UnsupportedEntailmentTypeException, TimeOutException,
    AxiomNotInProfileException, FreshEntitiesException, InconsistentOntologyException
  {
    for (OWLAxiom axiom : axioms) {
      if (!EntitySearcher.containsAxiomIgnoreAnnotations(axiom, getRootOntology(), true)) {
        return false;
      }
    }
    return true;
  }

  @Override public boolean isEntailmentCheckingSupported(AxiomType<?> axiomType)
  {
    return false;
  }

  @NonNull @Override public Node<@NonNull OWLClass> getTopClassNode()
  {
    Set<@NonNull OWLClass> classes = new HashSet<>();

    for (String classID : getDroolsOWLAxiomHandler().getEquivalentClasses(OWLThingPrefixedName)) {
      OWLClass c = resolveOWLClass(classID);
      classes.add(c);
    }
    return new OWLClassNode(classes);
  }

  @NonNull @Override public Node<@NonNull OWLClass> getBottomClassNode()
  {
    Set<@NonNull OWLClass> classes = new HashSet<>();

    for (String classID : getDroolsOWLAxiomHandler().getEquivalentClasses(OWLNothingPrefixedName)) {
      OWLClass c = resolveOWLClass(classID);
      classes.add(c);
    }
    return new OWLClassNode(classes);
  }

  @NonNull @Override public NodeSet<@NonNull OWLClass> getSubClasses(@NonNull OWLClassExpression classExpression,
    boolean direct)
    throws ReasonerInterruptedException, TimeOutException, FreshEntitiesException, InconsistentOntologyException,
    ClassExpressionNotInProfileException
  {
    OWLClassNodeSet ns = new OWLClassNodeSet();

    if (!classExpression.isAnonymous()) {
      ensurePrepared();
      C c = resolveC(classExpression.asOWLClass());
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
    OWLClassNodeSet ns = new OWLClassNodeSet();

    if (!classExpression.isAnonymous()) {
      ensurePrepared();
      C c = resolveC(classExpression.asOWLClass());
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
    Set<@NonNull OWLClass> classes = new HashSet<>();

    CE ce = resolveCE(classExpression);
    String classID = ce.getceid();
    for (String equivalentClassID : getDroolsOWLAxiomHandler().getEquivalentClasses(classID)) {
      OWLClass c = resolveOWLClass(equivalentClassID);
      classes.add(c);
    }
    return new OWLClassNode(classes);
  }

  @NonNull @Override public NodeSet<@NonNull OWLClass> getDisjointClasses(@NonNull OWLClassExpression classExpression)
    throws ReasonerInterruptedException, TimeOutException, FreshEntitiesException, InconsistentOntologyException
  {
    OWLClassNodeSet nodeSet = new OWLClassNodeSet();

    if (!classExpression.isAnonymous()) {
      ensurePrepared();
      C c = resolveC(classExpression.asOWLClass());
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
    Set<@NonNull OWLObjectPropertyExpression> properties = new HashSet<>();

    for (String propertyID : getDroolsOWLAxiomHandler()
      .getEquivalentObjectProperties(OWLTopObjectPropertyPrefixedName)) {
      OWLObjectPropertyExpression property = resolveOWLObjectPropertyExpression(propertyID);
      properties.add(property);
    }
    return new OWLObjectPropertyNode(properties);
  }

  @NonNull @Override public Node<@NonNull OWLObjectPropertyExpression> getBottomObjectPropertyNode()
  {
    Set<@NonNull OWLObjectPropertyExpression> properties = new HashSet<>();

    for (String propertyID : getDroolsOWLAxiomHandler()
      .getEquivalentObjectProperties(OWLBottomObjectPropertyPrefixedName)) {
      OWLObjectPropertyExpression property = resolveOWLObjectPropertyExpression(propertyID);
      properties.add(property);
    }
    return new OWLObjectPropertyNode(properties);
  }

  @NonNull @Override public NodeSet<@NonNull OWLObjectPropertyExpression> getSubObjectProperties(
    @NonNull OWLObjectPropertyExpression objectPropertyExpression, boolean direct)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
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
    Set<@NonNull OWLObjectPropertyExpression> properties = new HashSet<>();

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
    ensurePrepared();
    OWLObjectPropertyExpression inv = objectPropertyExpression.getInverseProperty();

    return getEquivalentObjectProperties(inv);
  }

  @NonNull @Override public NodeSet<@NonNull OWLClass> getObjectPropertyDomains(
    @NonNull OWLObjectPropertyExpression objectPropertyExpression, boolean direct)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    OWLClassNodeSet ns = new OWLClassNodeSet();
    OPE ope = resolveOPE(objectPropertyExpression);
    String propertyID = ope.getid();
    for (String classID : getDroolsOWLAxiomHandler().getObjectPropertyDomains(propertyID, direct)) {
      OWLClass domainClass = resolveOWLClass(classID);
      Node<OWLClass> cNode = getEquivalentClasses(domainClass);
      ns.addNode(cNode);
    }
    return ns;
  }

  @NonNull @Override public NodeSet<@NonNull OWLClass> getObjectPropertyRanges(
    @NonNull OWLObjectPropertyExpression objectPropertyExpression, boolean direct)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    OWLClassNodeSet ns = new OWLClassNodeSet();
    OPE ope = resolveOPE(objectPropertyExpression);
    String propertyID = ope.getid();
    for (String classID : getDroolsOWLAxiomHandler().getObjectPropertyRanges(propertyID, direct)) {
      OWLClass rangeClass = resolveOWLClass(classID);
      Node<OWLClass> cNode = getEquivalentClasses(rangeClass);
      ns.addNode(cNode);
    }
    return ns;
  }

  @NonNull @Override public Node<@NonNull OWLDataProperty> getTopDataPropertyNode()
  {
    Set<@NonNull OWLDataProperty> properties = new HashSet<>();

    for (String propertyID : getDroolsOWLAxiomHandler().getEquivalentDataProperties(OWLTopDataPropertyPrefixedName)) {
      OWLDataProperty property = resolveOWLDataProperty(propertyID);
      properties.add(property);
    }
    return new OWLDataPropertyNode(properties);
  }

  @NonNull @Override public Node<@NonNull OWLDataProperty> getBottomDataPropertyNode()
  {
    Set<@NonNull OWLDataProperty> properties = new HashSet<>();

    for (String propertyID : getDroolsOWLAxiomHandler()
      .getEquivalentDataProperties(OWLBottomDataPropertyPrefixedName)) {
      OWLDataProperty property = resolveOWLDataProperty(propertyID);
      properties.add(property);
    }
    return new OWLDataPropertyNode(properties);
  }

  @NonNull @Override public NodeSet<@NonNull OWLDataProperty> getSubDataProperties(
    @NonNull OWLDataProperty dataProperty, boolean direct)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    OWLDataPropertyNodeSet ns = new OWLDataPropertyNodeSet();

    ensurePrepared();
    DP dp = resolveDP(dataProperty);
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
    OWLDataPropertyNodeSet ns = new OWLDataPropertyNodeSet();

    ensurePrepared();
    DP dp = resolveDP(dataProperty);
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
    Set<@NonNull OWLDataProperty> properties = new HashSet<>();

    DP dp = resolveDP(dataProperty);
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
    OWLClassNodeSet ns = new OWLClassNodeSet();
    DP dp = resolveDP(dataProperty);
    String propertyID = dp.getid();
    for (String classID : getDroolsOWLAxiomHandler().getDataPropertyDomains(propertyID, direct)) {
      OWLClass domainClass = resolveOWLClass(classID);
      Node<OWLClass> cNode = getEquivalentClasses(domainClass);
      ns.addNode(cNode);
    }
    return ns;
  }

  @NonNull @Override public NodeSet<OWLClass> getTypes(@NonNull OWLNamedIndividual namedIndividual, boolean direct)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    return new OWLClassNodeSet(); // TODO See StructuralReasoner - find all CAAs and follow
  }

  @NonNull @Override public NodeSet<OWLNamedIndividual> getInstances(@NonNull OWLClassExpression classExpression,
    boolean b) throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException,
    ReasonerInterruptedException, TimeOutException
  {
    return new OWLNamedIndividualNodeSet(); // TODO See StructuralReasoner - find all CAAs and follow
  }

  @NonNull @Override public NodeSet<OWLNamedIndividual> getObjectPropertyValues(
    @NonNull OWLNamedIndividual namedIndividual, @NonNull OWLObjectPropertyExpression owlObjectPropertyExpression)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    OWLNamedIndividualNodeSet ns = new OWLNamedIndividualNodeSet();
    I i = resolveI(namedIndividual);
    OPE ope = resolveOPE(owlObjectPropertyExpression);
    String individualID = i.getid();
    String propertyID = ope.getid();
    for (String valueIndividualID : getDroolsOWLAxiomHandler()
      .getObjectPropertyValuesForIndividual(individualID, propertyID)) {
      OWLNamedIndividual valueIndividual = resolveOWLNamedIndividual(valueIndividualID);
      Node<OWLNamedIndividual> valueIndividualsNode = getSameIndividuals(valueIndividual);
      ns.addNode(valueIndividualsNode);
    }
    return ns;
  }

  @NonNull @Override public Set<@NonNull OWLLiteral> getDataPropertyValues(@NonNull OWLNamedIndividual namedIndividual,
    OWLDataProperty owlDataProperty)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    Set<@NonNull OWLLiteral> values = new HashSet<>();
    I i = resolveI(namedIndividual);
    DP dp = resolveDP(owlDataProperty);
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
    Set<@NonNull OWLNamedIndividual> individuals = new HashSet<>();
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
    OWLNamedIndividualNodeSet ns = new OWLNamedIndividualNodeSet();
    I i = resolveI(namedIndividual);
    String individualID = i.getid();
    for (String sameIndividualID : getDroolsOWLAxiomHandler().getDifferentIndividuals(individualID)) {
      OWLNamedIndividual sameIndividual = resolveOWLNamedIndividual(sameIndividualID);
      Node<OWLNamedIndividual> sameIndividualsNode = getSameIndividuals(sameIndividual);
      ns.addNode(sameIndividualsNode);
    }
    return ns;
  }

  public void prepareReasoner() throws ReasonerInterruptedException, TimeOutException
  {
    // TODO implement prepareReasoner
    this.prepared = true;
  }

  @Override protected void handleChanges(Set<OWLAxiom> owlAxioms, @NonNull Set<@NonNull OWLAxiom> owlAxioms2)
  {
    // TODO implement handleChanges
  }

  private void ensurePrepared()
  {
    if (!this.prepared) {
      prepareReasoner();
    }
  }

  protected void throwExceptionIfInterrupted()
  {
    if (this.interrupted) {
      this.interrupted = false;
      throw new ReasonerInterruptedException();
    }
  }

  @NonNull private OWLClass resolveOWLClass(@NonNull String classID)
  {
    return this.owlObjectResolver.resolveOWLClass(classID);
  }

  @NonNull private OWLClassExpression resolveOWLClassExpression(@NonNull String classID)
  {
    return this.owlObjectResolver.resolveOWLClassExpression(classID);
  }

  @NonNull private OWLNamedIndividual resolveOWLNamedIndividual(@NonNull String individualID)
  {
    return this.owlObjectResolver.resolveOWLNamedIndividual(individualID);
  }

  @NonNull private OWLObjectPropertyExpression resolveOWLObjectPropertyExpression(@NonNull String propertyID)
  {
    return this.owlObjectResolver.resolveOWLObjectPropertyExpression(propertyID);
  }

  private @NonNull OWLObjectProperty resolveOWLObjectProperty(@NonNull String propertyID)
  {
    return this.owlObjectResolver.resolveOWLObjectProperty(propertyID);
  }

  @NonNull private OWLDataPropertyExpression resolveOWLDataPropertyExpression(@NonNull String propertyID)
  {
    return this.owlObjectResolver.resolveOWLDataPropertyExpression(propertyID);
  }

  @NonNull private OWLDataProperty resolveOWLDataProperty(@NonNull String propertyID)
  {
    return this.owlObjectResolver.resolveOWLDataProperty(propertyID);
  }

  @NonNull private C resolveC(@NonNull OWLClass cls)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(cls);
  }

  @NonNull private CE resolveCE(@NonNull OWLClassExpression classExpression)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(classExpression);
  }

  @NonNull private I resolveI(@NonNull OWLNamedIndividual namedIndividual)
  {
    return getDroolsOWLIndividual2IConverter().convert(namedIndividual);
  }

  @NonNull private OPE resolveOPE(@NonNull OWLObjectPropertyExpression objectPropertyExpression)
  {
    return getDroolsOWLPropertyExpression2PEConverter().convert(objectPropertyExpression);
  }

  @NonNull private DP resolveDP(@NonNull OWLDataProperty dataProperty)
  {
    return getDroolsOWLEntity2OEConverter().convert(dataProperty);
  }

  @NonNull private DPE resolveDPE(@NonNull OWLDataPropertyExpression dataPropertyExpression)
  {
    return getDroolsOWLPropertyExpression2PEConverter().convert(dataPropertyExpression);
  }

  @NonNull private OWLLiteral l2OWLLiteral(@NonNull L l)
  {
    OWLDatatype datatype = this.owlObjectResolver.resolveOWLDatatype(l.datatypeName);
    return getOWLLiteralFactory().getOWLLiteral(l.getValue(), datatype);
  }

  @NonNull private OWLLiteralFactory getOWLLiteralFactory() { return this.owlLiteralFactory; }

  @NonNull private DroolsOWLAxiomHandler getDroolsOWLAxiomHandler()
  {
    return this.droolsOWLAxiomHandler;
  }

  @NonNull DroolsOWLClassExpression2CEConverter getDroolsOWLClassExpression2CEConverter()
  {
    return this.droolsOWLClassExpression2CEConverter;
  }

  @NonNull DroolsOWLPropertyExpression2PEConverter getDroolsOWLPropertyExpression2PEConverter()
  {
    return this.droolsOWLPropertyExpression2PEConverter;
  }

  @NonNull DroolsOWLIndividual2IConverter getDroolsOWLIndividual2IConverter()
  {
    return this.droolsOWLIndividual2IConverter;
  }

  @NonNull DroolsOWLEntity2OEConverter getDroolsOWLEntity2OEConverter()
  {
    return this.droolsOWLEntity2OEConverter;
  }
}
