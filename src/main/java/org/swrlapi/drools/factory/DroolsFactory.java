package org.swrlapi.drools.factory;

import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.core.SWRLRuleEngineManager;
import org.swrlapi.drools.core.DroolsSWRLRuleEngineCreator;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.extractors.DroolsOWLIndividualExtractor;
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

  public static DroolsOWLAxiomExtractor getDroolsOWLAxiomExtractor(SWRLRuleEngineBridge bridge)
  {
    return new DefaultDroolsOWLAxiomExtractor(bridge);
  }

  public static DroolsOWLEntityExtractor getDroolsOWLEntityExtractor(SWRLRuleEngineBridge bridge)
  {
    return new DefaultDroolsOWLEntityExtractor(bridge);
  }

  public static DroolsOWLIndividualExtractor getDroolsOWLIndividualExtractor(SWRLRuleEngineBridge bridge)
  {
    return new DefaultDroolsOWLIndividualExtractor(bridge);
  }
}
