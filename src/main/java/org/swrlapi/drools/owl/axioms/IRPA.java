package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL irreflexive object property axiom in Drools.
 */
public class IRPA extends UnaryObjectPropertyAxiom
{
	public IRPA(OP p)
	{
		super(p);
	}

	public IRPA(String propertyID)
	{
		super(new OP(propertyID));
	}

	@Override
	public OWLIrreflexiveObjectPropertyAxiom extract(DroolsA2OWLAxiomExtractor extractor)
			throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "IRPA" + super.toString();
	}
}