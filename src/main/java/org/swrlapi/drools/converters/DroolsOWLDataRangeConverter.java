package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLDataRangeConverter;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.exceptions.TargetRuleEngineNotImplementedFeatureException;

/**
 * Converts an OWL data range to its Drools representation
 */
public class DroolsOWLDataRangeConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineOWLDataRangeConverter<String>
{
	public DroolsOWLDataRangeConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	public String convert(OWLDataRange range) throws TargetRuleEngineException
	{ // TODO Use visitor to get rid of instanceof
		if (range instanceof OWLDatatype) {
			return convert((OWLDatatype)range);
		} else if (range instanceof OWLDataOneOf) {
			return convert((OWLDataOneOf)range);
		} else if (range instanceof OWLDataComplementOf) {
			return convert((OWLDataComplementOf)range);
		} else if (range instanceof OWLDataIntersectionOf) {
			return convert((OWLDataIntersectionOf)range);
		} else if (range instanceof OWLDataUnionOf) {
			return convert((OWLDataUnionOf)range);
		} else if (range instanceof OWLDatatypeRestriction) {
			return convert((OWLDatatypeRestriction)range);
		} else
			throw new RuntimeException("unknown OWL data range type " + range.getClass().getCanonicalName());
	}

	@Override
	public String convert(OWLDatatype range) throws TargetRuleEngineException
	{
		return getIRIResolver().iri2PrefixedName(range.getIRI());
	}

	@Override
	public String convert(OWLDataOneOf range) throws TargetRuleEngineException
	{
		throw new TargetRuleEngineNotImplementedFeatureException("owl:DataOneOf is not supported in Drools");
	}

	@Override
	public String convert(OWLDataComplementOf range) throws TargetRuleEngineException
	{
		throw new TargetRuleEngineNotImplementedFeatureException("owl:DataComplementOf is not supported in Drools");
	}

	@Override
	public String convert(OWLDataIntersectionOf range) throws TargetRuleEngineException
	{
		throw new TargetRuleEngineNotImplementedFeatureException("owl:DataIntersectionOf is not supported in Drools");
	}

	@Override
	public String convert(OWLDataUnionOf range) throws TargetRuleEngineException
	{
		throw new TargetRuleEngineNotImplementedFeatureException("owl:DataUnionOf is not supported in Drools");
	}

	@Override
	public String convert(OWLDatatypeRestriction range) throws TargetRuleEngineException
	{
		throw new TargetRuleEngineNotImplementedFeatureException("owl:DatatypeRestriction is not supported in Drools");
	}
}
