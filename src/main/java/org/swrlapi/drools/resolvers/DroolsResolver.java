package org.swrlapi.drools.resolvers;

import checkers.nullness.quals.NonNull;

public class DroolsResolver
{
  @NonNull private final DroolsCEResolver droolsCEResolver;
  @NonNull private final DroolsOPEResolver droolsOPEResolver;
  @NonNull private final DroolsDPEResolver droolsDPEResolver;

  public DroolsResolver()
  {
    this.droolsCEResolver = new DroolsCEResolver();
    this.droolsOPEResolver = new DroolsOPEResolver();
    this.droolsDPEResolver = new DroolsDPEResolver();
  }

  @NonNull public DroolsCEResolver getDroolsCEResolver()
  {
    return this.droolsCEResolver;
  }

  @NonNull public DroolsOPEResolver getDroolsOPEResolver()
  {
    return this.droolsOPEResolver;
  }

  @NonNull public DroolsDPEResolver getDroolsDPEResolver()
  {
    return this.droolsDPEResolver;
  }

  public void reset()
  {
    this.droolsCEResolver.reset();
    this.droolsOPEResolver.reset();
    this.droolsDPEResolver.reset();
  }
}
