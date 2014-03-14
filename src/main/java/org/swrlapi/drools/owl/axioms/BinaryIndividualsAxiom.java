package org.swrlapi.drools.owl.axioms;

import org.swrlapi.drools.owl.DroolsBinaryObject;
import org.swrlapi.drools.owl.entities.I;

public abstract class BinaryIndividualsAxiom extends DroolsBinaryObject<I, I> implements A
{
	public BinaryIndividualsAxiom(I individual1, I individual2)
	{
		super(individual1, individual2);
	}

	public I getI1()
	{
		return getT1();
	}

	public I getI2()
	{
		return getT2();
	}
}