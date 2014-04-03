package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.converters.TargetRuleEngineConverterBase;
import org.swrlapi.converters.TargetRuleEngineOWLNamedObjectConverter;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class converts OWL named objects to their DRL representation for use in rules.
 */
public class DroolsOWLNamedObject2DRLConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineOWLNamedObjectConverter<String>
{
	public DroolsOWLNamedObject2DRLConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	@Override
	public String convert(OWLEntity entity) throws TargetRuleEngineException
	{
		return getOWLIRIResolver().iri2ShortName(entity.getIRI());
	}

	@Override
	public String convert(OWLClass cls) throws TargetRuleEngineException
	{
		return getOWLIRIResolver().iri2ShortName(cls.getIRI());
	}

	@Override
	public String convert(OWLNamedIndividual individual) throws TargetRuleEngineException
	{
		return getOWLIRIResolver().iri2ShortName(individual.getIRI());
	}

	@Override
	public String convert(OWLObjectProperty property) throws TargetRuleEngineException
	{
		return getOWLIRIResolver().iri2ShortName(property.getIRI());
	}

	@Override
	public String convert(OWLDataProperty property) throws TargetRuleEngineException
	{
		return getOWLIRIResolver().iri2ShortName(property.getIRI());
	}

	@Override
	public String convert(OWLAnnotationProperty property) throws TargetRuleEngineException
	{
		return getOWLIRIResolver().iri2ShortName(property.getIRI());
	}

	@Override
	public String convert(OWLDatatype datatype) throws TargetRuleEngineException
	{
		return getOWLIRIResolver().iri2ShortName(datatype.getIRI());
	}
}
