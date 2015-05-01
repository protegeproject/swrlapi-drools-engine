package org.swrlapi.drools.core;

import org.swrlapi.core.SWRLRuleEngineManager;
import org.swrlapi.exceptions.SWRLAPIException;

import javax.swing.*;
import java.net.URL;

public class DroolsFactory
{
  private static final String DROOLS_ICON_NAME = "Drools.gif";

  public static SWRLRuleEngineManager.TargetSWRLRuleEngineCreator getSWRLRuleEngineCreator()
  {
    return new DroolsSWRLRuleEngineCreator();
  }

  public static Icon getSWRLRuleEngineIcon() throws SWRLAPIException
  {
    URL url = DroolsFactory.class.getResource(DROOLS_ICON_NAME);

    if (url != null)
      return new ImageIcon(url);
    else
      throw new SWRLAPIException("No Drools icon found!");
  }
}
