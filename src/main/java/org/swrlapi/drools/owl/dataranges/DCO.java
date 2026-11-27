package org.swrlapi.drools.owl.dataranges;

import org.checkerframework.checker.nullness.qual.NonNull;

// TODO Data complement of data range not implemented

/**
 * Class representing an OWL data complement of data range
 *
 * @see org.semanticweb.owlapi.model.OWLDataComplementOf
 */
public class DCO implements DR
{
  private static final long serialVersionUID = 1L;

  private final @NonNull String drid;
  private final @NonNull String complementDataRangeID;

  public DCO(@NonNull String drid, @NonNull String complementDataRangeID)
  {
    this.drid = drid;
    this.complementDataRangeID = complementDataRangeID;
  }

  @Override public @NonNull String getdrid()
  {
    return this.drid;
  }

  public @NonNull String getComplementDataRangeID()
  {
    return this.complementDataRangeID;
  }
}
