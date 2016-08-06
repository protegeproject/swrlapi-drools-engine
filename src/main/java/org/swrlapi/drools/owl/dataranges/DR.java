package org.swrlapi.drools.owl.dataranges;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.drools.owl.core.OO;

/**
 * This interface represents an OWL data range in a Drools knowledge base. Subclasses will implement specific
 * data range types.
 * <p>
 * TODO Note that the implementation of data ranges in Drools is incomplete.
 *
 * @see org.semanticweb.owlapi.model.OWLDataRange
 */
public interface DR extends OO
{
  @NonNull String getdrid();
}
