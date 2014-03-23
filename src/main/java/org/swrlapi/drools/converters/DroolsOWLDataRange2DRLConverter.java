package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.swrlapi.converters.TargetRuleEngineOWLDataRangeConverter;
import org.swrlapi.core.OWLIRIResolver;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.exceptions.TargetRuleEngineNotImplementedFeatureException;

/**
 * Converts an OWL data range to its DRL representation for use in a Drools rule.
 */
public class DroolsOWLDataRange2DRLConverter implements TargetRuleEngineOWLDataRangeConverter<String>
{
	private final OWLIRIResolver resolver;

	public DroolsOWLDataRange2DRLConverter(OWLIRIResolver resolver)
	{
		this.resolver = resolver;
	}

	public String convert(OWLDataRange range) throws TargetRuleEngineException
	{
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
		return resolver.iri2PrefixedName(range.getIRI());
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
