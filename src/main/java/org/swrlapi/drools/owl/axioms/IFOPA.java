package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL inverse functional object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom
 */
public class IFOPA extends DroolsUnaryPropertyAxiom
{
	private static final long serialVersionUID = 1L;

	public IFOPA(String propertyID)
	{
		super(propertyID);
	}

	@Override
	public OWLInverseFunctionalObjectPropertyAxiom extract(DroolsOWLAxiomExtractor extractor)
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
		return "IFOPA" + super.toString();
	}
}