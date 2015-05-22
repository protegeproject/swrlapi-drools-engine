package org.swrlapi.drools.owl.core;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

public abstract class DroolsBinaryObject<T1, T2>
{
  @NonNull private final T1 t1;
  @NonNull private final T2 t2;

  protected DroolsBinaryObject(@NonNull T1 t1, @NonNull T2 t2)
  {
    this.t1 = t1;
    this.t2 = t2;
  }

  @NonNull public T1 getT1()
  {
    return this.t1;
  }

  @NonNull public T2 getT2()
  {
    return this.t2;
  }

  @Override
  public boolean equals(@Nullable Object obj)
  {

    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    @SuppressWarnings("unchecked")
    DroolsBinaryObject<T1, T2> ba = (DroolsBinaryObject<T1, T2>)obj;
    return (getT1() == ba.getT1() || (getT1() != null && getT1().equals(ba.getT1())))
        && (getT2() == ba.getT2() || (getT2() != null && getT2().equals(ba.getT2())));
  }

  @Override
  public int hashCode()
  {
    int hash = 61;

    hash = hash + (null == getT1() ? 0 : getT1().hashCode());
    hash = hash + (null == getT2() ? 0 : getT2().hashCode());

    return hash;
  }

  @Override
  public String toString()
  {
    return "(" + this.t1 + ", " + this.t2 + ")";
  }
}
