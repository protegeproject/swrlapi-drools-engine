package org.swrlapi.drools.extractors;

import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.extractors.TargetRuleEngineExtractorBase;

public class DroolsSWRLVariableExtractor extends TargetRuleEngineExtractorBase
{
	public DroolsSWRLVariableExtractor(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	public String variableName2VariableShortName(String variableName)
	{
		return ":" + variableName;
	}
}
