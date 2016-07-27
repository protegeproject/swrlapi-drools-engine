package org.swrlapi.drools.converters.drl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLBuiltInArgumentConverter;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyExpressionBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.drools.core.DroolsNames;
import org.swrlapi.exceptions.TargetSWRLRuleEngineNotImplementedFeatureException;

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
      return "new " + DroolsNames.UNBOUND_ARGUMENT_CLASS_NAME + "(" + addQuotes(
        getDroolsSWRLVariable2NameConverter().swrlVariable2VariableName(argument)) + ")";
    else
      return getDroolsSWRLVariable2NameConverter().swrlVariable2DRL(argument);
  }

  @NonNull @Override public String convert(@NonNull SWRLClassBuiltInArgument argument)
  {
    String prefixedName = iri2PrefixedName(argument.getIRI());

    return "new " + DroolsNames.CLASS_CLASS_NAME + "(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override public String convert(@NonNull SWRLClassExpressionBuiltInArgument argument)
  {
    OWLClassExpression ce = argument.getOWLClassExpression();
    String ceid = getOWLObjectResolver().resolveOWLClassExpression2ID(ce);

    return "new " + DroolsNames.CLASS_EXPRESSION_CLASS_NAME + "(" + addQuotes(ceid) + ")";
  }

  @NonNull @Override public String convert(@NonNull SWRLNamedIndividualBuiltInArgument argument)
  {
    String prefixedName = iri2PrefixedName(argument.getIRI());

    return "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override public String convert(@NonNull SWRLObjectPropertyExpressionBuiltInArgument argument)
  {
    OWLObjectPropertyExpression pe = argument.getOWLObjectPropertyExpression();
    String peid = getOWLObjectResolver().resolveOWLObjectPropertyExpression2ID(pe);

    return "new " + DroolsNames.OBJECT_PROPERTY_EXPRESSION_CLASS_NAME + "(" + addQuotes(peid) + ")";
  }

  @NonNull @Override public String convert(@NonNull SWRLObjectPropertyBuiltInArgument argument)
  {
    String prefixedName = iri2PrefixedName(argument.getIRI());

    return "new " + DroolsNames.OBJECT_PROPERTY_CLASS_NAME + "(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override public String convert(@NonNull SWRLDataPropertyBuiltInArgument argument)
  {
    String prefixedName = iri2PrefixedName(argument.getIRI());

    return "new " + DroolsNames.DATA_PROPERTY_CLASS_NAME + "(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override public String convert(@NonNull SWRLDataPropertyExpressionBuiltInArgument argument)
  {
    OWLDataPropertyExpression pe = argument.getOWLDataPropertyExpression();
    String peid = getOWLObjectResolver().resolveOWLDataPropertyExpression2ID(pe);

    return "new " + DroolsNames.DATA_PROPERTY_EXPRESSION_CLASS_NAME + "(" + addQuotes(peid) + ")";
  }

  @NonNull @Override public String convert(@NonNull SWRLAnnotationPropertyBuiltInArgument argument)
  {
    String prefixedName = iri2PrefixedName(argument.getIRI());

    return "new " + DroolsNames.ANNOTATION_PROPERTY_CLASS_NAME + "(" + addQuotes(prefixedName) + ")";
  }

  @NonNull @Override public String convert(@NonNull SWRLDatatypeBuiltInArgument argument)
  {
    String prefixedName = iri2PrefixedName(argument.getIRI());

    return "new " + DroolsNames.DATATYPE_CLASS_NAME + "(" + addQuotes(prefixedName) + ")";
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

  @NonNull @Override public String visit(SWRLClassExpressionBuiltInArgument argument)
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

  @Override public String visit(SWRLObjectPropertyExpressionBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public String visit(@NonNull SWRLDataPropertyBuiltInArgument argument)
  {
    return convert(argument);
  }

  @Override public String visit(SWRLDataPropertyExpressionBuiltInArgument argument)
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
