package org.swrlapi.drools.owl.dataranges;

import java.util.Set;

/**
 * Class representing an OWL data union of data range
 *
 * @see org.semanticweb.owlapi.model.OWLDataUnionOf
 */
public class DUO implements DR
{
	private final String rid;
	private final Set<DR> dataRanges;

	public DUO(String rid, Set<DR> dataRanges)
	{
		this.rid = rid;
		this.dataRanges = dataRanges;
	}

	public String getid()
	{
		return this.rid;
	}
}
