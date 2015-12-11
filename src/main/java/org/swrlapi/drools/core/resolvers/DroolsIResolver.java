package org.swrlapi.drools.core.resolvers;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.individuals.I;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @see I
 */
class DroolsIResolver
{
  private final @NonNull Map<@NonNull String, I> is;

  private int individualIndex;

  public DroolsIResolver()
  {
    this.is = new HashMap<>();
    this.individualIndex = 0;
  }

  public void reset()
  {
    this.is.clear();
    this.individualIndex = 0;
  }

  public boolean recordsIID(@NonNull String iid)
  {
    return this.is.containsKey(iid);
  }

  public @NonNull I resolveI(@NonNull String iid)
  {
    if (this.is.containsKey(iid))
      return is.get(iid);
    else
      throw new IllegalArgumentException("unknown individual ID " + iid);
  }

  public void recordI(@NonNull I i)
  {
    this.is.put(i.getid(), i);
  }

  public @NonNull Set<I> getIs()
  {
    return new HashSet<>(this.is.values());
  }

  @NonNull public String generateIID()
  {
    return "IID" + this.individualIndex++;
  }
}
