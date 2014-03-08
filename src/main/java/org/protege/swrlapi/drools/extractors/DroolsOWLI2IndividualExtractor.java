package org.protege.swrlapi.drools.extractors;

import org.protege.swrlapi.drools.owl.entities.I;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.protege.swrlapi.extractors.TargetRuleEngineExtractor;
import org.semanticweb.owlapi.model.OWLIndividual;

/**
 * Converts an OWL individual represented by the class {@link I} from a Portability API OWL individual.
 */
public interface DroolsOWLI2IndividualExtractor extends TargetRuleEngineExtractor
{
	OWLIndividual extract(I i) throws TargetRuleEngineException;
}