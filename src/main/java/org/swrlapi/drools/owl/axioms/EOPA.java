package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an equivalent object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom
 */
public class EOPA extends DroolsBinaryPropertiesAxiom
{
	public EOPA(String property1ID, String property2ID)
	{
		super(property1ID, property2ID);
	}

	@Override
	public OWLEquivalentObjectPropertiesAxiom extract(DroolsOWLAxiomExtractor extractor)
			throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(AVisitor visitor) { visitor.visit(this); }

	@Override
	public String toString()
	{
		return "EOPA" + super.toString();
	}
}
