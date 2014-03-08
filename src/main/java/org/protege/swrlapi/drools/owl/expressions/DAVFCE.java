package org.protege.swrlapi.drools.owl.expressions;

import org.protege.swrlapi.drools.owl.DroolsTernaryObject;
import org.protege.swrlapi.drools.owl.entities.D;
import org.protege.swrlapi.drools.owl.entities.DP;

/**
 * This class represents an OWL data all values from class expression in Drools.
 */
public class DAVFCE extends DroolsTernaryObject<String, DP, D> implements CE
{
	public DAVFCE(String id, DP p, D o)
	{
		super(id, p, o);
	}

	public DAVFCE(String id, String propertyID, String datatypeID)
	{
		super(id, new DP(propertyID), new D(datatypeID));
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

	public D getV()
	{
		return getT3();
	}

	@Override
	public String toString()
	{
		return "DAVFCE" + super.toString();
	}
}
