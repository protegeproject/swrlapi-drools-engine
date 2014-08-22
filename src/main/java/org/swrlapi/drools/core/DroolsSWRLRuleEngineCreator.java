package org.swrlapi.drools.core;

import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.core.SWRLRuleEngineManager;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Creator class that is supplied to a {@link org.swrlapi.core.SWRLRuleEngineManager} to create new instances of a
 * {@link org.swrlapi.drools.core.DroolsSWRLRuleEngine}.
 *
 * @see org.swrlapi.core.SWRLRuleEngineManager
 * @see org.swrlapi.drools.core.DroolsSWRLRuleEngine
 */
public class DroolsSWRLRuleEngineCreator implements SWRLRuleEngineManager.TargetSWRLRuleEngineCreator
{
	private static final String RULE_ENGINE_NAME = "Drools";

	@Override
	public String getRuleEngineName()
	{
		return RULE_ENGINE_NAME;
	}

	@Override
	public TargetSWRLRuleEngine create(SWRLRuleEngineBridge bridge) throws TargetRuleEngineException
	{
		return new DroolsSWRLRuleEngine(bridge);
	}
}
