package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.DroolsBinaryObject;
import org.protege.swrlapi.drools.owl.entities.OP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;

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
	public OWLObjectPropertyRangeAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "ROPA" + super.toString();
	}
}