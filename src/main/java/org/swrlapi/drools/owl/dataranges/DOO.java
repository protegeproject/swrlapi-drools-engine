package org.swrlapi.drools.owl.dataranges;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.drools.owl.literals.L;

import java.util.Collections;
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

  @NonNull public final String drid;
  @NonNull public final Set<@NonNull L> literals;

  public DOO(@NonNull String drid, @NonNull Set<@NonNull L> literals)
  {
    this.drid = drid;
    this.literals = literals;
  }

  @NonNull @Override public String getdrid()
  {
    return this.drid;
  }

  @NonNull Set<@NonNull L> getLiterals() { return Collections.unmodifiableSet(this.literals); }
}
