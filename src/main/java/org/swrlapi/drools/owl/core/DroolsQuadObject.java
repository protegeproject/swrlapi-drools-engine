package org.swrlapi.drools.owl.core;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;

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

  @NonNull @Override
  public String toString()
  {
    return "(" + this.t1 + ", " + this.t2 + ", " + this.t3 + ", " + this.t4 + ")";
  }

  @Override
  public boolean equals(@Nullable Object obj)
  {

    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    @SuppressWarnings("unchecked")
    DroolsQuadObject<T1, T2, T3, T4> ba = (DroolsQuadObject<T1, T2, T3, T4>)obj;
    return (getT1() == ba.getT1() || (getT1() != null && getT1().equals(ba.getT1())))
        && (getT2() == ba.getT2() || (getT2() != null && getT2().equals(ba.getT2())))
        && (getT3() == ba.getT3() || (getT3() != null && getT3().equals(ba.getT3())))
        && (getT4() == ba.getT4() || (getT4() != null && getT4().equals(ba.getT4())));
  }

  @Override
  public int hashCode()
  {
    int hash = 94;

    hash = hash + (null == getT1() ? 0 : getT1().hashCode());
    hash = hash + (null == getT2() ? 0 : getT2().hashCode());
    hash = hash + (null == getT3() ? 0 : getT3().hashCode());
    hash = hash + (null == getT4() ? 0 : getT4().hashCode());

    return hash;
  }
}
