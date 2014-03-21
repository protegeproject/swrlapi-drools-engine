package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.SWRLArgument;
import org.swrlapi.converters.TargetRuleEngineConverterBase;
import org.swrlapi.converters.TargetRuleEngineSWRLHeadAtomArgumentConverter;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyAtomArgument;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLClassAtomArgument;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyAtomArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDatatypeAtomArgument;
import org.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.core.arguments.SWRLNamedIndividualAtomArgument;
import org.swrlapi.core.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLLiteralAtomArgument;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyAtomArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableAtomArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.exceptions.TargetRuleEngineNotImplementedFeatureException;

/**
 * This class converts SWRL head atom and built-in arguments to DRL clauses for use in rules.
 */
public class DroolsSWRLHeadAtomArgument2DRLConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineSWRLHeadAtomArgumentConverter<String>
{
	private final DroolsOWLLiteral2DRLConverter literalConvertor;

	public DroolsSWRLHeadAtomArgument2DRLConverter(SWRLRuleEngineBridge bridge,
			DroolsOWLLiteral2DRLConverter literalConvertor)
	{
		super(bridge);
		this.literalConvertor = literalConvertor;
	}

	public String convert(SWRLArgument argument) throws TargetRuleEngineException
	{
		if (argument instanceof SWRLVariableAtomArgument) {
			return convert((SWRLVariableAtomArgument)argument);
		} else if (argument instanceof SWRLClassAtomArgument) {
			return convert((SWRLClassAtomArgument)argument);
		} else if (argument instanceof SWRLNamedIndividualAtomArgument) {
			return convert((SWRLNamedIndividualAtomArgument)argument);
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
	public String convert(SWRLVariableAtomArgument variableArgument) throws TargetRuleEngineException
	{
		return "$" + variableArgument.getVariableName();
	}

	@Override
	public String convert(SWRLClassAtomArgument classArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(classArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLNamedIndividualAtomArgument individualArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(individualArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLObjectPropertyAtomArgument propertyArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(propertyArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLDataPropertyAtomArgument propertyArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(propertyArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLAnnotationPropertyAtomArgument propertyArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(propertyArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLDatatypeAtomArgument datatypeArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(datatypeArgument.getIRI());

		return prefixedName;
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
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(classArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLNamedIndividualBuiltInArgument individualArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(individualArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLObjectPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(propertyArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLDataPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(propertyArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLAnnotationPropertyBuiltInArgument propertyArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(propertyArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@Override
	public String convert(SWRLDatatypeBuiltInArgument datatypeArgument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(datatypeArgument.getIRI());

		return prefixedName;
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
