package org.swrlapi.drools.reasoner;

import org.swrlapi.drools.owl.core.I;

import java.util.Set;

/**
 * @see org.semanticweb.owlapi.reasoner.impl.OWLNamedIndividualNodeSet
 */
public class INodeSet extends OONodeSet<I>
{
	public INodeSet() {}

	public INodeSet(I entity)
	{
		super(entity);
	}

	public INodeSet(DroolsNode<I> iNode)
	{
		super(iNode);
	}

	public INodeSet(Set<DroolsNode<I>> nodes)
	{
		super(nodes);
	}

	@Override
	protected OONode<I> getNode(I entity)
	{
		return new INode(entity);
	}

	@Override
	protected OONode<I> getNode(Set<I> entities) { return new INode(entities); }
}
