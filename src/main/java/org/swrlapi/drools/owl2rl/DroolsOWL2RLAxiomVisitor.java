package org.swrlapi.drools.owl2rl;

import org.swrlapi.drools.owl.axioms.AOPA;
import org.swrlapi.drools.owl.axioms.APDA;
import org.swrlapi.drools.owl.axioms.CAA;
import org.swrlapi.drools.owl.axioms.CDA;
import org.swrlapi.drools.owl.axioms.DCA;
import org.swrlapi.drools.owl.axioms.DDPA;
import org.swrlapi.drools.owl.axioms.DIA;
import org.swrlapi.drools.owl.axioms.DJDPA;
import org.swrlapi.drools.owl.axioms.DJOPA;
import org.swrlapi.drools.owl.axioms.DOPA;
import org.swrlapi.drools.owl.axioms.DPAA;
import org.swrlapi.drools.owl.axioms.DPDA;
import org.swrlapi.drools.owl.axioms.DPRA;
import org.swrlapi.drools.owl.axioms.ECA;
import org.swrlapi.drools.owl.axioms.EDPA;
import org.swrlapi.drools.owl.axioms.EOPA;
import org.swrlapi.drools.owl.axioms.FDPA;
import org.swrlapi.drools.owl.axioms.FOPA;
import org.swrlapi.drools.owl.axioms.IDA;
import org.swrlapi.drools.owl.axioms.IFOPA;
import org.swrlapi.drools.owl.axioms.IOPA;
import org.swrlapi.drools.owl.axioms.IROPA;
import org.swrlapi.drools.owl.axioms.NDPAA;
import org.swrlapi.drools.owl.axioms.NOPAA;
import org.swrlapi.drools.owl.axioms.OPAA;
import org.swrlapi.drools.owl.axioms.OPDA;
import org.swrlapi.drools.owl.axioms.OPRA;
import org.swrlapi.drools.owl.axioms.SCA;
import org.swrlapi.drools.owl.axioms.SDPA;
import org.swrlapi.drools.owl.axioms.SIA;
import org.swrlapi.drools.owl.axioms.SOPA;
import org.swrlapi.drools.owl.axioms.SPA;
import org.swrlapi.drools.owl.axioms.TOPA;

/**
 * A visitor that can visit a Drools representation of OWL axioms ({@link org.swrlapi.drools.owl.axioms.A}).
 * Its primary role is to provide a visitor to deal with axioms asserted by a Drools OWL 2 RL reasoner.
 * </p>
 * It is based on the OWLAPI's {@link org.semanticweb.owlapi.model.OWLAxiomVisitor} but only defines visitor
 * methods for axioms that are asserted by an OWL 2 RL reasoner. If an axiom is not asserted by an OWL 2
 * RL reasoner then it is not handled by this visitor.
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

	// A SWRLRule is not asserted by OWL 2 RL rules so we do not need to record it
}