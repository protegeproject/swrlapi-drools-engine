package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.core.DroolsQuadObject;
import org.swrlapi.drools.owl.properties.DP;

/**
 * This class represents an OWL data maximum qualified cardinality class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataMaxCardinality
 */
public class DMaxQCCE extends DroolsQuadObject<String, String, String, Integer> implements CE
{
	public DMaxQCCE(String id, String propertyID, String fillerID, Integer card)
	{
		super(id, propertyID, fillerID, card);
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

	public String getf()
	{
		return getT3();
	}

	public Integer getcard()
	{
		return getT4();
	}

	@Override
	public String toString()
	{
		return "DMaxQCCE" + super.toString();
	}
}
