package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl2rl.DroolsOWL2RLAxiomVisitor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL inverse functional object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom
 */
public class IFOPA extends DroolsUnaryPropertyAxiom
{
	public IFOPA(String propertyID)
	{
		super(propertyID);
	}

	@Override
	public OWLInverseFunctionalObjectPropertyAxiom extract(DroolsOWLAxiomExtractor extractor)
			throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(DroolsOWL2RLAxiomVisitor visitor) { visitor.visit(this); }

	@Override
	public String toString()
	{
		return "IFOPA" + super.toString();
	}
}