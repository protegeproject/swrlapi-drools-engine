package org.swrlapi.drools.converters.id;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLPropertyExpressionConverter;
import org.swrlapi.drools.converters.oo.DroolsOWLPropertyExpression2PEConverter;

/**
 * This class converts OWLAPI OWL property expressions to their Drools representation.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectPropertyExpression
 * @see org.semanticweb.owlapi.model.OWLDataPropertyExpression
 */
public class DroolsOWLPropertyExpression2IDConverter extends TargetRuleEngineConverterBase
  implements TargetRuleEngineOWLPropertyExpressionConverter<String>
{
  @NonNull private final DroolsOWLPropertyExpression2PEConverter droolsOWLPropertyExpression2PEConverter;

  public DroolsOWLPropertyExpression2IDConverter(@NonNull SWRLRuleEngineBridge bridge,
    @NonNull DroolsOWLPropertyExpression2PEConverter droolsOWLPropertyExpression2PEConverter)
  {
    super(bridge);

    this.droolsOWLPropertyExpression2PEConverter = droolsOWLPropertyExpression2PEConverter;
  }

  @NonNull @Override public String convert(@NonNull OWLObjectPropertyExpression propertyExpression)
  {
    return this.droolsOWLPropertyExpression2PEConverter.convert(propertyExpression).getid();
  }

  @NonNull @Override public String convert(@NonNull OWLDataPropertyExpression propertyExpression)
  {
    return this.droolsOWLPropertyExpression2PEConverter.convert(propertyExpression).getid();
  }
}