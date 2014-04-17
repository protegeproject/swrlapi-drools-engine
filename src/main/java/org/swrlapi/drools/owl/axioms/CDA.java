package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.entities.C;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an OWL class declaration axiom.
 */
public class CDA extends DA<C>
{
	public CDA(String classID)
	{
		super(new C(classID));
	}

	public CDA(C c)
	{
		super(c);
	}

	public C getC()
	{
		return getE();
	}

	@Override
	public String toString()
	{
		return "CDA(" + super.toString() + ")";
	}

	@Override
	public OWLDeclarationAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}
}
