package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.entities.OP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;

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