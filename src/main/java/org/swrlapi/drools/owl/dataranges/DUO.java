package org.swrlapi.drools.owl.dataranges;

import java.util.Set;

/**
 * Class representing an OWL data union of data range
 */
public class DUO implements DR
{
	private final String id;
	private final Set<DR> dataRanges;

	public DUO(String id, Set<DR> dataRanges)
	{
		this.id = id;
		this.dataRanges = dataRanges;
	}

	public String getid()
	{
		return this.id;
	}
}
