package org.swrlapi.drools.reasoner;

import org.swrlapi.drools.owl.core.OO;

public interface DroolsNode<E extends OO> extends java.lang.Iterable<E> {
	boolean isTopNode();

	boolean isBottomNode();

	java.util.Set<E> getEntities();

	int getSize();

	boolean contains(E e);

	java.util.Set<E> getEntitiesMinus(E e);

	java.util.Set<E> getEntitiesMinusTop();

	java.util.Set<E> getEntitiesMinusBottom();

	boolean isSingleton();

	E getRepresentativeElement();
}