package org.swrlapi.drools.converters;

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
import org.swrlapi.drools.core.DroolsNames;
import org.swrlapi.drools.core.DroolsSWRLBuiltInInvoker;
import org.swrlapi.drools.sqwrl.VPATH;
import org.swrlapi.drools.swrl.BAP;
import org.swrlapi.drools.swrl.BAVNs;
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
public class DroolsSWRLBodyAtom2DRLConverter extends DroolsConverterBase
		implements TargetRuleEngineSWRLBodyAtomWithVariableNamesConverter<String>
{
	private final DroolsSWRLBodyAtomArgument2DRLConverter bodyAtomArgumentConverter;
	private final DroolsSWRLBuiltInArgument2DRLConverter builtInArgumentConverter;
	private final DroolsOWLPropertyExpressionConverter propertyExpressionConverter;
	private final DroolsOWLClassExpressionConverter classExpressionConverter;

	private int builtInIndexInBody; // Each built-in atom in the body gets a unique index, starting at 0

	public DroolsSWRLBodyAtom2DRLConverter(SWRLRuleEngineBridge bridge,
			DroolsOWLClassExpressionConverter classExpressionConverter,
			DroolsOWLPropertyExpressionConverter propertyExpressionConverter)
	{
		super(bridge);

		this.bodyAtomArgumentConverter = new DroolsSWRLBodyAtomArgument2DRLConverter(bridge);
		this.builtInArgumentConverter = new DroolsSWRLBuiltInArgument2DRLConverter(bridge);
		this.classExpressionConverter = classExpressionConverter;
		this.propertyExpressionConverter = propertyExpressionConverter;
		this.builtInIndexInBody = 0;
	}

	public void reset()
	{
		this.builtInIndexInBody = 0;
	}

	@Override
	public String convert(SWRLDataRangeAtom atom, Set<String> previouslyEncounteredVariablePrefixedNames)
	{
		throw new TargetSWRLRuleEngineNotImplementedFeatureException("data range atoms not implemented in rule body");
	}

	public String convert(SWRLDataRangeAtom atom)
	{
		throw new TargetSWRLRuleEngineNotImplementedFeatureException("data range atoms not implemented in rule head");
	}

	@Override
	public String convert(SWRLClassAtom atom, Set<String> previouslyEncounteredVariablePrefixedNames)
	{
		String classID = getOWLClassExpressionConverter().convert(atom.getPredicate());
		SWRLIArgument argument = atom.getArgument();
		String representation =
				DroolsNames.CLASS_ASSERTION_AXIOM_CLASS_NAME + "(" + DroolsNames.CLASS_FIELD_NAME + "==" + addQuotes(classID)
						+ ", ";

		representation += getSWRLBodyAtomArgumentConverter()
				.convert(argument, DroolsNames.INDIVIDUAL_FIELD_NAME, previouslyEncounteredVariablePrefixedNames);
		representation += ")";

		return representation;
	}

	@Override
	public String convert(SWRLObjectPropertyAtom atom, Set<String> previouslyEncounteredVariablePrefixedNames)
	{
		String propertyID = getOWLPropertyExpressionConverter().convert(atom.getPredicate());
		SWRLIArgument argument1 = atom.getFirstArgument();
		SWRLIArgument argument2 = atom.getSecondArgument();
		String representation = DroolsNames.OBJECT_PROPERTY_ASSERTION_AXIOM_CLASS_NAME + "(";

		representation += getSWRLBodyAtomArgumentConverter()
				.convert(argument1, DroolsNames.SUBJECT_FIELD_NAME, previouslyEncounteredVariablePrefixedNames);
		representation += ", " + DroolsNames.PROPERTY_FIELD_NAME + "==" + addQuotes(propertyID) + ", ";
		representation += getSWRLBodyAtomArgumentConverter()
				.convert(argument2, DroolsNames.OBJECT_FIELD_NAME, previouslyEncounteredVariablePrefixedNames);
		representation += ")";

		return representation;
	}

	@Override
	public String convert(SWRLDataPropertyAtom atom, Set<String> previouslyEncounteredVariablePrefixedNames)
	{
		String propertyID = getOWLPropertyExpressionConverter().convert(atom.getPredicate());
		SWRLIArgument argument1 = atom.getFirstArgument();
		SWRLDArgument argument2 = atom.getSecondArgument();
		String representation = DroolsNames.DATA_PROPERTY_ASSERTION_AXIOM_CLASS_NAME + "(";

		representation += getSWRLBodyAtomArgumentConverter()
				.convert(argument1, DroolsNames.SUBJECT_FIELD_NAME, previouslyEncounteredVariablePrefixedNames);
		representation += ", " + DroolsNames.PROPERTY_FIELD_NAME + "==" + addQuotes(propertyID) + ", ";
		representation += getSWRLBodyAtomArgumentConverter()
				.convert(argument2, DroolsNames.OBJECT_FIELD_NAME, previouslyEncounteredVariablePrefixedNames);
		representation += ")";

		return representation;
	}

	@Override
	public String convert(SWRLSameIndividualAtom atom, Set<String> previouslyEncounteredVariablePrefixedNames)
	{
		SWRLIArgument argument1 = atom.getFirstArgument();
		SWRLIArgument argument2 = atom.getSecondArgument();
		String representation = DroolsNames.SAME_INDIVIDUAL_AXIOM_CLASS_NAME + "(";

		representation += getSWRLBodyAtomArgumentConverter()
				.convert(argument1, DroolsNames.INDIVIDUAL_1_FIELD_NAME, previouslyEncounteredVariablePrefixedNames);
		representation += ", ";
		representation += getSWRLBodyAtomArgumentConverter()
				.convert(argument2, DroolsNames.INDIVIDUAL_2_FIELD_NAME, previouslyEncounteredVariablePrefixedNames);
		representation += ")";

		return representation;
	}

	@Override
	public String convert(SWRLDifferentIndividualsAtom atom, Set<String> previouslyEncounteredVariablePrefixedNames)
	{
		SWRLIArgument argument1 = atom.getFirstArgument();
		SWRLIArgument argument2 = atom.getSecondArgument();
		String representation = DroolsNames.DIFFERENT_INDIVIDUALS_AXIOM_CLASS_NAME + "(";

		representation += getSWRLBodyAtomArgumentConverter()
				.convert(argument1, DroolsNames.INDIVIDUAL_1_FIELD_NAME, previouslyEncounteredVariablePrefixedNames);
		representation += ", ";
		representation += getSWRLBodyAtomArgumentConverter()
				.convert(argument2, DroolsNames.INDIVIDUAL_2_FIELD_NAME, previouslyEncounteredVariablePrefixedNames);
		representation += ")";

		return representation;
	}

	@Override
	public String convert(SWRLAPIBuiltInAtom builtInAtom, Set<String> previouslyEncounteredVariablePrefixedNames)
	{
		String builtInPrefixedName = builtInAtom.getBuiltInPrefixedName();
		String ruleName = builtInAtom.getRuleName();
		boolean variableArgumentEncountered = false;
		String representation = DroolsNames.BUILT_IN_ARGUMENTS_PATTERN_CLASS_NAME + "(";
		boolean isFirst;

		int argumentNumber = 1;
		for (SWRLBuiltInArgument argument : builtInAtom.getBuiltInArguments()) {
			if (argument.isVariable()) {
				String variablePrefixedName = getDroolsSWRLVariableConverter()
						.swrlVariable2VariablePrefixedName(argument.asVariable());
				if (variableArgumentEncountered)
					representation += ", ";
				representation += getDroolsSWRLVariableConverter().variablePrefixedName2DRL(variablePrefixedName,
						DroolsNames.BUILT_IN_ARGUMENT_PATTERN_FIELD_NAME_PREFIX + argumentNumber,
						previouslyEncounteredVariablePrefixedNames);
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

		if (builtInAtom.getPathVariablePrefixedNames().size() > VPATH.MaxArguments)
			throw new TargetSWRLRuleEngineException("at most " + VPATH.MaxArguments + " built-in arguments supported");

		isFirst = true;
		representation += "new " + DroolsNames.BUILT_IN_VARIABLE_PATH_CLASS_NAME + "(";
		for (String variablePrefixedName : builtInAtom.getPathVariablePrefixedNames()) {
			if (!isFirst)
				representation += ", ";
			representation += getDroolsSWRLVariableConverter().variablePrefixedName2DRL(variablePrefixedName);
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
						"\"" + getDroolsSWRLVariableConverter().swrlVariable2VariableName(argument.asVariable()) + "\"";
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

	public String convert(SWRLAtom atom, Set<String> previouslyEncounteredVariablePrefixedNames)
	{ // TODO Visitor to replace instanceof: SWRLAtomVisitorExP
		if (atom instanceof SWRLDataRangeAtom) {
			return convert((SWRLDataRangeAtom)atom, previouslyEncounteredVariablePrefixedNames);
		} else if (atom instanceof SWRLClassAtom) {
			return convert((SWRLClassAtom)atom, previouslyEncounteredVariablePrefixedNames);
		} else if (atom instanceof SWRLDataPropertyAtom) {
			return convert((SWRLDataPropertyAtom)atom, previouslyEncounteredVariablePrefixedNames);
		} else if (atom instanceof SWRLObjectPropertyAtom) {
			return convert((SWRLObjectPropertyAtom)atom, previouslyEncounteredVariablePrefixedNames);
		} else if (atom instanceof SWRLSameIndividualAtom) {
			return convert((SWRLSameIndividualAtom)atom, previouslyEncounteredVariablePrefixedNames);
		} else if (atom instanceof SWRLDifferentIndividualsAtom) {
			return convert((SWRLDifferentIndividualsAtom)atom, previouslyEncounteredVariablePrefixedNames);
		} else if (atom instanceof SWRLAPIBuiltInAtom) {
			return convert((SWRLAPIBuiltInAtom)atom, previouslyEncounteredVariablePrefixedNames);
		} else
			throw new TargetSWRLRuleEngineInternalException("unknown SWRL atom type " + atom.getClass().getCanonicalName());
	}

	private DroolsSWRLBodyAtomArgument2DRLConverter getSWRLBodyAtomArgumentConverter()
	{
		return this.bodyAtomArgumentConverter;
	}

	private DroolsSWRLBuiltInArgument2DRLConverter getSWRLBuiltInArgumentConverter()
	{
		return this.builtInArgumentConverter;
	}

	private DroolsOWLPropertyExpressionConverter getOWLPropertyExpressionConverter()
	{
		return this.propertyExpressionConverter;
	}

	private DroolsOWLClassExpressionConverter getOWLClassExpressionConverter()
	{
		return this.classExpressionConverter;
	}

	private String addQuotes(String s)
	{
		return "\"" + s + "\"";
	}
}
