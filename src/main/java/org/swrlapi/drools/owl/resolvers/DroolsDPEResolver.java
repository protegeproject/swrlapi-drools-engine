package org.swrlapi.drools.owl.resolvers;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.properties.DPE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @see org.swrlapi.drools.owl.properties.DPE
 */
class DroolsDPEResolver
{
  @NonNull private final Map<String, DPE> pes;

  private int propertyExpressionIndex;

  public DroolsDPEResolver()
  {
    this.pes = new HashMap<>();
    this.propertyExpressionIndex = 0;
  }

  public void reset()
  {
    this.pes.clear();
    this.propertyExpressionIndex = 0;
  }

  public boolean recordsDPEID(@NonNull String peid)
  {
    return this.pes.containsKey(peid);
  }

  public void recordDPE(@NonNull DPE pe)
  {
    this.pes.put(pe.getid(), pe);
  }

  @NonNull public Set<DPE> getDPEs()
  {
    return new HashSet<>(this.pes.values());
  }

  @NonNull public String generateDPEID()
  {
    return "DPEID" + this.propertyExpressionIndex++;
  }
}