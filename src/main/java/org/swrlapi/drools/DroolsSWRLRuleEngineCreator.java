package org.swrlapi.drools;

import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.core.SWRLRuleEngineManager;
import org.swrlapi.core.TargetRuleEngine;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Creator class that is supplied to a {@link SWRLRuleEngineManager} to create new instances of a
 * {@link DroolsSWRLRuleEngine}.
 */
public class DroolsSWRLRuleEngineCreator implements SWRLRuleEngineManager.TargetSWRLRuleEngineCreator
{
	@Override
	public TargetRuleEngine create(SWRLRuleEngineBridge bridge) throws TargetRuleEngineException
	{
		return new DroolsSWRLRuleEngine(bridge);
	}
}
