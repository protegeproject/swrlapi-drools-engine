package org.protege.swrlapi.drools.converters;

import org.protege.swrlapi.converters.TargetRuleEngineOWLNamedObjectConverter;
import org.protege.swrlapi.core.OWLNamedObjectResolver;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * This class converts OWL named objects to their DRL representation for use in rules.
 */
public class DroolsOWLNamedObject2DRLConverter implements TargetRuleEngineOWLNamedObjectConverter<String>
{
	private final OWLNamedObjectResolver resolver;

	public DroolsOWLNamedObject2DRLConverter(OWLNamedObjectResolver resolver)
	{
		this.resolver = resolver;
	}

	@Override
	public String convert(OWLEntity entity) throws TargetRuleEngineException
	{
		return resolver.iri2PrefixedName(entity.getIRI());
	}

	@Override
	public String convert(OWLClass cls) throws TargetRuleEngineException
	{
		return resolver.iri2PrefixedName(cls.getIRI());
	}

	@Override
	public String convert(OWLNamedIndividual individual) throws TargetRuleEngineException
	{
		return resolver.iri2PrefixedName(individual.getIRI());
	}

	@Override
	public String convert(OWLObjectProperty property) throws TargetRuleEngineException
	{
		return resolver.iri2PrefixedName(property.getIRI());
	}

	@Override
	public String convert(OWLDataProperty property) throws TargetRuleEngineException
	{
		return resolver.iri2PrefixedName(property.getIRI());
	}

	@Override
	public String convert(OWLAnnotationProperty property) throws TargetRuleEngineException
	{
		return resolver.iri2PrefixedName(property.getIRI());
	}

	@Override
	public String convert(OWLDatatype datatype) throws TargetRuleEngineException
	{
		return resolver.iri2PrefixedName(datatype.getIRI());
	}
}
