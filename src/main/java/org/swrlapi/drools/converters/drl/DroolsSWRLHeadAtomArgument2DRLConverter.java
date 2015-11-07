package org.swrlapi.drools.converters.drl;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLHeadAtomArgumentConverter;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineNotImplementedFeatureException;

import checkers.nullness.quals.NonNull;

/**
 * This class converts OWLAPI SWRL head atom argument to DRL clauses for use in rules.
 *
 * @see org.semanticweb.owlapi.model.SWRLArgument
 * @see DroolsSWRLBodyAtomArgument2DRLConverter
 */
public class DroolsSWRLHeadAtomArgument2DRLConverter extends DroolsDRLConverterBase implements
		TargetRuleEngineSWRLHeadAtomArgumentConverter<String>
{
	public DroolsSWRLHeadAtomArgument2DRLConverter(@NonNull SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	@NonNull
	@Override
	public String convert(@NonNull SWRLVariable variableArgument)
	{
		return getDroolsSWRLVariable2NameConverter().swrlVariable2DRL(variableArgument);
	}

	@NonNull
	@Override
	public String convert(@NonNull SWRLIndividualArgument individualArgument)
	{
		OWLIndividual individual = individualArgument.getIndividual();

		if (individual.isNamed()) {
			IRI iri = individual.asOWLNamedIndividual().getIRI();
			String prefixedName = getIRIResolver().iri2PrefixedName(iri);
			return addQuotes(prefixedName);
		} else if (individual.isAnonymous()) {
			String id = individual.asOWLAnonymousIndividual().toStringID();
			return addQuotes(id);
		} else
			throw new TargetSWRLRuleEngineInternalException("unknown OWL individual type "
					+ individual.getClass().getCanonicalName() + " passed as a " + SWRLIndividualArgument.class.getName());
	}

	@NonNull
	@Override
	public String convert(@NonNull SWRLLiteralArgument argument)
	{
		return getDroolsOWLLiteral2DRLConverter().convert(argument.getLiteral());
	}

	@NonNull
	@Override
	public String convert(@NonNull SWRLVariableBuiltInArgument variableArgument)
	{
		return getDroolsSWRLVariable2NameConverter().swrlVariable2DRL(variableArgument);
	}

	@NonNull
	@Override
	public String convert(@NonNull SWRLClassBuiltInArgument classArgument)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(classArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@NonNull
	@Override
	public String convert(@NonNull SWRLNamedIndividualBuiltInArgument individualArgument)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(individualArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@NonNull
	@Override
	public String convert(@NonNull SWRLObjectPropertyBuiltInArgument propertyArgument)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@NonNull
	@Override
	public String convert(@NonNull SWRLDataPropertyBuiltInArgument propertyArgument)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@NonNull
	@Override
	public String convert(@NonNull SWRLAnnotationPropertyBuiltInArgument propertyArgument)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

		return addQuotes(prefixedName);
	}

	@NonNull
	@Override
	public String convert(@NonNull SWRLDatatypeBuiltInArgument datatypeArgument)
	{
		return getIRIResolver().iri2PrefixedName(datatypeArgument.getIRI());
	}

	@NonNull
	@Override
	public String convert(@NonNull SWRLLiteralBuiltInArgument argument)
	{
		return getDroolsOWLLiteral2DRLConverter().convert(argument.getLiteral());
	}

	@NonNull
	@Override
	public String convert(@NonNull SQWRLCollectionVariableBuiltInArgument argument)
	{
		throw new TargetSWRLRuleEngineNotImplementedFeatureException(
				"SQWRL collections can not be referenced in a rule head");
	}

	@NonNull
	public String convert(@NonNull SWRLArgument argument)
	{ // TODO Use visitor to replace instanceof: define SWRLArgumentVisitorEx in OWLAPI and then
		// SWRLBuiltInArgumentVisitorEx
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
		} else if (argument instanceof SWRLVariable) {
			return convert((SWRLVariable)argument);
		} else if (argument instanceof SWRLLiteralArgument) {
			return convert((SWRLLiteralArgument)argument);
		} else if (argument instanceof SWRLIndividualArgument) {
			return convert((SWRLIndividualArgument)argument);
		} else
			throw new TargetSWRLRuleEngineInternalException("unknown SWRL atom argument type "
					+ argument.getClass().getCanonicalName());
	}

	@NonNull
	private String addQuotes(@NonNull String s)
	{
		return "\"" + s + "\"";
	}
}
