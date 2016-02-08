package org.swrlapi.drools.extractors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractorBase;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * @see org.semanticweb.owlapi.model.SWRLVariable
 */
public class DroolsSWRLVariableExtractor extends TargetRuleEngineExtractorBase
{
  public DroolsSWRLVariableExtractor(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull public IRI variableName2VariableIRI(@NonNull String variableName) throws TargetSWRLRuleEngineException
  {
    String variablePrefixedName = variableName;

    return prefixedName2IRI(variablePrefixedName);
  }
}
