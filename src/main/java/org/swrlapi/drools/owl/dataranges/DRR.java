package org.swrlapi.drools.owl.dataranges;

// TODO Datatype restriction data range are not implemented

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Class representing an OWL datatype restriction data range
 *
 * @see org.semanticweb.owlapi.model.OWLDatatypeRestriction
 */
public class DRR implements DR
{
  private static final long serialVersionUID = 1L;

  private final @NonNull String drid;

  public DRR(@NonNull String drid)
  {
    this.drid = drid;
  }

  @Override public @NonNull String getdrid()
  {
    return this.drid;
  }
}
