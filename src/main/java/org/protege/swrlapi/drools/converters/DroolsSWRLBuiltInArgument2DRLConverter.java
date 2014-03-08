package org.protege.swrlapi.drools.converters;

import org.protege.swrlapi.converters.TargetRuleEngineSWRLBuiltInArgumentConverter;
import org.protege.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.protege.swrlapi.exceptions.TargetRuleEngineNotImplementedFeatureException;

/**
 * This class converts SWRL atom arguments to DRL clauses for use in rules.
 */
public class DroolsSWRLBuiltInArgument2DRLConverter implements TargetRuleEngineSWRLBuiltInArgumentConverter<String>
{
	private final DroolsOWLLiteral2DRLConverter literalConvertor;

	public DroolsSWRLBuiltInArgument2DRLConverter(DroolsOWLLiteral2DRLConverter literalConvertor)
	{
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
		return "new C(" + addQuotes(classArgument.getPrefixedName()) + ")";
	}

	@Override
	public String convert(SWRLIndividualBuiltInArgument individualArgument) throws TargetRuleEngineException
	{
		return "new I(" + addQuotes(individualArgument.getPrefixedName()) + ")";
	}

	@Override
	public String convert(SWRLObjectPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		return "new OP(" + addQuotes(propertyArgument.getPrefixedName()) + ")";
	}

	@Override
	public String convert(SWRLDataPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		return "new DP(" + addQuotes(propertyArgument.getPrefixedName()) + ")";
	}

	@Override
	public String convert(SWRLAnnotationPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		return "new AP(" + addQuotes(propertyArgument.getPrefixedName()) + ")";
	}

	@Override
	public String convert(SWRLDatatypeBuiltInArgument datatypeArgument) throws TargetRuleEngineException
	{
		return "new D(" + addQuotes(datatypeArgument.getPrefixedName()) + ")";
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
