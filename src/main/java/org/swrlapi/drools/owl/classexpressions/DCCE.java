package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.properties.DP;

/**
 * This class represents an OWL data exact cardinality class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataExactCardinality
 */
public class DCCE extends DroolsTernaryObject<String, String, Integer> implements CE
{
	public DCCE(String id, String propertyID, Integer card)
	{
		super(id, propertyID, card);
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

	public Integer getcard()
	{
		return getT3();
	}

	@Override
	public String toString()
	{
		return "DCCE" + super.toString();
	}
}
