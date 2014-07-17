package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an OWL data property range axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom
 */
public class DPRA extends DroolsBinaryObject<String, String> implements A
{
	public DPRA(String propertyID, String dataRangeID)
	{
		super(propertyID, dataRangeID);
	}

	public String getpid()
	{
		return getT1();
	}

	public String getrid()
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
		return "DPRA" + super.toString();
	}
}