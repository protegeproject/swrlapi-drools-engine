package org.swrlapi.drools.converters.oo;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLClassExpressionConverter;
import org.swrlapi.drools.owl.classes.CE;

import javax.annotation.Nonnull;

class DroolsOWLClassExpression2CEConverter extends TargetRuleEngineConverterBase
  implements TargetRuleEngineOWLClassExpressionConverter<CE>, OWLClassExpressionVisitorEx<CE>
{

  public DroolsOWLClassExpression2CEConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @Nonnull @Override public CE visit(@Nonnull OWLClass owlClass)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLObjectIntersectionOf owlObjectIntersectionOf)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLObjectUnionOf owlObjectUnionOf)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLObjectComplementOf owlObjectComplementOf)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLObjectSomeValuesFrom owlObjectSomeValuesFrom)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLObjectAllValuesFrom owlObjectAllValuesFrom)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLObjectHasValue owlObjectHasValue)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLObjectMinCardinality owlObjectMinCardinality)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLObjectExactCardinality owlObjectExactCardinality)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLObjectMaxCardinality owlObjectMaxCardinality)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLObjectHasSelf owlObjectHasSelf)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLObjectOneOf owlObjectOneOf)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLDataSomeValuesFrom owlDataSomeValuesFrom)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLDataAllValuesFrom owlDataAllValuesFrom)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLDataHasValue owlDataHasValue)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLDataMinCardinality owlDataMinCardinality)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLDataExactCardinality owlDataExactCardinality)
  {
    return null;
  }

  @Nonnull @Override public CE visit(@Nonnull OWLDataMaxCardinality owlDataMaxCardinality)
  {
    return null;
  }

  @Override public CE convert(OWLClass cls)
  {
    return null;
  }

  @Override public CE convert(OWLObjectOneOf objectOneOf)
  {
    return null;
  }

  @Override public CE convert(OWLObjectIntersectionOf objectIntersectionOf)
  {
    return null;
  }

  @Override public CE convert(OWLObjectUnionOf objectUnionOf)
  {
    return null;
  }

  @Override public CE convert(OWLObjectSomeValuesFrom objectSomeValuesFrom)
  {
    return null;
  }

  @Override public CE convert(OWLObjectComplementOf objectComplementOf)
  {
    return null;
  }

  @Override public CE convert(OWLDataSomeValuesFrom dataSomeValuesFrom)
  {
    return null;
  }

  @Override public CE convert(OWLDataExactCardinality dataExactCardinality)
  {
    return null;
  }

  @Override public CE convert(OWLObjectExactCardinality objectExactCardinality)
  {
    return null;
  }

  @Override public CE convert(OWLDataMinCardinality dataMinCardinality)
  {
    return null;
  }

  @Override public CE convert(OWLObjectMinCardinality objectMinCardinality)
  {
    return null;
  }

  @Override public CE convert(OWLDataMaxCardinality dataMaxCardinality)
  {
    return null;
  }

  @Override public CE convert(OWLObjectMaxCardinality objectMaxCardinality)
  {
    return null;
  }

  @Override public CE convert(OWLDataHasValue dataHasValue)
  {
    return null;
  }

  @Override public CE convert(OWLObjectHasValue objectHasValue)
  {
    return null;
  }

  @Override public CE convert(OWLObjectAllValuesFrom objectAllValuesFrom)
  {
    return null;
  }

  @Override public CE convert(OWLDataAllValuesFrom dataAllValuesFrom)
  {
    return null;
  }

  @Override public CE convert(OWLObjectHasSelf owbjectHasSelf)
  {
    return null;
  }
}
