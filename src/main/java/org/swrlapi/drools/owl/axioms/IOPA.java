package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL inverse object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom
 */
public class IOPA extends DroolsBinaryPropertiesAxiom
{
	private static final long serialVersionUID = 1L;

	public IOPA(String property1ID, String property2ID)
	{
		super(property1ID, property2ID);
	}

	@Override
	public OWLInverseObjectPropertiesAxiom extract(DroolsOWLAxiomExtractor extractor)
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
		return "IOPA" + super.toString();
	}
}