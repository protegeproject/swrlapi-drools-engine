package org.swrlapi.drools.converters.id;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
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
import org.swrlapi.drools.converters.oo.DroolsOWLClassExpression2CEConverter;
import org.swrlapi.drools.owl.classes.C;
import org.swrlapi.drools.owl.classes.CE;
import org.swrlapi.drools.owl.classes.DAVFCE;
import org.swrlapi.drools.owl.classes.DECCE;
import org.swrlapi.drools.owl.classes.DHVCE;
import org.swrlapi.drools.owl.classes.DMaxCCE;
import org.swrlapi.drools.owl.classes.DMinCCE;
import org.swrlapi.drools.owl.classes.DSVFCE;
import org.swrlapi.drools.owl.classes.OAVFCE;
import org.swrlapi.drools.owl.classes.OCOCE;
import org.swrlapi.drools.owl.classes.OECCE;
import org.swrlapi.drools.owl.classes.OHVCE;
import org.swrlapi.drools.owl.classes.OIOCE;
import org.swrlapi.drools.owl.classes.OMaxCCE;
import org.swrlapi.drools.owl.classes.OMinCCE;
import org.swrlapi.drools.owl.classes.OOHSCE;
import org.swrlapi.drools.owl.classes.OOOCE;
import org.swrlapi.drools.owl.classes.OSVFCE;
import org.swrlapi.drools.owl.classes.OUOCE;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class resolves between OWLAPI OWL class expressions and identifiers.
 *
 * @see org.semanticweb.owlapi.model.OWLClassExpression
 * @see org.swrlapi.drools.owl.classes.CE
 */
