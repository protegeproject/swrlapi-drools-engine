package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.core.D;
import org.swrlapi.drools.owl.properties.DP;

/**
 * This class represents an OWL data some values from of class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataSomeValuesFrom
 */
public class DSVFCE extends DroolsTernaryObject<String, String, D> implements CE
{
	public DSVFCE(String ceid, String propertyID, String objectID)
	{
		super(ceid, propertyID, new D(objectID));
	}

	@Override
	public String getceid()
	{
		return getT1();
	}

	public String getpid()
	{
		return getT2();
	}

	public D getV()
	{
		return getT3();
	}

	@Override
	public String toString()
	{
		return "OSVFCE" + super.toString();
	}
}
