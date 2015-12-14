package org.swrlapi.drools.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.TargetSWRLRuleEngine;
import org.swrlapi.bridge.TargetSWRLRuleEngineCreator;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Creator class that is supplied to a {@link org.swrlapi.core.SWRLRuleEngineManager} to create new instances of a
 * {@link org.swrlapi.drools.core.DroolsSWRLRuleEngine}.
 *
 * @see org.swrlapi.core.SWRLRuleEngineManager
 * @see org.swrlapi.drools.core.DroolsSWRLRuleEngine
 */
public class DroolsSWRLRuleEngineCreator implements TargetSWRLRuleEngineCreator
{
  @NonNull @Override
  public String getRuleEngineName()
  {
    return DroolsNames.RULE_ENGINE_NAME;
  }

  @NonNull @Override
  public TargetSWRLRuleEngine create(@NonNull SWRLRuleEngineBridge bridge) throws TargetSWRLRuleEngineException
  {
    return new DroolsSWRLRuleEngine(bridge);
  }
}
