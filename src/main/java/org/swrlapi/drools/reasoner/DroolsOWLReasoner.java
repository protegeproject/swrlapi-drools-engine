package org.swrlapi.drools.reasoner;

import org.swrlapi.drools.owl.axioms.A;
import org.swrlapi.drools.owl.classexpressions.CE;
import org.swrlapi.drools.owl.core.C;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl.core.L;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.DPE;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.drools.owl.properties.OPE;

import java.util.Set;

/**
 * A Drools-based equivalent of the OWLAPI {@link org.semanticweb.owlapi.reasoner.OWLReasoner} interface.
 *
 * @see org.semanticweb.owlapi.reasoner.OWLReasoner
 * @see org.swrlapi.drools.owl.axioms.A
 * @see org.swrlapi.drools.owl.classexpressions.CE
 * @see org.swrlapi.drools.owl.core.I
 * @see org.swrlapi.drools.owl.properties.OPE
 * @see org.swrlapi.drools.owl.properties.DPE
 */
public interface DroolsOWLReasoner
{
	boolean isConsistent();

	// Axioms

	boolean isEntailed(A a);

	boolean isEntailed(Set<? extends A> axioms);

	// Classes

	CNode getUnsatisfiableClasses();

	CNode getTopClassNode();

	CNode getBottomClassNode();

	boolean isSatisfiable(CE ce);

	CNodeSet getDisjointClasses(CE ce);

	CNodeSet getSubClasses(CE ce, boolean direct);

	CNodeSet getSuperClasses(CE ce, boolean direct);

	CNode getEquivalentClasses(CE ce);

	CNodeSet getTypes(I i, boolean direct);

	// Individuals

	INodeSet getInstances(CE ce, boolean direct);

	INode getSameIndividuals(I individual);

	INodeSet getDifferentIndividuals(I individual);

	// Object properties

	OPNode getTopObjectPropertyNode();

	OPNode getBottomObjectPropertyNode();

	OPNodeSet getSuperObjectProperties(OP op, boolean direct);

	OPNodeSet getSubObjectProperties(OP op, boolean direct);

	OPNode getEquivalentObjectProperties(OP op);

	OPNodeSet getDisjointObjectProperties(OPE ope);

	CNodeSet getObjectPropertyDomains(OPE ope, boolean direct);

	CNodeSet getObjectPropertyRanges(OPE ope, boolean direct);

	INodeSet getObjectPropertyValues(I individual, OPE ope);

	DroolsNode<OPE> getInverseObjectProperties(OPE ope);

	// Data properties

	DPNode getTopDataPropertyNode();

	DPNode getBottomDataPropertyNode();

	Set<L> getDataPropertyValues(I individual, DP dp);

	DPNodeSet getDisjointDataProperties(DPE dpe);

	DPNode getEquivalentDataProperties(DP dp);

	DPNodeSet getSubDataProperties(DP dp, boolean direct);

	DPNodeSet getSuperDataProperties(DP dp, boolean direct);

	CNodeSet getDataPropertyDomains(DP dp, boolean direct);
}
