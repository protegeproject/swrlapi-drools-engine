package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.properties.DP;

/**
 * This class represents an OWL data minimum cardinality class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataMinCardinality
 */
public class DMinCCE extends DroolsTernaryObject<String, DP, Integer> implements CE
{
	private static final long serialVersionUID = 1L;

	public DMinCCE(String ceid, DP p, Integer card)
	{
		super(ceid, p, card);
	}

	public DMinCCE(String ceid, String propertyID, Integer card)
	{
		super(ceid, new DP(propertyID), card);
	}

	@Override
	public String getceid()
	{
		return getT1();
	}

	public DP getP()
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
		return "DMinCCE" + super.toString();
	}
}
