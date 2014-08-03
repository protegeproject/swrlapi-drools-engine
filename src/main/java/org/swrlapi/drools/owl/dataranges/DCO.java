package org.swrlapi.drools.owl.dataranges;

/**
 * Class representing an OWL data complement of data range
 *
 * @see org.semanticweb.owlapi.model.OWLDataComplementOf
 */
public class DCO implements DR
{
	private final String rid;
	private final DR complement;

	public DCO(String rid, DR complement)
	{
		this.rid = rid;
		this.complement = complement;
	}

	public String getid()
	{
		return this.rid;
	}

	public DR getcomplement() { return this.complement; }
}
