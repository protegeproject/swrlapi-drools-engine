package org.protege.swrlapi.drools.owl.entities;

import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.drools.extractors.DroolsAA2SWRLAtomArgumentExtractor;
import org.protege.swrlapi.drools.extractors.DroolsOE2OWLEntityExtractor;
import org.protege.swrlapi.drools.swrl.BA;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLDataProperty;

/**
 * This class represents an OWL data property.
 */
public class DP extends P
{
	public DP(String propertyID)
	{
		super(propertyID);
	}

	/**
	 * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
	 */
	public DP(BA ba)
	{
		super("<InProcess>");

		if (ba instanceof DP) {
			DP p = (DP)ba;
			setID(p.getid());
		} else
			throw new RuntimeException("expecting OWL data property from bound built-in argument, got "
					+ ba.getClass().getCanonicalName());
	}

	@Override
	public OWLDataProperty extract(DroolsOE2OWLEntityExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public SWRLBuiltInArgument extract(DroolsAA2SWRLAtomArgumentExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}
}
