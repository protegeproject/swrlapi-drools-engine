package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.classexpressions.CE;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL equivalent classes axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom
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
	public OWLEquivalentClassesAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "ECA" + super.toString();
	}
}