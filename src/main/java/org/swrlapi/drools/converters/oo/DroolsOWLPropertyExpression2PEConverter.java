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

import java.util.HashMap;
import java.util.Map;

public class DroolsOWLPropertyExpression2PEConverter extends TargetRuleEngineConverterBase
  implements TargetRuleEngineOWLPropertyExpressionConverter<PE>
{
  private int propertyExpressionIndex = 0;

  private final Map<OWLObjectPropertyExpression, OPE> opes = new HashMap<>();
  private final Map<OWLDataPropertyExpression, DPE> dpes = new HashMap<>();

  public DroolsOWLPropertyExpression2PEConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  public void reset()
  {
    this.propertyExpressionIndex = 0;
    this.opes.clear();
    this.dpes.clear();
  }

  @Override public OPE convert(@NonNull OWLObjectPropertyExpression propertyExpression)
  {
    if (this.opes.containsKey(propertyExpression))
      return this.opes.get(propertyExpression);
    else {
      String peid = generatePEID();
      OPE ope = new OPE(peid);
      this.opes.put(propertyExpression, ope);
      return ope;
    }
  }

  @Override public DPE convert(@NonNull OWLDataPropertyExpression propertyExpression)
  {
    if (this.dpes.containsKey(propertyExpression))
      return this.dpes.get(propertyExpression);
    else {
      String peid = generatePEID();
      DPE dpe = new DPE(peid);
      this.dpes.put(propertyExpression, dpe);
      return dpe;
    }
  }

  @NonNull private String generatePEID()
  {
    return "PEID" + this.propertyExpressionIndex++;
  }

}
