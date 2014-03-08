package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.entities.OP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;

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