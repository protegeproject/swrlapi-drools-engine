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
	private final Set<String> dataRangeIDs;

	public DUO(String rid, Set<String> dataRangeIDs)
	{
		this.rid = rid;
		this.dataRangeIDs = dataRangeIDs;
	}

	public String getrid()
	{
		return this.rid;
	}

	public Set<String> getDataRangeIDs() { return this.dataRangeIDs; }
}
