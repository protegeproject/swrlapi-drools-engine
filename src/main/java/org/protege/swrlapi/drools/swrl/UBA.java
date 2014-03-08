package org.protege.swrlapi.drools.swrl;

import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.drools.extractors.DroolsAA2SWRLAtomArgumentExtractor;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an unbound argument to a built-in
 */
public class UBA implements BA
{
	private final String variableName;

	public UBA(String variableName)
	{
		this.variableName = variableName;
	}

	public String getVariableName()
	{
		return this.variableName;
	}

	@Override
	public SWRLBuiltInArgument extract(DroolsAA2SWRLAtomArgumentExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "UBA(?" + getVariableName() + ")";
	}
}
