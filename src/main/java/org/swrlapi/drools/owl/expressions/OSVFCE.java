package org.swrlapi.drools.owl.expressions;

import org.swrlapi.drools.owl.DroolsTernaryObject;
import org.swrlapi.drools.owl.entities.OP;

/**
 * This class represents an OWL object some values from of class expression in Drools.
 */
public class OSVFCE extends DroolsTernaryObject<String, OP, String> implements CE
{
	public OSVFCE(String id, OP p, String valueClassID)
	{
		super(id, p, valueClassID);
	}

	public OSVFCE(String id, String propertyID, String valueClassID)
	{
		super(id, new OP(propertyID), valueClassID);
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

	public String getV()
	{
		return getT3();
	}

	@Override
	public String toString()
	{
		return "OSVFCE" + super.toString();
	}
}
