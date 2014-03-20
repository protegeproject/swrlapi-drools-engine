package org.swrlapi.drools.converters;

import org.swrlapi.converters.TargetRuleEngineConverterBase;
import org.swrlapi.converters.TargetRuleEngineSWRLBuiltInArgumentConverter;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.exceptions.TargetRuleEngineNotImplementedFeatureException;

/**
 * This class converts SWRL atom arguments to DRL clauses for use in rules.
 */
public class DroolsSWRLBuiltInArgument2DRLConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineSWRLBuiltInArgumentConverter<String>
{
	private final DroolsOWLLiteral2DRLConverter literalConvertor;

	public DroolsSWRLBuiltInArgument2DRLConverter(SWRLRuleEngineBridge bridge,
			DroolsOWLLiteral2DRLConverter literalConvertor)
	{
		super(bridge);
		this.literalConvertor = literalConvertor;
	}

	public String convert(SWRLBuiltInArgument argument) throws TargetRuleEngineException
	{
		if (argument instanceof SQWRLCollectionBuiltInArgument) {
			return convert((SQWRLCollectionBuiltInArgument)argument);
		} else if (argument instanceof SWRLVariableBuiltInArgument) {
			return convert((SWRLVariableBuiltInArgument)argument);
		} else if (argument instanceof SWRLClassBuiltInArgument) {
			return convert((SWRLClassBuiltInArgument)argument);
		} else if (argument instanceof SWRLIndividualBuiltInArgument) {
			return convert((SWRLIndividualBuiltInArgument)argument);
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
	public String convert(SWRLVariableBuiltInArgument argument) throws TargetRuleEngineException
	{
		if (argument.isUnbound())
			return "new UBA(\"" + argument.getVariableName() + "\")";
		else
			return "$" + argument.getVariableName();
	}

	@Override
	public String convert(SWRLClassBuiltInArgument classArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(classArgument.getIRI());

		return "new C(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLIndividualBuiltInArgument individualArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(individualArgument.getIRI());

		return "new I(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLObjectPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(propertyArgument.getIRI());

		return "new OP(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLDataPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(propertyArgument.getIRI());

		return "new DP(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLAnnotationPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(propertyArgument.getIRI());

		return "new AP(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLDatatypeBuiltInArgument datatypeArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(datatypeArgument.getIRI());

		return "new D(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLLiteralBuiltInArgument argument) throws TargetRuleEngineException
	{
		return getOWLLiteralConvertor().convert(argument.getLiteral());
	}

	@Override
	public String convert(SQWRLCollectionBuiltInArgument argument) throws TargetRuleEngineException
	{
		throw new TargetRuleEngineNotImplementedFeatureException("collection built-in arguments not yet implemented");
	}

	private DroolsOWLLiteral2DRLConverter getOWLLiteralConvertor()
	{
		return this.literalConvertor;
	}

	private String addQuotes(String s)
	{
		return "\"" + s + "\"";
	}
}
