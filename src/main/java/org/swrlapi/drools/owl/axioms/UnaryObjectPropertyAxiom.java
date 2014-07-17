package org.swrlapi.drools.owl.axioms;

import org.swrlapi.drools.owl.core.DroolsUnaryObject;
import org.swrlapi.drools.owl.properties.OP;

public abstract class UnaryObjectPropertyAxiom extends DroolsUnaryObject<String>
{
	public UnaryObjectPropertyAxiom(String propertyID)
	{
		super(propertyID);
	}

	public String getP()
	{
		return super.getT1();
	}
}
