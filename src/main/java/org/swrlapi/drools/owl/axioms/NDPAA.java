package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.DroolsTernaryObject;
import org.swrlapi.drools.owl.L;
import org.swrlapi.drools.owl.entities.DP;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an OWL negative data property assertion axiom.
 *
 * @see org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom
 */
public class NDPAA extends DroolsTernaryObject<I, DP, L> implements A
{
	public NDPAA(I subject, DP property, L object)
	{
		super(subject, property, object);
	}

	public NDPAA(String subjectName, String propertyID, L object)
	{
		this(new I(subjectName), new DP(propertyID), object);
	}

	public NDPAA(I subject, String propertyName, L object)
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
	public OWLNegativeDataPropertyAssertionAxiom extract(DroolsOWLAxiomExtractor converter) throws TargetRuleEngineException
	{
		return converter.extract(this);
	}

	@Override
	public String toString()
	{
		return "NDPAA" + super.toString();
	}
}
