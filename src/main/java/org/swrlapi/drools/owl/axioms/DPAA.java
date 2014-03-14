package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.swrlapi.drools.owl.DroolsTernaryObject;
import org.swrlapi.drools.owl.L;
import org.swrlapi.drools.owl.entities.DP;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an OWL data property assertion axiom.
 * <p>
 * We need to have 4 possible constructors for the different argument combinations. This approach makes the rules more
 * readable.
 */
public class DPAA extends DroolsTernaryObject<I, DP, L> implements A
{
	public DPAA(I subject, DP property, L object)
	{
		super(subject, property, object);
	}

	public DPAA(String subjectName, DP property, L object)
	{
		this(new I(subjectName), property, object);
	}

	public DPAA(String subjectName, String propertyName, L object)
	{
		this(new I(subjectName), new DP(propertyName), object);
	}

	public DPAA(I subject, String propertyName, L object)
	{
		this(subject, new DP(propertyName), object);
	}

	public I getS()
	{
		return getT1();
	}

	public DP getP()
	{
		return getT2();
	}

	public L getO()
	{
		return getT3();
	}

	@Override
	public OWLDataPropertyAssertionAxiom extract(DroolsA2OWLAxiomExtractor converter) throws TargetRuleEngineException
	{
		return converter.extract(this);
	}

	@Override
	public String toString()
	{
		return "DPAA" + super.toString();
	}
}
