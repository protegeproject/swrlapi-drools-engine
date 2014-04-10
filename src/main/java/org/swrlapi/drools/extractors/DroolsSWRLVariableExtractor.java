package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.exceptions.TargetRuleEngineException;
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

	public IRI variableName2VariableIRI(String variableName) throws TargetRuleEngineException
	{
		String variableShortName = variableName2VariableShortName(variableName);

		return shortName2IRI(variableShortName);
	}
}
