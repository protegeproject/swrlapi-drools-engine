package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing a sub data property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom
 */
public class SDPA extends DroolsBinaryObject<String, String> implements A
{
	public SDPA(String property1ID, String property2ID)
	{
		super(property1ID, property2ID);
	}

	public String getsubpid()
	{
		return getT1();
	}

	public String getsuperpid()
	{
		return getT2();
	}

	@Override
	public String toString()
	{
		return "SDPA" + super.toString();
	}

	@Override
	public OWLSubDataPropertyOfAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetSWRLRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public void visit(AVisitor visitor) { visitor.visit(this); }

}
