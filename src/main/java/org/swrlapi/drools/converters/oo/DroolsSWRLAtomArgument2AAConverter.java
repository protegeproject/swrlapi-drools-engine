package org.swrlapi.drools.converters.oo;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLAtomArgumentConverter;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.drools.owl.classes.C;
import org.swrlapi.drools.owl.dataranges.D;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.drools.owl.literals.L;
import org.swrlapi.drools.owl.properties.AP;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.drools.swrl.AA;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.drools.swrl.UBA;
import org.swrlapi.drools.swrl.VA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineNotImplementedFeatureException;

/**
 * This class converts OWLAPI SWRL arguments to their Drools representation.
 *
 * @see org.semanticweb.owlapi.model.SWRLArgument
 * @see org.swrlapi.drools.swrl.AA
 */
public class DroolsSWRLAtomArgument2AAConverter extends DroolsOOConverterBase
    implements TargetRuleEngineSWRLAtomArgumentConverter<AA>
{
  public DroolsSWRLAtomArgument2AAConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull @Override public VA convert(@NonNull SWRLVariable argument)
  {
    String variableName = getDroolsSWRLVariable2NameConverter().swrlVariable2DRLVariableName(argument);
    return new VA(variableName);
  }

  @NonNull @Override public I convert(@NonNull SWRLIndividualArgument argument)
  {
    OWLIndividual individual = argument.getIndividual();

    return getDroolsOWLIndividual2IConverter().convert(individual);
  }

  @NonNull @Override public L convert(@NonNull SWRLLiteralArgument argument)
  {
    return getDroolsOWLLiteral2LConverter().convert(argument.getLiteral());
  }

  @NonNull @Override public C convert(@NonNull SWRLClassBuiltInArgument argument)
  {
    OWLClass cls = argument.getOWLClass();

    return getDroolsOWLEntity2OEConverter().convert(cls);
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

  @NonNull @Override public DP convert(@NonNull SWRLDataPropertyBuiltInArgument argument)
  {
    OWLDataProperty property = argument.getOWLDataProperty();

    return getDroolsOWLEntity2OEConverter().convert(property);
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
      return new UBA(getDroolsSWRLVariable2NameConverter().swrlVariable2DRLVariableName(argument));
    else
      throw new TargetSWRLRuleEngineInternalException("expecting unbound argument, got bound argument " + argument);
  }

  @NonNull @Override public BA convert(SQWRLCollectionVariableBuiltInArgument argument)
  { // TODO ? Yes it does!?
    throw new TargetSWRLRuleEngineNotImplementedFeatureException("Drools does not support SQWRL collections yet");
  }
}
