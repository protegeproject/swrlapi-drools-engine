package org.swrlapi.drools.reasoner;

import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.drools.owl.axioms.A;
import org.swrlapi.drools.owl.classexpressions.CE;
import org.swrlapi.drools.owl.core.C;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl.core.L;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.DPE;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.drools.owl.properties.OPE;
import org.swrlapi.drools.owl2rl.DroolsOWLAxiomHandler;

import java.util.Set;

/**
 * Implements the {@link org.swrlapi.drools.reasoner.DroolsOWLReasoner}. This class is supplied with a
 * {@link org.swrlapi.drools.owl2rl.DroolsOWLAxiomHandler} which contains all OWL axioms that are
 * inferred by a reasoner implementation.
 *
 * @see org.semanticweb.owlapi.reasoner.OWLReasoner
 * @see org.swrlapi.drools.owl2rl.DroolsOWLAxiomHandler
 */
public class DefaultDroolsOWLReasoner implements DroolsOWLReasoner
{
	private final DroolsOWLAxiomHandler droolsOWLAxiomHandler;

	public DefaultDroolsOWLReasoner(DroolsOWLAxiomHandler droolsOWLAxiomHandler)
	{
		this.droolsOWLAxiomHandler = droolsOWLAxiomHandler;
	}

	@Override public boolean isConsistent()
	{
		return !this.droolsOWLAxiomHandler.isInconsistent();
	}

	// Axioms

	@Override public boolean isEntailed(A a)
	{
		return this.droolsOWLAxiomHandler.isEntailed(a);
	}

	@Override public boolean isEntailed(Set<? extends A> axioms)
	{
		return this.droolsOWLAxiomHandler.isEntailed(axioms);
	}

	// Classes

	@Override public CNode getUnsatisfiableClasses()
	{
		return getBottomClassNode();
	}

	@Override public CNode getTopClassNode()
	{
		CNode cNode = new CNode();

		for (String classID : this.droolsOWLAxiomHandler.getEquivalentClasses(OWLRDFVocabulary.OWL_THING.getShortForm())) {
			C c = resolveOWLClass(classID);
			cNode.add(c);
		}
		return cNode;
	}

	@Override public CNode getBottomClassNode()
	{
		CNode cNode = new CNode();

		for (String classID : this.droolsOWLAxiomHandler.getEquivalentClasses(OWLRDFVocabulary.OWL_NOTHING.getShortForm())) {
			C c = resolveOWLClass(classID);
			cNode.add(c);
		}
		return cNode;
	}

	@Override public boolean isSatisfiable(CE ce)
	{
		return this.droolsOWLAxiomHandler.getEquivalentClasses(OWLRDFVocabulary.OWL_NOTHING.getShortForm()).contains(ce.getceid());
	}

	@Override public CNodeSet getDisjointClasses(CE ce)
	{
		CNodeSet cNodeSet = new CNodeSet();

		for (String disjointClassID : this.droolsOWLAxiomHandler.getDisjointClasses(ce.getceid())) {
			CNode cNode = getEquivalentClasses(disjointClassID);
			cNodeSet.addNode(cNode);
		}
		return cNodeSet;
	}

	@Override public CNodeSet getSubClasses(CE ce, boolean direct)
	{
		CNodeSet cNodeSet = new CNodeSet();

		for (String subClassID : this.droolsOWLAxiomHandler.getSubClasses(ce.getceid(), direct)) {
			CNode cNode = getEquivalentClasses(subClassID);
			cNodeSet.addNode(cNode);
		}
		return cNodeSet;
	}

	@Override public CNodeSet getSuperClasses(CE ce, boolean direct)
	{
		CNodeSet cNodeSet = new CNodeSet();

		for (String superClassID : this.droolsOWLAxiomHandler.getSuperClasses(ce.getceid(), direct)) {
			CNode cNode = getEquivalentClasses(superClassID);
			cNodeSet.addNode(cNode);
		}
		return cNodeSet;
	}

	@Override public CNode getEquivalentClasses(CE ce)
	{
		return getEquivalentClasses(ce.getceid());
	}

	// Individuals

	@Override public CNodeSet getTypes(I i, boolean direct)
	{
		return null; // TODO
	}

	@Override public INodeSet getInstances(CE ce, boolean direct)
	{
		return null; // TODO
	}

