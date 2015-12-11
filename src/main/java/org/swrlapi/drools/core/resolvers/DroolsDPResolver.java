package org.swrlapi.drools.core.resolvers;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.properties.DP;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @see DP
 */
class DroolsDPResolver
{
  private final @NonNull Map<@NonNull String, DP> dps;

  private int propertyIndex;

  public DroolsDPResolver()
  {
    this.dps = new HashMap<>();
    this.propertyIndex = 0;
  }

  public void reset()
  {
    this.dps.clear();
    this.propertyIndex = 0;
  }

  public boolean recordsDPID(@NonNull String pid)
  {
    return this.dps.containsKey(pid);
  }

  public @NonNull DP resolveDP(@NonNull String pid)
  {
    if (this.dps.containsKey(pid))
      return dps.get(pid);
    else
      throw new IllegalArgumentException("unknown data property ID " + pid);
  }

  public void recordDP(@NonNull DP p)
  {
    this.dps.put(p.getid(), p);
  }

  public @NonNull Set<DP> getDPs()
  {
    return new HashSet<>(this.dps.values());
  }

  @NonNull public String generateDPID()
  {
    return "DPID" + this.propertyIndex++;
  }
}
