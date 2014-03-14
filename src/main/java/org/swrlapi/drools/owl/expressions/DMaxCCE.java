package org.swrlapi.drools.owl.expressions;

import org.swrlapi.drools.owl.DroolsTernaryObject;
import org.swrlapi.drools.owl.entities.DP;

/**
 * This class represents an OWL data maximum cardinality class expression in Drools.
 */
public class DMaxCCE extends DroolsTernaryObject<String, DP, Integer> implements CE
{
	public DMaxCCE(String id, DP p, Integer card)
	{
		super(id, p, card);
	}

	public DMaxCCE(String id, String propertyID, Integer card)
	{
		super(id, new DP(propertyID), card);
	}

	@Override
	public String getceid()
	{
		return getT1();
	}

	public DP getP()
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
		return "DMaxCCE" + super.toString();
	}
}
