package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.IRI;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractorBase;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 *
 * @see org.semanticweb.owlapi.model.SWRLVariable
 */
public class DroolsSWRLVariableExtractor extends TargetRuleEngineExtractorBase
{
  public DroolsSWRLVariableExtractor(SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  public String variableName2VariablePrefixedName(String variableName)
  {
    return ":" + variableName;
  }

  public IRI variableName2VariableIRI(String variableName) throws TargetSWRLRuleEngineException
  {
    String variablePrefixedName = variableName2VariablePrefixedName(variableName);

    return prefixedName2IRI(variablePrefixedName);
  }
}
