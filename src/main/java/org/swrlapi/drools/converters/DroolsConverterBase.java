package org.swrlapi.drools.converters;

import org.swrlapi.converters.TargetRuleEngineConverterBase;
import org.swrlapi.core.SWRLRuleEngineBridge;

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

	public DroolsOWLLiteral2DRLConverter getDroolsOWLLiteral2DRLConverter()
	{
		return this.literal2DRLConverter;
	}

	public DroolsOWLLiteral2LConverter getDroolsOWLLiteral2LConverter()
	{
		return this.literal2LConverter;
	}

	public DroolsOWLNamedObject2DRLConverter getDroolsOWLNamedObject2DRLConverter()
	{
		return namedObjectConverter;
	}

	public DroolsOWLIndividual2DRLConverter getDroolsOWLIndividual2DRLConverter()
	{
		return this.individualConverter;
	}

	public DroolsOWLDataRange2DRLConverter getDroolsOWLDataRange2DRLConverter()
	{
		return this.dataRangeConverter;
	}
}
