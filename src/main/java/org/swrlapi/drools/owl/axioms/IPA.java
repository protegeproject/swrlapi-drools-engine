package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL inverse functional object property axiom in Drools.
 */
public class IPA extends UnaryObjectPropertyAxiom
{
	public IPA(OP p)
	{
		super(p);
	}

	public IPA(String propertyID)
	{
		super(new OP(propertyID));
	}

	@Override
	public OWLInverseFunctionalObjectPropertyAxiom extract(DroolsA2OWLAxiomExtractor extractor)
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