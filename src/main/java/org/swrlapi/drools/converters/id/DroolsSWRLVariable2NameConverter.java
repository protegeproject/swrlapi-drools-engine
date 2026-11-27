package org.swrlapi.drools.converters.id;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

import java.util.Optional;
import java.util.Set;

public class DroolsSWRLVariable2NameConverter extends TargetRuleEngineConverterBase
{
  public DroolsSWRLVariable2NameConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  public @NonNull String swrlVariable2DRL(@NonNull SWRLVariable variable)
  {
    String variableName = iri2VariableName(variable.getIRI());
    return variableName2DRL(variableName);
  }

  public @NonNull String swrlVariable2DRLVariableName(@NonNull SWRLVariable variable)
  {
    String variableName = iri2VariableName(variable.getIRI());
    return variableName2DRL(variableName);
  }

  public @NonNull String swrlVariable2VariableName(@NonNull SWRLVariable variable)
  {
    return iri2VariableName(variable.getIRI());
  }

  public @NonNull String variableName2DRL(@NonNull String variableName, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    if (previouslyEncounteredVariableNames.contains(variableName)) {
      return fieldName + "==" + variableName2DRL(variableName);
    } else {
      previouslyEncounteredVariableNames.add(variableName);
      return variableName2DRL(variableName) + ":" + fieldName;
    }
  }

  public @NonNull String variableName2DRL(@NonNull String variableName)
  {
    return "$" + variableName;
  }

  private @NonNull String iri2VariableName(IRI variableIRI)
  {
    Optional<String> remainder = variableIRI.getRemainder();

    if (remainder.isPresent())
      return remainder.get();
    else
      throw new TargetSWRLRuleEngineException("SWRL variable with IRI " + variableIRI + " has no remainder");

  }
}
