package org.swrlapi.drools.owl.core;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

public abstract class DroolsUnaryObject<T1>
{
  @NonNull private final T1 t1;

  protected DroolsUnaryObject(@NonNull T1 t1)
  {
    this.t1 = t1;
  }

  @NonNull protected T1 getT1()
  {
    return this.t1;
  }

  @NonNull @Override
  public String toString()
  {
    return "(" + this.t1 + ")";
  }

  @Override
  public boolean equals(@Nullable Object obj)
  {
    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    @SuppressWarnings("unchecked")
    DroolsUnaryObject<T1> ua = (DroolsUnaryObject<T1>)obj;
    return (getT1() == ua.getT1() || (getT1() != null && getT1().equals(ua.getT1())));
  }

  @Override
  public int hashCode()
  {
    int hash = 44;

    hash = hash + (null == getT1() ? 0 : getT1().hashCode());

    return hash;
  }
}
