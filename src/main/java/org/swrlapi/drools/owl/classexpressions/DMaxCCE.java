package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.properties.DP;

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
