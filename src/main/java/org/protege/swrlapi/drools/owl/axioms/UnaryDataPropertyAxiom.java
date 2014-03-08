package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.owl.entities.DP;

public abstract class UnaryDataPropertyAxiom extends UnaryPropertyAxiom<DP>
{
	public UnaryDataPropertyAxiom(DP p)
	{
		super(p);
	}

	public DP getP()
	{
		return super.getT1();
	}
}
