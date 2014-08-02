package org.swrlapi.drools.reasoner;

import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.impl.DefaultNode;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.swrlapi.drools.owl.core.OO;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class OONodeSet<E extends OO> implements DroolsNodeSet<E>
{
	private final Set<DroolsNode<E>> nodes = new HashSet<DroolsNode<E>>();

	public OONodeSet() {}

	public OONodeSet(E entity) {
		nodes.add(getNode(entity));
	}

	public OONodeSet(DroolsNode<E> node) {
		nodes.add(node);
	}

	public OONodeSet(Set<DroolsNode<E>> nodes) {
		this.nodes.addAll(nodes);
	}

	@Override
	public Set<DroolsNode<E>> getNodes() { return CollectionFactory.getCopyOnRequestSetFromMutableCollection(nodes); }

	public void addEntity(E entity) {
		if (entity == null) {
			throw new NullPointerException("entity cannot be null");
		}
		addNode(getNode(entity));
	}

	public void addNode(DroolsNode<E> node) {
		if (node == null) {
			throw new NullPointerException("Cannot add null to a NodeSet");
		}
		nodes.add(node);
	}

	public void addAllNodes(Collection<DroolsNode<E>> nodeset) {
		for (DroolsNode<E> node : nodeset) {
			if (node != null) {
				this.nodes.add(node);
			}
		}
	}

	public void addSameEntities(Set<E> entities) {
		nodes.add(getNode(entities));
	}

	public void addDifferentEntities(Set<E> entities) {
		for (E e : entities) {
			addNode(getNode(e));
		}
	}

	protected abstract OONode<E> getNode(E entity);

	protected abstract OONode<E> getNode(Set<E> entities);

	@Override
	public Set<E> getFlattened() {
		Set<E> result = new HashSet<E>();
		for (DroolsNode<E> node : nodes) {
			result.addAll(node.getEntities());
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		return nodes.isEmpty();
	}

	@Override
	public boolean containsEntity(E e) {
		for (DroolsNode<E> node : nodes) {
			if (node.contains(e)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isSingleton() {
		return nodes.size() == 1;
	}

	@Override
	public boolean isTopSingleton() {
		return isSingleton() && nodes.iterator().next().isTopNode();
	}

	@Override
	public boolean isBottomSingleton() {
		return isSingleton() && nodes.iterator().next().isBottomNode();
	}

	@Override
	public Iterator<DroolsNode<E>> iterator() {
		return nodes.iterator();
	}

	@Override
	public String toString() {
		return "Nodeset" + this.nodes.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof NodeSet)) {
			return false;
		}
		NodeSet<?> other = (NodeSet<?>) obj;
		return nodes.equals(other.getNodes());
	}

	@Override
	public int hashCode() {
		return nodes.hashCode();
	}
}
