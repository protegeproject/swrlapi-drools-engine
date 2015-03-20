package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL subclass axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLSubClassOfAxiom
 */
public class SCA extends DroolsBinaryObject<String, String> implements A
{
	private static final long serialVersionUID = 1L;

	public SCA(String class1ID, String class2ID)
	{
		super(class1ID, class2ID);
	}

	public String getsubcid()
	{
		return getT1();
	}

	public String getsupercid()
	{
		return getT2();
	}

	@Override
	public OWLSubClassOfAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetSWRLRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(AVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "SCA" + super.toString();
	}
}