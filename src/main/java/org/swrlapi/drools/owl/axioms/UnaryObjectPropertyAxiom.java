package org.swrlapi.drools.owl.axioms;

import org.swrlapi.drools.owl.properties.OP;

public abstract class UnaryObjectPropertyAxiom extends UnaryPropertyAxiom<OP>
{
	public UnaryObjectPropertyAxiom(OP p)
	{
		super(p);
	}

	public OP getP()
	{
		return super.getT1();
	}
}
