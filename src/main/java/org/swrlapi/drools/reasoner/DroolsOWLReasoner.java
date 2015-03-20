package org.swrlapi.drools.reasoner;

import java.util.HashSet;
import java.util.Set;

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
import org.swrlapi.drools.owl.classexpressions.CE;
import org.swrlapi.drools.owl.core.C;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl.core.L;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.DPE;
import org.swrlapi.drools.owl.properties.OPE;

/**
 * A Drools-based reasoner interface implementing the OWLAPI {@link org.semanticweb.owlapi.reasoner.OWLReasoner}
 * interface.
 *
 * @see org.semanticweb.owlapi.reasoner.OWLReasoner
 */
public class DroolsOWLReasoner extends OWLReasonerBase implements OWLReasoner
{
	private final DroolsOWLAxiomHandler droolsOWLAxiomHandler;

	private boolean prepared = false;
	private boolean interrupted = false;

	public DroolsOWLReasoner(OWLOntology rootOntology, OWLReasonerConfiguration configuration,
			BufferingMode bufferingMode, DroolsOWLAxiomHandler droolsOWLAxiomHandler)
	{
		super(rootOntology, configuration, bufferingMode);

		this.droolsOWLAxiomHandler = droolsOWLAxiomHandler;
	}

	@Override
	public String getReasonerName()
	{
		return "DroolsOWLR2RLReasoner";
	}

	@Override
	public Version getReasonerVersion()
	{
		return new Version(0, 0, 0, 0);
	}

	@Override
	public void interrupt()
	{
		this.interrupted = true;
	}

	@Override
	public void precomputeInferences(InferenceType... inferenceTypes) throws ReasonerInterruptedException,
			TimeOutException, InconsistentOntologyException
	{
		prepareReasoner();
	}

	@Override
	public boolean isPrecomputed(InferenceType inferenceType)
	{
		return true;
	}

	@Override
	public Set<InferenceType> getPrecomputableInferenceTypes()
	{
		return CollectionFactory.createSet(InferenceType.CLASS_HIERARCHY, InferenceType.OBJECT_PROPERTY_HIERARCHY,
				InferenceType.DATA_PROPERTY_HIERARCHY);
	}

	@Override
	public boolean isConsistent() throws ReasonerInterruptedException, TimeOutException
	{
		return !this.droolsOWLAxiomHandler.isInconsistent();
	}

	@Override
	public boolean isSatisfiable(OWLClassExpression owlClassExpression) throws ReasonerInterruptedException,
			TimeOutException, ClassExpressionNotInProfileException, FreshEntitiesException, InconsistentOntologyException
	{
		if (!owlClassExpression.isAnonymous()) {
			CE ce = resolveCE(owlClassExpression);
			return this.droolsOWLAxiomHandler.getEquivalentClasses(OWLRDFVocabulary.OWL_NOTHING.getShortForm()).contains(
					ce.getceid());
		} else
			return false;
	}

	@Override
	public Node<OWLClass> getUnsatisfiableClasses() throws ReasonerInterruptedException, TimeOutException,
			InconsistentOntologyException
	{
		return getBottomClassNode();
	}

	@Override
	public boolean isEntailed(OWLAxiom owlAxiom) throws ReasonerInterruptedException, UnsupportedEntailmentTypeException,
			TimeOutException, AxiomNotInProfileException, FreshEntitiesException, InconsistentOntologyException
	{
		return EntitySearcher.containsAxiomIgnoreAnnotations(owlAxiom, getRootOntology(), true);
	}

