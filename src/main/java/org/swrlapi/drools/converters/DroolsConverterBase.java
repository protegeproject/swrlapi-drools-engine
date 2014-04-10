package org.swrlapi.drools.converters;

import org.swrlapi.converters.TargetRuleEngineConverterBase;
import org.swrlapi.core.SWRLRuleEngineBridge;

/**
 * Base class providing functionality common to all Drools converters.
 */
public class DroolsConverterBase extends TargetRuleEngineConverterBase
{
	private final DroolsOWLLiteral2DRLConverter literal2DRLConverter;
	private final DroolsOWLLiteral2LConverter literal2LConverter;
	private final DroolsOWLNamedObject2DRLConverter namedObject2DRLConverter;
	private final DroolsOWLEntity2OEConverter entity2OEConverter;
	private final DroolsOWLIndividual2DRLConverter individual2DRLConverter;
	private final DroolsOWLIndividual2IConverter individual2IConverter;
	private final DroolsOWLDataRange2DRLConverter dataRange2DRLConverter;
	private final DroolsSWRLVariableConverter variableConverter;

	public DroolsConverterBase(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
		this.literal2DRLConverter = new DroolsOWLLiteral2DRLConverter(bridge);
		this.literal2LConverter = new DroolsOWLLiteral2LConverter(bridge);
		this.namedObject2DRLConverter = new DroolsOWLNamedObject2DRLConverter(bridge);
		this.entity2OEConverter = new DroolsOWLEntity2OEConverter(bridge);
		this.individual2DRLConverter = new DroolsOWLIndividual2DRLConverter(bridge);
		this.individual2IConverter = new DroolsOWLIndividual2IConverter(bridge);
		this.dataRange2DRLConverter = new DroolsOWLDataRange2DRLConverter(bridge);
		this.variableConverter = new DroolsSWRLVariableConverter(bridge);
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
		return this.namedObject2DRLConverter;
	}

	protected DroolsOWLEntity2OEConverter getDroolsOWLEntity2OEConverter()
	{
		return this.entity2OEConverter;
	}

	protected DroolsOWLIndividual2DRLConverter getDroolsOWLIndividual2DRLConverter()
	{
		return this.individual2DRLConverter;
	}

	protected DroolsOWLIndividual2IConverter getDroolsOWLIndividual2IConverter()
	{
		return this.individual2IConverter;
	}

	protected DroolsOWLDataRange2DRLConverter getDroolsOWLDataRange2DRLConverter()
	{
		return this.dataRange2DRLConverter;
	}

	protected DroolsSWRLVariableConverter getDroolsSWRLVariableConverter()
	{
		return this.variableConverter;
	}
}
