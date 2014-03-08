package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.DroolsBinaryObject;
import org.protege.swrlapi.drools.owl.entities.I;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;

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
	public OWLClassAssertionAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "CAA" + super.toString();
	}
}
