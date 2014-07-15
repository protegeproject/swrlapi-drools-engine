package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractorBase;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.exceptions.TargetRuleEngineException;

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
		if (getIRIResolver().isOWLNamedIndividual(i.getName()))
			return getOWLDataFactory().getOWLNamedIndividual(prefixedName2IRI(i.getName()));
		else
			return getOWLDataFactory().getOWLAnonymousIndividual(i.getName());
	}
}
