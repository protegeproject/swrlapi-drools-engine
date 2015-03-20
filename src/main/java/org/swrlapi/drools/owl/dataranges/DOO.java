package org.swrlapi.drools.owl.dataranges;

import java.util.Set;

import org.swrlapi.drools.owl.core.L;

/**
 * Class representing an OWL data one of data range
 *
 * @see org.semanticweb.owlapi.model.OWLDataOneOf
 */
public class DOO implements DR
{
	private static final long serialVersionUID = 1L;

	public final String rid;
	public final Set<L> literals;

	public DOO(String rid, Set<L> literals)
	{
		this.rid = rid;
		this.literals = literals;
	}

	@Override
	public String getrid()
	{
		return this.rid;
	}
}
