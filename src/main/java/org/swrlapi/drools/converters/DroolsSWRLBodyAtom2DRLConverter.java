package org.swrlapi.drools.converters;

import java.util.Set;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.swrlapi.converters.TargetRuleEngineConverterBase;
import org.swrlapi.converters.TargetRuleEngineSWRLBodyAtomWithVariableNamesConverter;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.DroolsNames;
import org.swrlapi.drools.DroolsSWRLBuiltInInvoker;
import org.swrlapi.drools.sqwrl.VPATH;
import org.swrlapi.drools.swrl.BAP;
import org.swrlapi.drools.swrl.BAVNs;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.exceptions.TargetRuleEngineNotImplementedFeatureException;
import org.swrlapi.ext.SWRLAPIBuiltInAtom;

/**
 * This class converts SWRL body atoms to a their DRL representation for use in rules.
 * <p>
 * Head and body atoms are converted differently - hence the need for two converters. Body atom converters must also
 * know the variables defined by previous atoms because a different syntax is required in DRL for declaring a variable
 * vs. referring to one that is already declared. In the head, all variables are guaranteed to have already been
 * declared in SWRL.
 */
public class DroolsSWRLBodyAtom2DRLConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineSWRLBodyAtomWithVariableNamesConverter<String>
{
	private final DroolsSWRLBodyAtomArgument2DRLConverter bodyAtomArgumentConverter;
	private final DroolsSWRLBuiltInArgument2DRLConverter builtInArgumentConverter;
	private final DroolsOWLLiteral2DRLConverter literalConverter;
	private final DroolsOWLPropertyExpressionConverter propertyExpressionConverter;
	private final DroolsOWLClassExpressionConverter classExpressionConverter;
	private int builtInIndex;

