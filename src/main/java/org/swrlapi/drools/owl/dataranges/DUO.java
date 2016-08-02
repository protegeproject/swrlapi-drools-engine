package org.swrlapi.drools.owl.dataranges;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Set;

// TODO Data union of data range not implemented

/**
 * Class representing an OWL data union of data range
 *
 * @see org.semanticweb.owlapi.model.OWLDataUnionOf
 */
public class DUO implements DR
{
  private static final long serialVersionUID = 1L;

  @NonNull private final String drid;
  @NonNull private final Set<@NonNull String> unionOfDataRangeIDs;

  public DUO(@NonNull String drid, @NonNull Set<@NonNull String> unionOfDataRangeIDs)
  {
    this.drid = drid;
    this.unionOfDataRangeIDs = unionOfDataRangeIDs;
  }

  @NonNull @Override public String getdrid()
  {
    return this.drid;
  }

  @NonNull public Set<@NonNull String> getUnionOfDataRangeIDs()
  {
    return this.unionOfDataRangeIDs;
  }
}
