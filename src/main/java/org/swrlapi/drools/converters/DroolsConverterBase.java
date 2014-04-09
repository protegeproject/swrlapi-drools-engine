package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.converters.TargetRuleEngineConverterBase;
import org.swrlapi.core.SWRLRuleEngineBridge;

/**
 * Base class providing functionality common to all Drools converters.
 */
public class DroolsConverterBase extends TargetRuleEngineConverterBase
{
	private final DroolsOWLLiteral2DRLConverter literal2DRLConverter;
	private final DroolsOWLLiteral2LConverter literal2LConverter;
	private final DroolsOWLNamedObject2DRLConverter namedObjectConverter;
	private final DroolsOWLIndividual2DRLConverter individualConverter;
	private final DroolsOWLDataRange2DRLConverter dataRangeConverter;

	public DroolsConverterBase(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
		this.literal2DRLConverter = new DroolsOWLLiteral2DRLConverter(bridge);
		this.literal2LConverter = new DroolsOWLLiteral2LConverter(bridge);
		this.namedObjectConverter = new DroolsOWLNamedObject2DRLConverter(bridge);
		this.individualConverter = new DroolsOWLIndividual2DRLConverter(bridge);
		this.dataRangeConverter = new DroolsOWLDataRange2DRLConverter(bridge);
	}

	protected DroolsOWLLiteral2DRLConverter getDroolsOWLLiteral2DRLConverter()
	{
		return this.literal2DRLConverter;
	}

	protected DroolsOWLLiteral2LConverter getDroolsOWLLiteral2LConverter()
	{
		return this.literal2LConverter;
	}

	protected DroolsOWLNamedObject2DRLConverter getDroolsOWLNamedObject2DRLConverter()
	{
		return namedObjectConverter;
	}

	protected DroolsOWLIndividual2DRLConverter getDroolsOWLIndividual2DRLConverter()
	{
		return this.individualConverter;
	}

	protected DroolsOWLDataRange2DRLConverter getDroolsOWLDataRange2DRLConverter()
	{
		return this.dataRangeConverter;
	}

	protected String swrlVariable2DRL(SWRLVariable variable)
	{
		IRI variableIRI = variable.getIRI();
		String variableShortName = getOWLIRIResolver().iri2ShortName(variableIRI);

		return variableShortName2DRL(variableShortName);
	}

	protected String swrlVariable2VariableName(SWRLVariable variable)
	{
		IRI variableIRI = variable.getIRI();
		String variableShortName = getOWLIRIResolver().iri2ShortName(variableIRI);

		return variableShortName2VariableName(variableShortName);
	}

	protected String variableIRI2DRL(IRI variableIRI)
	{
		String variableShortName = getOWLIRIResolver().iri2ShortName(variableIRI);

		return variableShortName2DRL(variableShortName);
	}

	protected String variableShortName2DRL(String variableShortName)
	{
		String variableName = variableShortName2VariableName(variableShortName);

		return variableName2DRL(variableName);
	}

	protected String variableName2DRL(String variableName)
	{
		return "$" + variableName;
	}

	private String variableShortName2VariableName(String variableShortName)
	{
		String variableName = variableShortName.startsWith(":") ? variableShortName.substring(1) : variableShortName;

		return variableName;
	}

}
