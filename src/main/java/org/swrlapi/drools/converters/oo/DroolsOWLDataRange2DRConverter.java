package org.swrlapi.drools.converters.oo;

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
import org.swrlapi.drools.owl.dataranges.D;
import org.swrlapi.drools.owl.dataranges.DCO;
import org.swrlapi.drools.owl.dataranges.DIO;
import org.swrlapi.drools.owl.dataranges.DOO;
import org.swrlapi.drools.owl.dataranges.DR;
import org.swrlapi.drools.owl.dataranges.DTR;
import org.swrlapi.drools.owl.dataranges.DUO;
import org.swrlapi.exceptions.TargetSWRLRuleEngineNotImplementedFeatureException;

/**
 * Converts an OWLAPI OWL data range to its Drools representation
 *
 * @see org.semanticweb.owlapi.model.OWLDataRange
 * @see DR
 */
public class DroolsOWLDataRange2DRConverter extends TargetRuleEngineConverterBase
    implements TargetRuleEngineOWLDataRangeConverter<DR>, OWLDataRangeVisitorEx<DR>
{
  public DroolsOWLDataRange2DRConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull public DR convert(@NonNull OWLDataRange dataRange)
  {
    return dataRange.accept(this);
  }

  @Override public @NonNull D convert(@NonNull OWLDatatype datatype)
  {
    throw new TargetSWRLRuleEngineNotImplementedFeatureException("datatype data ranges not implemented");
  }

  @Override public @NonNull DOO convert(OWLDataOneOf dataRange)
  {
    throw new TargetSWRLRuleEngineNotImplementedFeatureException("data one of data range restrictions not implemented");
  }

  @Override public @NonNull DCO convert(@NonNull OWLDataComplementOf dataRange)
  {
    throw new TargetSWRLRuleEngineNotImplementedFeatureException("data range datatypes not implemented");
  }

  @Override public @NonNull DIO convert(@NonNull OWLDataIntersectionOf dataRange)
  {
    throw new TargetSWRLRuleEngineNotImplementedFeatureException("data intersection of data ranges not implemented");
  }

  @Override public @NonNull DUO convert(@NonNull OWLDataUnionOf dataRange)
  {
    throw new TargetSWRLRuleEngineNotImplementedFeatureException("data union of data ranges not implemented");
  }

  @Override public @NonNull DTR convert(@NonNull OWLDatatypeRestriction datatypeRestriction)
  {
    throw new TargetSWRLRuleEngineNotImplementedFeatureException("datatype restriction data ranges not implemented");
  }

  @NonNull @Override public D visit(@NonNull OWLDatatype owlDatatype)
  {
    return convert(owlDatatype);
  }

  @NonNull @Override public DOO visit(OWLDataOneOf owlDataOneOf)
  {
    return convert(owlDataOneOf);
  }

  @NonNull @Override public DCO visit(@NonNull OWLDataComplementOf owlDataComplementOf)
  {
    return convert(owlDataComplementOf);
  }

  @NonNull @Override public DIO visit(@NonNull OWLDataIntersectionOf owlDataIntersectionOf)
  {
    return convert(owlDataIntersectionOf);
  }

  @NonNull @Override public DUO visit(@NonNull OWLDataUnionOf owlDataUnionOf)
  {
    return convert(owlDataUnionOf);
  }

  @NonNull @Override public DTR visit(OWLDatatypeRestriction owlDatatypeRestriction)
  {
    return convert(owlDatatypeRestriction);
  }
}
