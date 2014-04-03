package org.swrlapi.drools.extractors;

import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.extractors.TargetRuleEngineExtractorBase;

public class DroolsExtractorBase extends TargetRuleEngineExtractorBase
{
	private final DroolsOWLLiteralExtractor literalExtractor;
	private final DroolsOWLEntityExtractor entityExtractor;
	private final DroolsOWLIndividualExtractor individualExtractor;

	public DroolsExtractorBase(SWRLRuleEngineBridge bridge)
	{
		super(bridge);

		this.literalExtractor = new DroolsOWLLiteralExtractor(bridge);
		this.entityExtractor = new DefaultDroolsOE2OWLEntityExtractor(bridge);
		this.individualExtractor = new DefaultDroolsI2OWLIndividualExtractor(bridge);
	}

	protected DroolsOWLLiteralExtractor getDroolsOWLLiteralExtractor()
	{
		return this.literalExtractor;
	}

	protected DroolsOWLEntityExtractor getDroolsOWLEntityExtractor()
	{
		return this.entityExtractor;
	}

	protected DroolsOWLIndividualExtractor getDroolsOWLIndividualExtractor()
	{
		return this.individualExtractor;
	}
}
