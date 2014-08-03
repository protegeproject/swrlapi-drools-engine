package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.properties.OP;

/**
 * This class represents an OWL object maximum cardinality class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectMaxCardinality
 */
public class OMaxCCE extends DroolsTernaryObject<String, String, Integer> implements CE
{
	public OMaxCCE(String ceid, String propertyID, Integer card)
	{
		super(ceid, propertyID, card);
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
