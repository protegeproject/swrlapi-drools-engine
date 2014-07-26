package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl2rl.DroolsOWL2RLAxiomVisitor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL class assertion axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLClassAssertionAxiom
 */
public class CAA extends DroolsBinaryObject<String, I> implements A
{
	public CAA(String classID, I individual)
	{
		super(classID, individual);
	}

	public CAA(String classID, String individualID)
	{
		this(classID, new I(individualID));
	}

	public String getcid()
	{
		return getT1();
	}

	public I getI()
	{
		return getT2();
	}

	@Override
	public OWLClassAssertionAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(DroolsOWL2RLAxiomVisitor visitor) { visitor.visit(this); }

	@Override
	public String toString()
	{
		return "CAA(" + getcid() + ", " + getI() + ")";
	}
}
