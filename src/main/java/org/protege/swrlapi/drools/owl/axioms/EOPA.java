package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.entities.OP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;

/**
 * Class representing an equivalent object property axiom in Drools.
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
	public OWLEquivalentObjectPropertiesAxiom extract(DroolsA2OWLAxiomExtractor extractor)
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
