package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.entities.OP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;

public class FOPA extends UnaryObjectPropertyAxiom
{
	public FOPA(OP p)
	{
		super(p);
	}

	public FOPA(String propertyID)
	{
		super(new OP(propertyID));
	}

	@Override
	public OWLFunctionalObjectPropertyAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "FOPA" + super.toString();
	}
}