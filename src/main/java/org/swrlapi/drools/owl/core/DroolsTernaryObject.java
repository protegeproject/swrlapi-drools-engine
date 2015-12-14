package org.swrlapi.drools.owl.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.checkerframework.dataflow.qual.Deterministic;

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

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    DroolsTernaryObject<? extends @NonNull Object, ? extends @NonNull Object, ? extends @NonNull Object> that
        = (DroolsTernaryObject<? extends @NonNull Object, ? extends @NonNull Object, ? extends @NonNull Object>)o;

    if (t1 != null ? !t1.equals(that.t1) : that.t1 != null)
      return false;
    if (t2 != null ? !t2.equals(that.t2) : that.t2 != null)
      return false;
    return !(t3 != null ? !t3.equals(that.t3) : that.t3 != null);

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int result = t1 != null ? t1.hashCode() : 0;
    result = 31 * result + (t2 != null ? t2.hashCode() : 0);
    result = 31 * result + (t3 != null ? t3.hashCode() : 0);
    return result;
  }
}
