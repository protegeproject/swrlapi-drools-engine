package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.converters.DroolsOWLClassExpressionConverter;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;

/**
 * This class represents an OWL object union of class expression in Drools. Each element of the union class list is
 * broken up by the {@link DroolsOWLClassExpressionConverter} class. These are linked together in the Drools OWL 2 RL
 * rules using the class expression ID.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectUnionOf
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
