package org.swrlapi.drools.reasoner;

import org.semanticweb.owlapi.reasoner.Node;
import org.swrlapi.drools.owl.core.C;
import org.swrlapi.drools.owl.core.D;
import org.swrlapi.drools.owl.core.OO;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.OP;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @see org.semanticweb.owlapi.reasoner.Node
 * @see org.semanticweb.owlapi.reasoner.impl.DefaultNode
 */
public abstract class OONode<E extends OO> implements DroolsNode<E>
{
  protected static final C TOP_CLASS = C.getOWLThing();
	protected static final CNode TOP_NODE = new CNode(TOP_CLASS);
	protected static final C BOTTOM_CLASS = C.getOWLNothing();
	protected static final CNode BOTTOM_NODE = new CNode(BOTTOM_CLASS);
	protected static final DP TOP_DATA_PROPERTY = DP.getOWLTopDataProperty();
	protected static final DPNode TOP_DATA_NODE = new DPNode(TOP_DATA_PROPERTY);
	protected static final DP BOTTOM_DATA_PROPERTY = DP.getOWLBottomDataProperty();
	protected static final DPNode BOTTOM_DATA_NODE = new DPNode(BOTTOM_DATA_PROPERTY);
	protected static final D TOP_DATATYPE = D.getTopDatatype();
	protected static final OP TOP_OBJECT_PROPERTY = OP.getOWLTopObjectProperty();
	protected static final OPNode TOP_OBJECT_NODE = new OPNode(TOP_OBJECT_PROPERTY);
	protected static final OP BOTTOM_OBJECT_PROPERTY = OP.getOWLBottomObjectProperty();
	protected static final OPNode BOTTOM_OBJECT_NODE = new OPNode(BOTTOM_OBJECT_PROPERTY);

	private final Set<E> entities = new HashSet<E>(4);

	public OONode(E entity) {
		this.entities.add(entity);
	}

	public OONode(Set<E> entities) {
		this.entities.addAll(entities);
	}

	protected OONode() {}

	protected abstract E getTopEntity();

	protected abstract E getBottomEntity();

	public void add(E entity) {
		entities.add(entity);
	}

	@Override
	public boolean isTopNode() {
		return entities.contains(getTopEntity());
	}

	@Override
	public boolean isBottomNode() {
		return entities.contains(getBottomEntity());
	}

	@Override
	public Set<E> getEntities() {
		return entities;
	}

	@Override
	public int getSize() {
		return entities.size();
	}

	@Override
	public boolean contains(E entity) {
		return entities.contains(entity);
	}

	@Override
	public Set<E> getEntitiesMinus(E E) {
		HashSet<E> result = new HashSet<E>(entities);
		result.remove(E);
		return result;
	}

	@Override
	public Set<E> getEntitiesMinusTop() {
		return getEntitiesMinus(getTopEntity());
	}

	@Override
	public Set<E> getEntitiesMinusBottom() {
		return getEntitiesMinus(getBottomEntity());
	}

	@Override
	public boolean isSingleton() {
		return entities.size() == 1;
	}

	@Override
	public E getRepresentativeElement() {
		if (!entities.isEmpty()) {
			return entities.iterator().next();
		} else {
			return null;
		}
	}

	@Override
	public Iterator<E> iterator() {
		return entities.iterator();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Node( ");
		for (OO entity : entities) {
			sb.append(entity);
			sb.append(" ");
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Node)) {
			return false;
		}
		Node<?> other = (Node<?>) obj;
		return entities.equals(other.getEntities());
	}

	@Override
	public int hashCode() {
		return entities.hashCode();
	}
}
