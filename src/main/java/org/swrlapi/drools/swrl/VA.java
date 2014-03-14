package org.swrlapi.drools.swrl;

import org.swrlapi.core.arguments.SWRLVariableAtomArgument;
import org.swrlapi.drools.extractors.DroolsAA2SWRLAtomArgumentExtractor;
import org.swrlapi.exceptions.TargetRuleEngineException;

public class VA implements AA
{
	private final String variableName;

	public VA(String variableName)
	{
		this.variableName = variableName;
	}

	public String getVariableName()
	{
		return this.variableName;
	}

	@Override
	public SWRLVariableAtomArgument extract(DroolsAA2SWRLAtomArgumentExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "VA(?" + getVariableName() + ")";
	}
}
