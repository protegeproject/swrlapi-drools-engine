package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.drools.owl2rl.DroolsOWL2RLAxiomVisitor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class represents an OWL object property assertion axiom.
 * <p>
 * We have 4 possible constructors for the different argument combinations. This approach provides
 * more flexibility when generating Drools rules and makes the generated rules more readable.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom
 */
public class OPAA extends DroolsTernaryObject<I, String, I> implements A
{
	public OPAA(I subject, String propertyID, I object)
	{
		super(subject, propertyID, object);
	}

	public OPAA(I subject, String propertyID, String objectName)
	{
		super(subject, propertyID, new I(objectName));
	}

	public OPAA(String subjectName, String propertyID, I object)
	{
		super(new I(subjectName), propertyID, object);
	}

	public OPAA(String subjectName, String propertyID, String objectName)
	{
		super(new I(subjectName), propertyID, new I(objectName));
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
	public OWLObjectPropertyAssertionAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(DroolsOWL2RLAxiomVisitor visitor) { visitor.visit(this); }

	@Override
	public String toString()
	{
		return "OPAA" + super.toString();
	}
}
