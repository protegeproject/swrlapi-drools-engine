package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.owl.DroolsUnaryObject;
import org.protege.swrlapi.drools.owl.entities.P;

public abstract class UnaryPropertyAxiom<T extends P> extends DroolsUnaryObject<T> implements A
{
	public UnaryPropertyAxiom(T p)
	{
		super(p);
	}
}