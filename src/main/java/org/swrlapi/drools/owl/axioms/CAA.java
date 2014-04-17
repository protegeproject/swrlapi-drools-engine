package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.DroolsBinaryObject;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL class assertion axiom in Drools.
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

	public String getC()
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
	public String toString()
	{
		return "CAA(" + getC() + ", " + getI() + ")";
	}
}
