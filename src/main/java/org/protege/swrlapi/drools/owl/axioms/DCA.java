package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;

/**
 * Class representing an OWL disjoint classes axiom in Drools.
 */
public class DCA extends BinaryClassesAxiom
{
	public DCA(String class1ID, String class2ID)
	{
		super(class1ID, class2ID);
	}

	@Override
	public OWLDisjointClassesAxiom extract(DroolsA2OWLAxiomExtractor converter) throws TargetRuleEngineException
	{
		return converter.extract(this);
	}

	@Override
	public String toString()
	{
		return "DCA" + super.toString();
	}
}