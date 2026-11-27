package org.swrlapi.drools.owl.core;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;

import java.util.Objects;
import java.util.Set;

public abstract class DroolsNarySet<ID, E>
{
  private final @NonNull ID id;
  private final @NonNull Set<@NonNull E> elements;

  protected DroolsNarySet(@NonNull ID id, @NonNull Set<@NonNull E> elements)
  {
    this.id = id;
    this.elements = elements;
  }

  public @NonNull ID getID()
  {
    return this.id;
  }

  public @NonNull Set<@NonNull E> getElements()
  {
    return this.elements;
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    DroolsNarySet<? extends @NonNull Object, ? extends @NonNull Object> that = (DroolsNarySet<? extends @NonNull Object, ? extends @NonNull Object>)o;

    if (!Objects.equals(id, that.id))
      return false;
    return Objects.equals(elements, that.elements);

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (elements != null ? elements.hashCode() : 0);
    return result;
  }

  @SideEffectFree @Deterministic @Override public @NonNull String toString()
  {
    return "(" + "id=" + id + ", elements=" + elements + ")";
  }
}
