package org.swrlapi.drools.reasoner;

import org.swrlapi.drools.owl.properties.OP;

import java.util.Set;

/**
 * @see org.semanticweb.owlapi.reasoner.impl.OWLObjectPropertyNode
 */
public class OPNode extends OONode<OP>
{
	public OPNode(OP entity) {
		super(entity);
	}

	public OPNode(Set<OP> entities) {
		super(entities);
	}

	public OPNode() {}

	@Override
	protected OP getTopEntity() {
		return TOP_OBJECT_PROPERTY;
	}

	@Override
	protected OP getBottomEntity() {
		return BOTTOM_OBJECT_PROPERTY;
	}

	public static OPNode getTopNode() { return TOP_OBJECT_NODE;}

	public static OPNode getBottomNode() {
		return BOTTOM_OBJECT_NODE;
	}
}
