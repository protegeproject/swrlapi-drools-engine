package org.swrlapi.drools.reasoner;

import org.swrlapi.drools.owl.properties.DP;

import java.util.Set;

/**
 * @see org.semanticweb.owlapi.reasoner.impl.OWLDataPropertyNodeSet
 */
public class DPNodeSet extends OONodeSet<DP>
{
	public DPNodeSet() {}

	public DPNodeSet(DP entity)
	{
		super(entity);
	}

	public DPNodeSet(DroolsNode<DP> opNode)
	{
		super(opNode);
	}

	public DPNodeSet(Set<DroolsNode<DP>> nodes)
	{
		super(nodes);
	}

	@Override
	protected OONode<DP> getNode(DP entity)
	{
		return new DPNode(entity);
	}

	@Override
	protected OONode<DP> getNode(Set<DP> entities) { return new DPNode(entities); }
}
