package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl2rl.DroolsOWL2RLAxiomVisitor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an OWL data property declaration.
 *
 * @see org.semanticweb.owlapi.model.OWLDeclarationAxiom
 */
public class DPDA extends DA<DP>
{
	public DPDA(String propertyID)
	{
		super(new DP(propertyID));
	}

	public DPDA(DP property)
	{
		super(property);
	}

	public DP getP()
	{
		return getE();
	}

	@Override
	public String toString()
	{
		return "DPDA(" + super.toString() + ")";
	}

	@Override
	public OWLDeclarationAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(DroolsOWL2RLAxiomVisitor visitor) { visitor.visit(this); }
}
