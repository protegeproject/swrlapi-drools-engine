package org.swrlapi.drools.core.resolvers;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.classes.C;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @see C
 */
class DroolsCResolver
{
  @NonNull private final Map<@NonNull String, @NonNull C> cs;

  private int classIndex;

  public DroolsCResolver()
  {
    this.cs = new HashMap<>();
    this.classIndex = 0;
  }

  public void reset()
  {
    this.cs.clear();
    this.classIndex = 0;
  }

  public boolean recordsCID(@NonNull String cid)
  {
    return this.cs.containsKey(cid);
  }

  public @NonNull C resolveC(@NonNull String cid)
  {
    if (this.cs.containsKey(cid))
      return cs.get(cid);
    else
      throw new IllegalArgumentException("unknown class ID " + cid);
  }

  public void recordC(@NonNull C c)
  {
    this.cs.put(c.getceid(), c);
  }

  @NonNull public Set<@NonNull C> getCs()
  {
    return new HashSet<>(this.cs.values());
  }

  @NonNull public String generateCID()
  {
    return "CID" + this.classIndex++;
  }
}
