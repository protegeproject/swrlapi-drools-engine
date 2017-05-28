package org.swrlapi.drools.converters.drl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.drools.converters.id.DroolsSWRLVariable2NameConverter;
import org.swrlapi.drools.converters.oo.DroolsOWLLiteral2LConverter;

/**
 * Base class providing functionality common to all Drools converters.
 * <p>
 * Drools converters transform OWLAPI-based OWL entities to a Drools representation of those entities.
 */
class DroolsDRLConverterBase extends TargetRuleEngineConverterBase
{
  @NonNull private final DroolsOWLLiteral2DRLConverter literal2DRLConverter;
  @NonNull private final DroolsOWLIndividual2DRLConverter individual2DRLConverter;

  @NonNull private final DroolsSWRLVariable2NameConverter variable2NameConverter;

  @NonNull private final DroolsOWLLiteral2LConverter literal2LConverter;

  public DroolsDRLConverterBase(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);

    this.literal2DRLConverter = new DroolsOWLLiteral2DRLConverter(bridge);
    this.individual2DRLConverter = new DroolsOWLIndividual2DRLConverter(bridge);
    this.variable2NameConverter = new DroolsSWRLVariable2NameConverter(bridge);
    this.literal2LConverter = new DroolsOWLLiteral2LConverter(bridge);
  }

  @NonNull protected DroolsOWLLiteral2DRLConverter getDroolsOWLLiteral2DRLConverter()
  {
    return this.literal2DRLConverter;
  }

  @NonNull protected DroolsOWLLiteral2LConverter getDroolsOWLLiteral2LConverter()
  {
    return this.literal2LConverter;
  }

  @NonNull protected DroolsOWLIndividual2DRLConverter getDroolsOWLIndividual2DRLConverter()
  {
    return this.individual2DRLConverter;
  }

  @NonNull protected DroolsSWRLVariable2NameConverter getDroolsSWRLVariable2NameConverter()
  {
    return this.variable2NameConverter;
  }
}
