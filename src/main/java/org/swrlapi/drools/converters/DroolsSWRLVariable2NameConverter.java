package org.swrlapi.drools.converters;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;

import java.util.Set;

public class DroolsSWRLVariable2NameConverter extends TargetRuleEngineConverterBase
{
  public DroolsSWRLVariable2NameConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull public String swrlVariable2DRL(@NonNull SWRLVariable variable)
  {
    IRI variableIRI = variable.getIRI();
    String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);

    return variablePrefixedName2DRL(variablePrefixedName);
  }

  @NonNull public String swrlVariable2VariableName(@NonNull SWRLVariable variable)
  {
    IRI variableIRI = variable.getIRI();
    String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);

    return variablePrefixedName2VariableName(variablePrefixedName);
  }

  @NonNull public String swrlVariable2VariablePrefixedName(@NonNull SWRLVariable variable)
  {
    IRI variableIRI = variable.getIRI();

    return getIRIResolver().iri2PrefixedName(variableIRI);
  }

  @NonNull public String variableIRI2DRL(@NonNull IRI variableIRI)
  {
    String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);

    return variablePrefixedName2DRL(variablePrefixedName);
  }

  @NonNull public String variablePrefixedName2DRL(@NonNull String variablePrefixedName)
  {
    String variableName = variablePrefixedName2VariableName(variablePrefixedName);

    return variableName2DRL(variableName);
  }

  @NonNull public String variableName2DRL(@NonNull String variableName)
  {
    return "$" + variableName;
  }

  @NonNull public String variablePrefixedName2DRL(@NonNull String variablePrefixedName, String fieldName,
      @NonNull Set<String> previouslyEncounteredVariablePrefixedNames)
  {
    if (previouslyEncounteredVariablePrefixedNames.contains(variablePrefixedName)) {
      return fieldName + "==" + variablePrefixedName2DRL(variablePrefixedName);
    } else {
      previouslyEncounteredVariablePrefixedNames.add(variablePrefixedName);
      return variablePrefixedName2DRL(variablePrefixedName) + ":" + fieldName;
    }
  }

  @NonNull public String variablePrefixedName2VariableName(@NonNull String variablePrefixedName)
  {
    return variablePrefixedName.startsWith(":") ? variablePrefixedName.substring(1) : variablePrefixedName;
  }
}
