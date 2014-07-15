package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL domain data property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom
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
	public OWLDataPropertyDomainAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "DDPA(" + getP() + ", " + getD() + ")";
	}
}