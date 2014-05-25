package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractor;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Converts an OWL individual represented by the class {@link I} from an OWLAPI OWL individual.
 */
public interface DroolsOWLIndividualExtractor extends TargetRuleEngineExtractor
{
	OWLIndividual extract(I i) throws TargetRuleEngineException;
}