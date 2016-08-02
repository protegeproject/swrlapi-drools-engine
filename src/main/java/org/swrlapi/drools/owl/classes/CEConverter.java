package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLClass;
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
 * A visitor that can visit a Drools representation of OWL class expressions, represented by
 * {@link org.swrlapi.drools.owl.classes.CE}s, and return an OWLAPI representation of that class expression.
 * <p>
 * It is modeled on the OWLAPI's {@link org.semanticweb.owlapi.model.OWLClassExpressionVisitor}.
 *
 * @see org.swrlapi.drools.owl.classes.CE
 * @see org.semanticweb.owlapi.model.OWLClassExpressionVisitor
 */
public interface CEConverter
{
  @NonNull OWLClass convert(@NonNull C c);

  @NonNull OWLDataAllValuesFrom convert(@NonNull DAVFCE ce);

  @NonNull OWLDataExactCardinality convert(@NonNull DECCE ce);

  @NonNull OWLDataHasValue convert(@NonNull DHVCE ce);

  @NonNull OWLDataMaxCardinality convert(@NonNull DMaxCCE ce);

  @NonNull OWLDataMinCardinality convert(@NonNull DMinCCE ce);

  @NonNull OWLDataSomeValuesFrom convert(@NonNull DSVFCE ce);

  @NonNull OWLObjectAllValuesFrom convert(@NonNull OAVFCE ce);

  @NonNull OWLObjectExactCardinality convert(@NonNull OECCE ce);

  @NonNull OWLObjectHasValue convert(@NonNull OHVCE ce);

  @NonNull OWLObjectIntersectionOf convert(@NonNull OIOCE ce);

  @NonNull OWLObjectMaxCardinality convert(@NonNull OMaxCCE ce);

  @NonNull OWLObjectMinCardinality convert(@NonNull OMinCCE ce);

  @NonNull OWLObjectSomeValuesFrom convert(@NonNull OSVFCE ce);

  @NonNull OWLObjectUnionOf convert(@NonNull OUOCE ce);
}
