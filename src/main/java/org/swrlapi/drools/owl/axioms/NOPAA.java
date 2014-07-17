package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an OWL negative object property assertion axiom.
 * <p/>
 * We have 4 possible constructors for the different argument combinations. This approach provides
 * more flexibility when generating Drools rules and makes the generated rules more readable.
 *
 * @see org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom
 */
public class NOPAA extends DroolsTernaryObject<I, String, I> implements A
{
	public NOPAA(I subject, String propertyID, I object)
	{
		super(subject, propertyID, object);
	}

	public NOPAA(I subject, String propertyID, String objectName)
	{
		this(subject, propertyID, new I(objectName));
	}

	public NOPAA(String subjectName, String propertyID, I object)
	{
		this(new I(subjectName), propertyID, object);
	}

	public NOPAA(String subjectName, String propertyID, String objectName)
	{
		this(new I(subjectName), propertyID, new I(objectName));
	}

	public I gets()
	{
		return getT1();
	}

	public String getpid()
	{
		return getT2();
	}

	public I geto()
	{
		return getT3();
	}

	@Override
	public OWLNegativeObjectPropertyAssertionAxiom extract(DroolsOWLAxiomExtractor extractor)
			throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "NOPAA" + super.toString();
	}
}
