package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
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
}