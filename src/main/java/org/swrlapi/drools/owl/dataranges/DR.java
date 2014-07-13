package org.swrlapi.drools.owl.dataranges;

import org.swrlapi.drools.owl.OO;

/**
 * This interface represents an OWL data range in a Drools knowledge base.
 * <p/>
 * We do not have an extractor associated with class expressions because our OWL 2 RL reasoner does not create new data
 * ranges during reasoning. Hence, we can simply keep track of the originally supplied data range using its ID, which
 * we keep ttack of using the {@link OWLDataRangeResolver} class.
 */
public interface DR extends OO
{
	String getdrid();
}
