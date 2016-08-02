package org.swrlapi.drools.owl.properties;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.builtins.arguments.SWRLDataPropertyExpressionBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineNotImplementedFeatureException;

/**
 * @see org.semanticweb.owlapi.model.OWLDataPropertyExpression
 */
public class DPE extends PE implements BA
{
  public DPE(@NonNull String peid) { super(peid); }

  @NonNull @Override public SWRLDataPropertyExpressionBuiltInArgument extract(
    @NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    throw new TargetSWRLRuleEngineNotImplementedFeatureException("DPE.extract");
  }
}
