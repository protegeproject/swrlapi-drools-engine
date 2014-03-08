package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.entities.OP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;

/**
 * Class representing an OWL symmetric object property axiom in Drools.
 */
public class SPA extends UnaryObjectPropertyAxiom
{
	public SPA(OP p)
	{
		super(p);
	}

	public SPA(String propertyID)
	{
		super(new OP(propertyID));
	}

	@Override
	public String toString()
	{
		return "SPA" + super.toString();
	}

	@Override
	public OWLSymmetricObjectPropertyAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}
}