package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.entities.I;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;

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
		return "IDA" + super.toString();
	}

	@Override
	public OWLDeclarationAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}
}
