package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.core.DroolsTernaryObject;

/**
 * This class represents an OWL data maximum cardinality class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataMaxCardinality
 */
public class DMaxCCE extends DroolsTernaryObject<String, String, Integer> implements CE
{
	public DMaxCCE(String ceid, String propertyID, Integer card)
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
		return "DMaxCCE" + super.toString();
	}
}
