package org.swrlapi.drools.owl.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.checkerframework.dataflow.qual.Deterministic;

import java.util.Objects;

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

   @NonNull @SideEffectFree @Override public String toString()
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

    return !(!Objects.equals(t1, that.t1));

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    return t1 != null ? t1.hashCode() : 0;
  }
}
