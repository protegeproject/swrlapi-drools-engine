package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl2rl.DroolsOWL2RLAxiomVisitor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an OWL individual declaration.
 *
 * @see org.semanticweb.owlapi.model.OWLDeclarationAxiom
 */
public class IDA extends DA<I>
{
	public IDA(String individualID)
	{
		super(new I(individualID));
	}

	public IDA(I individual)
	{
		super(individual);
	}

	public I getI()
	{
		return getE();
	}

	@Override
	public String toString()
	{
		return "IDA(" + super.toString() + ")";
	}

	@Override
	public OWLDeclarationAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(DroolsOWL2RLAxiomVisitor visitor) { visitor.visit(this); }
}
