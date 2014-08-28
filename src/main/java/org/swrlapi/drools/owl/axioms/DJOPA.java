package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing a disjoint object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom
 */
public class DJOPA extends DroolsBinaryPropertiesAxiom
{
	public DJOPA(String property1ID, String property2ID)
	{
		super(property1ID, property2ID);
	}

	@Override
	public OWLDisjointObjectPropertiesAxiom extract(DroolsOWLAxiomExtractor converter) throws
			TargetSWRLRuleEngineException
	{
		return converter.extract(this);
	}

	@Override
	public void visit(AVisitor visitor) { visitor.visit(this); }

	@Override
	public String toString()
	{
		return "DJOPA" + super.toString();
	}
}
