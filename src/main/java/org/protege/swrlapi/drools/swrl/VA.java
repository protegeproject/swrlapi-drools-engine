package org.protege.swrlapi.drools.swrl;

import org.protege.swrlapi.core.arguments.SWRLVariableAtomArgument;
import org.protege.swrlapi.drools.extractors.DroolsAA2SWRLAtomArgumentExtractor;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

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
