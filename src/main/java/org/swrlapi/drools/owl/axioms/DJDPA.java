package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.DP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing a disjoint data property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom
 */
public class DJDPA extends BinaryDataPropertiesAxiom
{
	public DJDPA(DP property1, DP property2)
	{
		super(property1, property2);
	}

	public DJDPA(String property1ID, String property2ID)
	{
		this(new DP(property1ID), new DP(property2ID));
	}

	public DJDPA(DP property1, String property2ID)
	{
		this(property1, new DP(property2ID));
	}

	public DJDPA(String property1ID, DP property2)
	{
		this(new DP(property1ID), property2);
	}

	@Override
	public OWLDisjointDataPropertiesAxiom extract(DroolsOWLAxiomExtractor converter) throws TargetRuleEngineException
	{
		return converter.extract(this);
	}

	@Override
	public String toString()
	{
		return "DJDPA" + super.toString();
	}
}
