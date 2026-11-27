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
  private final @NonNull DroolsOWLLiteralExtractor literalExtractor;
  private final @NonNull DroolsOWLEntityExtractor entityExtractor;
  private final @NonNull DroolsOWLNamedIndividualExtractor namedIndividualExtractor;
  private final @NonNull DroolsSWRLVariableExtractor variableExtractor;

  public DroolsExtractorBase(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);

    this.literalExtractor = new DroolsOWLLiteralExtractor(bridge);
    this.entityExtractor = DroolsFactory.getDroolsOWLEntityExtractor(bridge);
    this.namedIndividualExtractor = DroolsFactory.getDroolsOWLIndividualExtractor(bridge);
    this.variableExtractor = new DroolsSWRLVariableExtractor(bridge);
  }

  protected @NonNull DroolsOWLLiteralExtractor getDroolsOWLLiteralExtractor()
  {
    return this.literalExtractor;
  }

  protected @NonNull DroolsOWLEntityExtractor getDroolsOWLEntityExtractor()
  {
    return this.entityExtractor;
  }

  protected @NonNull DroolsOWLNamedIndividualExtractor getDroolsOWLNamedIndividualExtractor()
  {
    return this.namedIndividualExtractor;
  }

  protected @NonNull DroolsSWRLVariableExtractor getDroolsSWRLVariableExtractor()
  {
    return this.variableExtractor;
  }
}
