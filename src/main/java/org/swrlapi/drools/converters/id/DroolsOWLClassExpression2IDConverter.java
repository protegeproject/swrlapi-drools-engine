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
import org.swrlapi.drools.owl.classes.CE;

import java.util.Set;

/**
 * This class converts OWLAPI OWL class expressions to identifiers that can be used to resolve them.
 *
 * @see org.semanticweb.owlapi.model.OWLClassExpression
 * @see org.swrlapi.drools.owl.classes.CE
 */
public class DroolsOWLClassExpression2IDConverter extends TargetRuleEngineConverterBase
  implements TargetRuleEngineOWLClassExpressionConverter<String>, OWLClassExpressionVisitorEx<String>
{
  @NonNull private final DroolsOWLClassExpression2CEConverter droolsOWLClassExpression2CEConverter;

  public DroolsOWLClassExpression2IDConverter(@NonNull SWRLRuleEngineBridge bridge,
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

  // TODO Temporary fix - the individual may not be named
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

  private @NonNull DroolsOWLClassExpression2CEConverter getDroolsOWLClassExpression2CEConverter()
  {
    return this.droolsOWLClassExpression2CEConverter;
  }
}
