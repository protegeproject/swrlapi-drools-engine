package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLHeadAtomArgumentConverter;
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
import org.swrlapi.exceptions.TargetSWRLRuleEngineNotImplementedFeatureException;

/**
 * This class converts OWLAPI SWRL head atom argument to DRL clauses for use in rules.
 *
 * @see org.semanticweb.owlapi.model.SWRLArgument
 * @see org.swrlapi.drools.converters.DroolsSWRLBodyAtomArgument2DRLConverter
 */
public class DroolsSWRLHeadAtomArgument2DRLConverter extends DroolsConverterBase implements
TargetRuleEngineSWRLHeadAtomArgumentConverter<String>
{
  public DroolsSWRLHeadAtomArgument2DRLConverter(SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @Override
  public String convert(SWRLVariable variableArgument)
  {
    return getDroolsSWRLVariableConverter().swrlVariable2DRL(variableArgument);
  }

  @Override
  public String convert(SWRLIndividualArgument individualArgument)
  {
    OWLIndividual individual = individualArgument.getIndividual();

    if (individual.isNamed()) {
      IRI iri = individual.asOWLNamedIndividual().getIRI();
      String prefixedName = getIRIResolver().iri2PrefixedName(iri);
      return addQuotes(prefixedName);
    } else if (individual.isAnonymous()) {
      String id = individual.asOWLAnonymousIndividual().toStringID();
      return addQuotes(id);
    } else
      throw new TargetSWRLRuleEngineInternalException("unknown OWL individual type "
          + individual.getClass().getCanonicalName() + " passed as a " + SWRLIndividualArgument.class.getName());
  }

  @Override
  public String convert(SWRLLiteralArgument argument)
  {
    return getDroolsOWLLiteral2DRLConverter().convert(argument.getLiteral());
  }

  @Override
  public String convert(SWRLVariableBuiltInArgument variableArgument)
  {
    return getDroolsSWRLVariableConverter().swrlVariable2DRL(variableArgument);
  }

  @Override
  public String convert(SWRLClassBuiltInArgument classArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(classArgument.getIRI());

    return addQuotes(prefixedName);
  }

  @Override
  public String convert(SWRLNamedIndividualBuiltInArgument individualArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(individualArgument.getIRI());

    return addQuotes(prefixedName);
  }

  @Override
  public String convert(SWRLObjectPropertyBuiltInArgument propertyArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

    return addQuotes(prefixedName);
  }

  @Override
  public String convert(SWRLDataPropertyBuiltInArgument propertyArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

    return addQuotes(prefixedName);
  }

  @Override
  public String convert(SWRLAnnotationPropertyBuiltInArgument propertyArgument)
  {
    String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

    return addQuotes(prefixedName);
  }

  @Override
  public String convert(SWRLDatatypeBuiltInArgument datatypeArgument)
  {
    return getIRIResolver().iri2PrefixedName(datatypeArgument.getIRI());
  }

  @Override
  public String convert(SWRLLiteralBuiltInArgument argument)
  {
    return getDroolsOWLLiteral2DRLConverter().convert(argument.getLiteral());
  }

  @Override
  public String convert(SQWRLCollectionVariableBuiltInArgument argument)
  {
    throw new TargetSWRLRuleEngineNotImplementedFeatureException(
        "SQWRL collections can not be referenced in a rule head");
  }

  public String convert(SWRLArgument argument)
  { // TODO Use visitor to replace instanceof: define SWRLArgumentVisitorEx in OWLAPI and then
    // SWRLBuiltInArgumentVisitorEx
    if (argument instanceof SQWRLCollectionVariableBuiltInArgument) {
      return convert((SQWRLCollectionVariableBuiltInArgument)argument);
    } else if (argument instanceof SWRLVariableBuiltInArgument) {
      return convert((SWRLVariableBuiltInArgument)argument);
    } else if (argument instanceof SWRLLiteralBuiltInArgument) {
      return convert((SWRLLiteralBuiltInArgument)argument);
    } else if (argument instanceof SWRLClassBuiltInArgument) {
      return convert((SWRLClassBuiltInArgument)argument);
    } else if (argument instanceof SWRLNamedIndividualBuiltInArgument) {
      return convert((SWRLNamedIndividualBuiltInArgument)argument);
    } else if (argument instanceof SWRLObjectPropertyBuiltInArgument) {
      return convert((SWRLObjectPropertyBuiltInArgument)argument);
    } else if (argument instanceof SWRLDataPropertyBuiltInArgument) {
      return convert((SWRLDataPropertyBuiltInArgument)argument);
    } else if (argument instanceof SWRLAnnotationPropertyBuiltInArgument) {
      return convert((SWRLAnnotationPropertyBuiltInArgument)argument);
    } else if (argument instanceof SWRLDatatypeBuiltInArgument) {
      return convert((SWRLDatatypeBuiltInArgument)argument);
    } else if (argument instanceof SWRLVariable) {
      return convert((SWRLVariable)argument);
    } else if (argument instanceof SWRLLiteralArgument) {
      return convert((SWRLLiteralArgument)argument);
    } else if (argument instanceof SWRLIndividualArgument) {
      return convert((SWRLIndividualArgument)argument);
    } else
      throw new TargetSWRLRuleEngineInternalException("unknown SWRL atom argument type "
          + argument.getClass().getCanonicalName());
  }

  private String addQuotes(String s)
  {
    return "\"" + s + "\"";
  }
}
