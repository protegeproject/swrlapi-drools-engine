package org.protege.swrlapi.drools.owl.expressions;

import org.protege.swrlapi.drools.converters.DroolsOWLClassExpressionConverter;
import org.protege.swrlapi.drools.owl.DroolsBinaryObject;

/**
 * This class represents an OWL object union of class expression in Drools. Each element of the union class list is
 * broken up by the {@link DroolsOWLClassExpressionConverter} class. These are linked together in the Drools OWL 2 RL
 * rules using the class expression ID.
 */
public class OUOCE extends DroolsBinaryObject<String, String> implements CE
{
	public OUOCE(String id, String c1)
	{
		super(id, c1);
	}

	@Override
	public String getceid()
	{
		return getT1();
	}

	public String getC1()
	{
		return getT2();
	}

	@Override
	public String toString()
	{
		return "OUOCE" + super.toString();
	}
}
