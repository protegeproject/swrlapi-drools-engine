package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.drools.owl.core.DroolsUnaryObject;

abstract class DroolsUnaryIndividualAxiom extends DroolsUnaryObject<String> implements A
{
  private static final long serialVersionUID = 1L;

  protected DroolsUnaryIndividualAxiom(@NonNull String individualID)
  {
    super(individualID);
  }

  @NonNull public String getiid()
  {
    return getT1();
  }
}