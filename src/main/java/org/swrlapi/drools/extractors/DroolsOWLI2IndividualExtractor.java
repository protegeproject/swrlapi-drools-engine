package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.extractors.TargetRuleEngineExtractor;

/**
 * Converts an OWL individual represented by the class {@link I} from an OWLAPI OWL individual.
 */
public interface DroolsOWLI2IndividualExtractor extends TargetRuleEngineExtractor
{
	OWLIndividual extract(I i) throws TargetRuleEngineException;
}