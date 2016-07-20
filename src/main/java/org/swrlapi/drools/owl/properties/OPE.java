package org.swrlapi.drools.owl.properties;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyExpressionBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineNotImplementedFeatureException;

/**
 * @see org.semanticweb.owlapi.model.OWLObjectPropertyExpression
 */
public class OPE extends PE implements BA
{
  public OPE(@NonNull String peid) { super(peid); }

  @NonNull @Override public SWRLObjectPropertyExpressionBuiltInArgument extract(
    @NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    throw new TargetSWRLRuleEngineNotImplementedFeatureException("OPE.extract");
  }
}
