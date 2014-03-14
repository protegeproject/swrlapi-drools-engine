package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

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