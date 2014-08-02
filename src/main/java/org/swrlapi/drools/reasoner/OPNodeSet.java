package org.swrlapi.drools.reasoner;

import org.swrlapi.drools.owl.properties.OP;

import java.util.Set;

/**
 * @see org.semanticweb.owlapi.reasoner.impl.OWLObjectPropertyNodeSet
 */
public class OPNodeSet extends OONodeSet<OP>
{
	public OPNodeSet() {}

	public OPNodeSet(OP entity)
	{
		super(entity);
	}

	public OPNodeSet(DroolsNode<OP> opNode)
	{
		super(opNode);
	}

	public OPNodeSet(Set<DroolsNode<OP>> nodes)
	{
		super(nodes);
	}

	@Override
	protected OONode<OP> getNode(OP entity)
	{
		return new OPNode(entity);
	}

	@Override
	protected OONode<OP> getNode(Set<OP> entities) { return new OPNode(entities); }
}
