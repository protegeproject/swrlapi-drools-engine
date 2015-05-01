package org.swrlapi.drools.owl.dataranges;

/**
 * A visitor that can visit a Drools representation of OWL data ranges, represented by
 * {@link org.swrlapi.drools.owl.dataranges.DR}s.
 * <p>
 * It is modeled on the OWLAPI's {@link org.semanticweb.owlapi.model.OWLDataRangeVisitor}.
 *
 * @see org.swrlapi.drools.owl.dataranges.DR
 * @see org.semanticweb.owlapi.model.OWLDataRangeVisitor
 */
public interface DRVisitor
{
  void visit(DCO dr);

  void visit(DIO dr);

  void visit(DOO dr);

  void visit(DUO dr);

  void visit(DRR drr);
}
