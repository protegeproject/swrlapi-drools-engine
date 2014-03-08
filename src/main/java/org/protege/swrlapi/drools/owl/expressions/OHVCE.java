package org.protege.swrlapi.drools.owl.expressions;

import org.protege.swrlapi.drools.owl.DroolsTernaryObject;
import org.protege.swrlapi.drools.owl.entities.I;
import org.protege.swrlapi.drools.owl.entities.OP;

/**
 * This class represents an OWL object has value class expression in Drools.
 */
public class OHVCE extends DroolsTernaryObject<String, OP, I> implements CE
{
	public OHVCE(String id, OP p, I o)
	{
		super(id, p, o);
	}

	public OHVCE(String id, String propertyID, String individualID)
	{
		super(id, new OP(propertyID), new I(individualID));
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

	public I getV()
	{
		return getT3();
	}

	@Override
	public String toString()
	{
		return "OHVCE" + super.toString();
	}
}
