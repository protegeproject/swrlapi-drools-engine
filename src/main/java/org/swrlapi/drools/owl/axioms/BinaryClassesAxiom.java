package org.swrlapi.drools.owl.axioms;

import org.swrlapi.drools.owl.core.DroolsBinaryObject;

public abstract class BinaryClassesAxiom extends DroolsBinaryObject<String, String> implements A
{
	public BinaryClassesAxiom(String class1ID, String class2ID)
	{
		super(class1ID, class2ID);
	}

	public String getC1()
	{
		return getT1();
	}

	public String getC2()
	{
		return getT2();
	}
}