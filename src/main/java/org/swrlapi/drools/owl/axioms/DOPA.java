package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.DroolsBinaryObject;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL domain object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom
 */
public class DOPA extends DroolsBinaryObject<OP, String> implements A
{
	public DOPA(OP property, String domainClassID)
	{
		super(property, domainClassID);
	}

	public DOPA(String propertyID, String domainClassID)
	{
		this(new OP(propertyID), domainClassID);
	}

	public OP getP()
	{
		return getT1();
	}

	public String getD()
	{
		return getT2();
	}

	@Override
	public OWLObjectPropertyDomainAxiom extract(DroolsOWLAxiomExtractor converter) throws TargetRuleEngineException
	{
		return converter.extract(this);
	}

	@Override
	public String toString()
	{
		return "DOPA" + super.toString();
	}
}