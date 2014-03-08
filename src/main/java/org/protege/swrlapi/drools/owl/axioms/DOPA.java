package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.DroolsBinaryObject;
import org.protege.swrlapi.drools.owl.entities.OP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;

/**
 * Class representing an OWL domain object property axiom in Drools.
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
	public OWLObjectPropertyDomainAxiom extract(DroolsA2OWLAxiomExtractor converter) throws TargetRuleEngineException
	{
		return converter.extract(this);
	}

	@Override
	public String toString()
	{
		return "DOPA" + super.toString();
	}
}