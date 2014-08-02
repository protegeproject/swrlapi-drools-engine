package org.swrlapi.drools.reasoner;

import org.swrlapi.drools.owl.properties.DP;

import java.util.Set;

public class DPNode extends OONode<DP>
{
	public DPNode(DP entity) {
		super(entity);
	}

	public DPNode(Set<DP> entities) {
		super(entities);
	}

	public DPNode() {}

	@Override
	protected DP getTopEntity() {
		return TOP_DATA_PROPERTY;
	}

	@Override
	protected DP getBottomEntity() {
		return BOTTOM_DATA_PROPERTY;
	}

	public static DPNode getTopNode() { return TOP_DATA_NODE; }

	public static DPNode getBottomNode() {
		return BOTTOM_DATA_NODE;
	}
}
