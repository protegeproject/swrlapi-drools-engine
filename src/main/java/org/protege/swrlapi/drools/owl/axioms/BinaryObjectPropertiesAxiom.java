package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.owl.entities.OP;

public abstract class BinaryObjectPropertiesAxiom extends BinaryPropertiesAxiom<OP>
{
	public BinaryObjectPropertiesAxiom(OP p1, OP p2)
	{
		super(p1, p2);
	}

	public OP getP1()
	{
		return super.getT1();
	}

	public OP getP2()
	{
		return super.getT2();
	}
}
