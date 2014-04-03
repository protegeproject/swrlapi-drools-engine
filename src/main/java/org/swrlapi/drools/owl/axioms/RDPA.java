package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.DroolsBinaryObject;
import org.swrlapi.drools.owl.entities.D;
import org.swrlapi.drools.owl.entities.DP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL range data property axiom in Drools.
 */
public class RDPA extends DroolsBinaryObject<DP, D> implements A
{
	public RDPA(DP property, D range)
	{
		super(property, range);
	}

	public RDPA(String propertyID, String rangeID)
	{
		this(new DP(propertyID), new D(rangeID));
	}

	public RDPA(DP property, String rangeID)
	{
		this(property, new D(rangeID));
	}

	public RDPA(String propertyID, D range)
	{
		this(new DP(propertyID), range);
	}

	public DP getP()
	{
		return getT1();
	}

	public D getR()
	{
		return getT2();
	}

	@Override
	public OWLDataPropertyRangeAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "RDPA" + super.toString();
	}
}