	public DroolsSWRLBodyAtom2DRLConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);

		this.literalConverter = new DroolsOWLLiteral2DRLConverter(bridge.getOWLIRIResolver());
		this.bodyAtomArgumentConverter = new DroolsSWRLBodyAtomArgument2DRLConverter(bridge, this.literalConverter);
		this.builtInArgumentConverter = new DroolsSWRLBuiltInArgument2DRLConverter(bridge, this.literalConverter);
		this.propertyExpressionConverter = new DroolsOWLPropertyExpressionConverter(bridge);
		this.classExpressionConverter = new DroolsOWLClassExpressionConverter(bridge, new DroolsOWLLiteral2LConverter(
				bridge.getOWLIRIResolver()));
		this.builtInIndex = 0;
	}

	public void reset()
	{
		this.builtInIndex = 0;
		this.propertyExpressionConverter.reset();
	}

	public String convert(SWRLAtom atom, Set<String> variableNames) throws TargetRuleEngineException
	{
		if (atom instanceof SWRLDataRangeAtom) {
			return convert((SWRLDataRangeAtom)atom, variableNames);
		} else if (atom instanceof SWRLClassAtom) {
			return convert((SWRLClassAtom)atom, variableNames);
		} else if (atom instanceof SWRLDataPropertyAtom) {
			return convert((SWRLDataPropertyAtom)atom, variableNames);
		} else if (atom instanceof SWRLObjectPropertyAtom) {
			return convert((SWRLObjectPropertyAtom)atom, variableNames);
		} else if (atom instanceof SWRLSameIndividualAtom) {
			return convert((SWRLSameIndividualAtom)atom, variableNames);
		} else if (atom instanceof SWRLDifferentIndividualsAtom) {
			return convert((SWRLDifferentIndividualsAtom)atom, variableNames);
		} else if (atom instanceof SWRLAPIBuiltInAtom) {
			return convert((SWRLAPIBuiltInAtom)atom, variableNames);
		} else
			throw new RuntimeException("unknown SWRL atom type " + atom.getClass().getCanonicalName());
	}

	@Override
	public String convert(SWRLDataRangeAtom atom, Set<String> variableNames) throws TargetRuleEngineException
	{
		throw new TargetRuleEngineNotImplementedFeatureException("data range atoms not implemented in rule body");
	}

	public String convert(SWRLDataRangeAtom atom) throws TargetRuleEngineException
	{
		throw new TargetRuleEngineNotImplementedFeatureException("data range atoms not implemented in rule head");
	}

	@Override
	public String convert(SWRLClassAtom atom, Set<String> variableNames) throws TargetRuleEngineException
	{
		String classID = getOWLClassExpressionConverter().convert(atom.getPredicate());
		SWRLIArgument argument = atom.getArgument();
		String representation = DroolsNames.ClassAssertionAxiomClassName + "(" + DroolsNames.ClassFieldName + "=="
				+ addQuotes(classID) + ", ";

		representation += getSWRLBodyAtomArgumentConverter().convert(argument, DroolsNames.IndividualFieldName,
				variableNames);
		representation += ")";

		return representation;
	}

	@Override
	public String convert(SWRLDataPropertyAtom atom, Set<String> variableNames) throws TargetRuleEngineException
	{
		String propertyID = getOWLPropertyExpressionConverter().convert(atom.getPredicate());
		SWRLIArgument argument1 = atom.getFirstArgument();
		SWRLDArgument argument2 = atom.getSecondArgument();
		String representation = DroolsNames.DataPropertyAssertionAxiomClassName + "(";

		representation += getSWRLBodyAtomArgumentConverter()
				.convert(argument1, DroolsNames.SubjectFieldName, variableNames);
		representation += ", " + DroolsNames.PropertyFieldName + "." + DroolsNames.IDFieldName + "=="
				+ addQuotes(propertyID) + ", ";
		representation += getSWRLBodyAtomArgumentConverter().convert(argument2, DroolsNames.ObjectFieldName, variableNames);
		representation += ")";

		return representation;
	}

	@Override
	public String convert(SWRLObjectPropertyAtom atom, Set<String> variableNames) throws TargetRuleEngineException
	{
		String propertyID = getOWLPropertyExpressionConverter().convert(atom.getPredicate());
		SWRLIArgument argument1 = atom.getFirstArgument();
		SWRLIArgument argument2 = atom.getSecondArgument();
		String representation = DroolsNames.ObjectPropertyAssertionAxiomClassName + "(";

		representation += getSWRLBodyAtomArgumentConverter()
				.convert(argument1, DroolsNames.SubjectFieldName, variableNames);
		representation += ", " + DroolsNames.PropertyFieldName + "." + DroolsNames.IDFieldName + "=="
				+ addQuotes(propertyID) + ", ";
		representation += getSWRLBodyAtomArgumentConverter().convert(argument2, DroolsNames.ObjectFieldName, variableNames);
		representation += ")";

		return representation;
	}

	@Override
	public String convert(SWRLSameIndividualAtom atom, Set<String> variableNames) throws TargetRuleEngineException
	{
		SWRLIArgument argument1 = atom.getFirstArgument();
		SWRLIArgument argument2 = atom.getSecondArgument();
		String representation = DroolsNames.SameIndividualAxiomClassName + "(";

		representation += getSWRLBodyAtomArgumentConverter().convert(argument1, DroolsNames.Individual1FieldName,
				variableNames);
		representation += ", ";
		representation += getSWRLBodyAtomArgumentConverter().convert(argument2, DroolsNames.Individual2FieldName,
				variableNames);
		representation += ")";

		return representation;
	}

	@Override
	public String convert(SWRLDifferentIndividualsAtom atom, Set<String> variableNames) throws TargetRuleEngineException
	{
		SWRLIArgument argument1 = atom.getFirstArgument();
		SWRLIArgument argument2 = atom.getSecondArgument();
		String representation = DroolsNames.DifferentIndividualsAxiomClassName + "(";

		representation += getSWRLBodyAtomArgumentConverter().convert(argument1, DroolsNames.Individual1FieldName,
				variableNames);
		representation += ", ";
		representation += getSWRLBodyAtomArgumentConverter().convert(argument2, DroolsNames.Individual2FieldName,
				variableNames);
		representation += ")";

		return representation;
	}

	@Override
	public String convert(SWRLAPIBuiltInAtom builtInAtom, Set<String> variableNames) throws TargetRuleEngineException
	{
		String builtInName = builtInAtom.getBuiltInPrefixedName();
		String ruleName = builtInAtom.getRuleName();
		boolean variableArgumentEncountered = false;
		String representation = DroolsNames.BuiltInArgumentsPatternClassName + "(";
		boolean isFirst;

		int argumentNumber = 1;
		for (SWRLBuiltInArgument argument : builtInAtom.getBuiltInArguments()) {
			if (argument.isVariable()) {
				String variableName = argument.getVariableName();
				if (variableArgumentEncountered)
					representation += ", ";
				representation += variableName2DRL(variableName, DroolsNames.BuiltInArgumentPatternFieldNamePrefix
						+ argumentNumber, variableNames);
				variableArgumentEncountered = true;
			}
			argumentNumber++;
			if (argumentNumber > BAP.MaxArguments)
				throw new TargetRuleEngineException("a maximum of " + BAP.MaxArguments
						+ " built-in arguments are currently supported by Drools");
		}

		representation += ") from invoker.invoke(\"" + ruleName + "\", \"" + builtInName + "\", " + this.builtInIndex
				+ ", false, ";

		if (builtInAtom.getPathVariableNames().size() > VPATH.MaxArguments)
			throw new TargetRuleEngineException("a maximum of " + VPATH.MaxArguments
					+ " built-in arguments are currently supported by Drools");

		isFirst = true;
		representation += "new " + DroolsNames.BuiltInVariablePathClassName + "(";
		isFirst = true;
		for (String variableName : builtInAtom.getPathVariableNames()) {
			if (!isFirst)
				representation += ", ";
			representation += "$" + variableName;
			isFirst = false;
		}
		representation += "), ";

		if (builtInAtom.getNumberOfArguments() > BAVNs.MaxArguments) // TODO fix with BAVNs varargs Drools fix
			throw new TargetRuleEngineException("a maximum of " + BAVNs.MaxArguments
					+ " built-in arguments are currently supported by Drools");

		representation += "new " + DroolsNames.BuiltInVariableNamesClassName + "(";
		isFirst = true;
		for (SWRLBuiltInArgument argument : builtInAtom.getBuiltInArguments()) {
			if (!isFirst)
				representation += ", ";
			if (argument.isVariable())
				representation += "\"" + argument.getVariableName() + "\"";
			else
				representation += "\"\"";
			isFirst = false;
		}
		representation += "), ";

		if (builtInAtom.getNumberOfArguments() > DroolsSWRLBuiltInInvoker.MaxBuiltInArguments)
			throw new TargetRuleEngineException("a maximum of " + DroolsSWRLBuiltInInvoker.MaxBuiltInArguments
					+ " can be passed to built-ins");

		isFirst = true;
		for (SWRLBuiltInArgument argument : builtInAtom.getBuiltInArguments()) {
			if (!isFirst)
				representation += ", ";
			representation += getSWRLBuiltInArgumentConverter().convert(argument);
			isFirst = false;
		}

		representation += ")";

		this.builtInIndex++;

		return representation;
	}

	private String variableName2DRL(String variableName, String fieldName, Set<String> variableNames)
	{
		if (variableNames.contains(variableName)) {
			return fieldName + "==$" + variableName;
		} else {
			variableNames.add(variableName);
			return "$" + variableName + ":" + fieldName;
		}
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
