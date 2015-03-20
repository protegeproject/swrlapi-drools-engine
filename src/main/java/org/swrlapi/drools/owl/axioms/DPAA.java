package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl.core.L;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL data property assertion axiom.
 *
 * @see org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom
 */
public class DPAA extends DroolsTernaryObject<I, String, L> implements A
{
	private static final long serialVersionUID = 1L;

	public DPAA(String subjectName, String propertyID, L object)
	{
		super(new I(subjectName), propertyID, object);
	}

	public DPAA(I subject, String propertyID, L object)
	{
		super(subject, propertyID, object);
	}

	public I gets()
	{
		return getT1();
	}

	public String getsid()
	{
		return getT1().getid();
	}

	public String getpid()
	{
		return getT2();
	}

	public L geto()
	{
		return getT3();
	}

	@Override
	public OWLDataPropertyAssertionAxiom extract(DroolsOWLAxiomExtractor converter) throws TargetSWRLRuleEngineException
	{
		return converter.extract(this);
	}

	@Override
	public void visit(AVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "DPAA" + super.toString();
	}
}
