package org.swrlapi.drools.converters.oo;

import checkers.nullness.quals.NonNull;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.drools.converters.drl.DroolsOWLDataRange2IDConverter;
import org.swrlapi.drools.converters.DroolsOWLEntity2NameConverter;
import org.swrlapi.drools.converters.DroolsSWRLVariable2NameConverter;

class DroolsOOConverterBase extends TargetRuleEngineConverterBase
{
  private final @NonNull DroolsOWLLiteral2LConverter literal2LConverter;
  private final @NonNull DroolsOWLEntity2OEConverter entity2OEConverter;
  private final @NonNull DroolsOWLIndividual2IConverter individual2IConverter;
  private final @NonNull DroolsOWLDataRange2DRConverter dataRange2DRConverter;
  private final @NonNull DroolsOWLEntity2NameConverter entity2NameConverter;
  private final @NonNull DroolsOWLDataRange2IDConverter dataRange2IDConverter;
  private final @NonNull DroolsSWRLVariable2NameConverter swrlVariable2NameConverter;

  public DroolsOOConverterBase(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);

    this.literal2LConverter = new DroolsOWLLiteral2LConverter(bridge);
    this.entity2OEConverter = new DroolsOWLEntity2OEConverter(bridge);
    this.individual2IConverter = new DroolsOWLIndividual2IConverter(bridge);
    this.dataRange2DRConverter = new DroolsOWLDataRange2DRConverter(bridge);

    this.entity2NameConverter = new DroolsOWLEntity2NameConverter(bridge);
    this.dataRange2IDConverter = new DroolsOWLDataRange2IDConverter(bridge);
    this.swrlVariable2NameConverter = new DroolsSWRLVariable2NameConverter(bridge);
  }

  @NonNull protected DroolsOWLLiteral2LConverter getDroolsOWLLiteral2LConverter()
  {
    return this.literal2LConverter;
  }

  @NonNull protected DroolsOWLEntity2OEConverter getDroolsOWLEntity2OEConverter()
  {
    return this.entity2OEConverter;
  }

  @NonNull protected DroolsOWLIndividual2IConverter getDroolsOWLIndividual2IConverter()
  {
    return this.individual2IConverter;
  }

  @NonNull protected DroolsOWLDataRange2DRConverter getDroolsOWLDataRange2DRConverter()
  {
    return this.dataRange2DRConverter;
  }

  protected @NonNull DroolsOWLDataRange2IDConverter getDroolsOWLDataRange2IDConverter()
  {
    return dataRange2IDConverter;
  }

  protected @NonNull DroolsOWLEntity2NameConverter getDroolsOWLEntity2NameConverter()
  {
    return this.entity2NameConverter;
  }

  protected @NonNull DroolsSWRLVariable2NameConverter getDroolsSWRLVariable2NameConverter()
  {
    return this.swrlVariable2NameConverter;
  }
}