public class DroolsOWLClassExpressionResolver extends TargetRuleEngineConverterBase
  implements TargetRuleEngineOWLClassExpressionConverter<String>, OWLClassExpressionVisitorEx<String>
{
  private final Map<C, OWLClass> cces = new HashMap<>();
  private final Map<OIOCE, OWLObjectIntersectionOf> oioces = new HashMap<>();
  private final Map<OUOCE, OWLObjectUnionOf> ouoces = new HashMap<>();
  private final Map<OCOCE, OWLObjectComplementOf> ococes = new HashMap<>();
  private final Map<OSVFCE, OWLObjectSomeValuesFrom> osvfces = new HashMap<>();
  private final Map<OAVFCE, OWLObjectAllValuesFrom> oavfces = new HashMap<>();
  private final Map<OHVCE, OWLObjectHasValue> ohvces = new HashMap<>();
  private final Map<OECCE, OWLObjectExactCardinality> oecces = new HashMap<>();
  private final Map<OMinCCE, OWLObjectMinCardinality> omincces = new HashMap<>();
  private final Map<OMaxCCE, OWLObjectMaxCardinality> omaxcces = new HashMap<>();
  private final Map<OOHSCE, OWLObjectHasSelf> oohsces = new HashMap<>();
  private final Map<OOOCE, OWLObjectOneOf> oooces = new HashMap<>();
  private final Map<DSVFCE, OWLDataSomeValuesFrom> dsvfces = new HashMap<>();
  private final Map<DAVFCE, OWLDataAllValuesFrom> davfces = new HashMap<>();
  private final Map<DHVCE, OWLDataHasValue> dhvces = new HashMap<>();
  private final Map<DECCE, OWLDataExactCardinality> decces = new HashMap<>();
  private final Map<DMinCCE, OWLDataMinCardinality> dmincces = new HashMap<>();
  private final Map<DMaxCCE, OWLDataMaxCardinality> dmaxcces = new HashMap<>();

  @NonNull private final DroolsOWLClassExpression2CEConverter droolsOWLClassExpression2CEConverter;

  public DroolsOWLClassExpressionResolver(@NonNull SWRLRuleEngineBridge bridge,
    @NonNull DroolsOWLClassExpression2CEConverter droolsOWLClassExpression2CEConverter)
  {
    super(bridge);

    this.droolsOWLClassExpression2CEConverter = droolsOWLClassExpression2CEConverter;
  }

  @NonNull public String convert(@NonNull OWLClassExpression classExpression)
  {
    return classExpression.accept(this);
  }

  @NonNull @Override public String convert(@NonNull OWLClass cls)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(cls).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLObjectOneOf objectOneOf)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(objectOneOf).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLObjectIntersectionOf objectIntersectionOf)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(objectIntersectionOf).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLObjectUnionOf objectUnionOf)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(objectUnionOf).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLObjectComplementOf objectComplementOf)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(objectComplementOf).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLObjectSomeValuesFrom objectSomeValuesFrom)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(objectSomeValuesFrom).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLDataSomeValuesFrom dataSomeValuesFrom)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(dataSomeValuesFrom).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLDataExactCardinality dataExactCardinality)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(dataExactCardinality).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLObjectExactCardinality objectExactCardinality)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(objectExactCardinality).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLDataMinCardinality dataMinCardinality)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(dataMinCardinality).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLObjectMinCardinality objectMinCardinality)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(objectMinCardinality).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLDataMaxCardinality dataMaxCardinality)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(dataMaxCardinality).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLObjectMaxCardinality objectMaxCardinality)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(objectMaxCardinality).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLDataHasValue dataHasValue)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(dataHasValue).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLObjectHasValue objectHasValue)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(objectHasValue).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLObjectAllValuesFrom objectAllValuesFrom)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(objectAllValuesFrom).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLDataAllValuesFrom dataAllValuesFrom)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(dataAllValuesFrom).getceid();
  }

  @NonNull @Override public String convert(@NonNull OWLObjectHasSelf owbjectHasSelf)
  {
    return getDroolsOWLClassExpression2CEConverter().convert(owbjectHasSelf).getceid();
  }

  @NonNull public Set<@NonNull CE> getCEs()
  {
    return getDroolsOWLClassExpression2CEConverter().getCEs();
  }

  @NonNull @Override public String visit(@NonNull OWLClass owlClass)
  {
    return convert(owlClass);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectIntersectionOf objectIntersectionOf)
  {
    return convert(objectIntersectionOf);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectUnionOf owlObjectUnionOf)
  {
    return convert(owlObjectUnionOf);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectComplementOf objectComplementOf)
  {
    return convert(objectComplementOf);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectSomeValuesFrom objectSomeValuesFrom)
  {
    return convert(objectSomeValuesFrom);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectAllValuesFrom objectAllValuesFrom)
  {
    return convert(objectAllValuesFrom);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectHasValue objectHasValue)
  {
    return convert(objectHasValue);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectMinCardinality objectMinCardinality)
  {
    return convert(objectMinCardinality);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectExactCardinality objectExactCardinality)
  {
    return convert(objectExactCardinality);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectMaxCardinality objectMaxCardinality)
  {
    return convert(objectMaxCardinality);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectHasSelf objectHasSelf)
  {
    return convert(objectHasSelf);
  }

  @NonNull @Override public String visit(@NonNull OWLObjectOneOf objectOneOf)
  {
    return convert(objectOneOf);
  }

  @NonNull @Override public String visit(@NonNull OWLDataSomeValuesFrom dataSomeValuesFrom)
  {
    return convert(dataSomeValuesFrom);
  }

  @NonNull @Override public String visit(@NonNull OWLDataAllValuesFrom dataAllValuesFrom)
  {
    return convert(dataAllValuesFrom);
  }

  @NonNull @Override public String visit(@NonNull OWLDataHasValue dataHasValue)
  {
    return convert(dataHasValue);
  }

  @NonNull @Override public String visit(@NonNull OWLDataMinCardinality dataMinCardinality)
  {
    return convert(dataMinCardinality);
  }

  @NonNull @Override public String visit(@NonNull OWLDataExactCardinality dataExactCardinality)
  {
    return convert(dataExactCardinality);
  }

  @NonNull @Override public String visit(@NonNull OWLDataMaxCardinality dataMaxCardinality)
  {
    return convert(dataMaxCardinality);
  }


  @NonNull private DroolsOWLClassExpression2CEConverter getDroolsOWLClassExpression2CEConverter()
  {
    return this.droolsOWLClassExpression2CEConverter;
  }

}
