package org.swrlapi.drools.owl.dataranges;

import java.util.Set;

/**
 * Class representing an OWL data intersection of data range
 */
public class DIO implements DR
{
	private final String id;
	private final Set<DR> dataRanges;

	public DIO(String id, Set<DR> dataRanges)
	{
		this.id = id;
		this.dataRanges = dataRanges;
	}

	public String getid()
	{
		return this.id;
	}
}
