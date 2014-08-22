package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLIndividualConverter;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Converts an OWLAPI OWL individual to its Drools representation.
 *
 * @see org.semanticweb.owlapi.model.OWLIndividual
 * @see org.semanticweb.owlapi.model.OWLAnonymousIndividual
 * @see org.semanticweb.owlapi.model.OWLNamedIndividual
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
