package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.extractors.TargetRuleEngineExtractorBase;

/**
 * Converts an OWL individual represented by the class {@link I} to an OWLAPI OWL individual.
 */
public class DefaultDroolsI2OWLIndividualExtractor extends TargetRuleEngineExtractorBase implements
		DroolsOWLIndividualExtractor
{
	public DefaultDroolsI2OWLIndividualExtractor(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	@Override
	public OWLIndividual extract(I i) throws TargetRuleEngineException
	{
		if (getOWLIRIResolver().isOWLNamedIndividual(i.getName()))
			return getOWLDataFactory().getOWLNamedIndividual(shortName2IRI(i.getName()));
		else
			return getOWLDataFactory().getOWLAnonymousIndividual(i.getName());
	}
}
