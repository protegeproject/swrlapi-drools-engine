package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsUnaryObject;
import org.swrlapi.drools.owl2rl.DroolsOWL2RLAxiomVisitor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL asymmetric object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom
 */
public class AOPA extends DroolsUnaryObject<String> implements A
{
	public AOPA(String propertyID)
	{
		super(propertyID);
	}

	public String getpid() { return getT1(); }

	@Override
	public String toString()
	{
		return "AOPA" + super.toString();
	}

	@Override
	public OWLAsymmetricObjectPropertyAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(DroolsOWL2RLAxiomVisitor visitor) { visitor.visit(this); }
}