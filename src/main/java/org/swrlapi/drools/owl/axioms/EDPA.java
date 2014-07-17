package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class representing an equivalent data property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom
 */
public class EDPA extends DroolsBinaryObject<String, String> implements A
{
	public EDPA(String property1ID, String property2ID)
	{
		super(property1ID, property2ID);
	}

	public String getp1id() { return getT1(); }

	public String getp2id() { return getT2(); }

	@Override
	public OWLEquivalentDataPropertiesAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException
	{
		return extractor.extract(this);
	}

	@Override
	public String toString()
	{
		return "EDPA" + super.toString();
	}
}
