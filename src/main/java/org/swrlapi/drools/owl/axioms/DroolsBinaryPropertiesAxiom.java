package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import dataflow.quals.SideEffectFree;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;

abstract class DroolsBinaryPropertiesAxiom extends DroolsBinaryObject<String, String> implements A
{
  private static final long serialVersionUID = 1L;

  protected DroolsBinaryPropertiesAxiom(@NonNull String propertyID1, @NonNull String propertyID2)
  {
    super(propertyID1, propertyID2);
  }

  @NonNull public String getp1id()
  {
    return getT1();
  }

  @NonNull public String getp2id()
  {
    return getT2();
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return super.toString();
  }
}