package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/*
 * This class represents an OWL object property declaration axiom.
 */
public class OPDA extends DA<OP>
{
	public OPDA(String propertyID)
	{
		super(new OP(propertyID));
	}

	public OPDA(OP property)
	{
		super(property);
	}

	public OP getP()
	{
		return getE();
	}

	@Override
	public String toString()
	{
		return "OPDA" + super.toString();
	}

	@Override
	public OWLDeclarationAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}
}
