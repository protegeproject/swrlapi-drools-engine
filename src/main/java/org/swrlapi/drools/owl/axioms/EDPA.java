package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an equivalent data property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom
 */
public class EDPA extends DroolsBinaryPropertiesAxiom
{
	private static final long serialVersionUID = 1L;

	public EDPA(String property1ID, String property2ID)
	{
		super(property1ID, property2ID);
	}

	@Override
	public OWLEquivalentDataPropertiesAxiom extract(DroolsOWLAxiomExtractor extractor)
			throws TargetSWRLRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(AVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "EDPA" + super.toString();
	}
}
