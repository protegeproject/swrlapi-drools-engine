package org.swrlapi.drools.converters;

import org.swrlapi.converters.TargetRuleEngineSWRLBuiltInArgumentConverter;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.core.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.exceptions.TargetRuleEngineNotImplementedFeatureException;

/**
 * This class converts SWRL atom arguments to DRL clauses for use in rules.
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
		String shortName = getOWLIRIResolver().iri2ShortName(classArgument.getIRI());

		return "new C(" + addQuotes(shortName) + ")";
	}

	@Override
	public String convert(SWRLNamedIndividualBuiltInArgument individualArgument) throws TargetRuleEngineException
	{
		String shortName = getOWLIRIResolver().iri2ShortName(individualArgument.getIRI());

		return "new I(" + addQuotes(shortName) + ")";
	}

	@Override
	public String convert(SWRLObjectPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		String shortName = getOWLIRIResolver().iri2ShortName(propertyArgument.getIRI());

		return "new OP(" + addQuotes(shortName) + ")";
	}

	@Override
	public String convert(SWRLDataPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		String shortName = getOWLIRIResolver().iri2ShortName(propertyArgument.getIRI());

		return "new DP(" + addQuotes(shortName) + ")";
	}

	@Override
	public String convert(SWRLAnnotationPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		String shortName = getOWLIRIResolver().iri2ShortName(propertyArgument.getIRI());

		return "new AP(" + addQuotes(shortName) + ")";
	}

	@Override
	public String convert(SWRLDatatypeBuiltInArgument datatypeArgument) throws TargetRuleEngineException
	{
		String shortName = getOWLIRIResolver().iri2ShortName(datatypeArgument.getIRI());

		return "new D(" + addQuotes(shortName) + ")";
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
