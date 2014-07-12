package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.*;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLDataRangeConverter;
import org.swrlapi.drools.owl.L;
import org.swrlapi.drools.owl.classexpressions.CE;
import org.swrlapi.drools.owl.classexpressions.OOOCE;
import org.swrlapi.drools.owl.dataranges.*;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.exceptions.TargetRuleEngineNotImplementedFeatureException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Converts an OWL data range to its Drools representation
 */
public class DroolsOWLDataRangeConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineOWLDataRangeConverter<String>
{
	private final Map<OWLDataRange, String> dataRange2IDMap;
	private final Set<String> convertedDataRangeIDs;
	private int dataRangeIndex;
	private final Set<DR> dataRanges;

	public DroolsOWLDataRangeConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);

		this.dataRangeIndex = 0;
		this.dataRanges = new HashSet<DR>();
		this.dataRange2IDMap = new HashMap<OWLDataRange, String>();
		this.convertedDataRangeIDs = new HashSet<String>();
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
	public String convert(OWLDatatype dataRange) throws TargetRuleEngineException
	{
		String datatypePrefixedName = getIRIResolver().iri2PrefixedName(dataRange.getIRI());

		if (!this.convertedDataRangeIDs.contains(datatypePrefixedName)) {
			this.convertedDataRangeIDs.add(datatypePrefixedName);
		}
		return datatypePrefixedName;
	}

	@Override
	public String convert(OWLDataOneOf dataRange) throws TargetRuleEngineException
	{
		String dataRangeID = getOWLDataRangeID(dataRange);

		if (!this.convertedDataRangeIDs.contains(dataRangeID)) {
			Set<L> literals = new HashSet<L>();
			// TODO Pull out and convert the literals
			DOO doo = new DOO(dataRangeID, literals);
			addOWLDataRange(doo);
			this.convertedDataRangeIDs.add(dataRangeID);
		}
		return dataRangeID;
	}

	@Override
	public String convert(OWLDataComplementOf dataRange) throws TargetRuleEngineException
	{
		String dataRangeID = getOWLDataRangeID(dataRange);

		if (!this.convertedDataRangeIDs.contains(dataRangeID)) {
			DCO dco = null; // TODO
			addOWLDataRange(dco);
			this.convertedDataRangeIDs.add(dataRangeID);
		}
		return dataRangeID;
	}

	@Override
	public String convert(OWLDataIntersectionOf dataRange) throws TargetRuleEngineException
	{
		String dataRangeID = getOWLDataRangeID(dataRange);

		if (!this.convertedDataRangeIDs.contains(dataRangeID)) {
			DIO dio = null; // TODO
			addOWLDataRange(dio);
			this.convertedDataRangeIDs.add(dataRangeID);
		}
		return dataRangeID;
	}

	@Override
	public String convert(OWLDataUnionOf dataRange) throws TargetRuleEngineException
	{
		String dataRangeID = getOWLDataRangeID(dataRange);

		if (!this.convertedDataRangeIDs.contains(dataRangeID)) {
			DUO duo = null; // TODO
			addOWLDataRange(duo);
			this.convertedDataRangeIDs.add(dataRangeID);
		}
		return dataRangeID;
	}

	@Override
	public String convert(OWLDatatypeRestriction range) throws TargetRuleEngineException
	{
		throw new TargetRuleEngineNotImplementedFeatureException(
				"owl:DatatypeRestriction is not supported in by Drools OWL 2 RL reasoner");
	}

	private String getOWLDataRangeID(OWLDataRange dataRange)
	{
		if (this.dataRange2IDMap.containsKey(dataRange))
			return this.dataRange2IDMap.get(dataRange);
		else {
			String dataRangeID = "DRID" + this.dataRangeIndex++;
			this.dataRange2IDMap.put(dataRange, dataRangeID);
			return dataRangeID;
		}
	}

	private void addOWLDataRange(DR dataRange)
	{
		this.dataRanges.add(dataRange);
	}

}
