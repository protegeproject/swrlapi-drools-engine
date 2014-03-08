package org.protege.swrlapi.drools.extractors;

import org.protege.swrlapi.core.SWRLRuleEngineBridge;
import org.protege.swrlapi.drools.owl.entities.I;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.protege.swrlapi.extractors.TargetRuleEngineExtractorBase;
import org.semanticweb.owlapi.model.OWLIndividual;

/**
 * Converts an OWL individual represented by the class {@link I} from a Portability API OWL individual.
 */
public class DefaultDroolsI2OWLIndividualExtractor extends TargetRuleEngineExtractorBase implements
		DroolsOWLI2IndividualExtractor
{
	public DefaultDroolsI2OWLIndividualExtractor(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	@Override
	public OWLIndividual extract(I individual) throws TargetRuleEngineException
	{
		if (getOWLNamedObjectResolver().isOWLNamedIndividual(individual.getid()))
			return getOWLDataFactory().getOWLNamedIndividual(getIRI(individual.getid()));
		else
			return getOWLDataFactory().getOWLAnonymousIndividual(individual.getid());
	}
}
