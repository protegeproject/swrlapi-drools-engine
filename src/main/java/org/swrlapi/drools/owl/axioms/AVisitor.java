package org.swrlapi.drools.owl.axioms;

/**
 * A visitor that can visit a Drools representation of OWL axioms, represented by
 * {@link org.swrlapi.drools.owl.axioms.A}s.
 * <p>
 * It is modeled on the OWLAPI's {@link org.semanticweb.owlapi.model.OWLAxiomVisitor}.
 *
 * @see org.swrlapi.drools.owl.axioms.A
 * @see org.semanticweb.owlapi.model.OWLAxiomVisitor
 */
public interface AVisitor
{
	void visit(CDA cda);

	void visit(OPDA cda);

	void visit(DPDA cda);

	void visit(APDA cda);

	void visit(IDA cda);

	void visit(SCA sca);

	void visit(NOPAA nopa);

	void visit(AOPA aopa);

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

	void visit(IFOPA ifopa);

	void visit(SIA sia);

	void visit(IOPA iopa);

	// OWLReflexiveObjectPropertyAxiom not dealt with by OWL 2 RL so we ignore

	// An OWLDisjointUnionAxiom not dealt with by OWL 2 RL so we ignore

	// An OWLSubPropertyChainOfAxiom not asserted by OWL 2 RL rules so we do not need to record it

	// An OWLHasKeyAxiom not asserted by OWL 2 RL rules so we do not need to record it

	// An OWLDatatypeDefinitionAxiom not asserted by OWL 2 RL rules so we do not need to record it

	// A SWRLRule is not asserted by OWL 2 RL rules so we do not need to record it
}