package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL inverse object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom
 */
public class IOPA extends BinaryObjectPropertiesAxiom
{
	public IOPA(OP property1, OP property2)
	{
		super(property1, property2);
	}

	public IOPA(String property1ID, String property2ID)
	{
		this(new OP(property1ID), new OP(property2ID));
	}

	public IOPA(OP property1, String property2ID)
	{
		this(property1, new OP(property2ID));
	}

	public IOPA(String property1ID, OP property2)
	{
		this(new OP(property1ID), property2);
	}

	@Override
	public OWLInverseObjectPropertiesAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "IOPA" + super.toString();
	}
}