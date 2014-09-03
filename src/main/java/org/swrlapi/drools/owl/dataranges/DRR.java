package org.swrlapi.drools.owl.dataranges;

import java.util.Set;

// TODO Facets are not represented

/**
 * Class representing an OWL datatype restriction data range
 *
 * @see org.semanticweb.owlapi.model.OWLDatatypeRestriction
 */
public class DRR implements DR
{
	private final String rid;

	public DRR(String rid)
	{
		this.rid = rid;
	}

	public String getrid()
	{
		return this.rid;
	}
}
