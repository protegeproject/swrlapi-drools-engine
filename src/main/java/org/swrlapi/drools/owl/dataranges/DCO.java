package org.swrlapi.drools.owl.dataranges;

import java.util.Set;

/**
 * Class representing an OWL data complement of data range
 */
public class DCO implements DR
{
	private final String id;
	private final DR complement;

	public DCO(String id, DR complement)
	{
		this.id = id;
		this.complement = complement;
	}

	public String getdrid()
	{
		return this.id;
	}
}
