package org.swrlapi.drools.resolvers;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.classexpressions.CE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @see org.swrlapi.drools.owl.classexpressions.CE
 */
public class DroolsCEResolver
{
  @NonNull private final Map<String, CE> ces;
  private int classExpressionIndex;

  public DroolsCEResolver()
  {
    this.ces = new HashMap<>();
    this.classExpressionIndex = 0;
  }

  public void reset()
  {
    this.ces.clear();
    this.classExpressionIndex = 0;
  }

  public boolean recordsCEID(@NonNull String ceid)
  {
    return this.ces.containsKey(ceid);
  }

  public void record(@NonNull CE ce)
  {
    this.ces.put(ce.getceid(), ce);
  }

  @NonNull public Set<CE> getCEs()
  {
    return new HashSet<>(this.ces.values());
  }

  @NonNull public String generateCEID()
  {
    return "CEID" + this.classExpressionIndex++;
  }
}
