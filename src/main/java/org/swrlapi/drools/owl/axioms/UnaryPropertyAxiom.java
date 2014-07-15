package org.swrlapi.drools.owl.axioms;

import org.swrlapi.drools.owl.core.DroolsUnaryObject;
import org.swrlapi.drools.owl.properties.P;

public abstract class UnaryPropertyAxiom<T extends P> extends DroolsUnaryObject<T> implements A
{
	public UnaryPropertyAxiom(T p)
	{
		super(p);
	}
}