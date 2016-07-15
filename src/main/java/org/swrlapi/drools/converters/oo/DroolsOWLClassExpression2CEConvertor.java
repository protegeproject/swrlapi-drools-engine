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
import org.swrlapi.drools.owl.classes.C;
import org.swrlapi.drools.owl.classes.CE;
import org.swrlapi.drools.owl.classes.DAVFCE;
import org.swrlapi.drools.owl.classes.DCCE;
import org.swrlapi.drools.owl.classes.DHVCE;
import org.swrlapi.drools.owl.classes.DMaxCCE;
import org.swrlapi.drools.owl.classes.DMinCCE;
import org.swrlapi.drools.owl.classes.DSVFCE;
import org.swrlapi.drools.owl.classes.OAVFCE;
import org.swrlapi.drools.owl.classes.OCCE;
import org.swrlapi.drools.owl.classes.OHVCE;
import org.swrlapi.drools.owl.classes.OIOCE;
import org.swrlapi.drools.owl.classes.OMaxCCE;
import org.swrlapi.drools.owl.classes.OMinCCE;
import org.swrlapi.drools.owl.classes.OOOCE;
import org.swrlapi.drools.owl.classes.OSVFCE;
import org.swrlapi.drools.owl.classes.OUOCE;

class DroolsOWLClassExpression2CEConverter extends TargetRuleEngineConverterBase
  implements TargetRuleEngineOWLClassExpressionConverter<CE>, OWLClassExpressionVisitorEx<CE>
{

  public DroolsOWLClassExpression2CEConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull @Override public C visit(@NonNull OWLClass owlClass)
  {
    throw new RuntimeException("create C");
  }

  @NonNull @Override public OIOCE visit(@NonNull OWLObjectIntersectionOf owlObjectIntersectionOf)
  {
    throw new RuntimeException("create OIOCE");
  }

  @NonNull @Override public OUOCE visit(@NonNull OWLObjectUnionOf owlObjectUnionOf)
  {
    throw new RuntimeException("create OUOCE");
  }

  @NonNull @Override public OCCE visit(@NonNull OWLObjectComplementOf owlObjectComplementOf)
  {
    throw new RuntimeException("create OCCO");
  }

  @NonNull @Override public OSVFCE visit(@NonNull OWLObjectSomeValuesFrom owlObjectSomeValuesFrom)
  {
    throw new RuntimeException("create OSVFCE");
  }

  @NonNull @Override public OAVFCE visit(@NonNull OWLObjectAllValuesFrom owlObjectAllValuesFrom)
  {
    throw new RuntimeException("create OAFVCE");
  }

  @NonNull @Override public OHVCE visit(@NonNull OWLObjectHasValue owlObjectHasValue)
  {
    throw new RuntimeException("create OHVCE");
  }

  @NonNull @Override public OMinCCE visit(@NonNull OWLObjectMinCardinality owlObjectMinCardinality)
  {
    throw new RuntimeException("create OMinCCE");
  }

  @NonNull @Override public OCCE visit(@NonNull OWLObjectExactCardinality owlObjectExactCardinality)
  {
    throw new RuntimeException("create OCCE");
  }

  @NonNull @Override public OMaxCCE visit(@NonNull OWLObjectMaxCardinality owlObjectMaxCardinality)
  {
    throw new RuntimeException("create OMaxCCE");
  }

  @NonNull @Override public CE visit(@NonNull OWLObjectHasSelf owlObjectHasSelf)
  {
    throw new RuntimeException("create CE");
  }

  @NonNull @Override public OOOCE visit(@NonNull OWLObjectOneOf owlObjectOneOf)
  {
    throw new RuntimeException("create OOOCE");
  }

  @NonNull @Override public DSVFCE visit(@NonNull OWLDataSomeValuesFrom owlDataSomeValuesFrom)
  {
    throw new RuntimeException("create DSVFCE");
  }

  @NonNull @Override public DAVFCE visit(@NonNull OWLDataAllValuesFrom owlDataAllValuesFrom)
  {
    throw new RuntimeException("create DAVFCE");
  }

  @NonNull @Override public DHVCE visit(@NonNull OWLDataHasValue owlDataHasValue)
  {
    throw new RuntimeException("create DHVCE");
  }

  @NonNull @Override public DMinCCE visit(@NonNull OWLDataMinCardinality owlDataMinCardinality)
  {
    throw new RuntimeException("create DMinCCE");
  }

  @NonNull @Override public DCCE visit(@NonNull OWLDataExactCardinality owlDataExactCardinality)
  {
    throw new RuntimeException("create DCCE");
  }

  @NonNull @Override public DMaxCCE visit(@NonNull OWLDataMaxCardinality owlDataMaxCardinality)
  {
    throw new RuntimeException("create DMaxCCE");
  }

  @NonNull @Override public C convert(OWLClass cls)
  {
    return visit(cls);
  }

  @NonNull @Override public OOOCE convert(OWLObjectOneOf objectOneOf)
  {
    return visit(objectOneOf);
  }

  @NonNull @Override public OIOCE convert(OWLObjectIntersectionOf objectIntersectionOf)
  {
    return visit(objectIntersectionOf);
  }

  @NonNull @Override public OUOCE convert(OWLObjectUnionOf objectUnionOf)
  {
    return visit(objectUnionOf);
  }

  @NonNull @Override public OSVFCE convert(OWLObjectSomeValuesFrom objectSomeValuesFrom)
  {
    return visit(objectSomeValuesFrom);
  }

  @NonNull @Override public OCCE convert(OWLObjectComplementOf objectComplementOf)
  {
    return visit(objectComplementOf);
  }

  @NonNull @Override public DSVFCE convert(OWLDataSomeValuesFrom dataSomeValuesFrom)
  {
    return visit(dataSomeValuesFrom);
  }

  @NonNull @Override public DCCE convert(OWLDataExactCardinality dataExactCardinality)
  {
    return visit(dataExactCardinality);
  }

  @NonNull @Override public OCCE convert(OWLObjectExactCardinality objectExactCardinality)
  {
    return visit(objectExactCardinality);
  }

  @NonNull @Override public DMinCCE convert(OWLDataMinCardinality dataMinCardinality)
  {
    return visit(dataMinCardinality);
  }

  @NonNull @Override public OMinCCE convert(OWLObjectMinCardinality objectMinCardinality)
  {
    return visit(objectMinCardinality);
  }

  @NonNull @Override public DMaxCCE convert(OWLDataMaxCardinality dataMaxCardinality)
  {
    return visit(dataMaxCardinality);
  }

  @NonNull @Override public OMaxCCE convert(OWLObjectMaxCardinality objectMaxCardinality)
  {
    return visit(objectMaxCardinality);
  }

  @NonNull @Override public DHVCE convert(OWLDataHasValue dataHasValue)
  {
    return visit(dataHasValue);
  }

  @NonNull @Override public OHVCE convert(OWLObjectHasValue objectHasValue)
  {
    return visit(objectHasValue);
  }

  @NonNull @Override public OAVFCE convert(OWLObjectAllValuesFrom objectAllValuesFrom)
  {
    return visit(objectAllValuesFrom);
  }

  @NonNull @Override public DAVFCE convert(OWLDataAllValuesFrom dataAllValuesFrom)
  {
    return visit(dataAllValuesFrom);
  }

  @NonNull @Override public CE convert(OWLObjectHasSelf objectHasSelf)
  {
    return visit(objectHasSelf);
  }
}
