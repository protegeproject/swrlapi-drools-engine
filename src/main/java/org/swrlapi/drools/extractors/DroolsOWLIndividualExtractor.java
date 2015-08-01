package org.swrlapi.drools.extractors;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractor;
import org.swrlapi.drools.factory.DefaultDroolsOWLIndividualExtractor;
import org.swrlapi.drools.owl.individuals.I;

/**
 * Converts a Drools OWL individual to an OWLAPI OWL individual.
 *
 * @see org.semanticweb.owlapi.model.OWLIndividual
 * @see I
 * @see DefaultDroolsOWLIndividualExtractor
 */
public interface DroolsOWLIndividualExtractor extends TargetRuleEngineExtractor
{
  @NonNull OWLIndividual extract(@NonNull I i);
}