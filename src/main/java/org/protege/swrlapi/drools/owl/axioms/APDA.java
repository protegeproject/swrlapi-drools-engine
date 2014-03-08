package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.entities.AP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;

/**
 * This class represents an OWL annotation property declaration axiom.
 */
public class APDA extends DA<AP>
{
	public APDA(String propertyID)
	{
		super(new AP(propertyID));
	}

	public APDA(AP property)
	{
		super(property);
	}

	public AP getP()
	{
		return getE();
	}

	@Override
	public String toString()
	{
		return "DPDA" + super.toString();
	}

	@Override
	public OWLDeclarationAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}
}
