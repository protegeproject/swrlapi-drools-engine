package org.swrlapi.drools.owl.axioms;

import org.swrlapi.drools.owl.core.DroolsBinaryObject;

public abstract class DroolsBinaryPropertiesAxiom extends DroolsBinaryObject<String, String> implements A
{
	private static final long serialVersionUID = 1L;

	public DroolsBinaryPropertiesAxiom(String propertyID1, String propertyID2)
	{
		super(propertyID1, propertyID2);
	}

	public String getp1id()
	{
		return getT1();
	}

	public String getp2id()
	{
		return getT2();
	}

	@Override
	public String toString()
	{
		return super.toString();
	}
}