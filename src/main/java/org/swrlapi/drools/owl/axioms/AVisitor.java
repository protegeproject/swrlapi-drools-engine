package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;

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
  void visit(@NonNull CDA cda);

  void visit(@NonNull OPDA cda);

  void visit(@NonNull DPDA cda);

  void visit(@NonNull APDA cda);

  void visit(@NonNull IDA cda);

  void visit(@NonNull SCA sca);

  void visit(@NonNull NOPAA nopa);

  void visit(@NonNull AOPA aopa);

  void visit(@NonNull DCA dca);

  void visit(@NonNull DDPA ddpa);

  void visit(@NonNull DOPA dopa);

  void visit(@NonNull EOPA eopa);

  void visit(@NonNull NDPAA ndpaa);

  void visit(@NonNull DIA dia);

  void visit(@NonNull DJDPA djdpa);

  void visit(@NonNull DJOPA djopa);

  void visit(@NonNull OPRA opra);

  void visit(@NonNull OPAA opaa);

  void visit(@NonNull FOPA fopa);

  void visit(@NonNull SOPA sopa);

  void visit(@NonNull SPA spa);

  void visit(@NonNull DPRA dpra);

  void visit(@NonNull FDPA fdpa);

  void visit(@NonNull EDPA edpa);

  void visit(@NonNull CAA caa);

  void visit(@NonNull ECA eca);

  void visit(@NonNull DPAA dpaa);

  void visit(@NonNull TOPA TOPA);

  void visit(@NonNull IROPA iropa);

  void visit(@NonNull SDPA sdpa);

  void visit(@NonNull IFOPA ifopa);

  void visit(@NonNull SIA sia);

  void visit(@NonNull IOPA iopa);

  // OWLReflexiveObjectPropertyAxiom not dealt with by OWL 2 RL so we ignore

  // An OWLDisjointUnionAxiom not dealt with by OWL 2 RL so we ignore

  // An OWLSubPropertyChainOfAxiom not asserted by OWL 2 RL rules so we do not need to recordOWLClassExpression it

  // An OWLHasKeyAxiom not asserted by OWL 2 RL rules so we do not need to recordOWLClassExpression it

  // An OWLDatatypeDefinitionAxiom not asserted by OWL 2 RL rules so we do not need to recordOWLClassExpression it

  // A SWRLRule is not asserted by OWL 2 RL rules so we do not need to recordOWLClassExpression it
}