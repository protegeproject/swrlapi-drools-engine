package org.swrlapi.drools.owl.dataranges;

import java.util.Set;

/**
 * Class representing an OWL data intersection of data range
 *
 * @see org.semanticweb.owlapi.model.OWLDataIntersectionOf
 */
public class DIO implements DR
{
	private final String rid;
	private final Set<DR> dataRanges;

	public DIO(String rid, Set<DR> dataRanges)
	{
		this.rid = rid;
		this.dataRanges = dataRanges;
	}

	public String getid()
	{
		return this.rid;
	}
}
