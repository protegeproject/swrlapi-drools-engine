package org.protege.swrlapi.drools.converters;

import org.protege.swrlapi.converters.TargetRuleEngineOWLIndividualConverter;
import org.protege.swrlapi.core.OWLNamedObjectResolver;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLIndividual;

/**
 * Converts an OWL individual to its DRL representation for use in a Drools rule.
 */
public class DroolsOWLIndividual2DRLConverter implements TargetRuleEngineOWLIndividualConverter<String>
{
	private final OWLNamedObjectResolver resolver;

	public DroolsOWLIndividual2DRLConverter(OWLNamedObjectResolver resolver)
	{
		this.resolver = resolver;
	}

	@Override
	public String convert(OWLIndividual individual) throws TargetRuleEngineException
	{
		if (individual.isNamed()) {
			return resolver.iri2PrefixedName(individual.asOWLNamedIndividual().getIRI());
		} else
			return individual.asOWLAnonymousIndividual().getID().getID();
	}
}
