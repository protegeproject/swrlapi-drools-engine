package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing a disjoint object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom
 */
public class DJOPA extends DroolsBinaryObject<String, String> implements A
{
	public DJOPA(String property1ID, String property2ID)
	{
		super(property1ID, property2ID);
	}

	public String getp1id() { return getT1(); }

	public String getp2id() { return getT2(); }

	@Override
	public OWLDisjointObjectPropertiesAxiom extract(DroolsOWLAxiomExtractor converter) throws TargetRuleEngineException
	{
		return converter.extract(this);
	}

	@Override
	public String toString()
	{
		return "DOPA" + super.toString();
	}
}
