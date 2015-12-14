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

  @NonNull private final String rid;
  @NonNull private final Set<@NonNull String> dataRangeIDs;

  public DUO(@NonNull String rid, @NonNull Set<@NonNull String> dataRangeIDs)
  {
    this.rid = rid;
    this.dataRangeIDs = dataRangeIDs;
  }

  @NonNull @Override
  public String getrid()
  {
    return this.rid;
  }

  @NonNull public Set<@NonNull String> getDataRangeIDs()
  {
    return this.dataRangeIDs;
  }
}
