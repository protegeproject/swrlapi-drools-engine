package org.swrlapi.drools.owl.dataranges;

import org.checkerframework.checker.nullness.qual.NonNull;

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
  void visit(@NonNull D dr);

  void visit(@NonNull DCO dr);

  void visit(@NonNull DIO dr);

  void visit(@NonNull DOO dr);

  void visit(@NonNull DUO dr);

  void visit(@NonNull DRR drr);

  void visit(@NonNull DTR dtr);
}
