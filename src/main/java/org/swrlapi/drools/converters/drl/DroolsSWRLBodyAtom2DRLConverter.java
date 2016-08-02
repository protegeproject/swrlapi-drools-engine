package org.swrlapi.drools.converters.drl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLBodyAtomWithVariableNamesConverter;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.drools.converters.id.DroolsOWLDataRangeHandler;
import org.swrlapi.drools.converters.id.DroolsOWLEntity2NameConverter;
import org.swrlapi.drools.converters.id.DroolsSWRLVariable2NameConverter;
import org.swrlapi.drools.converters.oo.DroolsOWLClassExpressionHandler;
import org.swrlapi.drools.converters.oo.DroolsOWLPropertyExpressionHandler;
import org.swrlapi.drools.core.DroolsNames;
import org.swrlapi.drools.core.DroolsSWRLBuiltInInvoker;
import org.swrlapi.drools.sqwrl.VPATH;
import org.swrlapi.drools.swrl.BAP;
import org.swrlapi.drools.swrl.BAVNs;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineNotImplementedFeatureException;

import java.util.Set;

/**
 * This class converts OWLAPI SWRL body atoms to a their DRL representation for use in rules.
 * <p/>
 * Head and body atoms are converted differently - hence the need for two converters. Body atom converters must also
 * know the variables defined by previous atoms because a different syntax is required in DRL for declaring a variable
 * vs. referring to one that is already declared. In the head, all variables are guaranteed to have already been
 * declared in SWRL.
 *
 * @see org.semanticweb.owlapi.model.SWRLAtom
 */
