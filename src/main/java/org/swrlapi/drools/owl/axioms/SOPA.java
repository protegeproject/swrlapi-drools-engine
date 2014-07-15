package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing a sub object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom
 */
public class SOPA extends BinaryObjectPropertiesAxiom
{
	public SOPA(OP property1, OP property2)
	{
		super(property1, property2);
	}

	public SOPA(String property1ID, String property2ID)
	{
		this(new OP(property1ID), new OP(property2ID));
	}

	public SOPA(OP property1, String property2ID)
	{
		this(property1, new OP(property2ID));
	}

	public SOPA(String property1ID, OP property2)
	{
		this(new OP(property1ID), property2);
	}

	public OP getSub()
	{
		return getP1();
	}

	public OP getSup()
	{
		return getP2();
	}

	@Override
	public String toString()
	{
		return "SOPA" + super.toString();
	}

	@Override
	public OWLSubObjectPropertyOfAxiom extract(DroolsOWLAxiomExtractor converter) throws TargetRuleEngineException
	{
		return converter.extract(this);
	}
}