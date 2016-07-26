package org.swrlapi.drools.converters.id;

import com.google.common.base.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

import java.util.Set;

public class DroolsSWRLVariable2NameConverter extends TargetRuleEngineConverterBase
{
  public DroolsSWRLVariable2NameConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull public String swrlVariable2DRL(@NonNull SWRLVariable variable)
  {
    String variableName = iri2VariableName(variable.getIRI());
    return variableName2DRL(variableName);
  }

  @NonNull public String swrlVariable2DRLVariableName(@NonNull SWRLVariable variable)
  {
    String variableName = iri2VariableName(variable.getIRI());
    return variableName2DRL(variableName);
  }

  @NonNull public String swrlVariable2VariableName(@NonNull SWRLVariable variable)
  {
    return iri2VariableName(variable.getIRI());
  }

  @NonNull public String variableName2DRL(@NonNull String variableName, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    if (previouslyEncounteredVariableNames.contains(variableName)) {
      return fieldName + "==" + variableName2DRL(variableName);
    } else {
      previouslyEncounteredVariableNames.add(variableName);
      return variableName2DRL(variableName) + ":" + fieldName;
    }
  }

  @NonNull public String variableName2DRL(@NonNull String variableName)
  {
    return "$" + variableName;
  }

  @NonNull private String iri2VariableName(IRI variableIRI)
  {
    Optional<String> remainder = variableIRI.getRemainder();

    if (remainder.isPresent())
      return remainder.get();
    else
      throw new TargetSWRLRuleEngineException("SWRL variable with IRI " + variableIRI + " has no remainder");

  }
}
