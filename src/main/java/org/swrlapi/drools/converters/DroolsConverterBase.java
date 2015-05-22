package org.swrlapi.drools.converters;

import checkers.nullness.quals.NonNull;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;

/**
 * Base class providing functionality common to all Drools converters.
 * <p>
 * Drools converters transform OWLAPI-based OWL entities to a Drools representation of those entities.
 */
class DroolsConverterBase extends TargetRuleEngineConverterBase
{
  @NonNull private final DroolsOWLLiteral2DRLConverter literal2DRLConverter;
  @NonNull private final DroolsOWLLiteral2LConverter literal2LConverter;
  @NonNull private final DroolsOWLNamedObject2DRLConverter namedObject2DRLConverter;
  @NonNull private final DroolsOWLEntity2OEConverter entity2OEConverter;
  @NonNull private final DroolsOWLIndividual2DRLConverter individual2DRLConverter;
  @NonNull private final DroolsOWLIndividual2IConverter individual2IConverter;
  @NonNull private final DroolsOWLDataRangeConverter dataRangeConverter;
  @NonNull private final DroolsSWRLVariableConverter variableConverter;

  public DroolsConverterBase(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);

    this.literal2DRLConverter = new DroolsOWLLiteral2DRLConverter(bridge);
    this.literal2LConverter = new DroolsOWLLiteral2LConverter(bridge);
    this.namedObject2DRLConverter = new DroolsOWLNamedObject2DRLConverter(bridge);
    this.entity2OEConverter = new DroolsOWLEntity2OEConverter(bridge);
    this.individual2DRLConverter = new DroolsOWLIndividual2DRLConverter(bridge);
    this.individual2IConverter = new DroolsOWLIndividual2IConverter(bridge);
    this.dataRangeConverter = new DroolsOWLDataRangeConverter(bridge);
    this.variableConverter = new DroolsSWRLVariableConverter(bridge);
  }

  @NonNull protected DroolsOWLLiteral2DRLConverter getDroolsOWLLiteral2DRLConverter()
  {
    return this.literal2DRLConverter;
  }

  @NonNull protected DroolsOWLLiteral2LConverter getDroolsOWLLiteral2LConverter()
  {
    return this.literal2LConverter;
  }

  @NonNull protected DroolsOWLNamedObject2DRLConverter getDroolsOWLNamedObject2DRLConverter()
  {
    return this.namedObject2DRLConverter;
  }

  @NonNull protected DroolsOWLEntity2OEConverter getDroolsOWLEntity2OEConverter()
  {
    return this.entity2OEConverter;
  }

  @NonNull protected DroolsOWLIndividual2DRLConverter getDroolsOWLIndividual2DRLConverter()
  {
    return this.individual2DRLConverter;
  }

  @NonNull protected DroolsOWLIndividual2IConverter getDroolsOWLIndividual2IConverter()
  {
    return this.individual2IConverter;
  }

  @NonNull protected DroolsOWLDataRangeConverter getDroolsOWLDataRangeConverter()
  {
    return this.dataRangeConverter;
  }

  @NonNull protected DroolsSWRLVariableConverter getDroolsSWRLVariableConverter()
  {
    return this.variableConverter;
  }
}
