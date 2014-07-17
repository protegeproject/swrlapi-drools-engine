package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an equivalent object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom
 */
public class EOPA extends DroolsBinaryObject<String, String> implements A
{
	public EOPA(String property1ID, String property2ID)
	{
		super(property1ID, property2ID);
	}

	public String getp1id() { return getT1(); }

	public String getp2id() { return getT2(); }

	@Override
	public OWLEquivalentObjectPropertiesAxiom extract(DroolsOWLAxiomExtractor extractor)
			throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "EOPA" + super.toString();
	}
}
