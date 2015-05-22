package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.core.DroolsUnaryObject;

abstract class DroolsUnaryClassAxiom extends DroolsUnaryObject<String> implements A
{
  private static final long serialVersionUID = 1L;

  protected DroolsUnaryClassAxiom(@NonNull String classID)
  {
    super(classID);
  }

  @NonNull public String getcid()
  {
    return getT1();
  }

  @NonNull @Override
  public String toString()
  {
    return super.toString();
  }
}