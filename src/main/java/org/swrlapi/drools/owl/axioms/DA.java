package org.swrlapi.drools.owl.axioms;

import org.swrlapi.drools.owl.core.DroolsUnaryObject;
import org.swrlapi.drools.owl.core.OE;

/**
 * Base class representing an OWL declaration axiom.
 *
 * @see org.semanticweb.owlapi.model.OWLDeclarationAxiom
 */
abstract class DA<T1 extends OE> extends DroolsUnaryObject<T1> implements A
{
  private static final long serialVersionUID = 1L;

  DA(T1 entity)
  {
    super(entity);
  }

  public T1 getE()
  {
    return getT1();
  }

  @Override
  public String toString()
  {
    return getE().toString();
  }
}