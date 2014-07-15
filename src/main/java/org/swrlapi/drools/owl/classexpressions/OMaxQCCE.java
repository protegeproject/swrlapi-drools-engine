package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.core.DroolsQuadObject;
import org.swrlapi.drools.owl.properties.DP;

/**
 * This class represents an OWL data maximum qualified cardinality class expression in Drools.
 */
public class OMaxQCCE extends DroolsQuadObject<String, DP, String, Integer> implements CE
{
	public OMaxQCCE(String id, DP p, String fillerID, Integer card)
	{
		super(id, p, fillerID, card);
	}

	public OMaxQCCE(String id, String propertyID, String fillerID, Integer card)
	{
		super(id, new DP(propertyID), fillerID, card);
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

	public String getF()
	{
		return getT3();
	}

	public Integer getCard()
	{
		return getT4();
	}

	@Override
	public String toString()
	{
		return "OMaxQCCE" + super.toString();
	}
}
