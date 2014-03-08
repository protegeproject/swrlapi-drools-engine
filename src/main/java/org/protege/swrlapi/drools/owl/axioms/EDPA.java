package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.entities.DP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;

/**
 * Class representing an equivalent data property axiom in Drools.
 */
public class EDPA extends BinaryDataPropertiesAxiom
{
	public EDPA(DP property1, DP property2)
	{
		super(property1, property2);
	}

	public EDPA(String property1ID, String property2ID)
	{
		this(new DP(property1ID), new DP(property2ID));
	}

	public EDPA(DP property1, String property2ID)
	{
		this(property1, new DP(property2ID));
	}

	public EDPA(String property1ID, DP property2)
	{
		this(new DP(property1ID), property2);
	}

	@Override
	public OWLEquivalentDataPropertiesAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "EDPA" + super.toString();
	}
}
