package org.swrlapi.drools.owl.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.checkerframework.dataflow.qual.Deterministic;

import java.util.Objects;

public abstract class DroolsQuadObject<T1, T2, T3, T4>
{
  private final @NonNull T1 t1;
  private final @NonNull T2 t2;
  private final @NonNull T3 t3;
  private final @NonNull T4 t4;

  protected DroolsQuadObject(@NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3, @NonNull T4 t4)
  {
    this.t1 = t1;
    this.t2 = t2;
    this.t3 = t3;
    this.t4 = t4;
  }

  protected @NonNull T1 getT1()
  {
    return this.t1;
  }

  protected @NonNull T2 getT2()
  {
    return this.t2;
  }

  protected @NonNull T3 getT3()
  {
    return this.t3;
  }

  protected @NonNull T4 getT4()
  {
    return this.t4;
  }

   @SideEffectFree @Override public @NonNull String toString()
  {
    return "(" + this.t1 + ", " + this.t2 + ", " + this.t3 + ", " + this.t4 + ")";
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    DroolsQuadObject<? extends @NonNull Object, ? extends @NonNull Object, ? extends @NonNull Object, ? extends @NonNull Object> that = (DroolsQuadObject<? extends @NonNull Object, ? extends @NonNull Object, ? extends @NonNull Object, ? extends @NonNull Object>)o;

    if (!Objects.equals(t1, that.t1))
      return false;
    if (!Objects.equals(t2, that.t2))
      return false;
    if (!Objects.equals(t3, that.t3))
      return false;
    return Objects.equals(t4, that.t4);

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int result = t1 != null ? t1.hashCode() : 0;
    result = 31 * result + (t2 != null ? t2.hashCode() : 0);
    result = 31 * result + (t3 != null ? t3.hashCode() : 0);
    result = 31 * result + (t4 != null ? t4.hashCode() : 0);
    return result;
  }
}
