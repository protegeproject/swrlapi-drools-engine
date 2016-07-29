package org.swrlapi.drools.converters.oo;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.drools.converters.id.DroolsOWLEntity2NameConverter;
import org.swrlapi.drools.converters.id.DroolsSWRLVariable2NameConverter;

class DroolsOOConverterBase extends TargetRuleEngineConverterBase
{
  @NonNull private final DroolsOWLLiteral2LConverter literal2LConverter;
  @NonNull private final DroolsOWLEntity2OEConverter entity2OEConverter;
  @NonNull private final DroolsOWLIndividual2IConverter individual2IConverter;
  @NonNull private final DroolsOWLEntity2NameConverter entity2NameConverter;
  @NonNull private final DroolsSWRLVariable2NameConverter swrlVariable2NameConverter;

  public DroolsOOConverterBase(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);

    this.literal2LConverter = new DroolsOWLLiteral2LConverter(bridge);
    this.entity2OEConverter = new DroolsOWLEntity2OEConverter(bridge);
    this.individual2IConverter = new DroolsOWLIndividual2IConverter(bridge);
    this.entity2NameConverter = new DroolsOWLEntity2NameConverter(bridge);
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

  @NonNull protected DroolsOWLEntity2NameConverter getDroolsOWLEntity2NameConverter()
  {
    return this.entity2NameConverter;
  }

  @NonNull protected DroolsSWRLVariable2NameConverter getDroolsSWRLVariable2NameConverter()
  {
    return this.swrlVariable2NameConverter;
  }
}
