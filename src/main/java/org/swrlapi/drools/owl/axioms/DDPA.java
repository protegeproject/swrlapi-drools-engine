package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.drools.owl2rl.DroolsOWL2RLAxiomVisitor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL domain data property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom
 */
public class DDPA extends DroolsBinaryObject<String, String> implements A
{
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
	public OWLDataPropertyDomainAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(DroolsOWL2RLAxiomVisitor visitor) { visitor.visit(this); }

	@Override
	public String toString()
	{
		return "DDPA(" + getpid() + ", " + getdid() + ")";
	}
}