package org.swrlapi.drools.owl.properties;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A visitor that can visit a Drools representation of OWL property expressions, represented by
 * {@link org.swrlapi.drools.owl.properties.PE}s.
 * <p>
 * It is modeled on the OWLAPI's {@link org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor}.
 *
 * @see org.swrlapi.drools.owl.properties.PE
 * @see org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor
 */
public interface PEVisitor
{
  void visit(@NonNull AP pe);

  void visit(@NonNull DP pe);

  void visit(@NonNull OP pe);
}
