package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.entities.OP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;

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