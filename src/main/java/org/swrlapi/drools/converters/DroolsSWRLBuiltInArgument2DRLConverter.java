package org.swrlapi.drools.converters;

import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLBuiltInArgumentConverter;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.exceptions.TargetRuleEngineNotImplementedFeatureException;

/**
 * This class converts SWRLAPI SWRL built-in arguments to DRL clauses for use in rules.
 *
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 */
public class DroolsSWRLBuiltInArgument2DRLConverter extends DroolsConverterBase implements
		TargetRuleEngineSWRLBuiltInArgumentConverter<String>
{
	public DroolsSWRLBuiltInArgument2DRLConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	public String convert(SWRLBuiltInArgument argument) throws TargetRuleEngineException
	{ // TODO Visitor to replace instanceof
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
		} else
			throw new RuntimeException("unknown SWRL argument type " + argument.getClass().getCanonicalName());
	}

	@Override
	public String convert(SWRLVariableBuiltInArgument argument) throws TargetRuleEngineException
	{
		if (argument.isUnbound())
			return "new UBA(\"" + getDroolsSWRLVariableConverter().swrlVariable2VariableName(argument) + "\")";
		else
			return getDroolsSWRLVariableConverter().swrlVariable2DRL(argument);
	}

	@Override
	public String convert(SWRLClassBuiltInArgument classArgument) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(classArgument.getIRI());

		return "new C(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLNamedIndividualBuiltInArgument individualArgument) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(individualArgument.getIRI());

		return "new I(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLObjectPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

		return "new OP(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLDataPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

		return "new DP(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLAnnotationPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

		return "new AP(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLDatatypeBuiltInArgument datatypeArgument) throws TargetRuleEngineException
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(datatypeArgument.getIRI());

		return "new D(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLLiteralBuiltInArgument argument) throws TargetRuleEngineException
	{
		return getDroolsOWLLiteral2DRLConverter().convert(argument.getLiteral());
	}

	@Override
	public String convert(SQWRLCollectionVariableBuiltInArgument argument) throws TargetRuleEngineException
	{
		throw new TargetRuleEngineNotImplementedFeatureException("collection built-in arguments not yet implemented");
	}

	private String addQuotes(String s)
	{
		return "\"" + s + "\"";
	}
}
