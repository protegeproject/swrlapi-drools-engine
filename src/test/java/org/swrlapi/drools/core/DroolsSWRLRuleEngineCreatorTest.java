package org.swrlapi.drools.core;

import junit.framework.TestCase;
import org.swrlapi.bridge.TargetSWRLRuleEngineCreator;
import org.swrlapi.drools.factory.DroolsFactory;

/**
 * @see DroolsSWRLRuleEngineCreator
 */
public class DroolsSWRLRuleEngineCreatorTest extends TestCase
{
  public void testGetRuleEngineName() throws Exception
  {
    TargetSWRLRuleEngineCreator creator = DroolsFactory.getSWRLRuleEngineCreator();

    assertEquals(creator.getRuleEngineName(), DroolsNames.RULE_ENGINE_NAME);
  }
}