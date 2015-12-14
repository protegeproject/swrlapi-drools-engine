package org.swrlapi.drools.owl.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.checkerframework.dataflow.qual.Deterministic;

public abstract class DroolsQuadObject<T1, T2, T3, T4>
{
  @NonNull private final T1 t1;
  @NonNull private final T2 t2;
  @NonNull private final T3 t3;
  @NonNull private final T4 t4;

  protected DroolsQuadObject(@NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3, @NonNull T4 t4)
  {
    this.t1 = t1;
    this.t2 = t2;
    this.t3 = t3;
    this.t4 = t4;
  }

  @NonNull protected T1 getT1()
  {
    return this.t1;
  }

  @NonNull protected T2 getT2()
  {
    return this.t2;
  }

  @NonNull protected T3 getT3()
  {
    return this.t3;
  }

  @NonNull protected T4 getT4()
  {
    return this.t4;
  }

  @SideEffectFree @NonNull @Override public String toString()
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

    if (t1 != null ? !t1.equals(that.t1) : that.t1 != null)
      return false;
    if (t2 != null ? !t2.equals(that.t2) : that.t2 != null)
      return false;
    if (t3 != null ? !t3.equals(that.t3) : that.t3 != null)
      return false;
    return !(t4 != null ? !t4.equals(that.t4) : that.t4 != null);

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
