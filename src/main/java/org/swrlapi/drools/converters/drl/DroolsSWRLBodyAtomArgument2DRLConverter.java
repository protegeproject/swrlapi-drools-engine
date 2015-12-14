package org.swrlapi.drools.converters.drl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLBodyAtomArgumentWithVariableNamesConverter;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

import java.util.Set;

/**
 * This class converts OWLAPI SWRL atom and built-in arguments to DRL clauses for use in Drools rules.
 *
 * @see org.semanticweb.owlapi.model.SWRLArgument
 * @see DroolsSWRLHeadAtomArgument2DRLConverter
 */
public class DroolsSWRLBodyAtomArgument2DRLConverter extends DroolsDRLConverterBase
    implements TargetRuleEngineSWRLBodyAtomArgumentWithVariableNamesConverter<String>
{
  public DroolsSWRLBodyAtomArgument2DRLConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
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
    String prefixedName = getIRIResolver().iri2PrefixedName(classArgument.getIRI());

    return addQuotes(prefixedName);
  }

  @NonNull @Override public String convert(@NonNull SWRLNamedIndividualBuiltInArgument individualArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(individualArgument.getIRI());

    return addQuotes(prefixedName);
  }

  @NonNull @Override public String convert(@NonNull SWRLObjectPropertyBuiltInArgument propertyArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

    return addQuotes(prefixedName);
  }

  @NonNull @Override public String convert(@NonNull SWRLDataPropertyBuiltInArgument propertyArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

    return addQuotes(prefixedName);
  }

  @NonNull @Override public String convert(@NonNull SWRLAnnotationPropertyBuiltInArgument propertyArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

    return addQuotes(prefixedName);
  }

  @NonNull @Override public String convert(@NonNull SWRLDatatypeBuiltInArgument datatypeArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(datatypeArgument.getIRI());

    return addQuotes(prefixedName);
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
      @NonNull String fieldName, @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames)
  {
    throw new TargetSWRLRuleEngineInternalException("unexpected call to convert a SQWRLCollectionBuiltInArgument");
  }

  @NonNull @Override public String convert(@NonNull SWRLVariableBuiltInArgument argument, @NonNull String fieldName,
      @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames)
  {
    String variablePrefixedName = getDroolsSWRLVariable2NameConverter().swrlVariable2PrefixedName(argument);

    if (previouslyEncounteredVariablePrefixedNames.contains(variablePrefixedName)) {
      return fieldName + "==" + getDroolsSWRLVariable2NameConverter().variablePrefixedName2DRL(variablePrefixedName);
    } else {
      previouslyEncounteredVariablePrefixedNames.add(variablePrefixedName);
      return getDroolsSWRLVariable2NameConverter().variablePrefixedName2DRL(variablePrefixedName) + ":" + fieldName;
    }
  }

  @NonNull @Override public String convert(@NonNull SWRLVariable argument, @NonNull String fieldName,
      @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames)
  {
    String variablePrefixedName = getDroolsSWRLVariable2NameConverter().swrlVariable2PrefixedName(argument);

    if (previouslyEncounteredVariablePrefixedNames.contains(variablePrefixedName)) {
      return fieldName + "==" + getDroolsSWRLVariable2NameConverter().variablePrefixedName2DRL(variablePrefixedName);
    } else {
      previouslyEncounteredVariablePrefixedNames.add(variablePrefixedName);
      return getDroolsSWRLVariable2NameConverter().variablePrefixedName2DRL(variablePrefixedName) + ":" + fieldName;
    }
  }

  @NonNull @Override public String convert(@NonNull SWRLIndividualArgument argument, @NonNull String fieldName,
      @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLLiteralArgument argument, @NonNull String fieldName,
      Set<@NonNull String> previouslyEncounteredVariablePrefixedNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLClassBuiltInArgument argument, @NonNull String fieldName,
      @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLNamedIndividualBuiltInArgument argument,
      @NonNull String fieldName, @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLObjectPropertyBuiltInArgument argument,
      @NonNull String fieldName, @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLDataPropertyBuiltInArgument argument, @NonNull String fieldName,
      @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLAnnotationPropertyBuiltInArgument argument,
      @NonNull String fieldName, @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLLiteralBuiltInArgument argument, @NonNull String fieldName,
      @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLDatatypeBuiltInArgument argument, @NonNull String fieldName,
      @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames)
  {
    return fieldName + "==" + convert(argument);
  }

  @NonNull private String addQuotes(@NonNull String s)
  {
    return "\"" + s + "\"";
  }

  @NonNull public String convert(@NonNull SWRLArgument argument)
  { // TODO Use visitor to get rid of instanceof SWRLArgumentVisitorEx, SWRLBuiltInArgumentVisitorEx
    if (argument instanceof SWRLVariable) {
      return convert((SWRLVariable)argument);
    } else if (argument instanceof SWRLIndividualArgument) {
      return convert((SWRLIndividualArgument)argument);
    } else if (argument instanceof SWRLLiteralArgument) {
      return convert((SWRLLiteralArgument)argument);
    } else if (argument instanceof SWRLVariableBuiltInArgument) {
      return convert((SWRLVariableBuiltInArgument)argument);
    } else if (argument instanceof SQWRLCollectionVariableBuiltInArgument) {
      return convert((SQWRLCollectionVariableBuiltInArgument)argument);
    } else if (argument instanceof SWRLClassBuiltInArgument) {
      return convert((SWRLClassBuiltInArgument)argument);
    } else if (argument instanceof SWRLNamedIndividualBuiltInArgument) {
      return convert((SWRLNamedIndividualBuiltInArgument)argument);
    } else if (argument instanceof SWRLLiteralBuiltInArgument) {
      return convert((SWRLLiteralBuiltInArgument)argument);
    } else if (argument instanceof SWRLObjectPropertyBuiltInArgument) {
      return convert((SWRLObjectPropertyBuiltInArgument)argument);
    } else if (argument instanceof SWRLDataPropertyBuiltInArgument) {
      return convert((SWRLDataPropertyBuiltInArgument)argument);
    } else if (argument instanceof SWRLAnnotationPropertyBuiltInArgument) {
      return convert((SWRLAnnotationPropertyBuiltInArgument)argument);
    } else if (argument instanceof SWRLDatatypeBuiltInArgument) {
      return convert((SWRLDatatypeBuiltInArgument)argument);
    } else
      throw new TargetSWRLRuleEngineInternalException(
          "unknown SWRL atom argument type " + argument.getClass().getCanonicalName());
  }

  @NonNull public String convert(@NonNull SWRLArgument argument, @NonNull String fieldName,
      @NonNull Set<@NonNull String> previouslyEncounteredVariablePrefixedNames)
  { // TODO Visitor to replace instanceof
    if (argument instanceof SQWRLCollectionVariableBuiltInArgument) {
      return convert((SQWRLCollectionVariableBuiltInArgument)argument, fieldName,
          previouslyEncounteredVariablePrefixedNames);
    } else if (argument instanceof SWRLVariableBuiltInArgument) {
      return convert((SWRLVariableBuiltInArgument)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
    } else if (argument instanceof SWRLClassBuiltInArgument) {
      return convert((SWRLClassBuiltInArgument)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
    } else if (argument instanceof SWRLNamedIndividualBuiltInArgument) {
      return convert((SWRLNamedIndividualBuiltInArgument)argument, fieldName,
          previouslyEncounteredVariablePrefixedNames);
    } else if (argument instanceof SWRLIndividualArgument) {
      return convert((SWRLIndividualArgument)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
    } else if (argument instanceof SWRLLiteralBuiltInArgument) {
      return convert((SWRLLiteralBuiltInArgument)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
    } else if (argument instanceof SWRLObjectPropertyBuiltInArgument) {
      return convert((SWRLObjectPropertyBuiltInArgument)argument, fieldName,
          previouslyEncounteredVariablePrefixedNames);
    } else if (argument instanceof SWRLDataPropertyBuiltInArgument) {
      return convert((SWRLDataPropertyBuiltInArgument)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
    } else if (argument instanceof SWRLAnnotationPropertyBuiltInArgument) {
      return convert((SWRLAnnotationPropertyBuiltInArgument)argument, fieldName,
          previouslyEncounteredVariablePrefixedNames);
    } else if (argument instanceof SWRLDatatypeBuiltInArgument) {
      return convert((SWRLDatatypeBuiltInArgument)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
    } else if (argument instanceof SWRLVariable) {
      return convert((SWRLVariable)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
    } else if (argument instanceof SWRLLiteralArgument) {
      return convert((SWRLLiteralArgument)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
    } else
      throw new TargetSWRLRuleEngineInternalException(
          "unknown SWRL argument type " + argument.getClass().getCanonicalName());
  }
}
