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

  private final @NonNull String drid;
  private final @NonNull Set<@NonNull String> unionOfDataRangeIDs;

  public DUO(@NonNull String drid, @NonNull Set<@NonNull String> unionOfDataRangeIDs)
  {
    this.drid = drid;
    this.unionOfDataRangeIDs = unionOfDataRangeIDs;
  }

  @Override public @NonNull String getdrid()
  {
    return this.drid;
  }

  public @NonNull Set<@NonNull String> getUnionOfDataRangeIDs()
  {
    return this.unionOfDataRangeIDs;
  }
}
