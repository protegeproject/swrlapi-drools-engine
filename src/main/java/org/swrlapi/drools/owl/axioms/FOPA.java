package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents on OWL functional object property axiom.
 *
 * @see org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom
 */
public class FOPA extends DroolsUnaryPropertyAxiom
{
	public FOPA(String propertyID)
	{
		super(propertyID);
	}

	@Override
	public OWLFunctionalObjectPropertyAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(AVisitor visitor) { visitor.visit(this); }

	@Override
	public String toString()
	{
		return "FOPA" + super.toString();
	}
}