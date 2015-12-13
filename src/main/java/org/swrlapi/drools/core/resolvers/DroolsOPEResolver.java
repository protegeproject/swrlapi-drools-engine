package org.swrlapi.drools.core.resolvers;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.properties.OPE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @see org.swrlapi.drools.owl.properties.OPE
 */
class DroolsOPEResolver
{
  @NonNull private final Map<@NonNull String, @NonNull OPE> pes;

  private int propertyExpressionIndex;

  public DroolsOPEResolver()
  {
    this.pes = new HashMap<>();
    this.propertyExpressionIndex = 0;
  }

  public void reset()
  {
    this.pes.clear();
    this.propertyExpressionIndex = 0;
  }

  public @NonNull OPE resolveOPE(@NonNull String pid)
  {
    if (this.pes.containsKey(pid))
      return pes.get(pid);
    else
      throw new IllegalArgumentException("unknown object property expression ID " + pid);
  }

  public boolean recordsOPEID(@NonNull String peid)
  {
    return this.pes.containsKey(peid);
  }

  public void recordOPE(@NonNull OPE pe)
  {
    this.pes.put(pe.getid(), pe);
  }

  @NonNull public Set<@NonNull OPE> getOPEs()
  {
    return new HashSet<>(this.pes.values());
  }

  @NonNull public String generateOPEID()
  {
    return "OPEID" + this.propertyExpressionIndex++;
  }
}
