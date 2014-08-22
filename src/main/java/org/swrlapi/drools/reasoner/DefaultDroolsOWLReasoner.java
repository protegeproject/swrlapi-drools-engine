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
		String classID = ce.getceid();

		return null; // TODO
	}

	@Override public CNodeSet getSubClasses(CE ce, boolean direct)
	{
		String classID = ce.getceid();

		return null; // TODO
	}

	@Override public CNodeSet getSuperClasses(CE ce, boolean direct)
	{
		String classID = ce.getceid();

		return null; // TODO
	}

	@Override public CNode getEquivalentClasses(CE ce)
	{
		CNode cNode = new CNode();

		for (String classID : this.droolsOWLAxiomHandler.getEquivalentClasses(ce.getceid())) {
			C c = resolveOWLClass(classID);
			cNode.add(c);
		}
		return cNode;
	}

	@Override public CNodeSet getTypes(I i, boolean direct)
	{
		return null; // TODO
	}

	// Individuals

	@Override public INodeSet getInstances(CE ce, boolean direct)
	{
		return null; // TODO
	}

	@Override public INodeSet getSameIndividuals(I individual)
	{
		return null; // TODO
	}

	@Override public INodeSet getDifferentIndividuals(I individual)
	{
		return null; // TODO
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
		return null; // TODO
	}

	@Override public OPNodeSet getSubObjectProperties(OP op, boolean direct)
	{
		return null; // TODO
	}

	@Override public OPNode getEquivalentObjectProperties(OP op)
	{
		OPNode opNode = new OPNode();

		for (String propertyID : this.droolsOWLAxiomHandler.getEquivalentObjectProperties(op.getid())) {
			OP eop = resolveOWLObjectProperty(propertyID);
			opNode.add(eop);
		}
		return opNode;
	}

	@Override public OPNodeSet getDisjointObjectProperties(OPE ope)
	{
		return null; // TODO
	}

	@Override public CNodeSet getObjectPropertyDomains(OPE ope, boolean direct)
	{
		return null;
	}

	@Override public CNodeSet getObjectPropertyRanges(OPE ope, boolean direct)
	{
		return null; // TODO
	}

	@Override public INodeSet getObjectPropertyValues(I individual, OPE ope)
	{
		return null; // TODO
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
		return null; // TODO
	}

	@Override public DPNodeSet getDisjointDataProperties(DPE dpe)
	{
		return null;  // TODO
	}

	@Override public DPNode getEquivalentDataProperties(DP dp)
	{
		DPNode dpNode = new DPNode();

		for (String propertyID : this.droolsOWLAxiomHandler.getEquivalentObjectProperties(dp.getid())) {
			DP edp = resolveOWLDataProperty(propertyID);
			dpNode.add(edp);
		}
		return dpNode;
	}

	@Override public DPNodeSet getSubDataProperties(DP dp, boolean direct)
	{
		return null;  // TODO
	}

	@Override public DPNodeSet getSuperDataProperties(DP dp, boolean direct)
	{
		return null;  // TODO
	}

	@Override public CNodeSet getDataPropertyDomains(DP dp, boolean direct)
	{
		return null;  // TODO
	}

	private C resolveOWLClass(String classID)
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
