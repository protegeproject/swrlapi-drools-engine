package org.swrlapi.drools.core.resolvers;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.properties.OP;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @see OP
 */
class DroolsOPResolver
{
  private final @NonNull Map<String, OP> ops;

  private int propertyIndex;

  public DroolsOPResolver()
  {
    this.ops = new HashMap<>();
    this.propertyIndex = 0;
  }

  public void reset()
  {
    this.ops.clear();
    this.propertyIndex = 0;
  }

  public boolean recordsOPID(@NonNull String pid)
  {
    return this.ops.containsKey(pid);
  }

  public @NonNull OP resolveOP(@NonNull String pid)
  {
    if (this.ops.containsKey(pid))
      return ops.get(pid);
    else
      throw new IllegalArgumentException("unknown object property ID " + pid);
  }

  public void recordOP(@NonNull OP p)
  {
    this.ops.put(p.getid(), p);
  }

  public @NonNull Set<OP> getOPs()
  {
    return new HashSet<>(this.ops.values());
  }

  @NonNull public String generateOPID()
  {
    return "OPID" + this.propertyIndex++;
  }
}
