package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an equivalent object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom
 */
public class EOPA extends BinaryObjectPropertiesAxiom
{
	public EOPA(OP property1, OP property2)
	{
		super(property1, property2);
	}

	public EOPA(String property1ID, String property2ID)
	{
		this(new OP(property1ID), new OP(property2ID));
	}

	public EOPA(OP property1, String property2ID)
	{
		this(property1, new OP(property2ID));
	}

	public EOPA(String property1ID, OP property2)
	{
		this(new OP(property1ID), property2);
	}

	@Override
	public OWLEquivalentObjectPropertiesAxiom extract(DroolsOWLAxiomExtractor extractor)
			throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "EOPA" + super.toString();
	}
}
