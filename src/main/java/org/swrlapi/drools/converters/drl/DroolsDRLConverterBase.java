package org.swrlapi.drools.converters.drl;

import checkers.nullness.quals.NonNull;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.drools.converters.DroolsOWLDataRange2IDConverter;
import org.swrlapi.drools.converters.DroolsSWRLVariable2NameConverter;
import org.swrlapi.drools.converters.oo.DroolsOWLLiteral2LConverter;

/**
 * Base class providing functionality common to all Drools converters.
 * <p/>
 * Drools converters transform OWLAPI-based OWL entities to a Drools representation of those entities.
 */
class DroolsDRLConverterBase extends TargetRuleEngineConverterBase
{
  private final @NonNull DroolsOWLLiteral2DRLConverter literal2DRLConverter;
  private final @NonNull DroolsOWLIndividual2DRLConverter individual2DRLConverter;

  private final @NonNull DroolsSWRLVariable2NameConverter variable2NameConverter;
  private final @NonNull DroolsOWLDataRange2IDConverter dataRange2IDConverter;

  private final @NonNull DroolsOWLLiteral2LConverter literal2LConverter;

  public DroolsDRLConverterBase(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);

    this.literal2DRLConverter = new DroolsOWLLiteral2DRLConverter(bridge);
    this.individual2DRLConverter = new DroolsOWLIndividual2DRLConverter(bridge);
    this.variable2NameConverter = new DroolsSWRLVariable2NameConverter(bridge);
    this.dataRange2IDConverter = new DroolsOWLDataRange2IDConverter(bridge);
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

  @NonNull protected DroolsOWLDataRange2IDConverter getDroolsOWLDataRange2IDConverter()
  {
    return this.dataRange2IDConverter;
  }

  @NonNull protected DroolsSWRLVariable2NameConverter getDroolsSWRLVariable2NameConverter()
  {
    return this.variable2NameConverter;
  }
}
