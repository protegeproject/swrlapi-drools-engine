package org.swrlapi.drools.converters;

import checkers.nullness.quals.NonNull;
import com.google.common.base.Strings;
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

/**
 * This class converts SWRLAPI SWRL built-in arguments to DRL clauses for use in rules.
 *
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 */
public class DroolsSWRLBuiltInArgument2DRLConverter extends DroolsConverterBase implements
TargetRuleEngineSWRLBuiltInArgumentConverter<String>, SWRLBuiltInArgumentVisitorEx<String>
{
  public DroolsSWRLBuiltInArgument2DRLConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull public String convert(@NonNull SWRLBuiltInArgument argument)
  {
    return argument.accept(this);
  }

  @NonNull @Override
  public String convert(@NonNull SWRLVariableBuiltInArgument argument)
  {
    if (argument.isUnbound())
      return "new UBA(\"" + getDroolsSWRLVariableConverter().swrlVariable2VariableName(argument) + "\")";
    else
      return getDroolsSWRLVariableConverter().swrlVariable2DRL(argument);
  }

  @NonNull @Override
  public String convert(@NonNull SWRLClassBuiltInArgument classArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(classArgument.getIRI());

    return "new C(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override
  public String convert(@NonNull SWRLNamedIndividualBuiltInArgument individualArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(individualArgument.getIRI());

    return "new I(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override
  public String convert(@NonNull SWRLObjectPropertyBuiltInArgument propertyArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

    return "new OP(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override
  public String convert(@NonNull SWRLDataPropertyBuiltInArgument propertyArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

    return "new DP(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override
  public String convert(@NonNull SWRLAnnotationPropertyBuiltInArgument propertyArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

    return "new AP(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override
  public String convert(@NonNull SWRLDatatypeBuiltInArgument datatypeArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(datatypeArgument.getIRI());

    return "new D(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override
  public String convert(@NonNull SWRLLiteralBuiltInArgument argument)
  {
    return getDroolsOWLLiteral2DRLConverter().convert(argument.getLiteral());
  }

  @NonNull @Override
  public String convert(@NonNull SQWRLCollectionVariableBuiltInArgument argument)
  { // TODO This is not true!
    throw new TargetSWRLRuleEngineNotImplementedFeatureException(
        "SQWRL collection built-in arguments not yet implemented");
  }

  @NonNull private String addQuotes(@NonNull String s)
  {
    return "\"" + s + "\"";
  }

  @NonNull @Override
  public String visit(@NonNull SWRLClassBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override
  public String visit(@NonNull SWRLNamedIndividualBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override
  public String visit(@NonNull SWRLObjectPropertyBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override
  public String visit(@NonNull SWRLDataPropertyBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override
  public String visit(@NonNull SWRLAnnotationPropertyBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override
  public String visit(@NonNull SWRLDatatypeBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override
  public String visit(@NonNull SWRLLiteralBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override
  public String visit(@NonNull SWRLVariableBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override
  public String visit(@NonNull SWRLMultiValueVariableBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override
  public String visit(SQWRLCollectionVariableBuiltInArgument argument)
  {
    return convert(argument);
  }
}
