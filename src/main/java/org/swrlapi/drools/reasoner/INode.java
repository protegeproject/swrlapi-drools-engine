package org.swrlapi.drools.reasoner;

import org.swrlapi.drools.owl.core.I;

import java.util.Set;

/**
 * @see org.semanticweb.owlapi.reasoner.impl.OWLNamedIndividualNode
 */
public class INode extends OONode<I>
{
	public INode() {}

	public INode(I entity) {
		super(entity);
	}

	public INode(Set<I> entities) {
		super(entities);
	}

	@Override
	protected I getTopEntity() {
		return null;
	}

	@Override
	protected I getBottomEntity() {
		return null;
	}
}
