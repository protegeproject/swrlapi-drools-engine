package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an OWL different individual axiom.
 * <p>
 * Rather than holding an arbitrary number of individuals, we hold only pairs and expect that the axiom translator
 * generates all possible pairs from an original OWL axiom.
 * <p>
 * We need to have 4 possible constructors for the different argument combinations. This approach makes the rules more
 * readable.
 */
public class DIA extends BinaryIndividualsAxiom
{
	public DIA(I individual1, I individual2)
	{
		super(individual1, individual2);
	}

	public DIA(String individual1ID, String individual2ID)
	{
		this(new I(individual1ID), new I(individual2ID));
	}

	public DIA(I individual1, String individual2ID)
	{
		this(individual1, new I(individual2ID));
	}

	public DIA(String individual1ID, I individual2)
	{
		this(new I(individual1ID), individual2);
	}

	@Override
	public OWLDifferentIndividualsAxiom extract(DroolsA2OWLAxiomExtractor converter) throws TargetRuleEngineException
	{
		return converter.extract(this);
	}

	@Override
	public String toString()
	{
		return "DIA" + super.toString();
	}
}
