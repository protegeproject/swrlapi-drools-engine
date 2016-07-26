package org.swrlapi.drools.converters.oo;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLPropertyExpressionConverter;
import org.swrlapi.drools.owl.properties.DPE;
import org.swrlapi.drools.owl.properties.OPE;
import org.swrlapi.drools.owl.properties.PE;

public class DroolsOWLPropertyExpression2PEConverter extends TargetRuleEngineConverterBase
  implements TargetRuleEngineOWLPropertyExpressionConverter<PE>
{
  public DroolsOWLPropertyExpression2PEConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @Override public OPE convert(@NonNull OWLObjectPropertyExpression propertyExpression)
  {
    String peid = getOWLObjectResolver().resolveOWLObjectPropertyExpression2ID(propertyExpression);

    return new OPE(peid);
  }

  @Override public DPE convert(@NonNull OWLDataPropertyExpression propertyExpression)
  {
    String peid = getOWLObjectResolver().resolveOWLDataPropertyExpression2ID(propertyExpression);

    return new DPE(peid);
  }
}
