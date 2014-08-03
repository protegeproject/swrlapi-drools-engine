package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.converters.DroolsOWLClassExpressionConverter;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;

/**
 * This class represents an OWL object intersection of class expression in Drools. Each element of the intersection
 * class list is broken up by the {@link DroolsOWLClassExpressionConverter} class. These are linked together in the
 * Drools OWL 2 RL rules using the class expression ID.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectIntersectionOf
 */
public class OIOCE extends DroolsBinaryObject<String, String> implements CE
{
	public OIOCE(String ceid, String c1)
	{
		super(ceid, c1);
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
		return "OIOCE" + super.toString();
	}
}
