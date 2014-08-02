package org.swrlapi.drools.reasoner;

import org.swrlapi.drools.owl.core.OO;

import java.util.Set;

/**
 * @see org.semanticweb.owlapi.reasoner.NodeSet
 */
public interface DroolsNodeSet<E extends OO> extends Iterable<DroolsNode<E>> {

	Set<E> getFlattened();

	boolean isEmpty();

	boolean containsEntity(E e);

	boolean isSingleton();

	boolean isTopSingleton();

	boolean isBottomSingleton();

	Set<DroolsNode<E>> getNodes();
}
