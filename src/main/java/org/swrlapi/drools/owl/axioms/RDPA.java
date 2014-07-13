package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.DroolsBinaryObject;
import org.swrlapi.drools.owl.dataranges.DR;
import org.swrlapi.drools.owl.entities.D;
import org.swrlapi.drools.owl.entities.DP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an OWL range data property axiom in Drools.
 */
public class RDPA extends DroolsBinaryObject<DP, String> implements A
{
	public RDPA(DP property, String dataRangeID)
	{
		super(property, dataRangeID);
	}

	public RDPA(String propertyID, String dataRangeID)
	{
		this(new DP(propertyID), dataRangeID);
	}

	public DP getP()
	{
		return getT1();
	}

	public String getR()
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