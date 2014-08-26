package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an OWL class declaration.
 *
 * @see org.semanticweb.owlapi.model.OWLDeclarationAxiom
 */
public class CDA extends DroolsUnaryClassAxiom
{
	public CDA(String classID)
	{
		super(classID);
	}

	@Override
	public String toString()
	{
		return "CDA(" + super.toString() + ")";
	}

	@Override
	public OWLDeclarationAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(AVisitor visitor) { visitor.visit(this); }
}
