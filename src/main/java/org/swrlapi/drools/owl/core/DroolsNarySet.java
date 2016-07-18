package org.swrlapi.drools.owl.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;

import java.util.Set;

public abstract class DroolsNarySet<ID, E>
{
  @NonNull private final ID id;
  @NonNull private final Set<E> elements;

  protected DroolsNarySet(@NonNull ID id, @NonNull Set<E> elements)
  {
    this.id = id;
    this.elements = elements;
  }

  @NonNull public ID getID()
  {
    return this.id;
  }

  @NonNull public Set<E> getElements()
  {
    return this.elements;
  }

  @SideEffectFree @Deterministic @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    DroolsNarySet<? extends @NonNull Object, ? extends @NonNull Object> that = (DroolsNarySet<? extends @NonNull Object, ? extends @NonNull Object>)o;

    if (id != null ? !id.equals(that.id) : that.id != null)
      return false;
    return elements != null ? elements.equals(that.elements) : that.elements == null;

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (elements != null ? elements.hashCode() : 0);
    return result;
  }

  @SideEffectFree @Deterministic @Override public String toString()
  {
    return "(" + "id=" + id + ", elements=" + elements + ")";
  }
}
