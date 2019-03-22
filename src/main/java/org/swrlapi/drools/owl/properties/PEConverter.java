package org.swrlapi.drools.owl.properties;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/***
 * @see org.swrlapi.drools.owl.properties.PE
 * @see org.swrlapi.drools.owl.properties.OPE
 * @see org.swrlapi.drools.owl.properties.DPE
 * @see org.swrlapi.drools.owl.properties.AP
 */
public interface PEConverter
{
  @NonNull OWLObjectPropertyExpression convert(@NonNull OPE pe);

  @NonNull OWLDataPropertyExpression convert(@NonNull DPE pe);

  @NonNull OWLAnnotationProperty convert(@NonNull AP ap);
}
