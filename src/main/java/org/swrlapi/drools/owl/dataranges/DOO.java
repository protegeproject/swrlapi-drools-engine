package org.swrlapi.drools.owl.dataranges;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.drools.owl.literals.L;

import java.util.Set;

// TODO data one of data range are not implemented

/**
 * Class representing an OWL data one of data range
 *
 * @see org.semanticweb.owlapi.model.OWLDataOneOf
 */
public class DOO implements DR
{
  private static final long serialVersionUID = 1L;

  @NonNull public final String rid;
  @NonNull public final Set<@NonNull L> literals;

  public DOO(@NonNull String rid, @NonNull Set<@NonNull L> literals)
  {
    this.rid = rid;
    this.literals = literals;
  }

  @NonNull @Override public String getdrid()
  {
    return this.rid;
  }
}
