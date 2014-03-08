package org.protege.swrlapi.drools.converters;

import org.protege.swrlapi.converters.TargetRuleEngineOWLIndividualConverter;
import org.protege.swrlapi.core.OWLNamedObjectResolver;
import org.protege.swrlapi.drools.owl.entities.I;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLIndividual;

/**
 * Converts an OWL individual to a Drools individual represented by the class {@link I}.
 */
public class DroolsOWLIndividual2IConverter implements TargetRuleEngineOWLIndividualConverter<I>
{
	private final OWLNamedObjectResolver resolver;

	public DroolsOWLIndividual2IConverter(OWLNamedObjectResolver resolver)
	{
		this.resolver = resolver;
	}

	@Override
	public I convert(OWLIndividual individual) throws TargetRuleEngineException
	{
		if (individual.isNamed()) {
			return new I(resolver.iri2PrefixedName(individual.asOWLNamedIndividual().getIRI()));
		} else
			return new I(individual.asOWLAnonymousIndividual().getID().getID());
	}
}
