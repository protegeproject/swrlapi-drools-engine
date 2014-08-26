package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an OWL data property declaration.
 *
 * @see org.semanticweb.owlapi.model.OWLDeclarationAxiom
 */
public class DPDA extends DroolsUnaryPropertyAxiom
{
	public DPDA(String propertyID)
	{
		super(propertyID);
	}

	@Override
	public String toString()
	{
		return "DPDA(" + super.toString() + ")";
	}

	@Override
	public OWLDeclarationAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(AVisitor visitor) { visitor.visit(this); }
}
