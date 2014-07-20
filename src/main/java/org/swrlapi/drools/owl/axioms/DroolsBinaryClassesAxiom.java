package org.swrlapi.drools.owl.axioms;

import org.swrlapi.drools.owl.core.DroolsBinaryObject;

public abstract class DroolsBinaryClassesAxiom extends DroolsBinaryObject<String, String> implements A
{
	public DroolsBinaryClassesAxiom(String class1ID, String class2ID)
	{
		super(class1ID, class2ID);
	}

	public String getc1id()
	{
		return getT1();
	}

	public String getc2id()
	{
		return getT2();
	}
}