package org.swrlapi.drools.converters.drl;

import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLBuiltInArgumentConverter;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.exceptions.TargetSWRLRuleEngineNotImplementedFeatureException;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * This class converts SWRLAPI SWRL built-in arguments to DRL clauses for use in rules.
 *
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 */
public class DroolsSWRLBuiltInArgument2DRLConverter extends DroolsDRLConverterBase
    implements TargetRuleEngineSWRLBuiltInArgumentConverter<String>, SWRLBuiltInArgumentVisitorEx<String>
{
  public DroolsSWRLBuiltInArgument2DRLConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull public String convert(@NonNull SWRLBuiltInArgument argument)
  {
    return argument.accept(this);
  }

  @NonNull @Override public String convert(@NonNull SWRLVariableBuiltInArgument argument)
  {
    if (argument.isUnbound())
      return "new UBA(" + addQuotes(getDroolsSWRLVariable2NameConverter().swrlVariable2DRLVariableName(argument)) + ")";
    else
      return getDroolsSWRLVariable2NameConverter().swrlVariable2DRL(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLClassBuiltInArgument argument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(argument.getIRI());

    return "new C(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override public String convert(@NonNull SWRLNamedIndividualBuiltInArgument argument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(argument.getIRI());

    return "new I(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override public String convert(@NonNull SWRLObjectPropertyBuiltInArgument argument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(argument.getIRI());

    return "new OP(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override public String convert(@NonNull SWRLDataPropertyBuiltInArgument argument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(argument.getIRI());

    return "new DP(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override public String convert(@NonNull SWRLAnnotationPropertyBuiltInArgument argument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(argument.getIRI());

    return "new AP(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override public String convert(@NonNull SWRLDatatypeBuiltInArgument argument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(argument.getIRI());

    return "new D(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override public String convert(@NonNull SWRLLiteralBuiltInArgument argument)
  {
    return getDroolsOWLLiteral2DRLConverter().convert(argument.getLiteral());
  }

  @NonNull @Override public String convert(@NonNull SQWRLCollectionVariableBuiltInArgument argument)
  { // TODO This is not true!
    throw new TargetSWRLRuleEngineNotImplementedFeatureException(
        "SQWRL collection built-in arguments not yet implemented");
  }

  @NonNull private String addQuotes(@NonNull String s)
  {
    return "\"" + s + "\"";
  }

  @NonNull @Override public String visit(@NonNull SWRLClassBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public String visit(@NonNull SWRLNamedIndividualBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public String visit(@NonNull SWRLObjectPropertyBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public String visit(@NonNull SWRLDataPropertyBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public String visit(@NonNull SWRLAnnotationPropertyBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public String visit(@NonNull SWRLDatatypeBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public String visit(@NonNull SWRLLiteralBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public String visit(@NonNull SWRLVariableBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public String visit(@NonNull SWRLMultiValueVariableBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public String visit(SQWRLCollectionVariableBuiltInArgument argument)
  {
    return convert(argument);
  }
}
