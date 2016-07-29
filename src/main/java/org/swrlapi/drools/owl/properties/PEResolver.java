package org.swrlapi.drools.owl.properties;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * Resolves property expression identifiers to their corresponding Drools {@link PE} object.
 *
 * @see org.swrlapi.drools.owl.properties.PE
 * @see org.semanticweb.owlapi.model.OWLObjectPropertyExpression
 * @see org.semanticweb.owlapi.model.OWLDataPropertyExpression
 */
public interface PEResolver
{
  @NonNull OWLObjectPropertyExpression resolveOWLObjectPropertyExpression(@NonNull String peid);

  @NonNull OWLDataPropertyExpression resolveOWLDataPropertyExpression(@NonNull String peid);

  @NonNull OWLAnnotationProperty resolveOWLAnnotationProperty(@NonNull String pid);
}
