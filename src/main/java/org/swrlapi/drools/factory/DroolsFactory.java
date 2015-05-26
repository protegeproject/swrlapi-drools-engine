package org.swrlapi.drools.factory;

import checkers.nullness.quals.NonNull;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.core.TargetSWRLRuleEngineCreator;
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

  public static @NonNull TargetSWRLRuleEngineCreator getSWRLRuleEngineCreator()
  {
    return new DroolsSWRLRuleEngineCreator();
  }

  @NonNull public static Icon getSWRLRuleEngineIcon() throws SWRLAPIException
  {
    URL url = DroolsFactory.class.getResource(DROOLS_ICON_NAME);

    if (url != null)
      return new ImageIcon(url);
    else
      throw new SWRLAPIException("No Drools icon found!");
  }

  @NonNull public static DroolsOWLAxiomExtractor getDroolsOWLAxiomExtractor(@NonNull SWRLRuleEngineBridge bridge)
  {
    return new DefaultDroolsOWLAxiomExtractor(bridge);
  }

  @NonNull public static DroolsOWLEntityExtractor getDroolsOWLEntityExtractor(@NonNull SWRLRuleEngineBridge bridge)
  {
    return new DefaultDroolsOWLEntityExtractor(bridge);
  }

  @NonNull public static DroolsOWLIndividualExtractor getDroolsOWLIndividualExtractor(
    @NonNull SWRLRuleEngineBridge bridge)
  {
    return new DefaultDroolsOWLIndividualExtractor(bridge);
  }
}
