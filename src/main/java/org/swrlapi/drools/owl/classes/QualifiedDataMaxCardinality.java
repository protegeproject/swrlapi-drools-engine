package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;

/** Retains the data range while preserving the existing converter's DMaxCCE return type. */
public final class QualifiedDataMaxCardinality extends DMaxCCE
{
  private static final long serialVersionUID = 1L;
  private final String filler;

  public QualifiedDataMaxCardinality(String id, String property, String filler, int cardinality) {
    super(id, property, cardinality);
    this.filler = filler;
  }

  public @NonNull String getFiller() { return this.filler; }
}
