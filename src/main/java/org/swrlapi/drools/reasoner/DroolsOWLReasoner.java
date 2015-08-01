package org.swrlapi.drools.reasoner;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
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
import org.swrlapi.drools.owl.classes.CE;
import org.swrlapi.drools.owl.classes.C;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.drools.owl.literals.L;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.DPE;
import org.swrlapi.drools.owl.properties.OPE;
import org.swrlapi.drools.core.resolvers.DroolsObjectResolver;

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
  private final @NonNull DroolsObjectResolver droolsObjectResolver;
  @NonNull private final OWLObjectResolver owlObjectResolver;

  private boolean prepared = false;
  private boolean interrupted = false;

  public DroolsOWLReasoner(@NonNull OWLOntology rootOntology, @NonNull OWLReasonerConfiguration configuration,
    @NonNull BufferingMode bufferingMode, @NonNull DroolsOWLAxiomHandler droolsOWLAxiomHandler,
    @NonNull OWLObjectResolver owlObjectResolver, DroolsObjectResolver droolsObjectResolver)
  {
    super(rootOntology, configuration, bufferingMode);

    this.droolsOWLAxiomHandler = droolsOWLAxiomHandler;
    this.droolsObjectResolver = droolsObjectResolver;
    this.owlObjectResolver = owlObjectResolver;
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

  @NonNull @Override public Set<InferenceType> getPrecomputableInferenceTypes()
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

  @NonNull @Override public Node<OWLClass> getUnsatisfiableClasses()
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

  @Override public boolean isEntailed(@NonNull Set<? extends OWLAxiom> axioms)
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

  @NonNull @Override public Node<OWLClass> getTopClassNode()
  {
    Set<OWLClass> classes = new HashSet<>();

    for (String classID : getDroolsOWLAxiomHandler().getEquivalentClasses(OWLThingPrefixedName)) {
      OWLClass c = resolveOWLClass(classID);
      classes.add(c);
    }
    return new OWLClassNode(classes);
  }

  @NonNull @Override public Node<OWLClass> getBottomClassNode()
  {
    Set<OWLClass> classes = new HashSet<>();

    for (String classID : getDroolsOWLAxiomHandler().getEquivalentClasses(OWLNothingPrefixedName)) {
      OWLClass c = resolveOWLClass(classID);
      classes.add(c);
    }
    return new OWLClassNode(classes);
  }

  @NonNull @Override public NodeSet<OWLClass> getSubClasses(@NonNull OWLClassExpression classExpression,
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

  @NonNull @Override public NodeSet<OWLClass> getSuperClasses(@NonNull OWLClassExpression classExpression,
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

  @NonNull @Override public Node<OWLClass> getEquivalentClasses(OWLClassExpression classExpression)
    throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException,
    ReasonerInterruptedException, TimeOutException
  {
    Set<OWLClass> classes = new HashSet<>();

    CE ce = resolveCE(classExpression);
    String classID = ce.getceid();
    for (String equivalentClassID : getDroolsOWLAxiomHandler().getEquivalentClasses(classID)) {
      OWLClass c = resolveOWLClass(equivalentClassID);
      classes.add(c);
    }
    return new OWLClassNode(classes);
  }

  @NonNull @Override public NodeSet<OWLClass> getDisjointClasses(@NonNull OWLClassExpression classExpression)
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

  @NonNull @Override public Node<OWLObjectPropertyExpression> getTopObjectPropertyNode()
  {
    Set<OWLObjectPropertyExpression> properties = new HashSet<>();

    for (String propertyID : getDroolsOWLAxiomHandler()
      .getEquivalentObjectProperties(OWLTopObjectPropertyPrefixedName)) {
      OWLObjectPropertyExpression property = resolveOWLObjectPropertyExpression(propertyID);
      properties.add(property);
    }
    return new OWLObjectPropertyNode(properties);
  }

  @NonNull @Override public Node<OWLObjectPropertyExpression> getBottomObjectPropertyNode()
  {
    Set<OWLObjectPropertyExpression> properties = new HashSet<>();

    for (String propertyID : getDroolsOWLAxiomHandler()
      .getEquivalentObjectProperties(OWLBottomObjectPropertyPrefixedName)) {
      OWLObjectPropertyExpression property = resolveOWLObjectPropertyExpression(propertyID);
      properties.add(property);
    }
    return new OWLObjectPropertyNode(properties);
  }

  @NonNull @Override public NodeSet<OWLObjectPropertyExpression> getSubObjectProperties(
    OWLObjectPropertyExpression objectPropertyExpression, boolean direct)
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

  @NonNull @Override public NodeSet<OWLObjectPropertyExpression> getSuperObjectProperties(
    OWLObjectPropertyExpression objectPropertyExpression, boolean direct)
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

  @NonNull @Override public Node<OWLObjectPropertyExpression> getEquivalentObjectProperties(
    OWLObjectPropertyExpression objectPropertyExpression)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    Set<OWLObjectPropertyExpression> properties = new HashSet<>();

    OPE ope = resolveOPE(objectPropertyExpression);
    String propertyID = ope.getid();
    for (String equivalentPropertyID : getDroolsOWLAxiomHandler().getEquivalentObjectProperties(propertyID)) {
      OWLObjectPropertyExpression p = resolveOWLObjectPropertyExpression(equivalentPropertyID);
      properties.add(p);
    }
    return new OWLObjectPropertyNode(properties);
  }

  @NonNull @Override public NodeSet<OWLObjectPropertyExpression> getDisjointObjectProperties(
    OWLObjectPropertyExpression objectPropertyExpression)
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

  @NonNull @Override public Node<OWLObjectPropertyExpression> getInverseObjectProperties(
    @NonNull OWLObjectPropertyExpression objectPropertyExpression)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    ensurePrepared();
    OWLObjectPropertyExpression inv = objectPropertyExpression.getInverseProperty().getSimplified();

    return getEquivalentObjectProperties(inv);
  }

  @NonNull @Override public NodeSet<OWLClass> getObjectPropertyDomains(
    OWLObjectPropertyExpression objectPropertyExpression, boolean direct)
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

  @NonNull @Override public NodeSet<OWLClass> getObjectPropertyRanges(
    OWLObjectPropertyExpression objectPropertyExpression, boolean direct)
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

  @NonNull @Override public Node<OWLDataProperty> getTopDataPropertyNode()
  {
    Set<OWLDataProperty> properties = new HashSet<>();

    for (String propertyID : getDroolsOWLAxiomHandler().getEquivalentDataProperties(OWLTopDataPropertyPrefixedName)) {
      OWLDataProperty property = resolveOWLDataProperty(propertyID);
      properties.add(property);
    }
    return new OWLDataPropertyNode(properties);
  }

  @NonNull @Override public Node<OWLDataProperty> getBottomDataPropertyNode()
  {
    Set<OWLDataProperty> properties = new HashSet<>();

    for (String propertyID : getDroolsOWLAxiomHandler()
      .getEquivalentDataProperties(OWLBottomDataPropertyPrefixedName)) {
      OWLDataProperty property = resolveOWLDataProperty(propertyID);
      properties.add(property);
    }
    return new OWLDataPropertyNode(properties);
  }

  @NonNull @Override public NodeSet<OWLDataProperty> getSubDataProperties(OWLDataProperty dataProperty,
    boolean direct)
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

  @NonNull @Override public NodeSet<OWLDataProperty> getSuperDataProperties(OWLDataProperty dataProperty,
    boolean direct)
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

  @NonNull @Override public Node<OWLDataProperty> getEquivalentDataProperties(OWLDataProperty dataProperty)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    Set<OWLDataProperty> properties = new HashSet<>();

    DP dp = resolveDP(dataProperty);
    String propertyID = dp.getid();
    for (String equivalentPropertyID : getDroolsOWLAxiomHandler().getEquivalentDataProperties(propertyID)) {
      OWLDataProperty p = resolveOWLDataProperty(equivalentPropertyID);
      properties.add(p);
    }
    return new OWLDataPropertyNode(properties);
  }

  @NonNull @Override public NodeSet<OWLDataProperty> getDisjointDataProperties(
    OWLDataPropertyExpression dataPropertyExpression)
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

  @NonNull @Override public NodeSet<OWLClass> getDataPropertyDomains(OWLDataProperty dataProperty, boolean direct)
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

  @NonNull @Override public NodeSet<OWLClass> getTypes(OWLNamedIndividual namedIndividual, boolean direct)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    return new OWLClassNodeSet(); // TODO See StructuralReasoner - find all CAAs and follow
  }

  @NonNull @Override public NodeSet<OWLNamedIndividual> getInstances(OWLClassExpression classExpression, boolean b)
    throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException,
    ReasonerInterruptedException, TimeOutException
  {
    return new OWLNamedIndividualNodeSet(); // TODO See StructuralReasoner - find all CAAs and follow
  }

  @NonNull @Override public NodeSet<OWLNamedIndividual> getObjectPropertyValues(OWLNamedIndividual namedIndividual,
    OWLObjectPropertyExpression owlObjectPropertyExpression)
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

  @NonNull @Override public Set<OWLLiteral> getDataPropertyValues(OWLNamedIndividual namedIndividual,
    OWLDataProperty owlDataProperty)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    Set<OWLLiteral> values = new HashSet<>();
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

  @NonNull @Override public Node<OWLNamedIndividual> getSameIndividuals(OWLNamedIndividual namedIndividual)
    throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
  {
    Set<OWLNamedIndividual> individuals = new HashSet<>();
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

  @Override protected void handleChanges(Set<OWLAxiom> owlAxioms, Set<OWLAxiom> owlAxioms2)
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

  @NonNull private OWLDataProperty resolveOWLDataProperty(@NonNull String propertyID)
  {
    throw new IllegalArgumentException(); // TODO implement resolveOWLDataProperty
  }

  @NonNull private CE resolveCE(OWLClassExpression classExpression)
  {
    throw new IllegalArgumentException(); // TODO implement resolveCE
  }

  @NonNull private OPE resolveOPE(OWLObjectPropertyExpression owlObjectPropertyExpression)
  {
    throw new IllegalArgumentException(); // TODO implement resolveOPE
  }

  @Nullable private DP resolveDP(OWLDataProperty owlDataProperty)
  {
    return null; // TODO implement resolveDP
  }

  @Nullable private DPE resolveDPE(OWLDataPropertyExpression owlDataPropertyExpression)
  {
    return null;  // TODO implement resolveDPE
  }

  @Nullable private C resolveC(OWLClass owlClass)
  {
    return null; // TODO implement resolveC
  }

  @Nullable private I resolveI(OWLNamedIndividual owlNamedIndividual)
  {
    return null; // TODO implement resolveI
  }

  @Nullable private OWLLiteral l2OWLLiteral(L l)
  {
    return null; // TODO implement l2OWLLiteral
  }

  private DroolsOWLAxiomHandler getDroolsOWLAxiomHandler()
  {
    return this.droolsOWLAxiomHandler;
  }
}
