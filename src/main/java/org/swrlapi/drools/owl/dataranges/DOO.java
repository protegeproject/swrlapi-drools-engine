package org.swrlapi.drools.owl.dataranges;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.core.L;

import java.util.Set;

/**
 * Class representing an OWL data one of data range
 *
 * @see org.semanticweb.owlapi.model.OWLDataOneOf
 */
public class DOO implements DR
{
  private static final long serialVersionUID = 1L;

  @NonNull public final String rid;
  @NonNull public final Set<L> literals;

  public DOO(@NonNull String rid, @NonNull Set<L> literals)
  {
    this.rid = rid;
    this.literals = literals;
  }

  @NonNull @Override
  public String getrid()
  {
    return this.rid;
  }
}
