package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;

abstract class DroolsBinaryClassesAxiom extends DroolsBinaryObject<String, String> implements A
{
  private static final long serialVersionUID = 1L;

  protected DroolsBinaryClassesAxiom(@NonNull String class1ID, @NonNull String class2ID)
  {
    super(class1ID, class2ID);
  }

  public @NonNull String getc1id()
  {
    return getT1();
  }

  public @NonNull String getc2id()
  {
    return getT2();
  }
}