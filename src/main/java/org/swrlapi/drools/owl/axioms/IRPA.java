package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL irreflexive object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom
 */
public class IRPA extends DroolsUnaryPropertyAxiom
{
	public IRPA(String propertyID)
	{
		super(propertyID);
	}

	@Override
	public OWLIrreflexiveObjectPropertyAxiom extract(DroolsOWLAxiomExtractor extractor)
			throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "IRPA" + super.toString();
	}
}