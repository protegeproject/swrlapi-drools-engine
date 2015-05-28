package org.swrlapi.drools.factory;

import junit.framework.TestCase;
import org.swrlapi.bridge.TargetSWRLRuleEngineCreator;

import javax.swing.*;

/**
 * @see DroolsFactory
 */
public class DroolsFactoryTest extends TestCase
{
  public void testGetSWRLRuleEngineCreator() throws Exception
  {
    TargetSWRLRuleEngineCreator creator = DroolsFactory.getSWRLRuleEngineCreator();
    assertNotNull(creator);
  }

  public void testGetSWRLRuleEngineIcon() throws Exception
  {
    Icon icon = DroolsFactory.getSWRLRuleEngineIcon();
    assertNotNull(icon);
  }
}