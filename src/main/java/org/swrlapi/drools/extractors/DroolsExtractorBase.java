package org.swrlapi.drools.extractors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractorBase;
import org.swrlapi.drools.factory.DroolsFactory;

/**
 * Base class providing methods used by all Drools extractors.
 */
public class DroolsExtractorBase extends TargetRuleEngineExtractorBase
{
  @NonNull private final DroolsOWLLiteralExtractor literalExtractor;
  @NonNull private final DroolsOWLEntityExtractor entityExtractor;
  @NonNull private final DroolsOWLIndividualExtractor individualExtractor;
  @NonNull private final DroolsSWRLVariableExtractor variableExtractor;

  public DroolsExtractorBase(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);

    this.literalExtractor = new DroolsOWLLiteralExtractor(bridge);
    this.entityExtractor = DroolsFactory.getDroolsOWLEntityExtractor(bridge);
    this.individualExtractor = DroolsFactory.getDroolsOWLIndividualExtractor(bridge);
    this.variableExtractor = new DroolsSWRLVariableExtractor(bridge);
  }

  @NonNull protected DroolsOWLLiteralExtractor getDroolsOWLLiteralExtractor()
  {
    return this.literalExtractor;
  }

  @NonNull protected DroolsOWLEntityExtractor getDroolsOWLEntityExtractor()
  {
    return this.entityExtractor;
  }

  @NonNull protected DroolsOWLIndividualExtractor getDroolsOWLIndividualExtractor()
  {
    return this.individualExtractor;
  }

  @NonNull protected DroolsSWRLVariableExtractor getDroolsSWRLVariableExtractor()
  {
    return this.variableExtractor;
  }
}
