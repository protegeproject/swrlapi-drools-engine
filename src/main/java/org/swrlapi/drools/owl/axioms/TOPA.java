package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL transitive object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom
 */
public class TOPA extends DroolsUnaryPropertyAxiom
{
	public TOPA(String propertyID)
	{
		super(propertyID);
	}

	@Override
	public String toString()
	{
		return "TOPA" + super.toString();
	}

	@Override
	public OWLTransitiveObjectPropertyAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(AVisitor visitor) { visitor.visit(this); }
}