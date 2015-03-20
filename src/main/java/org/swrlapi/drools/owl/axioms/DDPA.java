package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL domain data property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom
 */
public class DDPA extends DroolsBinaryObject<String, String> implements A
{
	private static final long serialVersionUID = 1L;

	public DDPA(String propertyID, String domainID)
	{
		super(propertyID, domainID);
	}

	public String getpid()
	{
		return getT1();
	}

	public String getdid()
	{
		return getT2();
	}

	@Override
	public OWLDataPropertyDomainAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetSWRLRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(AVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "DDPA(" + getpid() + ", " + getdid() + ")";
	}
}