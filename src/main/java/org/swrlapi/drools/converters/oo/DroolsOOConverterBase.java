package org.swrlapi.drools.converters.oo;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.drools.converters.DroolsOWLDataRange2IDConverter;
import org.swrlapi.drools.converters.DroolsOWLEntity2NameConverter;
import org.swrlapi.drools.converters.DroolsSWRLVariable2NameConverter;
import org.swrlapi.drools.core.resolvers.DroolsObjectResolver;

class DroolsOOConverterBase extends TargetRuleEngineConverterBase
{
  private final @NonNull DroolsOWLLiteral2LConverter literal2LConverter;
  private final @NonNull DroolsOWLEntity2OEConverter entity2OEConverter;
  private final @NonNull DroolsOWLClassExpression2CEConverter classExpression2CEConverter;
  private final @NonNull DroolsOWLPropertyExpression2PEConverter propertyExpression2PEConverter;
  private final @NonNull DroolsOWLIndividual2IConverter individual2IConverter;
  private final @NonNull DroolsOWLDataRange2DRConverter dataRange2DRConverter;
  private final @NonNull DroolsOWLEntity2NameConverter entity2NameConverter;
  private final @NonNull DroolsOWLDataRange2IDConverter dataRange2IDConverter;
  private final @NonNull DroolsSWRLVariable2NameConverter swrlVariable2NameConverter;

  private final DroolsObjectResolver droolsObjectResolver;

  public DroolsOOConverterBase(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);

    this.literal2LConverter = new DroolsOWLLiteral2LConverter(bridge);
    this.entity2OEConverter = new DroolsOWLEntity2OEConverter(bridge);
    this.individual2IConverter = new DroolsOWLIndividual2IConverter(bridge);
    this.propertyExpression2PEConverter = new DroolsOWLPropertyExpression2PEConverter(bridge);
    this.classExpression2CEConverter = new DroolsOWLClassExpression2CEConverter(bridge, this.individual2IConverter,
      this.propertyExpression2PEConverter);
    this.dataRange2DRConverter = new DroolsOWLDataRange2DRConverter(bridge);
    this.entity2NameConverter = new DroolsOWLEntity2NameConverter(bridge);
    this.dataRange2IDConverter = new DroolsOWLDataRange2IDConverter(bridge);
    this.swrlVariable2NameConverter = new DroolsSWRLVariable2NameConverter(bridge);

    this.droolsObjectResolver = new DroolsObjectResolver();
  }

  @NonNull protected DroolsOWLLiteral2LConverter getDroolsOWLLiteral2LConverter()
  {
    return this.literal2LConverter;
  }

  @NonNull protected DroolsOWLEntity2OEConverter getDroolsOWLEntity2OEConverter()
  {
    return this.entity2OEConverter;
  }

  @NonNull protected DroolsOWLClassExpression2CEConverter getDroolsOWLClassExpression2CEConverter()
  {
    return this.classExpression2CEConverter;
  }

  @NonNull protected DroolsOWLIndividual2IConverter getDroolsOWLIndividual2IConverter()
  {
    return this.individual2IConverter;
  }

  @NonNull protected DroolsOWLDataRange2DRConverter getDroolsOWLDataRange2DRConverter()
  {
    return this.dataRange2DRConverter;
  }

  @NonNull protected DroolsOWLDataRange2IDConverter getDroolsOWLDataRange2IDConverter()
  {
    return dataRange2IDConverter;
  }

  @NonNull protected DroolsOWLEntity2NameConverter getDroolsOWLEntity2NameConverter()
  {
    return this.entity2NameConverter;
  }

  @NonNull protected DroolsSWRLVariable2NameConverter getDroolsSWRLVariable2NameConverter()
  {
    return this.swrlVariable2NameConverter;
  }

  @NonNull protected DroolsObjectResolver getDroolsObjectResolver()
  {
    return this.droolsObjectResolver;
  }
}
