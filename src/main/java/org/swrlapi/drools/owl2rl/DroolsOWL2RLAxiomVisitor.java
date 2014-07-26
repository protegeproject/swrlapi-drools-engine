package org.swrlapi.drools.owl2rl;

import org.swrlapi.drools.owl.axioms.*;

/**
 * A visitor that can visit a Drools representation of OWL axioms ({@link org.swrlapi.drools.owl.axioms.A}).
 * Its primary role is to deal with axioms asserted by Drools' OWL 2 RL reasoner.
 * </p>
 * It is based on the OWLAPI's {@link org.semanticweb.owlapi.model.OWLAxiomVisitor} but only defines visitor
 * methods for axioms that are asserted by an OWL 2 RL reasoner. If an axiom is not asserted by an OWL 2
 * RL reasoner then it is not handled.
 *
 * @see org.swrlapi.drools.owl.axioms.A
 * @see org.semanticweb.owlapi.model.OWLAxiomVisitor
 * @see org.swrlapi.drools.owl2rl.DroolsOWL2RLEngine
 * @see org.swrlapi.drools.owl2rl.DroolsOWL2RLRules
 */
public interface DroolsOWL2RLAxiomVisitor
{
	void visit(CDA cda);

	void visit(OPDA cda);

	void visit(DPDA cda);

	void visit(APDA cda);

	void visit(IDA cda);

	void visit(SCA sca);

	void visit(NOPAA nopa);

	void visit(AOPA aopa);

	// OWLReflexiveObjectPropertyAxiom not dealt with by OWL 2 RL so we ignore

	void visit(DCA dca);

	void visit(DDPA ddpa);

	void visit(DOPA dopa);

	void visit(EOPA eopa);

	void visit(NDPAA ndpaa);

	void visit(DIA dia);

	void visit(DJDPA djdpa);

	void visit(DJOPA djopa);

	void visit(OPRA opra);

	void visit(OPAA opaa);

	void visit(FOPA fopa);

	void visit(SOPA sopa);

	// An OWLDisjointUnionAxiom not dealt with by OWL 2 RL so we ignore

	void visit(SPA spa);

	void visit(DPRA dpra);

	void visit(FDPA fdpa);

	void visit(EDPA edpa);

	void visit(CAA caa);

	void visit(ECA eca);

	void visit(DPAA dpaa);

	void visit(TOPA TOPA);

	void visit(IROPA iropa);

	void visit(SDPA sdpa);

	void visit(IFOPA IFOPA);

	void visit(SIA sia);

	// An OWLSubPropertyChainOfAxiom not asserted by OWL 2 RL rules so we do not need to record it

	void visit(IOPA iopa);

	// An OWLHasKeyAxiom not asserted by OWL 2 RL rules so we do not need to record it

	// An OWLDatatypeDefinitionAxiom not asserted by OWL 2 RL rules so we do not need to record it

	// A SWRLRule not asserted by OWL 2 RL rules so we do not need to record it
}