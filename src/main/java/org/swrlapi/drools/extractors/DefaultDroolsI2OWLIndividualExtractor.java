package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.extractors.TargetRuleEngineExtractorBase;

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
		if (getOWLIRIResolver().isOWLNamedIndividual(individual.getid()))
			return getSWRLAPIOWLDataFactory().getOWLNamedIndividual(getIRI(individual.getid()));
		else
			return getSWRLAPIOWLDataFactory().getOWLAnonymousIndividual(individual.getid());
	}
}
