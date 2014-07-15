package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.DroolsTernaryObject;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an OWL negative object property assertion axiom.
 * <p>
 * We need to have 8 possible constructors for the different argument combinations. This approach provided
 * more flexibility when generating Drools rules and makes the generated rules more readable.
 *
 * @see org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom
 */
public class NOPAA extends DroolsTernaryObject<I, OP, I> implements A
{
	public NOPAA(I subject, OP property, I object)
	{
		super(subject, property, object);
	}

	public NOPAA(I subject, OP property, String objectName)
	{
		this(subject, property, new I(objectName));
	}

	public NOPAA(I subject, String propertyName, I object)
	{
		this(subject, new OP(propertyName), object);
	}

	public NOPAA(I subject, String propertyName, String objectName)
	{
		this(subject, new OP(propertyName), new I(objectName));
	}

	public NOPAA(String subjectName, OP property, I object)
	{
		this(new I(subjectName), property, object);
	}

	public NOPAA(String subjectName, OP property, String objectName)
	{
		this(new I(subjectName), property, new I(objectName));
	}

	public NOPAA(String subjectName, String propertyName, I object)
	{
		this(new I(subjectName), new OP(propertyName), object);
	}

	public NOPAA(String subjectName, String propertyName, String objectName)
	{
		this(new I(subjectName), new OP(propertyName), new I(objectName));
	}

	public I getS()
	{
		return getT1();
	}

	public OP getP()
	{
		return getT2();
	}

	public I getO()
	{
		return getT3();
	}

	@Override
	public OWLNegativeObjectPropertyAssertionAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "NOPAA" + super.toString();
	}
}
