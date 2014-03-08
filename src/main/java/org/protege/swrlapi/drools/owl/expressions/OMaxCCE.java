package org.protege.swrlapi.drools.owl.expressions;

import org.protege.swrlapi.drools.owl.DroolsTernaryObject;
import org.protege.swrlapi.drools.owl.entities.OP;

/**
 * This class represents an OWL object maximum cardinality class expression in Drools.
 */
public class OMaxCCE extends DroolsTernaryObject<String, OP, Integer> implements CE
{
	public OMaxCCE(String id, OP p, Integer card)
	{
		super(id, p, card);
	}

	public OMaxCCE(String id, String propertyID, Integer card)
	{
		super(id, new OP(propertyID), card);
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

	public Integer getCard()
	{
		return getT3();
	}

	@Override
	public String toString()
	{
		return "OMaxCE" + super.toString();
	}
}
