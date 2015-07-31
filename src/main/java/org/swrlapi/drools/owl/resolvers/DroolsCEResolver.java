package org.swrlapi.drools.owl.resolvers;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.classexpressions.CE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @see org.swrlapi.drools.owl.classexpressions.CE
 */
class DroolsCEResolver
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

  public @NonNull CE resolveCE(@NonNull String ceid)
  {
    if (this.ces.containsKey(ceid))
      return ces.get(ceid);
    else
      throw new IllegalArgumentException("unknown class expression ID " + ceid);
  }

  public void recordCE(@NonNull CE ce)
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
