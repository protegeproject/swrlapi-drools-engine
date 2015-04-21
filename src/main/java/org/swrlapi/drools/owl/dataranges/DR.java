package org.swrlapi.drools.owl.dataranges;

import org.swrlapi.drools.owl.core.OO;

/**
 * This interface represents an OWL data range in a Drools knowledge base.
 * <p>
 * We do not have an extractor associated with class expressions because our OWL 2 RL reasoner does not create new data
 * ranges during reasoning. Hence, we can simply keep track of the originally supplied data range using its ID, which we
 * keep track of using the {@link org.swrlapi.core.resolvers.OWLDataRangeResolver} class.
 *
 * @see org.semanticweb.owlapi.model.OWLDataRange
 */
public interface DR extends OO
{
	String getrid();
}
