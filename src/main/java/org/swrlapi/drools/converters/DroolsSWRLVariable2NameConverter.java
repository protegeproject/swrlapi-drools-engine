package org.swrlapi.drools.converters;

import org.checkerframework.checker.nullness.qual.NonNull;
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

  @NonNull public String swrlVariable2DRLVariableName(@NonNull SWRLVariable variable)
  {
    IRI variableIRI = variable.getIRI();
    String variablePrefixedName = getIRIResolver().iri2PrefixedName(variableIRI);

    return variablePrefixedName2VariableName(variablePrefixedName);
  }

  @NonNull public String swrlVariable2PrefixedName(@NonNull SWRLVariable variable)
  {
    IRI variableIRI = variable.getIRI();
    return getIRIResolver().iri2PrefixedName(variableIRI);
  }

  @NonNull public String variablePrefixedName2DRL(@NonNull String variablePrefixedName, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames)
  {
    if (previouslyEncounteredVariablePrefixedNames.contains(variablePrefixedName)) {
      return fieldName + "==" + variablePrefixedName2DRL(variablePrefixedName);
    } else {
      previouslyEncounteredVariablePrefixedNames.add(variablePrefixedName);
      return variablePrefixedName2DRL(variablePrefixedName) + ":" + fieldName;
    }
  }

  @NonNull public String variablePrefixedName2DRL(@NonNull String variablePrefixedName)
  {
    String variableName = variablePrefixedName2VariableName(variablePrefixedName);

    return variableName2DRL(variableName);
  }

  @NonNull private String variablePrefixedName2VariableName(@NonNull String variablePrefixedName)
  {
    if (variablePrefixedName.startsWith(":"))
      return variablePrefixedName.substring(1).replace(":", "_");
    else
      return variablePrefixedName.replace(":", "_");
  }

  @NonNull private String variableName2DRL(@NonNull String variableName)
  {
    return "$" + variableName;
  }
}
