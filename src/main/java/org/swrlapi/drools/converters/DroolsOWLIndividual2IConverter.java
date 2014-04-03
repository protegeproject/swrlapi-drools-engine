package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.converters.TargetRuleEngineOWLIndividualConverter;
import org.swrlapi.core.OWLIRIResolver;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Converts an OWL individual to a Drools individual represented by the class {@link I}.
 */
public class DroolsOWLIndividual2IConverter implements TargetRuleEngineOWLIndividualConverter<I>
{
	private final OWLIRIResolver iriResolver;

	public DroolsOWLIndividual2IConverter(OWLIRIResolver iriResolver)
	{
		this.iriResolver = iriResolver;
	}

	@Override
	public I convert(OWLIndividual individual) throws TargetRuleEngineException
	{
		if (individual.isNamed()) {
			return new I(iriResolver.iri2ShortName(individual.asOWLNamedIndividual().getIRI()));
		} else
			return new I(individual.asOWLAnonymousIndividual().getID().getID());
	}
}
