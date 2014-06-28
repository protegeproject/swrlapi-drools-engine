package org.swrlapi.drools.converters;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLBodyAtomArgumentWithVariableNamesConverter;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.drools.DroolsNames;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.exceptions.TargetRuleEngineNotImplementedFeatureException;

/**
 * This class converts SWRL atom and built-in arguments to DRL clauses for use in rules.
 */
public class DroolsSWRLBodyAtomArgument2DRLConverter extends DroolsConverterBase implements
		TargetRuleEngineSWRLBodyAtomArgumentWithVariableNamesConverter<String>
{
	public DroolsSWRLBodyAtomArgument2DRLConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	public String convert(SWRLArgument argument) throws TargetRuleEngineException
	{ // TODO Use visitor to get rid of instanceof
		if (argument instanceof SWRLArgument) {
			return convert(argument);
		} else if (argument instanceof SWRLVariable) {
			return convert((SWRLVariable)argument);
		} else if (argument instanceof SWRLIndividualArgument) {
			return convert((SWRLIndividualArgument)argument);
		} else if (argument instanceof SWRLLiteralArgument) {
			return convert((SWRLLiteralArgument)argument);
		} else if (argument instanceof SQWRLCollectionVariableBuiltInArgument) {
			return convert((SQWRLCollectionVariableBuiltInArgument)argument);
		} else if (argument instanceof SWRLVariableBuiltInArgument) {
			return convert((SWRLVariableBuiltInArgument)argument);
		} else if (argument instanceof SWRLClassBuiltInArgument) {
			return convert((SWRLClassBuiltInArgument)argument);
		} else if (argument instanceof SWRLNamedIndividualBuiltInArgument) {
			return convert((SWRLNamedIndividualBuiltInArgument)argument);
		} else if (argument instanceof SWRLLiteralBuiltInArgument) {
			return convert((SWRLLiteralBuiltInArgument)argument);
		} else if (argument instanceof SWRLObjectPropertyBuiltInArgument) {
			return convert((SWRLObjectPropertyBuiltInArgument)argument);
		} else if (argument instanceof SWRLDataPropertyBuiltInArgument) {
			return convert((SWRLDataPropertyBuiltInArgument)argument);
		} else if (argument instanceof SWRLAnnotationPropertyBuiltInArgument) {
			return convert((SWRLAnnotationPropertyBuiltInArgument)argument);
		} else if (argument instanceof SWRLDatatypeBuiltInArgument) {
			return convert((SWRLDatatypeBuiltInArgument)argument);
		} else
			throw new RuntimeException("unknown SWRL atom argument type " + argument.getClass().getCanonicalName());
	}

	@Override
	public String convert(SWRLVariable variableArgument) throws TargetRuleEngineException
	{
		return getDroolsSWRLVariableConverter().swrlVariable2DRL(variableArgument);
	}

	@Override
	public String convert(SWRLLiteralArgument argument) throws TargetRuleEngineException
	{
		return getDroolsOWLLiteral2DRLConverter().convert(argument.getLiteral());
	}

	@Override
	public String convert(SWRLIndividualArgument individualArgument) throws TargetRuleEngineException
	{
		OWLIndividual individual = individualArgument.getIndividual();

		return getDroolsOWLIndividual2DRLConverter().convert(individual);
	}

	@Override
	public String convert(SWRLVariableBuiltInArgument variableArgument) throws TargetRuleEngineException
	{
		return getDroolsSWRLVariableConverter().swrlVariable2DRL(variableArgument);
	}

	@Override
	public String convert(SWRLClassBuiltInArgument classArgument) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(classArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLNamedIndividualBuiltInArgument individualArgument) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(individualArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLObjectPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLDataPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLAnnotationPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLDatatypeBuiltInArgument datatypeArgument) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(datatypeArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLLiteralBuiltInArgument argument) throws TargetRuleEngineException
	{
		return getDroolsOWLLiteral2DRLConverter().convert(argument.getLiteral());
	}

	@Override
	public String convert(SQWRLCollectionVariableBuiltInArgument argument) throws TargetRuleEngineException
	{
		throw new TargetRuleEngineNotImplementedFeatureException(
				"unexpected call to convert a SQWRLCollectionBuiltInArgument");
	}

	@Override
	public String convert(SQWRLCollectionVariableBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException
	{
		throw new TargetRuleEngineNotImplementedFeatureException(
				"unexpected call to convert a SQWRLCollectionBuiltInArgument");
	}

	public String convert(SWRLArgument argument, String fieldName, Set<String> previouslyEncounteredVariablePrefixedNames)
			throws TargetRuleEngineException
	{ // TODO Visitor to replace instanceof
		if (argument instanceof SQWRLCollectionVariableBuiltInArgument) {
			return convert((SQWRLCollectionVariableBuiltInArgument)argument, fieldName,
					previouslyEncounteredVariablePrefixedNames);
		} else if (argument instanceof SWRLVariableBuiltInArgument) {
			return convert((SWRLVariableBuiltInArgument)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
		} else if (argument instanceof SWRLClassBuiltInArgument) {
			return convert((SWRLClassBuiltInArgument)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
		} else if (argument instanceof SWRLNamedIndividualBuiltInArgument) {
			return convert((SWRLNamedIndividualBuiltInArgument)argument, fieldName,
					previouslyEncounteredVariablePrefixedNames);
		} else if (argument instanceof SWRLIndividualArgument) {
			return convert((SWRLIndividualArgument)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
		} else if (argument instanceof SWRLLiteralBuiltInArgument) {
			return convert((SWRLLiteralBuiltInArgument)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
		} else if (argument instanceof SWRLObjectPropertyBuiltInArgument) {
			return convert((SWRLObjectPropertyBuiltInArgument)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
		} else if (argument instanceof SWRLDataPropertyBuiltInArgument) {
			return convert((SWRLDataPropertyBuiltInArgument)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
		} else if (argument instanceof SWRLAnnotationPropertyBuiltInArgument) {
			return convert((SWRLAnnotationPropertyBuiltInArgument)argument, fieldName,
					previouslyEncounteredVariablePrefixedNames);
		} else if (argument instanceof SWRLDatatypeBuiltInArgument) {
			return convert((SWRLDatatypeBuiltInArgument)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
		} else if (argument instanceof SWRLVariable) {
			return convert((SWRLVariable)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
		} else if (argument instanceof SWRLLiteralArgument) {
			return convert((SWRLLiteralArgument)argument, fieldName, previouslyEncounteredVariablePrefixedNames);
		} else
			throw new RuntimeException("unknown SWRL argument type " + argument.getClass().getCanonicalName());
	}

	@Override
	public String convert(SWRLVariableBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException
	{
		String variablePrefixedName = getDroolsSWRLVariableConverter().swrlVariable2VariablePrefixedName(argument);

		if (previouslyEncounteredVariablePrefixedNames.contains(variablePrefixedName)) {
			return fieldName + "==" + getDroolsSWRLVariableConverter().variablePrefixedName2DRL(variablePrefixedName);
		} else {
			previouslyEncounteredVariablePrefixedNames.add(variablePrefixedName);
			return getDroolsSWRLVariableConverter().variablePrefixedName2DRL(variablePrefixedName) + ":" + fieldName;
		}
	}

	@Override
	public String convert(SWRLVariable argument, String fieldName, Set<String> previouslyEncounteredVariablePrefixedNames)
			throws TargetRuleEngineException
	{
		String variablePrefixedName = getDroolsSWRLVariableConverter().swrlVariable2VariablePrefixedName(argument);

		if (previouslyEncounteredVariablePrefixedNames.contains(variablePrefixedName)) {
			return fieldName + "==" + getDroolsSWRLVariableConverter().variablePrefixedName2DRL(variablePrefixedName);
		} else {
			previouslyEncounteredVariablePrefixedNames.add(variablePrefixedName);
			return getDroolsSWRLVariableConverter().variablePrefixedName2DRL(variablePrefixedName) + ":" + fieldName;
		}
	}

	@Override
	public String convert(SWRLIndividualArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException
	{
		return fieldName + "." + DroolsNames.ID_FIELD_NAME + "==" + convert(argument);
	}

	@Override
	public String convert(SWRLLiteralArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException
	{
		return fieldName + "==" + convert(argument);
	}

	@Override
	public String convert(SWRLClassBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException
	{
		return fieldName + "." + DroolsNames.ID_FIELD_NAME + "==" + convert(argument);
	}

	@Override
	public String convert(SWRLNamedIndividualBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException
	{
		return fieldName + "." + DroolsNames.ID_FIELD_NAME + "==" + convert(argument);
	}

	@Override
	public String convert(SWRLObjectPropertyBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException
	{
		return fieldName + "." + DroolsNames.ID_FIELD_NAME + "==" + convert(argument);
	}

	@Override
	public String convert(SWRLDataPropertyBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException
	{
		return fieldName + "." + DroolsNames.ID_FIELD_NAME + "==" + convert(argument);
	}

	@Override
	public String convert(SWRLAnnotationPropertyBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException
	{
		return fieldName + "." + DroolsNames.ID_FIELD_NAME + "==" + convert(argument);
	}

	@Override
	public String convert(SWRLLiteralBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException
	{
		return fieldName + "==" + convert(argument);
	}

	@Override
	public String convert(SWRLDatatypeBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException
	{
		return fieldName + "==" + convert(argument);
	}

	private String addQuotes(String s)
	{
		return "\"" + s + "\"";
	}
}
