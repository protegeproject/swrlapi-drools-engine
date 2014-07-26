package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl2rl.DroolsOWL2RLAxiomVisitor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL irreflexive object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom
 */
public class IROPA extends DroolsUnaryPropertyAxiom
{
	public IROPA(String propertyID)
	{
		super(propertyID);
	}

	@Override
	public OWLIrreflexiveObjectPropertyAxiom extract(DroolsOWLAxiomExtractor extractor)
			throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(DroolsOWL2RLAxiomVisitor visitor) { visitor.visit(this); }

	@Override
	public String toString()
	{
		return "IROPA" + super.toString();
	}
}