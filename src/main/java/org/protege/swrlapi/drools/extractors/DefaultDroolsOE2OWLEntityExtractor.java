package org.protege.swrlapi.drools.extractors;

import org.protege.swrlapi.core.SWRLRuleEngineBridge;
import org.protege.swrlapi.drools.owl.entities.AP;
import org.protege.swrlapi.drools.owl.entities.C;
import org.protege.swrlapi.drools.owl.entities.D;
import org.protege.swrlapi.drools.owl.entities.DP;
import org.protege.swrlapi.drools.owl.entities.I;
import org.protege.swrlapi.drools.owl.entities.OE;
import org.protege.swrlapi.drools.owl.entities.OP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.protege.swrlapi.extractors.TargetRuleEngineExtractorBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * This class converts the Drools representation of OWL entities represented by the class {@link OE} to their
 * Portability API representation.
 */
public class DefaultDroolsOE2OWLEntityExtractor extends TargetRuleEngineExtractorBase implements
		DroolsOE2OWLEntityExtractor
{
	public DefaultDroolsOE2OWLEntityExtractor(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	@Override
	public OWLClass extract(C cls) throws TargetRuleEngineException
	{
		IRI classIRI = getIRI(cls.getid());
		return getOWLDataFactory().getOWLClass(classIRI);
	}

	@Override
	public OWLNamedIndividual extract(I individual) throws TargetRuleEngineException
	{
		IRI individualIRI = getIRI(individual.getid());
		return getOWLDataFactory().getOWLNamedIndividual(individualIRI);
	}

	@Override
	public OWLDataProperty extract(DP property) throws TargetRuleEngineException
	{
		IRI propertyIRI = getIRI(property.getid());
		return getOWLDataFactory().getOWLDataProperty(propertyIRI);
	}

	@Override
	public OWLAnnotationProperty extract(AP property) throws TargetRuleEngineException
	{
		IRI propertyIRI = getIRI(property.getid());
		return getOWLDataFactory().getOWLAnnotationProperty(propertyIRI);
	}

	@Override
	public OWLObjectProperty extract(OP property) throws TargetRuleEngineException
	{
		IRI propertyIRI = getIRI(property.getid());
		return getOWLDataFactory().getOWLObjectProperty(propertyIRI);
	}

	@Override
	public OWLDatatype extract(D datatype) throws TargetRuleEngineException
	{
		IRI datatypeIRI = getIRI(datatype.getid());
		return getOWLDataFactory().getOWLDatatype(datatypeIRI);
	}
}
