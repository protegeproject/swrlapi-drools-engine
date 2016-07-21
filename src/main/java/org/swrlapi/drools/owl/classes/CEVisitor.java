package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A visitor that can visit a Drools representation of OWL class expressions, represented by
 * {@link org.swrlapi.drools.owl.classes.CE}s.
 * <p>
 * It is modeled on the OWLAPI's {@link org.semanticweb.owlapi.model.OWLClassExpressionVisitor}.
 *
 * @see org.swrlapi.drools.owl.classes.CE
 * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor
 */
public interface CEVisitor
{
  void visit(@NonNull DAVFCE ce);

  void visit(@NonNull DECCE ce);

  void visit(@NonNull DHVCE ce);

  void visit(@NonNull DMaxCCE ce);

  void visit(@NonNull DMaxQCCE ce);

  void visit(@NonNull DMinCCE ce);

  void visit(@NonNull DSVFCE ce);

  void visit(@NonNull OAVFCE ce);

  void visit(@NonNull OECCE ce);

  void visit(@NonNull OHVCE ce);

  void visit(@NonNull OIOCE ce);

  void visit(@NonNull OMaxCCE ce);

  void visit(@NonNull OMaxQCCE ce);

  void visit(@NonNull OMinCCE ce);

  void visit(@NonNull OCOCE ce);

  void visit(@NonNull OSVFCE ce);

  void visit(@NonNull OUOCE ce);
}
