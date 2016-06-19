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

  @NonNull private final String rid;

  public DRR(@NonNull String rid)
  {
    this.rid = rid;
  }

  @NonNull @Override
  public String getdrid()
  {
    return this.rid;
  }
}
