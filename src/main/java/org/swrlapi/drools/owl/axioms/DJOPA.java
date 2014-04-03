package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing a disjoint object property axiom in Drools.
 */
public class DJOPA extends BinaryObjectPropertiesAxiom
{
	public DJOPA(OP property1, OP property2)
	{
		super(property1, property2);
	}

	public DJOPA(String property1Name, String property2Name)
	{
		this(new OP(property1Name), new OP(property2Name));
	}

	public DJOPA(OP property1, String property2Name)
	{
		this(property1, new OP(property2Name));
	}

	public DJOPA(String property1Name, OP property2)
	{
		this(new OP(property1Name), property2);
	}

	@Override
	public OWLDisjointObjectPropertiesAxiom extract(DroolsOWLAxiomExtractor converter) throws TargetRuleEngineException
	{
		return converter.extract(this);
	}

	@Override
	public String toString()
	{
		return "DOPA" + super.toString();
	}
}
