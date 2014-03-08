package org.protege.swrlapi.drools;

import org.protege.swrlapi.core.SWRLRuleEngineBridge;
import org.protege.swrlapi.core.SWRLRuleEngineManager;
import org.protege.swrlapi.core.TargetRuleEngine;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

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
