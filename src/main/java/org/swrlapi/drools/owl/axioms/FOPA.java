package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

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