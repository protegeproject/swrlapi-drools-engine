package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.owl.entities.OP;

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
