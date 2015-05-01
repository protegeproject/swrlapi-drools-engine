package org.swrlapi.drools.resolvers;

import org.swrlapi.drools.owl.properties.DPE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @see org.swrlapi.drools.owl.properties.DPE
 */
public class DroolsDPEResolver
{
  private final Map<String, DPE> pes;
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

  public boolean recordsPEID(String peid)
  {
    return this.pes.containsKey(peid);
  }

  public void record(DPE pe)
  {
    this.pes.put(pe.getid(), pe);
  }

  public Set<DPE> getPEs()
  {
    return new HashSet<>(this.pes.values());
  }

  public String generatePEID()
  {
    return "DPEID" + this.propertyExpressionIndex++;
  }
}