package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.core.I;

/**
 * This class represents an OWL object one of class expression in Drools. Drools is supplied with an exhaustive pairwise
 * set of classes from the list in an OWL one of class expression.
 */
public class OOOCE extends DroolsTernaryObject<String, I, I> implements CE
{
	public OOOCE(String id, I i1, I i2)
	{
		super(id, i1, i2);
	}

	public OOOCE(String id, String individual1ID, String individual2ID)
	{
		super(id, new I(individual1ID), new I(individual2ID));
	}

	@Override
	public String getceid()
	{
		return getT1();
	}

	public I getI1()
	{
		return getT2();
	}

	public I getI2()
	{
		return getT3();
	}

	@Override
	public String toString()
	{
		return "OOOCE" + super.toString();
	}
}
