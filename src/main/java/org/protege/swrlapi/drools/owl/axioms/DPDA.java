package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.entities.DP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;

/**
 * This class represents an OWL data property declaration axiom.
 */
public class DPDA extends DA<DP>
{
	public DPDA(String propertyID)
	{
		super(new DP(propertyID));
	}

	public DPDA(DP property)
	{
		super(property);
	}

	public DP getP()
	{
		return getE();
	}

	@Override
	public String toString()
	{
		return "DPDA" + super.toString();
	}

	@Override
	public OWLDeclarationAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}
}
