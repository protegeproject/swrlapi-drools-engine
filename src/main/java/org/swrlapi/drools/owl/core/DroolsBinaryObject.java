package org.swrlapi.drools.owl.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.checkerframework.dataflow.qual.Deterministic;

import java.util.Objects;

public abstract class DroolsBinaryObject<T1, T2>
{
  private final @NonNull T1 t1;
  private final @NonNull T2 t2;

  protected DroolsBinaryObject(@NonNull T1 t1, @NonNull T2 t2)
  {
    this.t1 = t1;
    this.t2 = t2;
  }

  public @NonNull T1 getT1()
  {
    return this.t1;
  }

  public @NonNull T2 getT2()
  {
    return this.t2;
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    DroolsBinaryObject<? extends @NonNull Object, ? extends @NonNull Object> that = (DroolsBinaryObject<? extends @NonNull Object, ? extends @NonNull Object>)o;

    if (!Objects.equals(t1, that.t1))
      return false;
    return Objects.equals(t2, that.t2);

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int result = t1 != null ? t1.hashCode() : 0;
    result = 31 * result + (t2 != null ? t2.hashCode() : 0);
    return result;
  }

   @SideEffectFree @Override public @NonNull String toString()
  {
    return "(" + this.t1 + ", " + this.t2 + ")";
  }
}