	@Override public INode getSameIndividuals(I individual) { return getSameIndividuals(individual.getid()); }

	@Override public INodeSet getDifferentIndividuals(I individual)
	{
		INodeSet iNodeSet = new INodeSet();

		for (String individualID : this.droolsOWLAxiomHandler.getDifferentIndividuals(individual.getid())) {
			INode iNode = getSameIndividuals(individualID);
			iNodeSet.addNode(iNode);
		}
		return iNodeSet;
	}

	// Object properties

	@Override public OPNode getTopObjectPropertyNode()
	{
		OPNode opNode = new OPNode();

		for (String propertyID : this.droolsOWLAxiomHandler.getEquivalentClasses(OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getShortForm())) {
			OP op = resolveOWLObjectProperty(propertyID);
			opNode.add(op);
		}
		return opNode;
	}

	@Override public OPNode getBottomObjectPropertyNode()
	{
		OPNode opNode = new OPNode();

		for (String propertyID : this.droolsOWLAxiomHandler.getEquivalentClasses(OWLRDFVocabulary.OWL_BOTTOM_OBJECT_PROPERTY.getShortForm())) {
			OP op = resolveOWLObjectProperty(propertyID);
			opNode.add(op);
		}
		return opNode;
	}

	@Override public OPNodeSet getSuperObjectProperties(OP op, boolean direct)
	{
		OPNodeSet opNodeSet = new OPNodeSet();

		for (String superPropertyID : this.droolsOWLAxiomHandler.getSuperObjectProperties(op.getid(), direct)) {
			OPNode opNode = getEquivalentObjectProperties(superPropertyID);
			opNodeSet.addNode(opNode);
		}
		return opNodeSet;
	}

	@Override public OPNodeSet getSubObjectProperties(OP op, boolean direct)
	{
		OPNodeSet opNodeSet = new OPNodeSet();

		for (String subPropertyID : this.droolsOWLAxiomHandler.getSubObjectProperties(op.getid(), direct)) {
			OPNode opNode = getEquivalentObjectProperties(subPropertyID);
			opNodeSet.addNode(opNode);
		}
		return opNodeSet;
	}

	@Override public OPNode getEquivalentObjectProperties(OP op) { return getEquivalentObjectProperties(op.getid()); }

	@Override public OPNodeSet getDisjointObjectProperties(OPE ope)
	{
		OPNodeSet opNodeSet = new OPNodeSet();

		for (String disjointPropertyID : this.droolsOWLAxiomHandler.getDisjointObjectProperties(ope.getid())) {
			OPNode opNode = getEquivalentObjectProperties(disjointPropertyID);
			opNodeSet.addNode(opNode);
		}
		return opNodeSet;
	}

	@Override public CNodeSet getObjectPropertyDomains(OPE ope, boolean direct)
	{
		CNodeSet cNodeSet = new CNodeSet();

		for (String superClassID : this.droolsOWLAxiomHandler.getObjectPropertyDomains(ope.getid(), direct)) {
			CNode cNode = getEquivalentClasses(superClassID);
			cNodeSet.addNode(cNode);
		}
		return cNodeSet;
	}

	@Override public CNodeSet getObjectPropertyRanges(OPE ope, boolean direct)
	{
		CNodeSet cNodeSet = new CNodeSet();

		for (String superClassID : this.droolsOWLAxiomHandler.getObjectPropertyRanges(ope.getid(), direct)) {
			CNode cNode = getEquivalentClasses(superClassID);
			cNodeSet.addNode(cNode);
		}
		return cNodeSet;
	}

	@Override public INodeSet getObjectPropertyValues(I individual, OPE ope)
	{
		INodeSet iNodeSet = new INodeSet();

		for (String individualID : this.droolsOWLAxiomHandler.getObjectPropertyValuesForIndividual(individual.getid(),
				ope.getid())) {
			INode iNode = getSameIndividuals(individualID);
			iNodeSet.addNode(iNode);
		}
		return iNodeSet;
	}

	@Override public DroolsNode<OPE> getInverseObjectProperties(OPE ope)
	{
		return null; // TODO
	}

	// Data properties

	@Override public DPNode getTopDataPropertyNode()
	{
		DPNode dpNode = new DPNode();

		for (String propertyID : this.droolsOWLAxiomHandler.getEquivalentClasses(OWLRDFVocabulary.OWL_TOP_DATA_PROPERTY.getShortForm())) {
			DP dp = resolveOWLDataProperty(propertyID);
			dpNode.add(dp);
		}
		return dpNode;
	}

