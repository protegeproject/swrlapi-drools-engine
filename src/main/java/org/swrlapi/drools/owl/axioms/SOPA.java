package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.drools.owl2rl.DroolsOWL2RLAxiomVisitor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing a sub object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom
 */
public class SOPA extends DroolsBinaryObject<String, String> implements A
{
	public SOPA(String property1ID, String property2ID)
	{
		super(property1ID, property2ID);
	}

	public String getsubpid()
	{
		return getT1();
	}

	public String getsuperpid()
	{
		return getT2();
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

	@Override
	public void visit(DroolsOWL2RLAxiomVisitor visitor) { visitor.visit(this); }
}