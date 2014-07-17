package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsUnaryObject;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 *
 * @see org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom
 */
public class FDPA extends DroolsUnaryObject<String> implements A
{
	public FDPA(String propertyID)
	{
		super(propertyID);
	}

	public String getpid()
	{
		return getT1();
	}

	@Override
	public OWLFunctionalDataPropertyAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "FDPA" + super.toString();
	}
}