package org.swrlapi.drools.converters.oo;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
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
import org.swrlapi.drools.owl.classes.C;
import org.swrlapi.drools.owl.classes.CE;
import org.swrlapi.drools.owl.dataranges.D;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.drools.owl.literals.L;
import org.swrlapi.drools.owl.properties.AP;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.DPE;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.drools.owl.properties.OPE;
import org.swrlapi.drools.sqwrl.SQWRLC;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.drools.swrl.UBA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

/**
 * This class converts OWLAPI SWRL built-in arguments to Drools {@link org.swrlapi.drools.swrl.BA} instances.
 *
 * @see org.swrlapi.drools.swrl.BA
 */
public class DroolsSWRLBuiltInArgument2BAConverter extends DroolsOOConverterBase
  implements TargetRuleEngineSWRLBuiltInArgumentConverter<BA>, SWRLBuiltInArgumentVisitorEx<BA>
{
  private final @NonNull DroolsOWLClassExpressionHandler droolsOWLClassExpressionHandler;

  public DroolsSWRLBuiltInArgument2BAConverter(@NonNull SWRLRuleEngineBridge bridge,
    @NonNull DroolsOWLClassExpressionHandler droolsOWLClassExpressionHandler)
  {
    super(bridge);
    this.droolsOWLClassExpressionHandler = droolsOWLClassExpressionHandler;
  }

  public @NonNull BA convert(@NonNull SWRLBuiltInArgument argument)
  {
    return argument.accept(this);
  }

  @Override public @NonNull C convert(@NonNull SWRLClassBuiltInArgument argument)
  {
    OWLClass cls = argument.getOWLClass();

    return getDroolsOWLEntity2OEConverter().convert(cls);
  }

  @Override public @NonNull CE convert(SWRLClassExpressionBuiltInArgument argument)
  {
    OWLClassExpression classExpression = argument.getOWLClassExpression();

    return getDroolsOWLClassExpressionHandler().convert(classExpression);
  }

  @Override public @NonNull I convert(@NonNull SWRLNamedIndividualBuiltInArgument argument)
  {
    OWLNamedIndividual individual = argument.getOWLNamedIndividual();

    return getDroolsOWLEntity2OEConverter().convert(individual);
  }

  @Override public @NonNull OP convert(@NonNull SWRLObjectPropertyBuiltInArgument argument)
  {
    OWLObjectProperty property = argument.getOWLObjectProperty();

    return getDroolsOWLEntity2OEConverter().convert(property);
  }

  @Override public @NonNull OPE convert(SWRLObjectPropertyExpressionBuiltInArgument argument)
  {
    OWLObjectPropertyExpression pe = argument.getOWLObjectPropertyExpression();

    throw new RuntimeException("create OPE from object property expression built-in argument");  // TODO
  }

  @Override public @NonNull DP convert(@NonNull SWRLDataPropertyBuiltInArgument argument)
  {
    OWLDataProperty property = argument.getOWLDataProperty();

    return getDroolsOWLEntity2OEConverter().convert(property);
  }

  @Override public @NonNull DPE convert(SWRLDataPropertyExpressionBuiltInArgument argument)
  {
    OWLDataPropertyExpression pe = argument.getOWLDataPropertyExpression();
    throw new RuntimeException("create OPE from object property expression built-in argument");  // TODO
  }

  @Override public @NonNull AP convert(@NonNull SWRLAnnotationPropertyBuiltInArgument argument)
  {
    OWLAnnotationProperty property = argument.getOWLAnnotationProperty();

    return getDroolsOWLEntity2OEConverter().convert(property);
  }

  @Override public @NonNull D convert(@NonNull SWRLDatatypeBuiltInArgument argument)
  {
    OWLDatatype datatype = argument.getOWLDatatype();

    return getDroolsOWLEntity2OEConverter().convert(datatype);
  }

  @Override public @NonNull L convert(@NonNull SWRLLiteralBuiltInArgument argument)
  {
    OWLLiteral literal = argument.getLiteral();

    return getDroolsOWLLiteral2LConverter().convert(literal);
  }

  @Override public @NonNull UBA convert(@NonNull SWRLVariableBuiltInArgument argument)
  {
    if (argument.isUnbound())
      return new UBA(getDroolsSWRLVariable2NameConverter().swrlVariable2VariableName(argument));
    else
      throw new TargetSWRLRuleEngineInternalException("expecting unbound argument, got bound argument " + argument);
  }

  @Override public @NonNull SQWRLC convert(@NonNull SQWRLCollectionVariableBuiltInArgument argument)
  {
    return new SQWRLC(getDroolsSWRLVariable2NameConverter().swrlVariable2DRLVariableName(argument),
      argument.getQueryName(), argument.getCollectionName(), argument.getGroupID());
  }

  @Override public @NonNull C visit(@NonNull SWRLClassBuiltInArgument argument)
  {
    return convert(argument);
  }

  @Override public @NonNull CE visit(SWRLClassExpressionBuiltInArgument argument)
  {
    return convert(argument);
  }

  @Override public @NonNull I visit(@NonNull SWRLNamedIndividualBuiltInArgument argument)
  {
    return convert(argument);
  }

  @Override public @NonNull OP visit(@NonNull SWRLObjectPropertyBuiltInArgument argument)
  {
    return convert(argument);
  }

  @Override public @NonNull OPE visit(@NonNull SWRLObjectPropertyExpressionBuiltInArgument argument)
  {
    return convert(argument);
  }

  @Override public @NonNull DP visit(@NonNull SWRLDataPropertyBuiltInArgument argument)
  {
    return convert(argument);
  }

  @Override public @NonNull DPE visit(SWRLDataPropertyExpressionBuiltInArgument argument)
  {
    return convert(argument);
  }

  @Override public @NonNull AP visit(@NonNull SWRLAnnotationPropertyBuiltInArgument argument)
  {
    return convert(argument);
  }

  @Override public @NonNull D visit(@NonNull SWRLDatatypeBuiltInArgument argument)
  {
    return convert(argument);
  }

  @Override public @NonNull L visit(@NonNull SWRLLiteralBuiltInArgument argument)
  {
    return convert(argument);
  }

  @Override public @NonNull UBA visit(@NonNull SWRLVariableBuiltInArgument argument)
  {
    return convert(argument);
  }

  @Override public @NonNull UBA visit(@NonNull SWRLMultiValueVariableBuiltInArgument argument)
  {
    return convert(argument);
  }

  @Override public @NonNull SQWRLC visit(@NonNull SQWRLCollectionVariableBuiltInArgument argument)
  {
    return convert(argument);
  }

  private @NonNull DroolsOWLClassExpressionHandler getDroolsOWLClassExpressionHandler()
  {
    return this.droolsOWLClassExpressionHandler;
  }
}
