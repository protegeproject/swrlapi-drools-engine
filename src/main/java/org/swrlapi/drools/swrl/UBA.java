package org.swrlapi.drools.swrl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an unbound argument to a SWRL built-in in Drools.
 */
public class UBA implements BA
{
  @NonNull private final String variableName;

  public UBA(@NonNull String variableName)
  {
    this.variableName = variableName;
  }

  @NonNull public String getVariableName()
  {
    return this.variableName;
  }

  @NonNull @Override public SWRLBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @NonNull @SideEffectFree @Override public String toString()
  {
    return "UBA(?" + getVariableName() + ")";
  }
}
