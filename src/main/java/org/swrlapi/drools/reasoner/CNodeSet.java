package org.swrlapi.drools.reasoner;

import org.swrlapi.drools.owl.core.C;
import org.swrlapi.drools.owl.core.I;

import java.util.Set;

/**
 * @see org.semanticweb.owlapi.reasoner.impl.OWLClassNodeSet
 */
public class CNodeSet extends OONodeSet<C>
{
	public CNodeSet() {}

	public CNodeSet(C entity)
	{
		super(entity);
	}

	public CNodeSet(DroolsNode<C> iNode)
	{
		super(iNode);
	}

	public CNodeSet(Set<DroolsNode<C>> nodes)
	{
		super(nodes);
	}

	@Override
	protected OONode<C> getNode(C entity)
	{
		return new CNode(entity);
	}

	@Override
	protected OONode<C> getNode(Set<C> entities) { return new CNode(entities); }
}
