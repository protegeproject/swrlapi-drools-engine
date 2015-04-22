package org.swrlapi.drools.resolvers;


public class DroolsResolver
{
	private final DroolsCEResolver droolsCEResolver;
	private final DroolsOPEResolver droolsOPEResolver;
	private final DroolsDPEResolver droolsDPEResolver;

	public DroolsResolver()
	{
		this.droolsCEResolver = new DroolsCEResolver();
		this.droolsOPEResolver = new DroolsOPEResolver();
		this.droolsDPEResolver = new DroolsDPEResolver();
	}

	public DroolsCEResolver getDroolsCEResolver()
	{
		return this.droolsCEResolver;
	}

	public DroolsOPEResolver getDroolsOPEResolver()
	{
		return this.droolsOPEResolver;
	}

	public DroolsDPEResolver getDroolsDPEResolver()
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
