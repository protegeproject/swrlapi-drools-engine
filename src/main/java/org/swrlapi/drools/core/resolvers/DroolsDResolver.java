package org.swrlapi.drools.core.resolvers;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.dataranges.D;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @see D
 */
class DroolsDResolver
{
  private final @NonNull Map<String, D> ds;

  private int datatypeIndex;

  public DroolsDResolver()
  {
    this.ds = new HashMap<>();
    this.datatypeIndex = 0;
  }

  public void reset()
  {
    this.ds.clear();
    this.datatypeIndex = 0;
  }

  public boolean recordsDID(@NonNull String did)
  {
    return this.ds.containsKey(did);
  }

  public @NonNull D resolveD(@NonNull String did)
  {
    if (this.ds.containsKey(did))
      return ds.get(did);
    else
      throw new IllegalArgumentException("unknown datatype ID " + did);
  }

  public void recordD(@NonNull D p)
  {
    this.ds.put(p.getid(), p);
  }

  public @NonNull Set<D> getDs()
  {
    return new HashSet<>(this.ds.values());
  }

  @NonNull public String generateDID()
  {
    return "DID" + this.datatypeIndex++;
  }
}
