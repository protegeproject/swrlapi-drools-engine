package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.expressions.CE;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * Class representing an OWL subclass axiom in Drools.
 */
public class SCA extends BinaryClassesAxiom
{
	public SCA(String class1ID, String class2ID)
	{
		super(class1ID, class2ID);
	}

	public SCA(CE class1, CE class2)
	{
		super(class1.getceid(), class2.getceid());
	}

	public SCA(String class1ID, CE class2)
	{
		super(class1ID, class2.getceid());
	}

	public SCA(CE class1, String class2ID)
	{
		super(class1.getceid(), class2ID);
	}

	public String getSub()
	{
		return getC1();
	}

	public String getSup()
	{
		return getC2();
	}

	@Override
	public OWLSubClassOfAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "SCA" + super.toString();
	}
}