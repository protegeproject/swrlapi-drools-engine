package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.converters.TargetRuleEngineConverterBase;
import org.swrlapi.converters.TargetRuleEngineOWLEntityConverter;
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
 * This class converts OWL entities to their DRL representation for use in rules.
 */
public class DroolsOWLEntity2OEConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineOWLEntityConverter<OE>
{
	public DroolsOWLEntity2OEConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
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
