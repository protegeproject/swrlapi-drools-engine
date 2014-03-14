package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.DP;
import org.swrlapi.exceptions.TargetRuleEngineException;

public class FDPA extends UnaryDataPropertyAxiom
{
	public FDPA(DP p)
	{
		super(p);
	}

	public FDPA(String propertyID)
	{
		super(new DP(propertyID));
	}

	@Override
	public OWLFunctionalDataPropertyAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "FDPA" + super.toString();
	}
}