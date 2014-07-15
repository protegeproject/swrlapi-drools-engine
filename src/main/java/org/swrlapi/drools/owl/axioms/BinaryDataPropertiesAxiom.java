package org.swrlapi.drools.owl.axioms;

import org.swrlapi.drools.owl.properties.DP;

public abstract class BinaryDataPropertiesAxiom extends BinaryPropertiesAxiom<DP>
{
	public BinaryDataPropertiesAxiom(DP p1, DP p2)
	{
		super(p1, p2);
	}

	public DP getP1()
	{
		return super.getT1();
	}

	public DP getP2()
	{
		return super.getT2();
	}
}
