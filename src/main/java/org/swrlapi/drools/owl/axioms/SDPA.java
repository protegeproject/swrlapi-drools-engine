package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.DP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing a sub data property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom
 */
public class SDPA extends BinaryDataPropertiesAxiom
{
	public SDPA(DP property1, DP property2)
	{
		super(property1, property2);
	}

	public SDPA(String property1ID, String property2ID)
	{
		this(new DP(property1ID), new DP(property2ID));
	}

	public SDPA(DP property1, String property2ID)
	{
		this(property1, new DP(property2ID));
	}

	public SDPA(String property1ID, DP property2)
	{
		this(new DP(property1ID), property2);
	}

	public DP getSub()
	{
		return getP1();
	}

	public DP getSup()
	{
		return getP2();
	}

	@Override
	public String toString()
	{
		return "SDPA" + super.toString();
	}

	@Override
	public OWLSubDataPropertyOfAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}
}
