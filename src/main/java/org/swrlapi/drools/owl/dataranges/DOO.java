package org.swrlapi.drools.owl.dataranges;

import org.swrlapi.drools.owl.core.L;

import java.util.Set;

/**
 * Class representing an OWL data one of data range
 *
 * @see org.semanticweb.owlapi.model.OWLDataOneOf
 */
public class DOO implements DR
{
	private final String rid;
	private final Set<L> literals;

	public DOO(String rid, Set<L> literals)
	{
		this.rid = rid;
		this.literals = literals;
	}

	public String getrid()
	{
		return this.rid;
	}
}
