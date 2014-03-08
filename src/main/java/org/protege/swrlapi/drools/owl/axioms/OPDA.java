package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.entities.OP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;

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
	public OWLDeclarationAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}
}
