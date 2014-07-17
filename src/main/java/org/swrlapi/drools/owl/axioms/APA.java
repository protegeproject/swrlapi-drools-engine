package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsUnaryObject;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL asymmetric object property axiom in Drools.
 */
public class APA extends DroolsUnaryObject<String> implements A
{
	public APA(String propertyID)
	{
		super(propertyID);
	}

	public String getpid() { return getT1(); }

	@Override
	public String toString()
	{
		return "APA" + super.toString();
	}

	@Override
	public OWLAsymmetricObjectPropertyAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}
}