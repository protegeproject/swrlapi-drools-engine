package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLIndividualConverter;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Converts an OWL individual to its DRL representation for use in a Drools rule.
 */
public class DroolsOWLIndividual2DRLConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineOWLIndividualConverter<String>
{
	public DroolsOWLIndividual2DRLConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	@Override
	public String convert(OWLIndividual individual) throws TargetRuleEngineException
	{
		if (individual.isNamed()) {
			IRI individualIRI = individual.asOWLNamedIndividual().getIRI();
			String prefixedName = getIRIResolver().iri2PrefixedName(individualIRI);
			//return addQuotes(prefixedName);
			return "new I(\"" + prefixedName + "\")";
		} else {
			String individualID = individual.asOWLAnonymousIndividual().getID().getID();
			//return addQuotes(individualID);
			return "new I(\"" + individualID + "\")";
		}
	}

	private String addQuotes(String s)
	{
		return "\"" + s + "\"";
	}
}
