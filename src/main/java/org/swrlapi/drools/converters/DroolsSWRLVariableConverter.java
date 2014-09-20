package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;

import java.util.Set;

public class DroolsSWRLVariableConverter extends TargetRuleEngineConverterBase
{
	public DroolsSWRLVariableConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	public String swrlVariable2DRL(SWRLVariable variable)
	{
		IRI variableIRI = variable.getIRI();
		String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);

		return variablePrefixedName2DRL(variablePrefixedName);
	}

	public String swrlVariable2VariableName(SWRLVariable variable)
	{
		IRI variableIRI = variable.getIRI();
		String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);

		return variablePrefixedName2VariableName(variablePrefixedName);
	}

	public String swrlVariable2VariablePrefixedName(SWRLVariable variable)
	{
		IRI variableIRI = variable.getIRI();

		return getIRIResolver().iri2PrefixedName(variableIRI);
	}

	public String variableIRI2DRL(IRI variableIRI)
	{
		String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);

		return variablePrefixedName2DRL(variablePrefixedName);
	}

	public String variablePrefixedName2DRL(String variablePrefixedName)
	{
		String variableName = variablePrefixedName2VariableName(variablePrefixedName);

		return variableName2DRL(variableName);
	}

	public String variableName2DRL(String variableName)
	{
		return "$" + variableName;
	}

	public String variablePrefixedName2DRL(String variablePrefixedName, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames)
	{
		if (previouslyEncounteredVariablePrefixedNames.contains(variablePrefixedName)) {
			return fieldName + "==" + variablePrefixedName2DRL(variablePrefixedName);
		} else {
			previouslyEncounteredVariablePrefixedNames.add(variablePrefixedName);
			return variablePrefixedName2DRL(variablePrefixedName) + ":" + fieldName;
		}
	}

	public String variablePrefixedName2VariableName(String variablePrefixedName)
	{
		return variablePrefixedName.startsWith(":") ? variablePrefixedName.substring(1) : variablePrefixedName;
	}
}
