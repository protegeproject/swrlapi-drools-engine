package org.swrlapi.drools.owl.core;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import dataflow.quals.Deterministic;
import dataflow.quals.SideEffectFree;

public abstract class DroolsTernaryObject<T1, T2, T3>
{
  @NonNull private final T1 t1;
  @NonNull private final T2 t2;
  @NonNull private final T3 t3;

  protected DroolsTernaryObject(@NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3)
  {
    this.t1 = t1;
    this.t2 = t2;
    this.t3 = t3;
  }

  @NonNull public T1 getT1()
  {
    return this.t1;
  }

  @NonNull public T2 getT2()
  {
    return this.t2;
  }

  @NonNull public T3 getT3()
  {
    return this.t3;
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "(" + this.t1 + ", " + this.t2 + ", " + this.t3 + ")";
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object obj)
  {
    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    @SuppressWarnings("unchecked") DroolsTernaryObject<T1, T2, T3> ba = (DroolsTernaryObject<T1, T2, T3>)obj;
    return (getT1() == ba.getT1() || (getT1() != null && getT1().equals(ba.getT1()))) && (getT2() == ba.getT2() || (
      getT2() != null && getT2().equals(ba.getT2()))) && (getT3() == ba.getT3() || (getT3() != null && getT3()
      .equals(ba.getT3())));
  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int hash = 64;

    hash = hash + (null == getT1() ? 0 : getT1().hashCode());
    hash = hash + (null == getT2() ? 0 : getT2().hashCode());
    hash = hash + (null == getT3() ? 0 : getT3().hashCode());

    return hash;
  }
}
