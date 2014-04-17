package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an OWL individual declaration axiom.
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
}
