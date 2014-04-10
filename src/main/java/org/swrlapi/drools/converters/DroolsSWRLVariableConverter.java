package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.converters.TargetRuleEngineConverterBase;
import org.swrlapi.core.SWRLRuleEngineBridge;

public class DroolsSWRLVariableConverter extends TargetRuleEngineConverterBase
{
	public DroolsSWRLVariableConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	public String swrlVariable2DRL(SWRLVariable variable)
	{
		IRI variableIRI = variable.getIRI();
		String variableShortName = getOWLIRIResolver().iri2ShortName(variableIRI);

		return variableShortName2DRL(variableShortName);
	}

	public String swrlVariable2VariableName(SWRLVariable variable)
	{
		IRI variableIRI = variable.getIRI();
		String variableShortName = getOWLIRIResolver().iri2ShortName(variableIRI);

		return variableShortName2VariableName(variableShortName);
	}

	public String variableIRI2DRL(IRI variableIRI)
	{
		String variableShortName = getOWLIRIResolver().iri2ShortName(variableIRI);

		return variableShortName2DRL(variableShortName);
	}

	public String variableShortName2DRL(String variableShortName)
	{
		String variableName = variableShortName2VariableName(variableShortName);

		return variableName2DRL(variableName);
	}

	public String variableName2DRL(String variableName)
	{
		return "$" + variableName;
	}

	private String variableShortName2VariableName(String variableShortName)
	{
		String variableName = variableShortName.startsWith(":") ? variableShortName.substring(1) : variableShortName;

		return variableName;
	}
}
