package org.swrlapi.drools.converters.oo;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitorEx;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLPropertyExpressionConverter;
import org.swrlapi.drools.owl.properties.AP;
import org.swrlapi.drools.owl.properties.DPE;
import org.swrlapi.drools.owl.properties.OPE;
import org.swrlapi.drools.owl.properties.PE;
import org.swrlapi.drools.owl.properties.PEConverter;
import org.swrlapi.drools.owl.properties.PEResolver;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class DroolsOWLPropertyExpressionHandler extends TargetRuleEngineConverterBase
  implements TargetRuleEngineOWLPropertyExpressionConverter<PE>, OWLPropertyExpressionVisitorEx<PE>, PEConverter,
  PEResolver
{
  private int propertyExpressionIndex = 0;

  @NonNull private final Map<@NonNull OWLObjectPropertyExpression, OPE> pe2ope = new HashMap<>();
  @NonNull private final Map<@NonNull OWLDataPropertyExpression, DPE> pe2dpe = new HashMap<>();
  @NonNull private final Map<@NonNull OWLAnnotationProperty, AP> ap2ap = new HashMap<>();

  @NonNull private final Map<@NonNull String, OPE> peid2OPE = new HashMap<>();
  @NonNull private final Map<@NonNull String, DPE> peid2DPE = new HashMap<>();
  @NonNull private final Map<@NonNull String, AP> peid2AP = new HashMap<>();

  public DroolsOWLPropertyExpressionHandler(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  public void reset()
  {
    this.propertyExpressionIndex = 0;
    this.pe2ope.clear();
    this.pe2dpe.clear();
    this.ap2ap.clear();
    this.peid2OPE.clear();
    this.peid2DPE.clear();
    this.peid2AP.clear();
  }

  @Override public OPE convert(@NonNull OWLObjectPropertyExpression propertyExpression)
  {
    if (this.pe2ope.containsKey(propertyExpression))
      return this.pe2ope.get(propertyExpression);
    else {
      String peid = generatePEID();
      OPE ope = new OPE(peid);
      this.pe2ope.put(propertyExpression, ope);
      return ope;
    }
  }

  @Override public DPE convert(@NonNull OWLDataPropertyExpression propertyExpression)
  {
    if (this.pe2dpe.containsKey(propertyExpression))
      return this.pe2dpe.get(propertyExpression);
    else {
      String peid = generatePEID();
      DPE dpe = new DPE(peid);
      this.pe2dpe.put(propertyExpression, dpe);
      return dpe;
    }
  }

  @Override public PE convert(OWLAnnotationProperty annotationProperty)
  {
    return null;
  }

  @Nonnull @Override public OPE visit(@Nonnull OWLObjectProperty objectProperty)
  {
    return convert(objectProperty);
  }

  @Nonnull @Override public OPE visit(@Nonnull OWLObjectInverseOf objectInverseOf)
  {
    return convert(objectInverseOf);
  }

  @Nonnull @Override public DPE visit(@Nonnull OWLDataProperty dataProperty)
  {
    return convert(dataProperty);
  }

  @Nonnull @Override public PE visit(@Nonnull OWLAnnotationProperty annotationProperty)
  {
    return convert(annotationProperty);
  }

  @Override public @NonNull OWLObjectPropertyExpression convert(@NonNull OPE pe)
  {
    return null;
  }

  @Override public @NonNull OWLDataPropertyExpression convert(@NonNull DPE pe)
  {
    return null;
  }

  @Override public @NonNull OWLAnnotationProperty convert(@NonNull AP ap)
  {
    return null;
  }

  @Override public @NonNull OWLObjectPropertyExpression resolveOWLObjectPropertyExpression(@NonNull String peid)
  {
    return null;
  }

  @Override public @NonNull OWLDataPropertyExpression resolveOWLDataPropertyExpression(@NonNull String peid)
  {
    return null;
  }

  @Override public @NonNull OWLAnnotationProperty resolveOWLAnnotationProperty(@NonNull String pid)
  {
    return null;
  }

  @NonNull private String generatePEID()
  {
    return "PEID" + this.propertyExpressionIndex++;
  }
}
