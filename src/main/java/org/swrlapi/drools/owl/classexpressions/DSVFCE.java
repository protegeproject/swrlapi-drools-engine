package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.DroolsTernaryObject;
import org.swrlapi.drools.owl.entities.D;
import org.swrlapi.drools.owl.entities.DP;

/**
 * This class represents an OWL data some values from of class expression in Drools.
 */
public class DSVFCE extends DroolsTernaryObject<String, DP, D> implements CE
{
	public DSVFCE(String id, DP p, D o)
	{
		super(id, p, o);
	}

	public DSVFCE(String id, String propertyID, String objectID)
	{
		super(id, new DP(propertyID), new D(objectID));
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
		return "OSVFCE" + super.toString();
	}
}
