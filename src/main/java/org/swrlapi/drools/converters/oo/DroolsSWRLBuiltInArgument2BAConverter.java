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
  public DroolsSWRLBuiltInArgument2BAConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull public BA convert(@NonNull SWRLBuiltInArgument argument)
  {
    return argument.accept(this);
  }

  @NonNull @Override public C convert(@NonNull SWRLClassBuiltInArgument argument)
  {
    OWLClass cls = argument.getOWLClass();

    return getDroolsOWLEntity2OEConverter().convert(cls);
  }

  @NonNull @Override public CE convert(SWRLClassExpressionBuiltInArgument argument)
  {
    OWLClassExpression ce = argument.getOWLClassExpression();
    String ceid = getOWLObjectResolver().resolveOWLClassExpression2ID(ce);

    throw new RuntimeException("create CE from class expression built-in argument"); // TODO
  }

  @NonNull @Override public I convert(@NonNull SWRLNamedIndividualBuiltInArgument argument)
  {
    OWLNamedIndividual individual = argument.getOWLNamedIndividual();

    return getDroolsOWLEntity2OEConverter().convert(individual);
  }

  @NonNull @Override public OP convert(@NonNull SWRLObjectPropertyBuiltInArgument argument)
  {
    OWLObjectProperty property = argument.getOWLObjectProperty();

    return getDroolsOWLEntity2OEConverter().convert(property);
  }

  @NonNull @Override public OPE convert(SWRLObjectPropertyExpressionBuiltInArgument argument)
  {
    OWLObjectPropertyExpression pe = argument.getOWLObjectPropertyExpression();

    throw new RuntimeException("create OPE from object property expression built-in argument");  // TODO
  }

  @NonNull @Override public DP convert(@NonNull SWRLDataPropertyBuiltInArgument argument)
  {
    OWLDataProperty property = argument.getOWLDataProperty();

    return getDroolsOWLEntity2OEConverter().convert(property);
  }

  @NonNull @Override public DPE convert(SWRLDataPropertyExpressionBuiltInArgument argument)
  {
    OWLDataPropertyExpression pe = argument.getOWLDataPropertyExpression();
    throw new RuntimeException("create OPE from object property expression built-in argument");  // TODO
  }

  @NonNull @Override public AP convert(@NonNull SWRLAnnotationPropertyBuiltInArgument argument)
  {
    OWLAnnotationProperty property = argument.getOWLAnnotationProperty();

    return getDroolsOWLEntity2OEConverter().convert(property);
  }

  @NonNull @Override public D convert(@NonNull SWRLDatatypeBuiltInArgument argument)
  {
    OWLDatatype datatype = argument.getOWLDatatype();

    return getDroolsOWLEntity2OEConverter().convert(datatype);
  }

  @NonNull @Override public L convert(@NonNull SWRLLiteralBuiltInArgument argument)
  {
    OWLLiteral literal = argument.getLiteral();

    return getDroolsOWLLiteral2LConverter().convert(literal);
  }

  @NonNull @Override public UBA convert(@NonNull SWRLVariableBuiltInArgument argument)
  {
    if (argument.isUnbound())
      return new UBA(getDroolsSWRLVariable2NameConverter().swrlVariable2VariableName(argument));
    else
      throw new TargetSWRLRuleEngineInternalException("expecting unbound argument, got bound argument " + argument);
  }

  @NonNull @Override public SQWRLC convert(@NonNull SQWRLCollectionVariableBuiltInArgument argument)
  {
    return new SQWRLC(getDroolsSWRLVariable2NameConverter().swrlVariable2DRLVariableName(argument),
      argument.getQueryName(), argument.getCollectionName(), argument.getGroupID());
  }

  @NonNull @Override public C visit(@NonNull SWRLClassBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public CE visit(SWRLClassExpressionBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public I visit(@NonNull SWRLNamedIndividualBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public OP visit(@NonNull SWRLObjectPropertyBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public OPE visit(@NonNull SWRLObjectPropertyExpressionBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public DP visit(@NonNull SWRLDataPropertyBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public DPE visit(SWRLDataPropertyExpressionBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public AP visit(@NonNull SWRLAnnotationPropertyBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public D visit(@NonNull SWRLDatatypeBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public L visit(@NonNull SWRLLiteralBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public UBA visit(@NonNull SWRLVariableBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public UBA visit(@NonNull SWRLMultiValueVariableBuiltInArgument argument)
  {
    return convert(argument);
  }

  @NonNull @Override public SQWRLC visit(@NonNull SQWRLCollectionVariableBuiltInArgument argument)
  {
    return convert(argument);
  }
}
