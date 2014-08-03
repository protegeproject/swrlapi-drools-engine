package org.swrlapi.drools.core;

import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.core.SWRLRuleEngineManager;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Creator class that is supplied to a {@link SWRLRuleEngineManager} to create new instances of a
 * {@link DroolsSWRLRuleEngine}.
 *
 * @see org.swrlapi.core.SWRLRuleEngineManager
 * @see DroolsSWRLRuleEngine
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
