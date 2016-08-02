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

  @NonNull private final String drid;
  @NonNull private final Set<@NonNull String> intersectionOfDataRangeIDs;

  public DIO(@NonNull String drid, @NonNull Set<@NonNull String> intersectionOfDataRangeIDs)
  {
    this.drid = drid;
    this.intersectionOfDataRangeIDs = intersectionOfDataRangeIDs;
  }

  @NonNull @Override public String getdrid()
  {
    return this.drid;
  }

  @NonNull public Set<@NonNull String> getIntersectionOfDataRangeIDs()
  {
    return this.intersectionOfDataRangeIDs;
  }
}
