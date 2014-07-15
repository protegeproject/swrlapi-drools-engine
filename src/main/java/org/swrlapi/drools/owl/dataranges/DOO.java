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
	private final String id;
	private final Set<L> literals;

	public DOO(String id, Set<L> literals)
	{
		this.id = id;
		this.literals = literals;
	}

	public String getid()
	{
		return this.id;
	}
}
