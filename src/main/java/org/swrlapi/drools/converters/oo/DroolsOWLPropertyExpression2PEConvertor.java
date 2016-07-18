package org.swrlapi.drools.converters.oo;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLPropertyExpressionConverter;
import org.swrlapi.drools.owl.properties.DPE;
import org.swrlapi.drools.owl.properties.OPE;
import org.swrlapi.drools.owl.properties.PE;

class DroolsOWLPropertyExpression2PEConverter extends DroolsOOConverterBase
  implements TargetRuleEngineOWLPropertyExpressionConverter<PE>
{

  protected DroolsOWLPropertyExpression2PEConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @Override public OPE convert(OWLObjectPropertyExpression propertyExpression)
  {
    String peid = getOWLObjectResolver().resolveOWLObjectPropertyExpression2ID(propertyExpression);

    return new OPE(peid);
  }

  @Override public DPE convert(OWLDataPropertyExpression propertyExpression)
  {
    String peid = getOWLObjectResolver().resolveOWLDataPropertyExpression2ID(propertyExpression);

    return new DPE(peid);
  }
}
