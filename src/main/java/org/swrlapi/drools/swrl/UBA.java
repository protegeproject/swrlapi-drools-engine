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
  private final @NonNull String variableName;

  public UBA(@NonNull String variableName)
  {
    this.variableName = variableName;
  }

  public @NonNull String getVariableName()
  {
    return this.variableName;
  }

  @Override public @NonNull SWRLBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @SideEffectFree @Override public @NonNull String toString()
  {
    return "UBA(?" + getVariableName() + ")";
  }
}
