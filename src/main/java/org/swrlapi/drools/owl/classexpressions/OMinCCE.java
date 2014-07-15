package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.properties.OP;

/**
 * This class represents an OWL object minimum cardinality class expression in Drools.
 */
public class OMinCCE extends DroolsTernaryObject<String, OP, Integer> implements CE
{
	public OMinCCE(String id, OP p, Integer card)
	{
		super(id, p, card);
	}

	public OMinCCE(String id, String propertyID, Integer card)
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
		return "OMinCE" + super.toString();
	}
}
