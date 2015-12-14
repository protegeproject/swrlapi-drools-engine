package org.swrlapi.drools.swrl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents a SWRL variable argument in Drools.
 *
 * @see org.semanticweb.owlapi.model.SWRLVariable
 */
public class VA implements AA
{
  @NonNull private final String variableName;

  public VA(@NonNull String variableName)
  {
    this.variableName = variableName;
  }

  @NonNull public String getVariableName()
  {
    return this.variableName;
  }

  @NonNull @Override public SWRLVariable extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "VA(?" + getVariableName() + ")";
  }
}
