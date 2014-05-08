package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.converters.TargetRuleEngineConverterBase;
import org.swrlapi.converters.TargetRuleEngineOWLIndividualConverter;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Converts an OWL individual to a Drools individual represented by the class {@link I}.
 * 
 * @see OWLIndividual, OWLAnonymousIndividual, OWLNamedIndividual, I
 */
public class DroolsOWLIndividual2IConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineOWLIndividualConverter<I>
{
	public DroolsOWLIndividual2IConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	@Override
	public I convert(OWLIndividual individual) throws TargetRuleEngineException
	{
		if (individual.isNamed()) {
			IRI individualIRI = individual.asOWLNamedIndividual().getIRI();
			String individualPrefixedName = getOWLIRIResolver().iri2PrefixedName(individualIRI);
			return new I(individualPrefixedName);
		} else
			return new I(individual.asOWLAnonymousIndividual().getID().getID());
	}
}
