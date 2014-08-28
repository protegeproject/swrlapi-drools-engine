package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL domain object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom
 */
public class DOPA extends DroolsBinaryObject<String, String> implements A
{
	public DOPA(String propertyID, String domainClassID)
	{
		super(propertyID, domainClassID);
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
	public OWLObjectPropertyDomainAxiom extract(DroolsOWLAxiomExtractor converter) throws TargetSWRLRuleEngineException
	{
		return converter.extract(this);
	}

	@Override
	public void visit(AVisitor visitor) { visitor.visit(this); }

	@Override
	public String toString()
	{
		return "DOPA" + super.toString();
	}
}