package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;

abstract class DroolsBinaryClassesAxiom extends DroolsBinaryObject<String, String> implements A
{
  private static final long serialVersionUID = 1L;

  protected DroolsBinaryClassesAxiom(@NonNull String class1ID, @NonNull String class2ID)
  {
    super(class1ID, class2ID);
  }

  @NonNull public String getc1id()
  {
    return getT1();
  }

  @NonNull public String getc2id()
  {
    return getT2();
  }
}