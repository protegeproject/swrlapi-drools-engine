package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;

/**
 * Resolves class expression identifiers to their corresponding Drools {@link CE} object.
 *
 * @see org.swrlapi.drools.owl.classes.CE
 */
public interface CEResolver
{
  @NonNull OWLClassExpression resolveOWLClassExpression(@NonNull String ceid);

  @NonNull OWLClass resolveOWLClass(@NonNull String ceid);

  @NonNull OWLDataAllValuesFrom resolveOWLDataAllValuesFrom(@NonNull String ceid);

  @NonNull OWLDataExactCardinality resolveOWLDataExactCardinality(@NonNull String ceid);

  @NonNull OWLDataHasValue resolveOWLDataHasValue(@NonNull String ceid);

  @NonNull OWLDataMaxCardinality resolveOWLDataMaxCardinality(@NonNull String ceid);

  @NonNull OWLDataMinCardinality resolveOWLDataMinCardinality(@NonNull String ceid);

  @NonNull OWLDataSomeValuesFrom resolveOWLDataSomeValuesFrom(@NonNull String ceid);

  @NonNull OWLObjectAllValuesFrom resolveOWLObjectAllValuesFrom(@NonNull String ceid);

  @NonNull OWLObjectExactCardinality resolveOWLObjectExactCardinality(@NonNull String ceid);

  @NonNull OWLObjectHasValue resolveOWLObjectHasValue(@NonNull String ceid);

  @NonNull OWLObjectIntersectionOf resolveOWLObjectIntersectionOf(@NonNull String ceid);

  @NonNull OWLObjectMaxCardinality resolveOWLObjectMaxCardinality(@NonNull String ceid);

  @NonNull OWLObjectMinCardinality resolveOWLObjectMinCardinality(@NonNull String ceid);

  @NonNull OWLObjectSomeValuesFrom resolveOWLObjectSomeValuesFrom(@NonNull String ceid);

  @NonNull OWLObjectUnionOf resolveOWLObjectUnionOf(@NonNull String ceid);
}
