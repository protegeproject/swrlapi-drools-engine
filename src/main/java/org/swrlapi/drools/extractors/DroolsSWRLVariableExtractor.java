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

	public String variableName2VariablePrefixedName(String variableName)
	{
		return ":" + variableName;
	}

	public IRI variableName2VariableIRI(String variableName) throws TargetRuleEngineException
	{
		String variablePrefixedName = variableName2VariablePrefixedName(variableName);

		return prefixedName2IRI(variablePrefixedName);
	}
}
