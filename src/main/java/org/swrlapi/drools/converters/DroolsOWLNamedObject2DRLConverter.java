package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLEntityConverter;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class converts OWL named objects to their DRL representation for use in rules.
 */
public class DroolsOWLNamedObject2DRLConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineOWLEntityConverter<String>
{
	public DroolsOWLNamedObject2DRLConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	@Override
	public String convert(OWLClass cls) throws TargetRuleEngineException
	{
		return getOWLIRIResolver().iri2PrefixedName(cls.getIRI());
	}

	@Override
	public String convert(OWLNamedIndividual individual) throws TargetRuleEngineException
	{
		return getOWLIRIResolver().iri2PrefixedName(individual.getIRI());
	}

	@Override
	public String convert(OWLObjectProperty property) throws TargetRuleEngineException
	{
		return getOWLIRIResolver().iri2PrefixedName(property.getIRI());
	}

	@Override
	public String convert(OWLDataProperty property) throws TargetRuleEngineException
	{
		return getOWLIRIResolver().iri2PrefixedName(property.getIRI());
	}

	@Override
	public String convert(OWLAnnotationProperty property) throws TargetRuleEngineException
	{
		return getOWLIRIResolver().iri2PrefixedName(property.getIRI());
	}

	@Override
	public String convert(OWLDatatype datatype) throws TargetRuleEngineException
	{
		return getOWLIRIResolver().iri2PrefixedName(datatype.getIRI());
	}
}
