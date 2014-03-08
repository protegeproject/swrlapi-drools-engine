package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.DroolsBinaryObject;
import org.protege.swrlapi.drools.owl.entities.DP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;

/**
 * Class representing an OWL domain data property axiom in Drools.
 */
public class DDPA extends DroolsBinaryObject<DP, String> implements A
{
	public DDPA(DP property, String domainClassID)
	{
		super(property, domainClassID);
	}

	public DDPA(String propertyID, String domainClassID)
	{
		this(new DP(propertyID), domainClassID);
	}

	public DP getP()
	{
		return getT1();
	}

	public String getD()
	{
		return getT2();
	}

	@Override
	public OWLDataPropertyDomainAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "DDPA" + super.toString();
	}
}