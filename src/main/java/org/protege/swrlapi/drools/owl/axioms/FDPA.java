package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.entities.DP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;

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