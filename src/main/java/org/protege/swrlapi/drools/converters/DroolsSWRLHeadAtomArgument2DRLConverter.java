package org.protege.swrlapi.drools.converters;

import org.protege.swrlapi.converters.TargetRuleEngineSWRLHeadAtomArgumentConverter;
import org.protege.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLClassAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLDatatypeAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLIndividualBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLVariableAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.protege.swrlapi.exceptions.TargetRuleEngineNotImplementedFeatureException;
import org.semanticweb.owlapi.model.SWRLArgument;

/**
 * This class converts SWRL head atom and built-in arguments to DRL clauses for use in rules.
 */
public class DroolsSWRLHeadAtomArgument2DRLConverter implements TargetRuleEngineSWRLHeadAtomArgumentConverter<String>
{
	private final DroolsOWLLiteral2DRLConverter literalConvertor;

	public DroolsSWRLHeadAtomArgument2DRLConverter(DroolsOWLLiteral2DRLConverter literalConvertor)
	{
		this.literalConvertor = literalConvertor;
	}

	public String convert(SWRLArgument argument) throws TargetRuleEngineException
	{
		if (argument instanceof SWRLVariableAtomArgument) {
			return convert((SWRLVariableAtomArgument)argument);
		} else if (argument instanceof SWRLClassAtomArgument) {
			return convert((SWRLClassAtomArgument)argument);
		} else if (argument instanceof SWRLIndividualAtomArgument) {
			return convert((SWRLIndividualAtomArgument)argument);
		} else if (argument instanceof SWRLLiteralAtomArgument) {
			return convert((SWRLLiteralAtomArgument)argument);
		} else if (argument instanceof SWRLObjectPropertyAtomArgument) {
			return convert((SWRLObjectPropertyAtomArgument)argument);
		} else if (argument instanceof SWRLDataPropertyAtomArgument) {
			return convert((SWRLDataPropertyAtomArgument)argument);
		} else if (argument instanceof SWRLAnnotationPropertyAtomArgument) {
			return convert((SWRLAnnotationPropertyAtomArgument)argument);
		} else if (argument instanceof SWRLDatatypeAtomArgument) {
			return convert((SWRLDatatypeAtomArgument)argument);
		} else if (argument instanceof SQWRLCollectionBuiltInArgument) {
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
	public String convert(SWRLVariableAtomArgument variableArgument) throws TargetRuleEngineException
	{
		return "$" + variableArgument.getVariableName();
	}

	@Override
	public String convert(SWRLClassAtomArgument classArgument) throws TargetRuleEngineException
	{
		return addQuotes(classArgument.getPrefixedName());
	}

	@Override
	public String convert(SWRLIndividualAtomArgument individualArgument) throws TargetRuleEngineException
	{
		return addQuotes(individualArgument.getPrefixedName());
	}

	@Override
	public String convert(SWRLObjectPropertyAtomArgument propertyArgument) throws TargetRuleEngineException
	{
		return addQuotes(propertyArgument.getPrefixedName());
	}

	@Override
	public String convert(SWRLDataPropertyAtomArgument propertyArgument) throws TargetRuleEngineException
	{
		return addQuotes(propertyArgument.getPrefixedName());
	}

	@Override
	public String convert(SWRLAnnotationPropertyAtomArgument propertyArgument) throws TargetRuleEngineException
	{
		return addQuotes(propertyArgument.getPrefixedName());
	}

	@Override
	public String convert(SWRLDatatypeAtomArgument datatypeArgument) throws TargetRuleEngineException
	{
		return datatypeArgument.getPrefixedName();
	}

	@Override
	public String convert(SWRLLiteralAtomArgument argument) throws TargetRuleEngineException
	{
		return getOWLLiteralConvertor().convert(argument.getLiteral());
	}

	@Override
	public String convert(SWRLVariableBuiltInArgument variableArgument) throws TargetRuleEngineException
	{
		return "$" + variableArgument.getVariableName();
	}

	@Override
	public String convert(SWRLClassBuiltInArgument classArgument) throws TargetRuleEngineException
	{
		return addQuotes(classArgument.getPrefixedName());
	}

	@Override
	public String convert(SWRLIndividualBuiltInArgument individualArgument) throws TargetRuleEngineException
	{
		return addQuotes(individualArgument.getPrefixedName());
	}

	@Override
	public String convert(SWRLObjectPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		return addQuotes(propertyArgument.getPrefixedName());
	}

	@Override
	public String convert(SWRLDataPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		return addQuotes(propertyArgument.getPrefixedName());
	}

	@Override
	public String convert(SWRLAnnotationPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		return addQuotes(propertyArgument.getPrefixedName());
	}

	@Override
	public String convert(SWRLDatatypeBuiltInArgument datatypeArgument) throws TargetRuleEngineException
	{
		return datatypeArgument.getPrefixedName();
	}

	@Override
	public String convert(SWRLLiteralBuiltInArgument argument) throws TargetRuleEngineException
	{
		return getOWLLiteralConvertor().convert(argument.getLiteral());
	}

	@Override
	public String convert(SQWRLCollectionBuiltInArgument argument) throws TargetRuleEngineException
	{
		throw new TargetRuleEngineNotImplementedFeatureException("SQWRL collections not yet supported in Drools");
	}

	protected DroolsOWLLiteral2DRLConverter getOWLLiteralConvertor()
	{
		return this.literalConvertor;
	}

	private String addQuotes(String s)
	{
		return "\"" + s + "\"";
	}
}
