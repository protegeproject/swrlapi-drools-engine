package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL inverse functional object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom
 */
public class IPA extends DroolsUnaryPropertyAxiom
{
	public IPA(String propertyID)
	{
		super(propertyID);
	}

	@Override
	public OWLInverseFunctionalObjectPropertyAxiom extract(DroolsOWLAxiomExtractor extractor)
			throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "IPA" + super.toString();
	}
}