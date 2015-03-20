package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 *
 * @see org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom
 */
public class FDPA extends DroolsUnaryPropertyAxiom
{
	private static final long serialVersionUID = 1L;

	public FDPA(String propertyID)
	{
		super(propertyID);
	}

	@Override
	public OWLFunctionalDataPropertyAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetSWRLRuleEngineException
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
		return "FDPA" + super.toString();
	}
}