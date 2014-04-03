package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.DroolsBinaryObject;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL range object property axiom in Drools.
 */
public class ROPA extends DroolsBinaryObject<OP, String> implements A
{
	public ROPA(OP property, String rangeClassID)
	{
		super(property, rangeClassID);
	}

	public ROPA(String propertyID, String rangeClassID)
	{
		this(new OP(propertyID), rangeClassID);
	}

	public OP getP()
	{
		return getT1();
	}

	public String getR()
	{
		return getT2();
	}

	@Override
	public OWLObjectPropertyRangeAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "ROPA" + super.toString();
	}
}