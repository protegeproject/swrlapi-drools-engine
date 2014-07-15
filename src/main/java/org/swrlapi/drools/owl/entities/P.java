package org.swrlapi.drools.owl.entities;

/**
 * This class represents an OWL property.
 *
 * @see org.semanticweb.owlapi.model.OWLProperty
 */
public abstract class P extends OE
{
	public P(String propertyID)
	{
		super(propertyID);
	}

	@Override
	public String toString()
	{
		return super.toString();
	}
}