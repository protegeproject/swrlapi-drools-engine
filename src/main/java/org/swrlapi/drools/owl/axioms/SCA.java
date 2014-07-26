package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.classexpressions.CE;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.drools.owl2rl.DroolsOWL2RLAxiomVisitor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an OWL subclass axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLSubClassOfAxiom
 */
public class SCA extends DroolsBinaryObject<String, String> implements A
{
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
	public OWLSubClassOfAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(DroolsOWL2RLAxiomVisitor visitor) { visitor.visit(this); }

	@Override
	public String toString()
	{
		return "SCA" + super.toString();
	}
}