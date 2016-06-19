package org.swrlapi.drools.owl.dataranges;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Set;

// TODO Data intersection of data range not implemented

/**
 * Class representing an OWL data intersection of data range
 *
 * @see org.semanticweb.owlapi.model.OWLDataIntersectionOf
 */
public class DIO implements DR
{
  private static final long serialVersionUID = 1L;

  @NonNull private final String rid;
  @NonNull private final Set<@NonNull String> dataRangeIDs;

  public DIO(@NonNull String rid, @NonNull Set<@NonNull String> dataRangeIDs)
  {
    this.rid = rid;
    this.dataRangeIDs = dataRangeIDs;
  }

  @NonNull @Override
  public String getdrid()
  {
    return this.rid;
  }

  @NonNull public Set<@NonNull String> getDataRangeIDs()
  {
    return this.dataRangeIDs;
  }
}
