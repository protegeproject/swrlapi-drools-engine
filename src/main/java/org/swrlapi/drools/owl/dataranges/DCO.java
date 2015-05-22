package org.swrlapi.drools.owl.dataranges;

import checkers.nullness.quals.NonNull;

/**
 * Class representing an OWL data complement of data range
 *
 * @see org.semanticweb.owlapi.model.OWLDataComplementOf
 */
public class DCO implements DR
{
  private static final long serialVersionUID = 1L;

  @NonNull private final String rid;
  @NonNull private final String complementRangeID;

  public DCO(@NonNull String rid, @NonNull String complementRangeID)
  {
    this.rid = rid;
    this.complementRangeID = complementRangeID;
  }

  @NonNull @Override public String getrid()
  {
    return this.rid;
  }

  @NonNull public String getComplementRangeID()
  {
    return this.complementRangeID;
  }
}
