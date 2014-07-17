package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing a disjoint data property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom
 */
public class DJDPA extends DroolsBinaryObject<String, String> implements A
{
	public DJDPA(String property1ID, String property2ID)
	{
		super(property1ID, property2ID);
	}

	public String getp1id() { return getT1(); }

	public String getp2id() { return getT2(); }

	@Override
	public OWLDisjointDataPropertiesAxiom extract(DroolsOWLAxiomExtractor converter) throws TargetRuleEngineException
	{
		return converter.extract(this);
	}

	@Override
	public String toString()
	{
		return "DJDPA" + super.toString();
	}
}
