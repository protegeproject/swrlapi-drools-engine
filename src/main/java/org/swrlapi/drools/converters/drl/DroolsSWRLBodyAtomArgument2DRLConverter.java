package org.swrlapi.drools.converters.drl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLBodyAtomArgumentWithVariableNamesConverter;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractorBase;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.drools.converters.id.DroolsOWLEntity2NameConverter;
import org.swrlapi.drools.converters.id.DroolsSWRLVariable2NameConverter;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

import java.util.Set;

/**
 * This class converts OWLAPI SWRL atom and built-in arguments to DRL clauses for use in Drools rules.
 *
 * @see org.semanticweb.owlapi.model.SWRLArgument
 * @see DroolsSWRLHeadAtomArgument2DRLConverter
 */
public class DroolsSWRLBodyAtomArgument2DRLConverter extends TargetRuleEngineExtractorBase
  implements TargetRuleEngineSWRLBodyAtomArgumentWithVariableNamesConverter<String>
{
  @NonNull private final DroolsSWRLVariable2NameConverter droolsSWRLVariable2NameConverter;
  @NonNull private final DroolsOWLLiteral2DRLConverter droolsOWLLiteral2DRLConverter;
  @NonNull private final DroolsOWLIndividual2DRLConverter droolsOWLIndividual2DRLConverter;
  @NonNull private final DroolsOWLEntity2NameConverter droolsOWLEntity2NameConverter;

  public DroolsSWRLBodyAtomArgument2DRLConverter(@NonNull SWRLRuleEngineBridge bridge,
    @NonNull DroolsSWRLVariable2NameConverter droolsSWRLVariable2NameConverter,
    @NonNull DroolsOWLLiteral2DRLConverter droolsOWLLiteral2DRLConverter,
    @NonNull DroolsOWLIndividual2DRLConverter droolsOWLIndividual2DRLConverter,
    @NonNull DroolsOWLEntity2NameConverter droolsOWLEntity2NameConverter)
  {
    super(bridge);
    this.droolsSWRLVariable2NameConverter = droolsSWRLVariable2NameConverter;
    this.droolsOWLLiteral2DRLConverter = droolsOWLLiteral2DRLConverter;
    this.droolsOWLIndividual2DRLConverter = droolsOWLIndividual2DRLConverter;
    this.droolsOWLEntity2NameConverter = droolsOWLEntity2NameConverter;
  }

  @NonNull @Override public String convert(@NonNull SWRLVariable variableArgument)
  {
    return getDroolsSWRLVariable2NameConverter().swrlVariable2DRL(variableArgument);
  }

  @NonNull @Override public String convert(@NonNull SWRLLiteralArgument argument)
  {
    return getDroolsOWLLiteral2DRLConverter().convert(argument.getLiteral());
  }

  @NonNull @Override public String convert(@NonNull SWRLIndividualArgument individualArgument)
  {
    OWLIndividual individual = individualArgument.getIndividual();

    return getDroolsOWLIndividual2DRLConverter().convert(individual);
  }

  @NonNull @Override public String convert(@NonNull SWRLVariableBuiltInArgument variableArgument)
  {
    return getDroolsSWRLVariable2NameConverter().swrlVariable2DRL(variableArgument);
  }

  @NonNull @Override public String convert(@NonNull SWRLClassBuiltInArgument classArgument)
  {
    return addQuotes(getDroolsOWLEntity2NameConverter().convert(classArgument.getOWLClass()));
  }

  @NonNull @Override public String convert(@NonNull SWRLNamedIndividualBuiltInArgument namedIndividualArgument)
  {
    return addQuotes(getDroolsOWLEntity2NameConverter().convert(namedIndividualArgument.getOWLNamedIndividual()));
  }

  @NonNull @Override public String convert(@NonNull SWRLObjectPropertyBuiltInArgument propertyArgument)
  {
    return addQuotes(getDroolsOWLEntity2NameConverter().convert(propertyArgument.getOWLObjectProperty()));
  }

  @NonNull @Override public String convert(@NonNull SWRLDataPropertyBuiltInArgument propertyArgument)
  {
    return addQuotes(getDroolsOWLEntity2NameConverter().convert(propertyArgument.getOWLDataProperty()));
  }

  @NonNull @Override public String convert(@NonNull SWRLAnnotationPropertyBuiltInArgument propertyArgument)
  {
    return addQuotes(getDroolsOWLEntity2NameConverter().convert(propertyArgument.getOWLAnnotationProperty()));
  }

  @NonNull @Override public String convert(@NonNull SWRLDatatypeBuiltInArgument datatypeArgument)
  {
    return addQuotes(getDroolsOWLEntity2NameConverter().convert(datatypeArgument.getOWLDatatype()));
  }

  @NonNull @Override public String convert(@NonNull SWRLLiteralBuiltInArgument argument)
  {
    return getDroolsOWLLiteral2DRLConverter().convert(argument.getLiteral());
  }

  @NonNull @Override public String convert(@NonNull SQWRLCollectionVariableBuiltInArgument argument)
  {
    throw new TargetSWRLRuleEngineInternalException("unexpected call to convert a SQWRLCollectionBuiltInArgument");
  }

  @NonNull @Override public String convert(@NonNull SQWRLCollectionVariableBuiltInArgument argument,
    @NonNull String fieldName, @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    throw new TargetSWRLRuleEngineInternalException("unexpected call to convert a SQWRLCollectionBuiltInArgument");
  }

  @NonNull @Override public String convert(@NonNull SWRLVariableBuiltInArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    String variableName = getDroolsSWRLVariable2NameConverter().swrlVariable2VariableName(argument);

    if (previouslyEncounteredVariableNames.contains(variableName)) {
      return fieldName + "==" + getDroolsSWRLVariable2NameConverter().variableName2DRL(variableName);
    } else {
      previouslyEncounteredVariableNames.add(variableName);
      return getDroolsSWRLVariable2NameConverter().variableName2DRL(variableName) + ":" + fieldName;
    }
  }

  @NonNull @Override public String convert(@NonNull SWRLVariable argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    String variableName = getDroolsSWRLVariable2NameConverter().swrlVariable2VariableName(argument);

    if (previouslyEncounteredVariableNames.contains(variableName)) {
      return fieldName + "==" + getDroolsSWRLVariable2NameConverter().variableName2DRL(variableName);
    } else {
      previouslyEncounteredVariableNames.add(variableName);
      return getDroolsSWRLVariable2NameConverter().variableName2DRL(variableName) + ":" + fieldName;
    }
  }

  @NonNull @Override public String convert(@NonNull SWRLIndividualArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLLiteralArgument argument, @NonNull String fieldName,
    Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLClassBuiltInArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLNamedIndividualBuiltInArgument argument,
    @NonNull String fieldName, @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLObjectPropertyBuiltInArgument argument,
    @NonNull String fieldName, @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLDataPropertyBuiltInArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLAnnotationPropertyBuiltInArgument argument,
    @NonNull String fieldName, @NonNull Set<@NonNull String> previouslyEncounteredNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLLiteralBuiltInArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLDatatypeBuiltInArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull private String addQuotes(@NonNull String s)
  {
    return "\"" + s + "\"";
  }

  @NonNull public String convert(@NonNull SWRLArgument argument, @NonNull String fieldName,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  { // TODO Visitor to replace instanceof
    if (argument instanceof SQWRLCollectionVariableBuiltInArgument) {
      return convert((SQWRLCollectionVariableBuiltInArgument)argument, fieldName, previouslyEncounteredVariableNames);
    } else if (argument instanceof SWRLVariableBuiltInArgument) {
      return convert((SWRLVariableBuiltInArgument)argument, fieldName, previouslyEncounteredVariableNames);
    } else if (argument instanceof SWRLClassBuiltInArgument) {
      return convert((SWRLClassBuiltInArgument)argument, fieldName, previouslyEncounteredVariableNames);
    } else if (argument instanceof SWRLNamedIndividualBuiltInArgument) {
      return convert((SWRLNamedIndividualBuiltInArgument)argument, fieldName, previouslyEncounteredVariableNames);
    } else if (argument instanceof SWRLIndividualArgument) {
      return convert((SWRLIndividualArgument)argument, fieldName, previouslyEncounteredVariableNames);
    } else if (argument instanceof SWRLLiteralBuiltInArgument) {
      return convert((SWRLLiteralBuiltInArgument)argument, fieldName, previouslyEncounteredVariableNames);
    } else if (argument instanceof SWRLObjectPropertyBuiltInArgument) {
      return convert((SWRLObjectPropertyBuiltInArgument)argument, fieldName, previouslyEncounteredVariableNames);
    } else if (argument instanceof SWRLDataPropertyBuiltInArgument) {
      return convert((SWRLDataPropertyBuiltInArgument)argument, fieldName, previouslyEncounteredVariableNames);
    } else if (argument instanceof SWRLAnnotationPropertyBuiltInArgument) {
      return convert((SWRLAnnotationPropertyBuiltInArgument)argument, fieldName, previouslyEncounteredVariableNames);
    } else if (argument instanceof SWRLDatatypeBuiltInArgument) {
      return convert((SWRLDatatypeBuiltInArgument)argument, fieldName, previouslyEncounteredVariableNames);
    } else if (argument instanceof SWRLVariable) {
      return convert((SWRLVariable)argument, fieldName, previouslyEncounteredVariableNames);
    } else if (argument instanceof SWRLLiteralArgument) {
      return convert((SWRLLiteralArgument)argument, fieldName, previouslyEncounteredVariableNames);
    } else
      throw new TargetSWRLRuleEngineInternalException(
        "unknown SWRL argument type " + argument.getClass().getCanonicalName());
  }

  @NonNull private DroolsSWRLVariable2NameConverter getDroolsSWRLVariable2NameConverter()
  {
    return this.droolsSWRLVariable2NameConverter;
  }

  @NonNull private DroolsOWLLiteral2DRLConverter getDroolsOWLLiteral2DRLConverter()
  {
    return this.droolsOWLLiteral2DRLConverter;
  }

  @NonNull private DroolsOWLIndividual2DRLConverter getDroolsOWLIndividual2DRLConverter()
  {
    return this.droolsOWLIndividual2DRLConverter;
  }

  @NonNull private DroolsOWLEntity2NameConverter getDroolsOWLEntity2NameConverter()
  {
    return this.droolsOWLEntity2NameConverter;
  }
}
