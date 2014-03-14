package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL asymmetric object property axiom in Drools.
 */
public class APA extends UnaryObjectPropertyAxiom
{
	public APA(OP p)
	{
		super(p);
	}

	public APA(String propertyID)
	{
		super(new OP(propertyID));
	}

	@Override
	public String toString()
	{
		return "APA" + super.toString();
	}

	@Override
	public OWLAsymmetricObjectPropertyAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}
}