package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.entities.C;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;

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
		return "CDA" + super.toString();
	}

	@Override
	public OWLDeclarationAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}
}
