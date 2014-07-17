package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.core.D;
import org.swrlapi.drools.owl.properties.DP;

/**
 * This class represents an OWL data all values from class expression in Drools.
 */
public class DAVFCE extends DroolsTernaryObject<String, String, D> implements CE
{
	public DAVFCE(String id, String propertyID, String datatypeID)
	{
		super(id, propertyID, new D(datatypeID));
	}

	@Override
	public String getceid()
	{
		return getT1();
	}

	public String getpid()
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