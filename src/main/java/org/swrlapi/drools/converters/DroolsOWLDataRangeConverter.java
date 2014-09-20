package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataRangeVisitorEx;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLDataRangeConverter;
import org.swrlapi.drools.owl.core.L;
import org.swrlapi.drools.owl.dataranges.DCO;
import org.swrlapi.drools.owl.dataranges.DIO;
import org.swrlapi.drools.owl.dataranges.DOO;
import org.swrlapi.drools.owl.dataranges.DR;
import org.swrlapi.drools.owl.dataranges.DRR;
import org.swrlapi.drools.owl.dataranges.DUO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Converts an OWLAPI OWL data range to its Drools representation
 *
 * @see org.semanticweb.owlapi.model.OWLDataRange
 */
public class DroolsOWLDataRangeConverter extends TargetRuleEngineConverterBase
		implements TargetRuleEngineOWLDataRangeConverter<String>, OWLDataRangeVisitorEx<String>
{
	private final Map<OWLDataRange, String> dataRange2IDMap;
	private final Set<String> convertedDataRangeIDs;
	private final Set<DR> dataRanges;
	private int dataRangeIndex;

	public DroolsOWLDataRangeConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);

		this.dataRanges = new HashSet<>();
		this.dataRange2IDMap = new HashMap<>();
		this.convertedDataRangeIDs = new HashSet<>();
		this.dataRangeIndex = 0;

		getOWLDataRangeResolver().reset();
	}

	public void reset()
	{
		this.dataRanges.clear();
		this.dataRange2IDMap.clear();
		this.convertedDataRangeIDs.clear();
		this.dataRangeIndex = 0;

		getOWLDataRangeResolver().reset();
	}

	public String convert(OWLDataRange dataRange)
	{
		return dataRange.accept(this);
	}

	@Override
	public String convert(OWLDatatype datatype)
	{
		String datatypePrefixedName = getIRIResolver().iri2PrefixedName(datatype.getIRI());

		if (!this.convertedDataRangeIDs.contains(datatypePrefixedName)) {
			this.convertedDataRangeIDs.add(datatypePrefixedName);
			getOWLDataRangeResolver().recordOWLDataRange(datatypePrefixedName, datatype);
		}
		return datatypePrefixedName;
	}

	@Override
	public String convert(OWLDataOneOf dataRange)
	{
		String dataRangeID = getOWLDataRangeID(dataRange);

		if (!this.convertedDataRangeIDs.contains(dataRangeID)) {
			Set<L> literals = new HashSet<>();
			// TODO Pull out and convert the literals
			DOO doo = new DOO(dataRangeID, literals);
			addOWLDataRange(doo);
		}
		return dataRangeID;
	}

	@Override
	public String convert(OWLDataComplementOf dataRange)
	{
		String dataRangeID = getOWLDataRangeID(dataRange);

		if (!this.convertedDataRangeIDs.contains(dataRangeID)) {
			String complementDataRangeID = getOWLDataRangeID(dataRange.getDataRange());
			DCO dco = new DCO(dataRangeID, complementDataRangeID);
			addOWLDataRange(dco);
		}
		return dataRangeID;
	}

	@Override
	public String convert(OWLDataIntersectionOf dataRange)
	{
		String dataRangeID = getOWLDataRangeID(dataRange);

		if (!this.convertedDataRangeIDs.contains(dataRangeID)) {
			Set<String> dataRangeIDs = getOWLDataRangeIDs(dataRange.getOperands());
			DIO dio = new DIO(dataRangeID, dataRangeIDs);
			addOWLDataRange(dio);
		}
		return dataRangeID;
	}

	@Override
	public String convert(OWLDataUnionOf dataRange)
	{
		String dataRangeID = getOWLDataRangeID(dataRange);

		if (!this.convertedDataRangeIDs.contains(dataRangeID)) {
			Set<String> dataRangeIDs = getOWLDataRangeIDs(dataRange.getOperands());
			DUO duo = new DUO(dataRangeID, dataRangeIDs);
			addOWLDataRange(duo);
		}
		return dataRangeID;
	}

	@Override
	public String convert(OWLDatatypeRestriction dataRange)
	{
		String dataRangeID = getOWLDataRangeID(dataRange);

		if (!this.convertedDataRangeIDs.contains(dataRangeID)) {
			// TODO Incomplete - does not represent datatype restriction facets
			DRR drr = new DRR(dataRangeID);
			addOWLDataRange(drr);
		}
		return dataRangeID;
	}

	@Override public String visit(OWLDatatype owlDatatype)
	{
		return convert(owlDatatype);
	}

	@Override public String visit(OWLDataOneOf owlDataOneOf)
	{
		return convert(owlDataOneOf);
	}

	@Override public String visit(OWLDataComplementOf owlDataComplementOf)
	{
		return convert(owlDataComplementOf);
	}

	@Override public String visit(OWLDataIntersectionOf owlDataIntersectionOf)
	{
		return convert(owlDataIntersectionOf);
	}

	@Override public String visit(OWLDataUnionOf owlDataUnionOf)
	{
		return convert(owlDataUnionOf);
	}

	@Override public String visit(OWLDatatypeRestriction owlDatatypeRestriction)
	{
		return convert(owlDatatypeRestriction);
	}

	private String getOWLDataRangeID(OWLDataRange dataRange)
	{
		if (this.dataRange2IDMap.containsKey(dataRange))
			return this.dataRange2IDMap.get(dataRange);
		else {
			String dataRangeID = "DRID" + this.dataRangeIndex++;
			this.dataRange2IDMap.put(dataRange, dataRangeID);
			this.convertedDataRangeIDs.add(dataRangeID);
			getOWLDataRangeResolver().recordOWLDataRange(dataRangeID, dataRange);
			return dataRangeID;
		}
	}

	private Set<String> getOWLDataRangeIDs(Set<OWLDataRange> dataRanges)
	{
		Set<String> dataRangeIDs = new HashSet<>();
		for (OWLDataRange dataRange : dataRanges) {
			String dataRangeID = getOWLDataRangeID(dataRange);
			dataRangeIDs.add(dataRangeID);
		}
		return dataRangeIDs;
	}

	private void addOWLDataRange(DR dataRange)
	{
		this.dataRanges.add(dataRange);
	}
}