	@Override
	public boolean isEntailed(Set<? extends OWLAxiom> owlAxioms) throws ReasonerInterruptedException,
			UnsupportedEntailmentTypeException, TimeOutException, AxiomNotInProfileException, FreshEntitiesException,
			InconsistentOntologyException
	{
		for (OWLAxiom owlAxiom : owlAxioms) {
			if (!EntitySearcher.containsAxiomIgnoreAnnotations(owlAxiom, getRootOntology(), true)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEntailmentCheckingSupported(AxiomType<?> axiomType)
	{
		return false;
	}

	@Override
	public Node<OWLClass> getTopClassNode()
	{
		Set<OWLClass> classes = new HashSet<>();

		for (String classID : this.droolsOWLAxiomHandler.getEquivalentClasses(OWLRDFVocabulary.OWL_THING.getShortForm())) {
			OWLClass c = resolveOWLClass(classID);
			classes.add(c);
		}
		return new OWLClassNode(classes);
	}

	@Override
	public Node<OWLClass> getBottomClassNode()
	{
		Set<OWLClass> classes = new HashSet<>();

		for (String classID : this.droolsOWLAxiomHandler.getEquivalentClasses(OWLRDFVocabulary.OWL_NOTHING.getShortForm())) {
			OWLClass c = resolveOWLClass(classID);
			classes.add(c);
		}
		return new OWLClassNode(classes);
	}

	@Override
	public NodeSet<OWLClass> getSubClasses(OWLClassExpression owlClassExpression, boolean direct)
			throws ReasonerInterruptedException, TimeOutException, FreshEntitiesException, InconsistentOntologyException,
			ClassExpressionNotInProfileException
	{
		OWLClassNodeSet ns = new OWLClassNodeSet();

		if (!owlClassExpression.isAnonymous()) {
			ensurePrepared();
			C c = resolveC(owlClassExpression.asOWLClass());
			for (String subClassID : this.droolsOWLAxiomHandler.getSubClasses(c.getceid(), direct)) {
				OWLClassExpression subClassExpression = resolveOWLClassExpression(subClassID);
				Node<OWLClass> cNode = getEquivalentClasses(subClassExpression);
				ns.addNode(cNode);
			}
		}
		return ns;
	}

	@Override
	public NodeSet<OWLClass> getSuperClasses(OWLClassExpression owlClassExpression, boolean direct)
			throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException
	{
		OWLClassNodeSet ns = new OWLClassNodeSet();

		if (!owlClassExpression.isAnonymous()) {
			ensurePrepared();
			C c = resolveC(owlClassExpression.asOWLClass());
			for (String superClassID : this.droolsOWLAxiomHandler.getSuperClasses(c.getceid(), direct)) {
				OWLClassExpression subClassExpression = resolveOWLClassExpression(superClassID);
				Node<OWLClass> cNode = getEquivalentClasses(subClassExpression);
				ns.addNode(cNode);
			}
		}
		return ns;
	}

	@Override
	public Node<OWLClass> getEquivalentClasses(OWLClassExpression owlClassExpression)
			throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException
	{
		Set<OWLClass> classes = new HashSet<>();

		CE ce = resolveCE(owlClassExpression);
		for (String classID : this.droolsOWLAxiomHandler.getEquivalentClasses(ce.getceid())) {
			OWLClass c = resolveOWLClass(classID);
			classes.add(c);
		}
		return new OWLClassNode(classes);
	}

	@Override
	public NodeSet<OWLClass> getDisjointClasses(OWLClassExpression owlClassExpression)
			throws ReasonerInterruptedException, TimeOutException, FreshEntitiesException, InconsistentOntologyException
	{
		OWLClassNodeSet nodeSet = new OWLClassNodeSet();

		if (!owlClassExpression.isAnonymous()) {
			ensurePrepared();
			C c = resolveC(owlClassExpression.asOWLClass());
			for (String disjointClassID : this.droolsOWLAxiomHandler.getDisjointClasses(c.getceid())) {
				OWLClassExpression disjointClassExpression = resolveOWLClassExpression(disjointClassID);
				Node<OWLClass> cNode = getEquivalentClasses(disjointClassExpression);
				nodeSet.addNode(cNode);
			}
		}
		return nodeSet;
	}

	@Override
	public Node<OWLObjectPropertyExpression> getTopObjectPropertyNode()
	{
		Set<OWLObjectPropertyExpression> properties = new HashSet<>();

		for (String propertyID : this.droolsOWLAxiomHandler
				.getEquivalentObjectProperties(OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getShortForm())) {
			OWLObjectPropertyExpression property = resolveOWLObjectPropertyExpression(propertyID);
			properties.add(property);
		}
		return new OWLObjectPropertyNode(properties);
	}

	@Override
	public Node<OWLObjectPropertyExpression> getBottomObjectPropertyNode()
	{
		Set<OWLObjectPropertyExpression> properties = new HashSet<>();

		for (String propertyID : this.droolsOWLAxiomHandler
				.getEquivalentObjectProperties(OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getShortForm())) {
			OWLObjectPropertyExpression property = resolveOWLObjectPropertyExpression(propertyID);
			properties.add(property);
		}
		return new OWLObjectPropertyNode(properties);
	}

	@Override
	public NodeSet<OWLObjectPropertyExpression> getSubObjectProperties(
			OWLObjectPropertyExpression owlObjectPropertyExpression, boolean direct) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException, TimeOutException
	{
		OWLObjectPropertyNodeSet ns = new OWLObjectPropertyNodeSet();

		ensurePrepared();
		OPE pe = resolveOPE(owlObjectPropertyExpression);
		for (String subPropertyID : this.droolsOWLAxiomHandler.getSubObjectProperties(pe.getid(), direct)) {
			OWLObjectPropertyExpression subPropertyExpression = resolveOWLObjectPropertyExpression(subPropertyID);
			Node<OWLObjectPropertyExpression> opNode = getEquivalentObjectProperties(subPropertyExpression);
			ns.addNode(opNode);
		}
		return ns;
	}

	@Override
	public NodeSet<OWLObjectPropertyExpression> getSuperObjectProperties(
			OWLObjectPropertyExpression owlObjectPropertyExpression, boolean direct) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException, TimeOutException
	{
		OWLObjectPropertyNodeSet ns = new OWLObjectPropertyNodeSet();

		ensurePrepared();
		OPE pe = resolveOPE(owlObjectPropertyExpression);
		for (String superPropertyID : this.droolsOWLAxiomHandler.getSuperObjectProperties(pe.getid(), direct)) {
			OWLObjectPropertyExpression superPropertyExpression = resolveOWLObjectPropertyExpression(superPropertyID);
			Node<OWLObjectPropertyExpression> opNode = getEquivalentObjectProperties(superPropertyExpression);
			ns.addNode(opNode);
		}
		return ns;
	}

	@Override
	public Node<OWLObjectPropertyExpression> getEquivalentObjectProperties(
			OWLObjectPropertyExpression owlObjectPropertyExpression) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException, TimeOutException
	{
		Set<OWLObjectPropertyExpression> properties = new HashSet<>();

		OPE ope = resolveOPE(owlObjectPropertyExpression);
		for (String equivalentPropertyID : this.droolsOWLAxiomHandler.getEquivalentObjectProperties(ope.getid())) {
			OWLObjectPropertyExpression p = resolveOWLObjectPropertyExpression(equivalentPropertyID);
			properties.add(p);
		}
		return new OWLObjectPropertyNode(properties);
	}

	@Override
	public NodeSet<OWLObjectPropertyExpression> getDisjointObjectProperties(
			OWLObjectPropertyExpression owlObjectPropertyExpression) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException, TimeOutException
	{
		OWLObjectPropertyNodeSet nodeSet = new OWLObjectPropertyNodeSet();

		ensurePrepared();
		OPE ope = resolveOPE(owlObjectPropertyExpression);
		for (String disjointPropertyID : this.droolsOWLAxiomHandler.getDisjointObjectProperties(ope.getid())) {
			OWLObjectPropertyExpression disjointProperty = resolveOWLObjectPropertyExpression(disjointPropertyID);
			Node<OWLObjectPropertyExpression> opNode = getEquivalentObjectProperties(disjointProperty);
			nodeSet.addNode(opNode);
		}
		return nodeSet;
	}

	@Override
	public Node<OWLObjectPropertyExpression> getInverseObjectProperties(
			OWLObjectPropertyExpression owlObjectPropertyExpression) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException, TimeOutException
	{
		ensurePrepared();
		OWLObjectPropertyExpression inv = owlObjectPropertyExpression.getInverseProperty().getSimplified();
		return getEquivalentObjectProperties(inv);
	}

	@Override
	public NodeSet<OWLClass> getObjectPropertyDomains(OWLObjectPropertyExpression owlObjectPropertyExpression,
			boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException
	{
		OWLClassNodeSet ns = new OWLClassNodeSet();
		OPE ope = resolveOPE(owlObjectPropertyExpression);

		for (String classID : this.droolsOWLAxiomHandler.getObjectPropertyDomains(ope.getid(), direct)) {
			OWLClass domainClass = resolveOWLClass(classID);
			Node<OWLClass> cNode = getEquivalentClasses(domainClass);
			ns.addNode(cNode);
		}
		return ns;
	}

	@Override
	public NodeSet<OWLClass> getObjectPropertyRanges(OWLObjectPropertyExpression owlObjectPropertyExpression,
			boolean direct) throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException
	{
		OWLClassNodeSet ns = new OWLClassNodeSet();
		OPE ope = resolveOPE(owlObjectPropertyExpression);

		for (String classID : this.droolsOWLAxiomHandler.getObjectPropertyRanges(ope.getid(), direct)) {
			OWLClass rangeClass = resolveOWLClass(classID);
			Node<OWLClass> cNode = getEquivalentClasses(rangeClass);
			ns.addNode(cNode);
		}
		return ns;
	}

	@Override
	public Node<OWLDataProperty> getTopDataPropertyNode()
	{
		Set<OWLDataProperty> properties = new HashSet<>();

		for (String propertyID : this.droolsOWLAxiomHandler
				.getEquivalentDataProperties(OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getShortForm())) {
			OWLDataProperty property = resolveOWLDataProperty(propertyID);
			properties.add(property);
		}
		return new OWLDataPropertyNode(properties);
	}

	@Override
	public Node<OWLDataProperty> getBottomDataPropertyNode()
	{
		Set<OWLDataProperty> properties = new HashSet<>();

		for (String propertyID : this.droolsOWLAxiomHandler
				.getEquivalentDataProperties(OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getShortForm())) {
			OWLDataProperty property = resolveOWLDataProperty(propertyID);
			properties.add(property);
		}
		return new OWLDataPropertyNode(properties);
	}

	@Override
	public NodeSet<OWLDataProperty> getSubDataProperties(OWLDataProperty owlDataProperty, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
	{
		OWLDataPropertyNodeSet ns = new OWLDataPropertyNodeSet();

		ensurePrepared();
		DP dp = resolveDP(owlDataProperty);
		for (String subPropertyID : this.droolsOWLAxiomHandler.getSubDataProperties(dp.getid(), direct)) {
			OWLDataProperty subProperty = resolveOWLDataProperty(subPropertyID);
			Node<OWLDataProperty> opNode = getEquivalentDataProperties(subProperty);
			ns.addNode(opNode);
		}
		return ns;
	}

	@Override
	public NodeSet<OWLDataProperty> getSuperDataProperties(OWLDataProperty owlDataProperty, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
	{
		OWLDataPropertyNodeSet ns = new OWLDataPropertyNodeSet();

		ensurePrepared();
		DP dp = resolveDP(owlDataProperty);
		for (String subPropertyID : this.droolsOWLAxiomHandler.getSuperDataProperties(dp.getid(), direct)) {
			OWLDataProperty subProperty = resolveOWLDataProperty(subPropertyID);
			Node<OWLDataProperty> opNode = getEquivalentDataProperties(subProperty);
			ns.addNode(opNode);
		}
		return ns;
	}

	@Override
	public Node<OWLDataProperty> getEquivalentDataProperties(OWLDataProperty owlDataProperty)
			throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
	{
		Set<OWLDataProperty> properties = new HashSet<>();

		DP dp = resolveDP(owlDataProperty);
		for (String equivalentPropertyID : this.droolsOWLAxiomHandler.getEquivalentDataProperties(dp.getid())) {
			OWLDataProperty p = resolveOWLDataProperty(equivalentPropertyID);
			properties.add(p);
		}
		return new OWLDataPropertyNode(properties);
	}

	@Override
	public NodeSet<OWLDataProperty> getDisjointDataProperties(OWLDataPropertyExpression owlDataPropertyExpression)
			throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
	{
		OWLDataPropertyNodeSet nodeSet = new OWLDataPropertyNodeSet();

		ensurePrepared();
		DPE dpe = resolveDPE(owlDataPropertyExpression);
		for (String disjointPropertyID : this.droolsOWLAxiomHandler.getDisjointDataProperties(dpe.getid())) {
			OWLDataProperty disjointProperty = resolveOWLDataProperty(disjointPropertyID);
			Node<OWLDataProperty> dpNode = getEquivalentDataProperties(disjointProperty);
			nodeSet.addNode(dpNode);
		}
		return nodeSet;
	}

	@Override
	public NodeSet<OWLClass> getDataPropertyDomains(OWLDataProperty owlDataProperty, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
	{
		OWLClassNodeSet ns = new OWLClassNodeSet();
		DP dp = resolveDP(owlDataProperty);

		for (String classID : this.droolsOWLAxiomHandler.getDataPropertyDomains(dp.getid(), direct)) {
			OWLClass domainClass = resolveOWLClass(classID);
			Node<OWLClass> cNode = getEquivalentClasses(domainClass);
			ns.addNode(cNode);
		}
		return ns;
	}

	@Override
	public NodeSet<OWLClass> getTypes(OWLNamedIndividual owlNamedIndividual, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
	{
		return new OWLClassNodeSet(); // TODO See StructuralReasoner - find all CAAs and follow
	}

	@Override
	public NodeSet<OWLNamedIndividual> getInstances(OWLClassExpression owlClassExpression, boolean b)
			throws InconsistentOntologyException, ClassExpressionNotInProfileException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException
	{
		return new OWLNamedIndividualNodeSet(); // TODO See StructuralReasoner - find all CAAs and follow
	}

	@Override
	public NodeSet<OWLNamedIndividual> getObjectPropertyValues(OWLNamedIndividual owlNamedIndividual,
			OWLObjectPropertyExpression owlObjectPropertyExpression) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException, TimeOutException
	{
		OWLNamedIndividualNodeSet ns = new OWLNamedIndividualNodeSet();
		I i = resolveI(owlNamedIndividual);
		OPE ope = resolveOPE(owlObjectPropertyExpression);

		for (String individualID : this.droolsOWLAxiomHandler.getObjectPropertyValuesForIndividual(i.getid(), ope.getid())) {
			OWLNamedIndividual valueIndividual = resolveOWLNamedIndividual(individualID);
			Node<OWLNamedIndividual> valueIndividualsNode = getSameIndividuals(valueIndividual);
			ns.addNode(valueIndividualsNode);
		}
		return ns;
	}

	@Override
	public Set<OWLLiteral> getDataPropertyValues(OWLNamedIndividual owlNamedIndividual, OWLDataProperty owlDataProperty)
			throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
	{
		Set<OWLLiteral> values = new HashSet<>();
		I i = resolveI(owlNamedIndividual);
		DP dp = resolveDP(owlDataProperty);

		for (L l : this.droolsOWLAxiomHandler.getDataPropertyValuesForIndividual(i.getid(), dp.getid())) {
			OWLLiteral literal = l2OWLLiteral(l);
			values.add(literal);
		}
		return values;
	}

	@Override
	public Node<OWLNamedIndividual> getSameIndividuals(OWLNamedIndividual owlNamedIndividual)
			throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
	{
		Set<OWLNamedIndividual> individuals = new HashSet<>();
		I i = resolveI(owlNamedIndividual);

		for (String sameIndividualID : this.droolsOWLAxiomHandler.getSameIndividual(i.getid())) {
			OWLNamedIndividual individual = resolveOWLNamedIndividual(sameIndividualID);
			individuals.add(individual);
		}
		return new OWLNamedIndividualNode(individuals);
	}

	@Override
	public NodeSet<OWLNamedIndividual> getDifferentIndividuals(OWLNamedIndividual owlNamedIndividual)
			throws InconsistentOntologyException, FreshEntitiesException, ReasonerInterruptedException, TimeOutException
	{
		OWLNamedIndividualNodeSet ns = new OWLNamedIndividualNodeSet();
		I i = resolveI(owlNamedIndividual);

		for (String sameIndividualID : this.droolsOWLAxiomHandler.getDifferentIndividuals(i.getid())) {
			OWLNamedIndividual sameIndividual = resolveOWLNamedIndividual(sameIndividualID);
			Node<OWLNamedIndividual> sameIndividualsNode = getSameIndividuals(sameIndividual);
			ns.addNode(sameIndividualsNode);
		}
		return ns;
	}

	public void prepareReasoner() throws ReasonerInterruptedException, TimeOutException
	{
		// TODO implement prepareReasoner
		prepared = true;
	}

	@Override
	protected void handleChanges(Set<OWLAxiom> owlAxioms, Set<OWLAxiom> owlAxioms2)
	{
		// TODO implement handleChanges
	}

	private void ensurePrepared()
	{
		if (!prepared) {
			prepareReasoner();
		}
	}

	protected void throwExceptionIfInterrupted()
	{
		if (interrupted) {
			interrupted = false;
			throw new ReasonerInterruptedException();
		}
	}

	private OWLClass resolveOWLClass(String classID)
	{
		return null;
	} // TODO implement resolveOWLClass

	private OWLClassExpression resolveOWLClassExpression(String classID)
	{
		return null;
	} // TODO implement resolveOWLClassExpression

	private OWLObjectPropertyExpression resolveOWLObjectPropertyExpression(String propertyID)
	{
		return null;
	} // TODO implement resolveOWLObjectPropertyExpression

	private OWLDataProperty resolveOWLDataProperty(String propertyID)
	{
		return null;
	} // TODO implement resolveOWLDataPropertyExpression

	private CE resolveCE(OWLClassExpression owlClassExpression)
	{
		return null;
	} // TODO implement resolveCE

	private OPE resolveOPE(OWLObjectPropertyExpression owlObjectPropertyExpression)
	{
		return null;
	} // TODO implement resolveOPE

	private DP resolveDP(OWLDataProperty owlDataProperty)
	{
		return null;
	} // TODO implement resolveDP

	private DPE resolveDPE(OWLDataPropertyExpression owlDataPropertyExpression)
	{
		return null;
	} // TODO implement resolveDPE

	private C resolveC(OWLClass owlClass)
	{
		return null;
	} // TODO implement resolveC

	private I resolveI(OWLNamedIndividual owlNamedIndividual)
	{
		return null;
	} // TODO implement resolveI

	private OWLNamedIndividual resolveOWLNamedIndividual(String individualID)
	{
		return null; // TODO implement resolveOWLNamedIndividual
	}

	private OWLLiteral l2OWLLiteral(L l)
	{
		return null; // TODO implement l2OWLLiteral
	}
}
