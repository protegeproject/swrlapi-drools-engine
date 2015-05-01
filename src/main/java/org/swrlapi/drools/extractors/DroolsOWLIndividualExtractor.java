package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractor;
import org.swrlapi.drools.owl.core.I;

/**
 * Converts a Drools OWL individual to an OWLAPI OWL individual.
 *
 * @see org.semanticweb.owlapi.model.OWLIndividual
 * @see org.swrlapi.drools.owl.core.I
 * @see org.swrlapi.drools.extractors.DefaultDroolsOWLIndividualExtractor
 */
public interface DroolsOWLIndividualExtractor extends TargetRuleEngineExtractor
{
  OWLIndividual extract(I i);
}