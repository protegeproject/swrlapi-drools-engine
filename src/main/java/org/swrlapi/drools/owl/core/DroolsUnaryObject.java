package org.swrlapi.drools.owl.core;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import dataflow.quals.SideEffectFree;
import org.checkerframework.dataflow.qual.Deterministic;

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

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "(" + this.t1 + ")";
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    DroolsUnaryObject<? extends @NonNull Object> that = (DroolsUnaryObject<? extends @NonNull Object>)o;

    return !(t1 != null ? !t1.equals(that.t1) : that.t1 != null);

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    return t1 != null ? t1.hashCode() : 0;
  }
}
