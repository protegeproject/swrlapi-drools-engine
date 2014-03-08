package org.protege.swrlapi.drools.owl.expressions;

import org.protege.swrlapi.drools.owl.DroolsTernaryObject;
import org.protege.swrlapi.drools.owl.L;
import org.protege.swrlapi.drools.owl.entities.DP;

/**
 * This class represents an OWL data has value class expression in Drools.
 */
public class DHVCE extends DroolsTernaryObject<String, DP, L> implements CE
{
	public DHVCE(String id, DP p, L o)
	{
		super(id, p, o);
	}

	public DHVCE(String id, String propertyID, L l)
	{
		super(id, new DP(propertyID), l);
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

	public L getV()
	{
		return getT3();
	}

	@Override
	public String toString()
	{
		return "DHVCE" + super.toString();
	}
}
