package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.drools.owl.core.OO;

/**
 * This interface represents an OWL class expression in a Drools knowledge base.
 * <p>
 * We do not have an extractor associated with class expressions because our OWL 2 RL reasoner does not create new class
 * expressions during reasoning. Hence, we can simply keep track of the originally supplied class expressions using its
 * ID, which we keep track of using the {@link org.swrlapi.core.OWLObjectResolver} class.
 *
 * @see org.semanticweb.owlapi.model.OWLClassExpression
 */
public interface CE extends OO
{
  @NonNull String getceid();
}
