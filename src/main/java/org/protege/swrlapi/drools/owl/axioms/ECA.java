package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.expressions.CE;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;

/**
 * Class representing an OWL equivalent classes axiom in Drools.
 */
public class ECA extends BinaryClassesAxiom
{
	public ECA(String class1ID, String class2ID)
	{
		super(class1ID, class2ID);
	}

	public ECA(CE class1, CE class2)
	{
		super(class1.getceid(), class2.getceid());
	}

	public ECA(String class1ID, CE class2)
	{
		super(class1ID, class2.getceid());
	}

	public ECA(CE class1, String class2ID)
	{
		super(class1.getceid(), class2ID);
	}

	@Override
	public OWLEquivalentClassesAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "ECA" + super.toString();
	}
}