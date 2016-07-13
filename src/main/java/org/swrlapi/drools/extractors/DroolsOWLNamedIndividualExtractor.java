package org.swrlapi.drools.extractors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractor;
import org.swrlapi.drools.factory.DefaultDroolsOWLNamedIndividualExtractor;
import org.swrlapi.drools.owl.individuals.I;

/**
 * Converts a Drools OWL individual to an OWLAPI OWL individual.
 *
 * @see org.semanticweb.owlapi.model.OWLIndividual
 * @see I
 * @see DefaultDroolsOWLNamedIndividualExtractor
 */
public interface DroolsOWLNamedIndividualExtractor extends TargetRuleEngineExtractor
{
  @NonNull OWLNamedIndividual extract(@NonNull I i);
}