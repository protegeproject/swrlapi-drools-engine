package org.protege.swrlapi.drools.owl.entities;

import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.drools.extractors.DroolsAA2SWRLAtomArgumentExtractor;
import org.protege.swrlapi.drools.extractors.DroolsOE2OWLEntityExtractor;
import org.protege.swrlapi.drools.extractors.DroolsOWLI2IndividualExtractor;
import org.protege.swrlapi.drools.swrl.BA;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 * This class represents an OWL named or anonymous individual.
 */
public class I extends OE
{
	public I(String individualId)
	{
		super(individualId);
	}

	/**
	 * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
	 */
	public I(BA ba)
	{
		super("<InProcess>");

		if (ba instanceof I) {
			I i = (I)ba;
			setID(i.getid());
		} else
			throw new RuntimeException("expecting OWL individual from bound built-in argument, got "
					+ ba.getClass().getCanonicalName());
	}

	public OWLIndividual extract(DroolsOWLI2IndividualExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public OWLNamedIndividual extract(DroolsOE2OWLEntityExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public SWRLBuiltInArgument extract(DroolsAA2SWRLAtomArgumentExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}
}
