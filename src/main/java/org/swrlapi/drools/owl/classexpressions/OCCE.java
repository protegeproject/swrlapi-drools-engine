package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.DroolsTernaryObject;
import org.swrlapi.drools.owl.entities.OP;

/**
 * This class represents an OWL object exact cardinality class expression in Drools.
 */
public class OCCE extends DroolsTernaryObject<String, OP, Integer> implements CE
{
	public OCCE(String id, OP p, Integer card)
	{
		super(id, p, card);
	}

	public OCCE(String id, String propertyID, Integer card)
	{
		super(id, new OP(propertyID), card);
	}

	@Override
	public String getceid()
	{
		return getT1();
	}

	public OP getP()
	{
		return getT2();
	}

	public Integer getCard()
	{
		return getT3();
	}

	@Override
	public String toString()
	{
		return "OCE" + super.toString();
	}
}
