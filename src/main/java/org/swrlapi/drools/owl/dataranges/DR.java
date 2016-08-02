package org.swrlapi.drools.owl.dataranges;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.drools.owl.core.OO;

/**
 * This interface represents an OWL data range in a Drools knowledge base. Subclasses will implement specific
 * data range types.
 * <p>
 * We do not have an extractor associated with data ranges because our Drools OWL 2 RL reasoner does not create new data
 * ranges during reasoning. Hence, we can simply keep track of the originally supplied data range using its ID,
 * a task performed by the {@link org.swrlapi.core.OWLObjectResolver} class.
 * <p>
 * TODO Note that the implementation of data ranges in Drools is incomplete.
 *
 * @see org.semanticweb.owlapi.model.OWLDataRange
 */
public interface DR extends OO
{
  @NonNull String getdrid();
}
