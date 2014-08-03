package org.swrlapi.drools.reasoner;

import org.swrlapi.drools.owl.axioms.A;
import org.swrlapi.drools.owl.classexpressions.CE;
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
 * {@link org.swrlapi.drools.owl2rl.DroolsOWLAxiomHandler} which contains all asserted and inferred OWL axioms.
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

	@Override public boolean isEntailed(A a)
	{
		return this.droolsOWLAxiomHandler.isEntailed(a);
	}

	@Override public boolean isEntailed(Set<? extends A> axioms)
	{
		return this.droolsOWLAxiomHandler.isEntailed(axioms);
	}

	@Override public CNode getUnsatisfiableClasses()
	{
		return null;
	}

	@Override public CNode getTopClassNode()
	{
		return null;
	}

	@Override public CNode getBottomClassNode()
	{
		return null;
	}

	@Override public boolean isSatisfiable(CE ce)
	{
		return false;
	}

	@Override public CNodeSet getDisjointClasses(CE ce)
	{
		return null;
	}

	@Override public CNodeSet getSubClasses(CE ce, boolean direct)
	{
		return null;
	}

	@Override public CNodeSet getSuperClasses(CE ce, boolean direct)
	{
		return null;
	}

	@Override public CNode getEquivalentClasses(CE ce)
	{
		return null;
	}

	@Override public CNodeSet getTypes(I i, boolean direct)
	{
		return null;
	}

	@Override public INodeSet getInstances(CE ce, boolean direct)
	{
		return null;
	}

	@Override public INodeSet getSameIndividuals(I individual)
	{
		return null;
	}

	@Override public INodeSet getDifferentIndividuals(I individual)
	{
		return null;
	}

	@Override public OPNode getTopObjectPropertyNode()
	{
		return null;
	}

	@Override public OPNode getBottomObjectPropertyNode()
	{
		return null;
	}

	@Override public OPNodeSet getSuperObjectProperties(OP op, boolean direct)
	{
		return null;
	}

	@Override public OPNodeSet getSubObjectProperties(OP op, boolean direct)
	{
		return null;
	}

	@Override public OPNode getEquivalentObjectProperties(OP op)
	{
		return null;
	}

	@Override public OPNodeSet getDisjointObjectProperties(OPE ope)
	{
		return null;
	}

	@Override public CNodeSet getObjectPropertyDomains(OPE ope, boolean direct)
	{
		return null;
	}

	@Override public CNodeSet getObjectPropertyRanges(OPE ope, boolean direct)
	{
		return null;
	}

	@Override public INodeSet getObjectPropertyValues(I individual, OPE ope)
	{
		return null;
	}

	@Override public DroolsNode<OPE> getInverseObjectProperties(OPE ope)
	{
		return null;
	}

	@Override public DPNode getTopDataPropertyNode()
	{
		return null;
	}

	@Override public DPNode getBottomDataPropertyNode()
	{
		return null;
	}

	@Override public Set<L> getDataPropertyValues(I individual, DP dp)
	{
		return null;
	}

	@Override public DPNodeSet getDisjointDataProperties(DPE dpe)
	{
		return null;
	}

	@Override public DPNode getEquivalentDataProperties(DP dp)
	{
		return null;
	}

	@Override public DPNodeSet getSubDataProperties(DP dp, boolean direct)
	{
		return null;
	}

	@Override public DPNodeSet getSuperDataProperties(DP dp, boolean direct)
	{
		return null;
	}

	@Override public CNodeSet getDataPropertyDomains(DP dp, boolean direct)
	{
		return null;
	}
}
