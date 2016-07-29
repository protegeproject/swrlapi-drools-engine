package org.swrlapi.drools.converters.id;

import org.checkerframework.checker.nullness.qual.NonNull;
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
import org.swrlapi.drools.owl.dataranges.DCO;
import org.swrlapi.drools.owl.dataranges.DIO;
import org.swrlapi.drools.owl.dataranges.DOO;
import org.swrlapi.drools.owl.dataranges.DR;
import org.swrlapi.drools.owl.dataranges.DRR;
import org.swrlapi.drools.owl.dataranges.DUO;
import org.swrlapi.drools.owl.literals.L;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Converts an OWLAPI OWL data range to its Drools representation
 *
 * @see org.semanticweb.owlapi.model.OWLDataRange
 */
public class DroolsOWLDataRangeHandler extends TargetRuleEngineConverterBase
  implements TargetRuleEngineOWLDataRangeConverter<String>, OWLDataRangeVisitorEx<String>
{
  @NonNull private final Map<@NonNull OWLDataRange, @NonNull String> dataRange2ID = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLDataRange> id2DataRange = new HashMap<>();
  @NonNull private final Set<@NonNull String> convertedDataRangeIDs = new HashSet<>();
  @NonNull private final Set<@NonNull DR> dataRanges = new HashSet<>();

  private int dataRangeIndex;

  public DroolsOWLDataRangeHandler(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);

    this.dataRangeIndex = 0;
  }

  public void reset()
  {
    this.dataRangeIndex = 0;

    this.dataRange2ID.clear();
    this.id2DataRange.clear();
    this.convertedDataRangeIDs.clear();
    this.dataRanges.clear();
  }

  @NonNull public OWLDataRange resolveOWLDataRange(String drid)
  {
    if (id2DataRange.containsKey(drid))
      return id2DataRange.get(drid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL data range from a Drools data range with id " + drid);
  }

  @NonNull public String convert(@NonNull OWLDataRange dataRange)
  {
    return dataRange.accept(this);
  }

  @NonNull @Override public String convert(@NonNull OWLDatatype datatype)
  {
    String dataRangeID = iri2PrefixedName(datatype.getIRI());

    if (!this.convertedDataRangeIDs.contains(dataRangeID)) {
      this.convertedDataRangeIDs.add(dataRangeID);
      id2DataRange.put(dataRangeID, datatype);
      dataRange2ID.put(datatype, dataRangeID);
    }
    return dataRangeID;
  }

  @Override public String convert(OWLDataOneOf dataRange)
  {
    String dataRangeID = getOWLDataRangeID(dataRange);

    if (!this.convertedDataRangeIDs.contains(dataRangeID)) {
      Set<@NonNull L> literals = new HashSet<>();
      // TODO Pull out and convert the literals
      DOO doo = new DOO(dataRangeID, literals);
      addOWLDataRange(doo);
    }
    return dataRangeID;
  }

  @Override public String convert(@NonNull OWLDataComplementOf dataRange)
  {
    String dataRangeID = getOWLDataRangeID(dataRange);

    if (!this.convertedDataRangeIDs.contains(dataRangeID)) {
      String complementDataRangeID = getOWLDataRangeID(dataRange.getDataRange());
      DCO dco = new DCO(dataRangeID, complementDataRangeID);
      addOWLDataRange(dco);
    }
    return dataRangeID;
  }

  @Override public String convert(@NonNull OWLDataIntersectionOf dataRange)
  {
    String dataRangeID = getOWLDataRangeID(dataRange);

    if (!this.convertedDataRangeIDs.contains(dataRangeID)) {
      Set<@NonNull String> dataRangeIDs = getOWLDataRangeIDs(dataRange.getOperands());
      DIO dio = new DIO(dataRangeID, dataRangeIDs);
      addOWLDataRange(dio);
    }
    return dataRangeID;
  }

  @Override public String convert(@NonNull OWLDataUnionOf dataRange)
  {
    String dataRangeID = getOWLDataRangeID(dataRange);

    if (!this.convertedDataRangeIDs.contains(dataRangeID)) {
      Set<@NonNull String> dataRangeIDs = getOWLDataRangeIDs(dataRange.getOperands());
      DUO duo = new DUO(dataRangeID, dataRangeIDs);
      addOWLDataRange(duo);
    }
    return dataRangeID;
  }

  @Override public String convert(OWLDatatypeRestriction dataRange)
  {
    String dataRangeID = getOWLDataRangeID(dataRange);

    if (!this.convertedDataRangeIDs.contains(dataRangeID)) {
      // TODO Incomplete - does not represent datatype restriction facets
      DRR drr = new DRR(dataRangeID);
      addOWLDataRange(drr);
    }
    return dataRangeID;
  }

  @NonNull @Override public String visit(@NonNull OWLDatatype owlDatatype)
  {
    return convert(owlDatatype);
  }

  @NonNull @Override public String visit(@NonNull OWLDataOneOf owlDataOneOf)
  {
    return convert(owlDataOneOf);
  }

  @NonNull @Override public String visit(@NonNull OWLDataComplementOf owlDataComplementOf)
  {
    return convert(owlDataComplementOf);
  }

  @NonNull @Override public String visit(@NonNull OWLDataIntersectionOf owlDataIntersectionOf)
  {
    return convert(owlDataIntersectionOf);
  }

  @NonNull @Override public String visit(@NonNull OWLDataUnionOf owlDataUnionOf)
  {
    return convert(owlDataUnionOf);
  }

  @NonNull @Override public String visit(OWLDatatypeRestriction owlDatatypeRestriction)
  {
    return convert(owlDatatypeRestriction);
  }

  private String getOWLDataRangeID(OWLDataRange dataRange)
  {
    if (this.dataRange2ID.containsKey(dataRange))
      return this.dataRange2ID.get(dataRange);
    else {
      String dataRangeID = "DRID" + this.dataRangeIndex++;
      this.dataRange2ID.put(dataRange, dataRangeID);
      this.convertedDataRangeIDs.add(dataRangeID);
      this.id2DataRange.put(dataRangeID, dataRange);
      this.dataRange2ID.put(dataRange, dataRangeID);
      return dataRangeID;
    }
  }

  @NonNull private Set<@NonNull String> getOWLDataRangeIDs(@NonNull Set<@NonNull OWLDataRange> dataRanges)
  {
    Set<@NonNull String> dataRangeIDs = new HashSet<>();
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
