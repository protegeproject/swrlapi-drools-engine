package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.owl.DroolsUnaryObject;
import org.protege.swrlapi.drools.owl.entities.OE;

/**
 * Base class representing an OWL declaration axiom. In the Portability API, there are four types of declaration axioms.
 */
public abstract class DA<T1 extends OE> extends DroolsUnaryObject<T1> implements A
{
	public DA(T1 entity)
	{
		super(entity);
	}

	public T1 getE()
	{
		return getT1();
	}
}