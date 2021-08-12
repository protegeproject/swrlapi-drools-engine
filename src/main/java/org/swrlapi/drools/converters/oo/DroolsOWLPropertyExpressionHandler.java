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
  @NonNull private final Map<@NonNull String, @NonNull OWLObjectPropertyExpression> peid2objectPropertyExpression = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLDataPropertyExpression> peid2dataPropertyExpression = new HashMap<>();
  @NonNull private final Map<@NonNull String, @NonNull OWLAnnotationProperty> pid2annotationProperty = new HashMap<>();

  @NonNull private final Map<@NonNull String, OPE> peid2OPE = new HashMap<>();
  @NonNull private final Map<@NonNull String, DPE> peid2DPE = new HashMap<>();
  @NonNull private final Map<@NonNull String, AP> pid2AP = new HashMap<>();

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
    this.pid2AP.clear();
    this.peid2objectPropertyExpression.clear();
    this.peid2dataPropertyExpression.clear();
    this.pid2annotationProperty.clear();
  }

  @Override public OPE convert(@NonNull OWLObjectPropertyExpression propertyExpression)
  {
    if (this.pe2ope.containsKey(propertyExpression))
      return this.pe2ope.get(propertyExpression);
    else {
      String peid = generatePEID();
      OPE ope = new OPE(peid);
      this.pe2ope.put(propertyExpression, ope);
      this.peid2objectPropertyExpression.put(peid, propertyExpression);
      this.peid2OPE.put(peid, ope);
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
      this.peid2dataPropertyExpression.put(peid, propertyExpression);
      this.peid2DPE.put(peid, dpe);
      return dpe;
    }
  }

  @Override public AP convert(OWLAnnotationProperty annotationProperty)
  {
    if (this.ap2ap.containsKey(annotationProperty))
      return this.ap2ap.get(annotationProperty);
    else {
      String pid = generatePEID();
      AP ap = new AP(pid);
      this.ap2ap.put(annotationProperty, ap);
      this.pid2annotationProperty.put(pid, annotationProperty);
      this.pid2AP.put(pid, ap);
      return ap;
    }
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

  @Nonnull @Override public AP visit(@Nonnull OWLAnnotationProperty annotationProperty)
  {
    return convert(annotationProperty);
  }

   @NonNull @Override public OWLObjectPropertyExpression convert(@NonNull OPE pe)
  {
    return resolveOWLObjectPropertyExpression(pe.getid());
  }

   @NonNull @Override public OWLDataPropertyExpression convert(@NonNull DPE pe)
  {
    return resolveOWLDataPropertyExpression(pe.getid());
  }

   @NonNull @Override public OWLAnnotationProperty convert(@NonNull AP ap)
  {
    return resolveOWLAnnotationProperty(ap.getid());
  }

   @NonNull @Override public OWLObjectPropertyExpression resolveOWLObjectPropertyExpression(@NonNull String peid)
  {
    if (this.peid2objectPropertyExpression.containsKey(peid))
      return this.peid2objectPropertyExpression.get(peid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL object property expression from a Drools object property expression with id " + peid);
  }

   @NonNull @Override public OWLDataPropertyExpression resolveOWLDataPropertyExpression(@NonNull String peid)
  {
    if (this.peid2dataPropertyExpression.containsKey(peid))
      return this.peid2dataPropertyExpression.get(peid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL data property expression from a Drools data property expression with id " + peid);
  }

   @NonNull @Override public OWLAnnotationProperty resolveOWLAnnotationProperty(@NonNull String pid)
  {
    if (this.pid2annotationProperty.containsKey(pid))
      return this.pid2annotationProperty.get(pid);
    else
      throw new IllegalArgumentException(
        "could not resolve an OWL annotation property from a Drools annotation property with id " + pid);
  }

  @NonNull private String generatePEID()
  {
    return "PEID" + this.propertyExpressionIndex++;
  }
}