	@Override public DPNode getBottomDataPropertyNode()
	{
		DPNode dpNode = new DPNode();

		for (String propertyID : this.droolsOWLAxiomHandler.getEquivalentClasses(OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getShortForm())) {
			DP dp = resolveOWLDataProperty(propertyID);
			dpNode.add(dp);
		}
		return dpNode;
	}

	@Override public Set<L> getDataPropertyValues(I individual, DP dp)
	{
		return this.droolsOWLAxiomHandler.getDataPropertyValuesForIndividual(individual.getid(), dp.getid());
	}

	@Override public DPNodeSet getDisjointDataProperties(DPE dpe)
	{
		DPNodeSet dpNodeSet = new DPNodeSet();

		for (String disjointPropertyID : this.droolsOWLAxiomHandler.getDisjointDataProperties(dpe.getid())) {
			DPNode dpNode = getEquivalentDataProperties(disjointPropertyID);
			dpNodeSet.addNode(dpNode);
		}
		return dpNodeSet;
	}

	@Override public DPNode getEquivalentDataProperties(DP dp) {	return getEquivalentDataProperties(dp.getid()); }

	@Override public DPNodeSet getSubDataProperties(DP dp, boolean direct)
	{
		DPNodeSet dpNodeSet = new DPNodeSet();

		for (String subPropertyID : this.droolsOWLAxiomHandler.getSubDataProperties(dp.getid(), direct)) {
		  DPNode dpNode = getEquivalentDataProperties(subPropertyID);
			dpNodeSet.addNode(dpNode);
		}
		return dpNodeSet;
	}

	@Override public DPNodeSet getSuperDataProperties(DP dp, boolean direct)
	{
		DPNodeSet dpNodeSet = new DPNodeSet();

		for (String subPropertyID : this.droolsOWLAxiomHandler.getSuperDataProperties(dp.getid(), direct)) {
			DPNode dpNode = getEquivalentDataProperties(subPropertyID);
			dpNodeSet.addNode(dpNode);
		}
		return dpNodeSet;
	}

	@Override public CNodeSet getDataPropertyDomains(DP dp, boolean direct)
	{
		CNodeSet cNodeSet = new CNodeSet();

		for (String superClassID : this.droolsOWLAxiomHandler.getDataPropertyDomains(dp.getid(), direct)) {
			CNode cNode = getEquivalentClasses(superClassID);
			cNodeSet.addNode(cNode);
		}
		return cNodeSet;
	}

	private CNode getEquivalentClasses(String classID)
	{
		CNode cNode = new CNode();

		for (String equivalentClassID : this.droolsOWLAxiomHandler.getEquivalentClasses(classID)) {
			C c = resolveOWLClass(equivalentClassID);
			cNode.add(c);
		}
		return cNode;
	}

	private INode getSameIndividuals(String individualID)
	{
		INode iNode = new INode();

		for (String sameIndividualID : this.droolsOWLAxiomHandler.getSameIndividual(individualID)) {
			I i = resolveOWLIndividual(sameIndividualID);
			iNode.add(i);
		}
		return iNode;
	}

	private OPNode getEquivalentObjectProperties(String propertyID)
	{
		OPNode opNode = new OPNode();

		for (String equivalentPropertyID : this.droolsOWLAxiomHandler.getEquivalentObjectProperties(propertyID)) {
			OP eop = resolveOWLObjectProperty(equivalentPropertyID);
			opNode.add(eop);
		}
		return opNode;
	}

	private DPNode getEquivalentDataProperties(String propertyID)
	{
		DPNode dpNode = new DPNode();

		for (String equivalentPropertyID : this.droolsOWLAxiomHandler.getEquivalentDataProperties(propertyID)) {
			DP edp = resolveOWLDataProperty(equivalentPropertyID);
			dpNode.add(edp);
		}
		return dpNode;
	}

	private C resolveOWLClass(String classID)
	{
		return null; // TODO
	}

	private I resolveOWLIndividual(String individualID)
	{
		return null; // TODO
	}

	private OP resolveOWLObjectProperty(String propertyID)
	{
		return null; // TODO
	}

	private DP resolveOWLDataProperty(String propertyID)
	{
		return null; // TODO
	}
}
