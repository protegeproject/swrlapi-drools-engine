package org.swrlapi.drools.reasoner;

import org.swrlapi.drools.owl.core.C;

import java.util.Set;

/**
 * @see org.semanticweb.owlapi.reasoner.impl.OWLClassNode
 * @see org.semanticweb.owlapi.reasoner.impl.DefaultNode
 */
public class CNode extends OONode<C>
{
	public CNode(C entity) {
		super(entity);
	}

	public CNode(Set<C> entities) {
		super(entities);
	}

	public CNode() {}

	@Override
	protected C getTopEntity() {
		return TOP_CLASS;
	}

	@Override
	protected C getBottomEntity() {
		return BOTTOM_CLASS;
	}

	public static CNode getTopNode() { return TOP_NODE; }

	public static CNode getBottomNode() {
		return BOTTOM_NODE;
	}
}
