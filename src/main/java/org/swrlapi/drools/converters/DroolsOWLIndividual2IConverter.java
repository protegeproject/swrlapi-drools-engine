package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLIndividualConverter;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Converts an OWLAPI OWL individual to a Drools individual represented by the class {@link org.swrlapi.drools.owl.core.I}.
 * 
 * @see org.semanticweb.owlapi.model.OWLIndividual
 * @see org.semanticweb.owlapi.model.OWLAnonymousIndividual
 * @see org.semanticweb.owlapi.model.OWLNamedIndividual
 * @see org.swrlapi.drools.owl.core.I
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
			String individualPrefixedName = getIRIResolver().iri2PrefixedName(individualIRI);
			return new I(individualPrefixedName);
		} else
			return new I(individual.asOWLAnonymousIndividual().getID().getID());
	}
}
