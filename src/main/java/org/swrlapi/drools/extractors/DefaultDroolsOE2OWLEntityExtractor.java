package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.drools.owl.entities.AP;
import org.swrlapi.drools.owl.entities.C;
import org.swrlapi.drools.owl.entities.D;
import org.swrlapi.drools.owl.entities.DP;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.drools.owl.entities.OE;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.extractors.TargetRuleEngineExtractorBase;

/**
 * This class converts the Drools representation of OWL entities represented by the class {@link OE} to their OWLAPI
 * representation.
 */
public class DefaultDroolsOE2OWLEntityExtractor extends TargetRuleEngineExtractorBase implements
		DroolsOWLEntityExtractor
{
	public DefaultDroolsOE2OWLEntityExtractor(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	@Override
	public OWLClass extract(C cls) throws TargetRuleEngineException
	{
		IRI classIRI = shortName2IRI(cls.getName());
		return getOWLDataFactory().getOWLClass(classIRI);
	}

	@Override
	public OWLNamedIndividual extract(I individual) throws TargetRuleEngineException
	{
		IRI individualIRI = shortName2IRI(individual.getName());
		return getOWLDataFactory().getOWLNamedIndividual(individualIRI);
	}

	@Override
	public OWLDataProperty extract(DP property) throws TargetRuleEngineException
	{
		IRI propertyIRI = shortName2IRI(property.getName());
		return getOWLDataFactory().getOWLDataProperty(propertyIRI);
	}

	@Override
	public OWLAnnotationProperty extract(AP property) throws TargetRuleEngineException
	{
		IRI propertyIRI = shortName2IRI(property.getName());
		return getOWLDataFactory().getOWLAnnotationProperty(propertyIRI);
	}

	@Override
	public OWLObjectProperty extract(OP property) throws TargetRuleEngineException
	{
		IRI propertyIRI = shortName2IRI(property.getName());
		return getOWLDataFactory().getOWLObjectProperty(propertyIRI);
	}

	@Override
	public OWLDatatype extract(D datatype) throws TargetRuleEngineException
	{
		IRI datatypeIRI = shortName2IRI(datatype.getName());
		return getOWLDataFactory().getOWLDatatype(datatypeIRI);
	}
}
