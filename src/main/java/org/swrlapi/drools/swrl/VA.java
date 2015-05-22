package org.swrlapi.drools.swrl;

import checkers.nullness.quals.NonNull;
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
  private final String variableName;

  public VA(String variableName)
  {
    this.variableName = variableName;
  }

  public String getVariableName()
  {
    return this.variableName;
  }

  @NonNull @Override
  public SWRLVariable extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @NonNull @Override
  public String toString()
  {
    return "VA(?" + getVariableName() + ")";
  }
}
