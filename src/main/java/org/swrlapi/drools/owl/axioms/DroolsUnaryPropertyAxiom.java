package org.swrlapi.drools.owl.axioms;

import org.swrlapi.drools.owl.core.DroolsUnaryObject;

abstract class DroolsUnaryPropertyAxiom extends DroolsUnaryObject<String> implements A
{
  private static final long serialVersionUID = 1L;

  protected DroolsUnaryPropertyAxiom(String propertyID)
  {
    super(propertyID);
  }

  public String getpid()
  {
    return getT1();
  }

  @Override
  public String toString()
  {
    return super.toString();
  }
}