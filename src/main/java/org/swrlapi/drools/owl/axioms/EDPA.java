package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl2rl.DroolsOWL2RLAxiomVisitor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an equivalent data property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom
 */
public class EDPA extends DroolsBinaryPropertiesAxiom
{
	public EDPA(String property1ID, String property2ID)
	{
		super(property1ID, property2ID);
	}

	@Override
	public OWLEquivalentDataPropertiesAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(DroolsOWL2RLAxiomVisitor visitor) { visitor.visit(this); }

	@Override
	public String toString()
	{
		return "EDPA" + super.toString();
	}
}
