package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import dataflow.quals.SideEffectFree;
import org.swrlapi.drools.owl.core.DroolsUnaryObject;

abstract class DroolsUnaryPropertyAxiom extends DroolsUnaryObject<String> implements A
{
  private static final long serialVersionUID = 1L;

  protected DroolsUnaryPropertyAxiom(@NonNull String propertyID)
  {
    super(propertyID);
  }

  @NonNull public String getpid()
  {
    return getT1();
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return super.toString();
  }
}