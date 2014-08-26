package org.swrlapi.drools.converters;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLBodyAtomArgumentWithVariableNamesConverter;
import org.swrlapi.builtins.arguments.*;
import org.swrlapi.core.visitors.SWRLArgumentVisitorEx;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.exceptions.TargetRuleEngineNotImplementedFeatureException;

/**
 * This class converts OWLAPI SWRL atom and built-in arguments to DRL clauses for use in rules.
 *
 * @see org.semanticweb.owlapi.model.SWRLArgument
 * @see org.swrlapi.drools.converters.DroolsSWRLHeadAtomArgument2DRLConverter
 */
public class DroolsSWRLBodyAtomArgument2DRLConverter extends DroolsConverterBase implements
		TargetRuleEngineSWRLBodyAtomArgumentWithVariableNamesConverter<String>,
		SWRLArgumentVisitorEx<String>, SWRLBuiltInArgumentVisitorEx<String>
{
	public DroolsSWRLBodyAtomArgument2DRLConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	public String convert(SWRLArgument argument) throws TargetRuleEngineException
	{ // TODO Use visitor to get rid of instanceof SWRLArgumentVisitorEx, SWRLBuiltInArgumentVisitorEx
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
		return fieldName + "==" + convert(argument);
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
		return fieldName + "==" + convert(argument);
	}

	@Override
	public String convert(SWRLNamedIndividualBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException
	{
		return fieldName + "==" + convert(argument);
	}

	@Override
	public String convert(SWRLObjectPropertyBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException
	{
		return fieldName + "==" + convert(argument);
	}

	@Override
	public String convert(SWRLDataPropertyBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException
	{
		return fieldName + "==" + convert(argument);
	}

	@Override
	public String convert(SWRLAnnotationPropertyBuiltInArgument argument, String fieldName,
			Set<String> previouslyEncounteredVariablePrefixedNames) throws TargetRuleEngineException
	{
		return fieldName + "==" + convert(argument);
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

	@Override public String visit(SWRLVariable swrlVariable)
	{
		return null;
	}

	@Override public String visit(SWRLIndividualArgument swrlIndividualArgument)
	{
		return null;
	}

	@Override public String visit(SWRLLiteralArgument swrlLiteralArgument)
	{
		return null;
	}

	@Override public String visit(SWRLClassBuiltInArgument argument)
	{
		return null;
	}

	@Override public String visit(SWRLNamedIndividualBuiltInArgument argument)
	{
		return null;
	}

	@Override public String visit(SWRLObjectPropertyBuiltInArgument argument)
	{
		return null;
	}

	@Override public String visit(SWRLDataPropertyBuiltInArgument argument)
	{
		return null;
	}

	@Override public String visit(SWRLAnnotationPropertyBuiltInArgument argument)
	{
		return null;
	}

	@Override public String visit(SWRLDatatypeBuiltInArgument argument)
	{
		return null;
	}

	@Override public String visit(SWRLLiteralBuiltInArgument argument)
	{
		return null;
	}

	@Override public String visit(SWRLVariableBuiltInArgument argument)
	{
		return null;
	}

	@Override public String visit(SWRLMultiValueVariableBuiltInArgument argument)
	{
		return null;
	}

	@Override public String visit(SQWRLCollectionVariableBuiltInArgument argument)
	{
		return null;
	}
}
