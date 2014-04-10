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
import org.swrlapi.drools.owl.entities.AP;
import org.swrlapi.drools.owl.entities.C;
import org.swrlapi.drools.owl.entities.D;
import org.swrlapi.drools.owl.entities.DP;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.drools.owl.entities.OE;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class converts OWL named objects to their DRL representation for use in rules.
 */
public class DroolsOWLNamedObject2OEConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineOWLNamedObjectConverter<OE>
{
	public DroolsOWLNamedObject2OEConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	@Override
	public OE convert(OWLEntity entity) throws TargetRuleEngineException
	{ // TODO Replace instanceof using visitor
		if (entity instanceof OWLClass)
			return convert((OWLClass)entity);
		else if (entity instanceof OWLNamedIndividual)
			return convert((OWLNamedIndividual)entity);
		else if (entity instanceof OWLObjectProperty)
			return convert((OWLObjectProperty)entity);
		else if (entity instanceof OWLDataProperty)
			return convert((OWLDataProperty)entity);
		else if (entity instanceof OWLAnnotationProperty)
			return convert((OWLAnnotationProperty)entity);
		else if (entity instanceof OWLDatatype)
			return convert((OWLDatatype)entity);
		else
			throw new RuntimeException("unknown OWL entity type " + entity.getClass().getCanonicalName());
	}

	@Override
	public C convert(OWLClass cls) throws TargetRuleEngineException
	{
		String shortName = getOWLIRIResolver().iri2ShortName(cls.getIRI());
		return new C(shortName);
	}

	@Override
	public I convert(OWLNamedIndividual individual) throws TargetRuleEngineException
	{
		String shortName = getOWLIRIResolver().iri2ShortName(individual.getIRI());
		return new I(shortName);
	}

	@Override
	public OP convert(OWLObjectProperty property) throws TargetRuleEngineException
	{
		String shortName = getOWLIRIResolver().iri2ShortName(property.getIRI());
		return new OP(shortName);
	}

	@Override
	public DP convert(OWLDataProperty property) throws TargetRuleEngineException
	{
		String shortName = getOWLIRIResolver().iri2ShortName(property.getIRI());
		return new DP(shortName);
	}

	@Override
	public AP convert(OWLAnnotationProperty property) throws TargetRuleEngineException
	{
		String shortName = getOWLIRIResolver().iri2ShortName(property.getIRI());
		return new AP(shortName);
	}

	@Override
	public D convert(OWLDatatype datatype) throws TargetRuleEngineException
	{
		String shortName = getOWLIRIResolver().iri2ShortName(datatype.getIRI());
		return new D(shortName);
	}
}
