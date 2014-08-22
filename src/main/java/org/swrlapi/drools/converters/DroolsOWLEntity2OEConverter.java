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
import org.swrlapi.drools.owl.properties.AP;
import org.swrlapi.drools.owl.core.C;
import org.swrlapi.drools.owl.core.D;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl.core.OE;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class converts OWLAPI OWL entities to their Drools representation.
 *
 * @see org.semanticweb.owlapi.model.OWLEntity
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
		String prefixedName = getIRIResolver().iri2PrefixedName(cls.getIRI());
		return new C(prefixedName);
	}

	@Override
	public I convert(OWLNamedIndividual individual) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(individual.getIRI());
		return new I(prefixedName);
	}

	@Override
	public OP convert(OWLObjectProperty property) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(property.getIRI());
		return new OP(prefixedName);
	}

	@Override
	public DP convert(OWLDataProperty property) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(property.getIRI());
		return new DP(prefixedName);
	}

	@Override
	public AP convert(OWLAnnotationProperty property) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(property.getIRI());
		return new AP(prefixedName);
	}

	@Override
	public D convert(OWLDatatype datatype) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(datatype.getIRI());
		return new D(prefixedName);
	}
}
