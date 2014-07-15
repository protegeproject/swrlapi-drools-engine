package org.swrlapi.drools.owl.axioms;

import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.drools.owl.properties.P;

public abstract class BinaryPropertiesAxiom<T extends P> extends DroolsBinaryObject<T, T> implements A
{
	public BinaryPropertiesAxiom(T property1, T property2)
	{
		super(property1, property2);
	}

	@Override
	public String toString()
	{
		return super.toString();
	}
}