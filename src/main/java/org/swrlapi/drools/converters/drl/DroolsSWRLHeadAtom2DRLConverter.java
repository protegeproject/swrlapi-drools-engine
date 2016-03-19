package org.swrlapi.drools.converters.drl;

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
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLHeadAtomConverter;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.SWRLAPIBuiltInAtom;
import org.swrlapi.drools.core.DroolsNames;
import org.swrlapi.drools.core.DroolsSWRLBuiltInInvoker;
import org.swrlapi.exceptions.SWRLAPIException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineNotImplementedFeatureException;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * This class converts OWLAPI SWRL head atoms to a their DRL representation for use in rules.
 * <p/>
 * Head and body atoms are converted differently - hence the need for two converters. Body atom converters must also
 * know the variables defined by previous atoms because a different syntax is required in DRL for declaring a variable
 * vs. referring to one that is already declared. In the head, all variables are guaranteed to have already been
 * declared because SWRL demands this.
 *
 * @see org.semanticweb.owlapi.model.SWRLAtom
 * @see DroolsSWRLBodyAtom2DRLConverter
 */
public class DroolsSWRLHeadAtom2DRLConverter extends DroolsDRLConverterBase
  implements TargetRuleEngineSWRLHeadAtomConverter<String>
{
  private final @NonNull DroolsSWRLHeadAtomArgument2DRLConverter headAtomArgumentConverter;
  private final @NonNull DroolsSWRLBuiltInArgument2DRLConverter builtInArgumentConverter;
  private final @NonNull DroolsOWLPropertyExpression2DRLConverter propertyExpressionConverter;
  private final @NonNull DroolsOWLClassExpression2DRLConverter classExpressionConverter;

  private int inferredAxiomVariableIndex, builtInIndexInHead;

  public DroolsSWRLHeadAtom2DRLConverter(@NonNull SWRLRuleEngineBridge bridge,
    @NonNull DroolsOWLClassExpression2DRLConverter classExpressionConverter,
    @NonNull DroolsOWLPropertyExpression2DRLConverter propertyExpressionConverter)
  {
    super(bridge);

    this.headAtomArgumentConverter = new DroolsSWRLHeadAtomArgument2DRLConverter(bridge);
    this.builtInArgumentConverter = new DroolsSWRLBuiltInArgument2DRLConverter(bridge);
    this.classExpressionConverter = classExpressionConverter;
    this.propertyExpressionConverter = propertyExpressionConverter;

    this.inferredAxiomVariableIndex = 0;
    this.builtInIndexInHead = 0;
  }

  public void reset()
  {
    this.inferredAxiomVariableIndex = 0;
    this.builtInIndexInHead = 0;
  }

  @NonNull @Override public String convert(@NonNull SWRLClassAtom atom)
  {
    String className = getOWLClassExpressionConverter().convert(atom.getPredicate());
    SWRLIArgument argument = atom.getArgument();
    String caaVariable = "caa" + this.inferredAxiomVariableIndex++;
    String representation = DroolsNames.CLASS_ASSERTION_AXIOM_CLASS_NAME + " " + caaVariable + "=new "
      + DroolsNames.CLASS_ASSERTION_AXIOM_CLASS_NAME + "(" + addQuotes(className) + ", ";

    representation +=
      "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "(" + getSWRLHeadAtomArgumentConverter().convert(argument) + ")";
    representation += "); ";
    representation += "inferrer.infer(" + caaVariable + "); ";

    return representation;
  }

  @NonNull @Override public String convert(@NonNull SWRLObjectPropertyAtom atom)
  {
    String propertyID = getOWLPropertyExpressionConverter().convert(atom.getPredicate());
    SWRLIArgument argument1 = atom.getFirstArgument();
    SWRLIArgument argument2 = atom.getSecondArgument();
    String opaaVariable = "opaa" + this.inferredAxiomVariableIndex++;
    String representation = DroolsNames.OBJECT_PROPERTY_ASSERTION_AXIOM_CLASS_NAME + " " + opaaVariable + "=new "
      + DroolsNames.OBJECT_PROPERTY_ASSERTION_AXIOM_CLASS_NAME + "(";

    representation +=
      "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "(" + getSWRLHeadAtomArgumentConverter().convert(argument1) + ")";
    representation += ", " + addQuotes(propertyID) + ", ";
    representation +=
      "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "(" + getSWRLHeadAtomArgumentConverter().convert(argument2) + ")";
    representation += "); ";
    representation += "inferrer.infer(" + opaaVariable + "); ";

    return representation;
  }

  @NonNull @Override public String convert(@NonNull SWRLDataPropertyAtom atom)
  {
    String propertyID = getOWLPropertyExpressionConverter().convert(atom.getPredicate());
    SWRLIArgument argument1 = atom.getFirstArgument();
    SWRLDArgument argument2 = atom.getSecondArgument();
    String dpaaVariable = "dpaa" + this.inferredAxiomVariableIndex++;
    String representation = DroolsNames.DATA_PROPERTY_ASSERTION_AXIOM_CLASS_NAME + " " + dpaaVariable + "=new "
      + DroolsNames.DATA_PROPERTY_ASSERTION_AXIOM_CLASS_NAME + "(";

    representation +=
      "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "(" + getSWRLHeadAtomArgumentConverter().convert(argument1) + ")";
    representation += ", " + addQuotes(propertyID) + ", ";
    representation +=
      "new " + DroolsNames.LITERAL_CLASS_NAME + "(" + getSWRLHeadAtomArgumentConverter().convert(argument2) + ")";
    representation += "); ";
    representation += "inferrer.infer(" + dpaaVariable + "); ";

    return representation;
  }

  @NonNull @Override public String convert(@NonNull SWRLSameIndividualAtom atom)
  {
    SWRLIArgument argument1 = atom.getFirstArgument();
    SWRLIArgument argument2 = atom.getSecondArgument();
    String siaVariable = "sia" + this.inferredAxiomVariableIndex++;
    String representation = DroolsNames.SAME_INDIVIDUAL_AXIOM_CLASS_NAME + " " + siaVariable + "=new "
      + DroolsNames.SAME_INDIVIDUAL_AXIOM_CLASS_NAME + "(";

    representation +=
      "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "(" + getSWRLHeadAtomArgumentConverter().convert(argument1) + ")";
    representation += ", ";
    representation +=
      "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "(" + getSWRLHeadAtomArgumentConverter().convert(argument2) + ")";
    representation += "); ";
    representation += "inferrer.infer(" + siaVariable + "); ";

    return representation;
  }

  @NonNull @Override public String convert(@NonNull SWRLDifferentIndividualsAtom atom)
  {
    SWRLIArgument argument1 = atom.getFirstArgument();
    SWRLIArgument argument2 = atom.getSecondArgument();
    String diaVariable = "dia" + this.inferredAxiomVariableIndex++;
    String representation = DroolsNames.DIFFERENT_INDIVIDUALS_AXIOM_CLASS_NAME + " " + diaVariable + "=new "
      + DroolsNames.DIFFERENT_INDIVIDUALS_AXIOM_CLASS_NAME + "(";

    representation +=
      "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "(" + getSWRLHeadAtomArgumentConverter().convert(argument1) + ")";
    representation += ", ";
    representation +=
      "new " + DroolsNames.INDIVIDUAL_CLASS_NAME + "(" + getSWRLHeadAtomArgumentConverter().convert(argument2) + ")";
    representation += "); ";
    representation += "inferrer.infer(" + diaVariable + "); ";

    return representation;
  }

  @NonNull @Override public String convert(@NonNull SWRLAPIBuiltInAtom builtInAtom)
  {
    String builtInName = builtInAtom.getBuiltInPrefixedName();
    String ruleName = builtInAtom.getRuleName();
    String representation =
      "invoker.invoke(\"" + ruleName + "\", \"" + builtInName + "\", " + this.builtInIndexInHead + ", true, ";
    boolean isFirst = true;

    if (builtInAtom.getNumberOfArguments() > DroolsSWRLBuiltInInvoker.MAX_BUILTIN_ARGUMENTS)
      throw new SWRLAPIException(
        "A maximum of " + DroolsSWRLBuiltInInvoker.MAX_BUILTIN_ARGUMENTS + " can be passed to built-ins");

    for (SWRLBuiltInArgument argument : builtInAtom.getBuiltInArguments()) {
      if (!isFirst)
        representation += ", ";
      representation += getSWRLBuiltInArgumentConverter().convert(argument);
      isFirst = false;
    }
    representation += "); ";

    this.builtInIndexInHead++;

    return representation;
  }

  @NonNull @Override public String convert(@NonNull SWRLDataRangeAtom atom)
  {
    throw new TargetSWRLRuleEngineNotImplementedFeatureException("data range atoms not implemented in rule head");
  }

  @NonNull public String convert(@NonNull SWRLAtom atom)
  { // TODO Visitor to replace instanceof: define SWRLAtomVisitorEx in OWLAPI
    if (atom instanceof SWRLDataRangeAtom) {
      return convert((SWRLDataRangeAtom)atom);
    } else if (atom instanceof SWRLClassAtom) {
      return convert((SWRLClassAtom)atom);
    } else if (atom instanceof SWRLDataPropertyAtom) {
      return convert((SWRLDataPropertyAtom)atom);
    } else if (atom instanceof SWRLObjectPropertyAtom) {
      return convert((SWRLObjectPropertyAtom)atom);
    } else if (atom instanceof SWRLSameIndividualAtom) {
      return convert((SWRLSameIndividualAtom)atom);
    } else if (atom instanceof SWRLDifferentIndividualsAtom) {
      return convert((SWRLDifferentIndividualsAtom)atom);
    } else if (atom instanceof SWRLAPIBuiltInAtom) {
      return convert((SWRLAPIBuiltInAtom)atom);
    } else
      throw new TargetSWRLRuleEngineInternalException("unknown SWRL atom type " + atom.getClass().getCanonicalName());
  }

  @NonNull private DroolsSWRLHeadAtomArgument2DRLConverter getSWRLHeadAtomArgumentConverter()
  {
    return this.headAtomArgumentConverter;
  }

  @NonNull private DroolsSWRLBuiltInArgument2DRLConverter getSWRLBuiltInArgumentConverter()
  {
    return this.builtInArgumentConverter;
  }

  private @NonNull DroolsOWLPropertyExpression2DRLConverter getOWLPropertyExpressionConverter()
  {
    return this.propertyExpressionConverter;
  }

  private @NonNull DroolsOWLClassExpression2DRLConverter getOWLClassExpressionConverter()
  {
    return this.classExpressionConverter;
  }

  @NonNull private String addQuotes(@NonNull String s)
  {
    return "\"" + s + "\"";
  }
}
