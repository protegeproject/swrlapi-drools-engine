package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.OO;

/**
 * This interface represents an OWL class expression in a Drools knowledge base.
 * <p/>
 * We do not have an extractor associated with class expressions because our OWL 2 RL reasoner does not create new
 * class expressions during reasoning. Hence, we can simply keep track of the originally supplied class expressions
 * using its ID, which we keep track of using the {@link org.swrlapi.core.OWLClassExpressionResolver} class.
 */
public interface CE extends OO
{
	String getceid();
}