public class DroolsSWRLBodyAtom2DRLConverter extends DroolsDRLConverterBase
  implements TargetRuleEngineSWRLBodyAtomWithVariableNamesConverter<String>
{
  @NonNull private final DroolsSWRLBodyAtomArgument2DRLConverter droolsBodyAtomArgument2DRLConverter;
  @NonNull private final DroolsSWRLBuiltInArgument2DRLConverter droolsBuiltInArgument2DRLConverter;
  @NonNull private final DroolsOWLClassExpressionHandler droolsOWLClassExpressionHandler;
  @NonNull private final DroolsOWLPropertyExpressionHandler droolsOWLPropertyExpressionHandler;
  @NonNull private final DroolsOWLDataRangeHandler droolsOWLDataRangeHandler;
  @NonNull private final DroolsSWRLVariable2NameConverter droolsSWRLVariable2NameConverter;
  @NonNull private final DroolsOWLLiteral2DRLConverter droolsOWLLiteral2DRLConverter;
  @NonNull private final DroolsOWLIndividual2DRLConverter droolsOWLIndividual2DRLConverter;
  @NonNull private final DroolsOWLEntity2NameConverter droolsOWLEntity2NameConverter;

  private int builtInIndexInBody; // Each built-in atom in the body gets a unique index, starting at 0

  public DroolsSWRLBodyAtom2DRLConverter(@NonNull SWRLRuleEngineBridge bridge,
    @NonNull DroolsOWLClassExpressionHandler droolsOWLClassExpressionHandler,
    @NonNull DroolsOWLPropertyExpressionHandler droolsOWLPropertyExpressionHandler,
    @NonNull DroolsOWLDataRangeHandler droolsOWLDataRangeHandler)
  {
    super(bridge);

    this.droolsOWLClassExpressionHandler = droolsOWLClassExpressionHandler;
    this.droolsOWLPropertyExpressionHandler = droolsOWLPropertyExpressionHandler;
    this.droolsOWLDataRangeHandler = droolsOWLDataRangeHandler;
    this.droolsBuiltInArgument2DRLConverter = new DroolsSWRLBuiltInArgument2DRLConverter(bridge,
      droolsOWLClassExpressionHandler, droolsOWLPropertyExpressionHandler);
    this.droolsSWRLVariable2NameConverter = new DroolsSWRLVariable2NameConverter(bridge);
    this.droolsOWLLiteral2DRLConverter = new DroolsOWLLiteral2DRLConverter(bridge);
    this.droolsOWLIndividual2DRLConverter = new DroolsOWLIndividual2DRLConverter(bridge);
    this.droolsOWLEntity2NameConverter = new DroolsOWLEntity2NameConverter(bridge);
    this.droolsBodyAtomArgument2DRLConverter = new DroolsSWRLBodyAtomArgument2DRLConverter(bridge,
      droolsSWRLVariable2NameConverter, droolsOWLLiteral2DRLConverter, droolsOWLIndividual2DRLConverter,
      droolsOWLEntity2NameConverter);

    this.builtInIndexInBody = 0;
  }

  public void reset()
  {
    this.builtInIndexInBody = 0;
  }

  @NonNull @Override public String convert(@NonNull SWRLDataRangeAtom atom,
    Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    String dataRangeID = getDroolsOWLDataRangeHandler().convert(atom.getPredicate());
    SWRLDArgument argument = atom.getArgument();

    throw new TargetSWRLRuleEngineNotImplementedFeatureException("data range atoms not implemented in rule head");
  }

  @NonNull public String convert(@NonNull SWRLDataRangeAtom atom)
  {
    String dataRangeID = getDroolsOWLDataRangeHandler().convert(atom.getPredicate());
    SWRLDArgument argument = atom.getArgument();

    throw new TargetSWRLRuleEngineNotImplementedFeatureException("data range atoms not implemented in rule head");
  }

  @NonNull @Override public String convert(@NonNull SWRLClassAtom atom,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    String classID = getDroolsOWLClassExpressionHandler().convert(atom.getPredicate()).getceid();
    SWRLIArgument argument = atom.getArgument();
    String representation =
      DroolsNames.CLASS_ASSERTION_AXIOM_CLASS_NAME + "(" + DroolsNames.CLASS_FIELD_NAME + "==" + addQuotes(classID)
        + ", ";

    representation += getSWRLBodyAtomArgumentConverter()
      .convert(argument, DroolsNames.INDIVIDUAL_FIELD_NAME, previouslyEncounteredVariableNames);
    representation += ")";

    return representation;
  }

  @NonNull @Override public String convert(@NonNull SWRLObjectPropertyAtom atom,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    String propertyID = getDroolsOWLPropertyExpressionHandler().convert(atom.getPredicate()).getid();
    SWRLIArgument argument1 = atom.getFirstArgument();
    SWRLIArgument argument2 = atom.getSecondArgument();
    String representation = DroolsNames.OBJECT_PROPERTY_ASSERTION_AXIOM_CLASS_NAME + "(";

    representation += getSWRLBodyAtomArgumentConverter()
      .convert(argument1, DroolsNames.SUBJECT_FIELD_NAME, previouslyEncounteredVariableNames);
    representation += ", " + DroolsNames.PROPERTY_FIELD_NAME + "==" + addQuotes(propertyID) + ", ";
    representation += getSWRLBodyAtomArgumentConverter()
      .convert(argument2, DroolsNames.OBJECT_FIELD_NAME, previouslyEncounteredVariableNames);
    representation += ")";

    return representation;
  }

  @NonNull @Override public String convert(@NonNull SWRLDataPropertyAtom atom,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    String propertyID = getDroolsOWLPropertyExpressionHandler().convert(atom.getPredicate()).getid();
    SWRLIArgument argument1 = atom.getFirstArgument();
    SWRLDArgument argument2 = atom.getSecondArgument();
    String representation = DroolsNames.DATA_PROPERTY_ASSERTION_AXIOM_CLASS_NAME + "(";

    representation += getSWRLBodyAtomArgumentConverter()
      .convert(argument1, DroolsNames.SUBJECT_FIELD_NAME, previouslyEncounteredVariableNames);
    representation += ", " + DroolsNames.PROPERTY_FIELD_NAME + "==" + addQuotes(propertyID) + ", ";
    representation += getSWRLBodyAtomArgumentConverter()
      .convert(argument2, DroolsNames.OBJECT_FIELD_NAME, previouslyEncounteredVariableNames);
    representation += ")";

    return representation;
  }

  @NonNull @Override public String convert(@NonNull SWRLSameIndividualAtom atom,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    SWRLIArgument argument1 = atom.getFirstArgument();
    SWRLIArgument argument2 = atom.getSecondArgument();
    String representation = DroolsNames.SAME_INDIVIDUAL_AXIOM_CLASS_NAME + "(";

    representation += getSWRLBodyAtomArgumentConverter()
      .convert(argument1, DroolsNames.INDIVIDUAL_1_FIELD_NAME, previouslyEncounteredVariableNames);
    representation += ", ";
    representation += getSWRLBodyAtomArgumentConverter()
      .convert(argument2, DroolsNames.INDIVIDUAL_2_FIELD_NAME, previouslyEncounteredVariableNames);
    representation += ")";

    return representation;
  }

  @NonNull @Override public String convert(@NonNull SWRLDifferentIndividualsAtom atom,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames)
  {
    SWRLIArgument argument1 = atom.getFirstArgument();
    SWRLIArgument argument2 = atom.getSecondArgument();
    String representation = DroolsNames.DIFFERENT_INDIVIDUALS_AXIOM_CLASS_NAME + "(";

    representation += getSWRLBodyAtomArgumentConverter()
      .convert(argument1, DroolsNames.INDIVIDUAL_1_FIELD_NAME, previouslyEncounteredVariableNames);
    representation += ", ";
    representation += getSWRLBodyAtomArgumentConverter()
      .convert(argument2, DroolsNames.INDIVIDUAL_2_FIELD_NAME, previouslyEncounteredVariableNames);
    representation += ")";

    return representation;
  }

  @Override public String convert(@NonNull SWRLAPIBuiltInAtom builtInAtom,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames) throws SWRLBuiltInException
  {
    String ruleName = builtInAtom.getRuleName();
    String builtInPrefixedName = builtInAtom.getBuiltInPrefixedName();
    boolean variableArgumentEncountered = false;
    String representation = DroolsNames.BUILT_IN_ARGUMENTS_PATTERN_CLASS_NAME + "(";
    boolean isFirst;

    int argumentNumber = 1;
    for (SWRLBuiltInArgument argument : builtInAtom.getBuiltInArguments()) {
      if (argument.isVariable()) {
        String variableName = getDroolsSWRLVariable2NameConverter().swrlVariable2VariableName(argument.asVariable());
        if (variableArgumentEncountered)
          representation += ", ";
        representation += getDroolsSWRLVariable2NameConverter()
          .variableName2DRL(variableName, DroolsNames.BUILT_IN_ARGUMENT_PATTERN_FIELD_NAME_PREFIX + argumentNumber,
            previouslyEncounteredVariableNames);
        variableArgumentEncountered = true;
      }
      argumentNumber++;
      if (argumentNumber > BAP.MaxArguments)
        throw new TargetSWRLRuleEngineException(
          "at most " + BAP.MaxArguments + " built-in arguments currently supported");
    }

    representation +=
      ") from invoker.invoke(\"" + ruleName + "\", \"" + builtInPrefixedName + "\", " + this.builtInIndexInBody
        + ", false, ";

    if (builtInAtom.getPathVariableNames().size() > VPATH.MaxArguments)
      throw new TargetSWRLRuleEngineException("at most " + VPATH.MaxArguments + " built-in arguments supported");

    isFirst = true;
    representation += "new " + DroolsNames.BUILT_IN_VARIABLE_PATH_CLASS_NAME + "(";
    for (String variableName : builtInAtom.getPathVariableNames()) {
      if (!isFirst)
        representation += ", ";
      representation += getDroolsSWRLVariable2NameConverter().variableName2DRL(variableName);
      isFirst = false;
    }
    representation += "), ";

    if (builtInAtom.getNumberOfArguments() > BAVNs.MaxArguments)
      throw new TargetSWRLRuleEngineException("at most " + BAVNs.MaxArguments + " built-in arguments supported");

    representation += "new " + DroolsNames.BUILT_IN_VARIABLE_NAMES_CLASS_NAME + "(";
    isFirst = true;
    for (SWRLBuiltInArgument argument : builtInAtom.getBuiltInArguments()) {
      if (!isFirst)
        representation += ", ";
      if (argument.isVariable())
        representation +=
          "\"" + getDroolsSWRLVariable2NameConverter().swrlVariable2VariableName(argument.asVariable()) + "\"";
      else
        representation += "\"\"";
      isFirst = false;
    }
    representation += "), ";

    if (builtInAtom.getNumberOfArguments() > DroolsSWRLBuiltInInvoker.MAX_BUILTIN_ARGUMENTS)
      throw new TargetSWRLRuleEngineException("at most " + DroolsSWRLBuiltInInvoker.MAX_BUILTIN_ARGUMENTS + " allowed");

    isFirst = true;
    for (SWRLBuiltInArgument argument : builtInAtom.getBuiltInArguments()) {
      if (!isFirst)
        representation += ", ";
      representation += getSWRLBuiltInArgumentConverter().convert(argument);
      isFirst = false;
    }

    representation += ")";

    this.builtInIndexInBody++;

    return representation;
  }

  @NonNull public String convert(@NonNull SWRLAtom atom,
    @NonNull Set<@NonNull String> previouslyEncounteredVariableNames) throws SWRLBuiltInException
  { // TODO Visitor to replace instanceof: SWRLAtomVisitorExP
    if (atom instanceof SWRLDataRangeAtom) {
      return convert((SWRLDataRangeAtom)atom, previouslyEncounteredVariableNames);
    } else if (atom instanceof SWRLClassAtom) {
      return convert((SWRLClassAtom)atom, previouslyEncounteredVariableNames);
    } else if (atom instanceof SWRLDataPropertyAtom) {
      return convert((SWRLDataPropertyAtom)atom, previouslyEncounteredVariableNames);
    } else if (atom instanceof SWRLObjectPropertyAtom) {
      return convert((SWRLObjectPropertyAtom)atom, previouslyEncounteredVariableNames);
    } else if (atom instanceof SWRLSameIndividualAtom) {
      return convert((SWRLSameIndividualAtom)atom, previouslyEncounteredVariableNames);
    } else if (atom instanceof SWRLDifferentIndividualsAtom) {
      return convert((SWRLDifferentIndividualsAtom)atom, previouslyEncounteredVariableNames);
    } else if (atom instanceof SWRLAPIBuiltInAtom) {
      return convert((SWRLAPIBuiltInAtom)atom, previouslyEncounteredVariableNames);
    } else
      throw new TargetSWRLRuleEngineInternalException("unknown SWRL atom type " + atom.getClass().getCanonicalName());
  }

  @NonNull private DroolsSWRLBodyAtomArgument2DRLConverter getSWRLBodyAtomArgumentConverter()
  {
    return this.droolsBodyAtomArgument2DRLConverter;
  }

  @NonNull private DroolsSWRLBuiltInArgument2DRLConverter getSWRLBuiltInArgumentConverter()
  {
    return this.droolsBuiltInArgument2DRLConverter;
  }

  private @NonNull DroolsOWLPropertyExpressionHandler getDroolsOWLPropertyExpressionHandler()
  {
    return this.droolsOWLPropertyExpressionHandler;
  }

  private @NonNull DroolsOWLClassExpressionHandler getDroolsOWLClassExpressionHandler()
  {
    return this.droolsOWLClassExpressionHandler;
  }

  private @NonNull DroolsOWLDataRangeHandler getDroolsOWLDataRangeHandler()
  {
    return this.droolsOWLDataRangeHandler;
  }

  @NonNull private String addQuotes(@NonNull String s)
  {
    return "\"" + s + "\"";
  }
}
