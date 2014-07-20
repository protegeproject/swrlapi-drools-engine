package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * @see org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom
 */
public class FOPA extends DroolsUnaryPropertyAxiom
{
	public FOPA(String propertyID)
	{
		super(propertyID);
	}

	@Override
	public OWLFunctionalObjectPropertyAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "FOPA" + super.toString();
	}
}