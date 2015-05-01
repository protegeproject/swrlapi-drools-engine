package org.swrlapi.drools.owl.properties;

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
  void visit(AP pe);

  void visit(DP pe);

  void visit(OP pe);
}
