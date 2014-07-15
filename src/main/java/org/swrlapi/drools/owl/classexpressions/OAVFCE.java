package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.properties.OP;

/**
 * This class represents an OWL object all values from class expression in Drools.
 */
public class OAVFCE extends DroolsTernaryObject<String, OP, String> implements CE
{
	public OAVFCE(String id, OP p, String valueClassID)
	{
		super(id, p, valueClassID);
	}

	public OAVFCE(String id, String propertyID, String valueClassID)
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
		return "OAVFCE" + super.toString();
	}
}
