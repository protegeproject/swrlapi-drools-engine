package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.converters.TargetRuleEngineConverterBase;
import org.swrlapi.converters.TargetRuleEngineOWLIndividualConverter;
import org.swrlapi.core.SWRLRuleEngineBridge;
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
			return addQuotes(getOWLIRIResolver().iri2PrefixedName(individualIRI));
		} else
			return addQuotes(individual.asOWLAnonymousIndividual().getID().getID());
	}

	private String addQuotes(String s)
	{
		return "\"" + s + "\"";
	}
}
