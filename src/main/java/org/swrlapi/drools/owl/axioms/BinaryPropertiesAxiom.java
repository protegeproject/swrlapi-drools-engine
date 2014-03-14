package org.swrlapi.drools.owl.axioms;

import org.swrlapi.drools.owl.DroolsBinaryObject;
import org.swrlapi.drools.owl.entities.P;

public abstract class BinaryPropertiesAxiom<T extends P> extends DroolsBinaryObject<T, T> implements A
{
	public BinaryPropertiesAxiom(T property1, T property2)
	{
		super(property1, property2);
	}
}