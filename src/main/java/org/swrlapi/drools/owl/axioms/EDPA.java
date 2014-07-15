package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.DP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an equivalent data property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom
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
	public OWLEquivalentDataPropertiesAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "EDPA" + super.toString();
	}
}
