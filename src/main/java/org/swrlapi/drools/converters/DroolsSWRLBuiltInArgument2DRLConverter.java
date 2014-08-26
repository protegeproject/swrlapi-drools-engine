package org.swrlapi.drools.converters;

import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLBuiltInArgumentConverter;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;

/**
 * This class converts SWRLAPI SWRL built-in arguments to DRL clauses for use in rules.
 *
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 */
public class DroolsSWRLBuiltInArgument2DRLConverter extends DroolsConverterBase implements
		TargetRuleEngineSWRLBuiltInArgumentConverter<String>, SWRLBuiltInArgumentVisitorEx<String>
{
	public DroolsSWRLBuiltInArgument2DRLConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	public String convert(SWRLBuiltInArgument argument)	{ return argument.accept(this); }

	@Override
	public String convert(SWRLVariableBuiltInArgument argument)
	{
		if (argument.isUnbound())
			return "new UBA(\"" + getDroolsSWRLVariableConverter().swrlVariable2VariableName(argument) + "\")";
		else
			return getDroolsSWRLVariableConverter().swrlVariable2DRL(argument);
	}

	@Override
	public String convert(SWRLClassBuiltInArgument classArgument)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(classArgument.getIRI());

		return "new C(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLNamedIndividualBuiltInArgument individualArgument)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(individualArgument.getIRI());

		return "new I(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLObjectPropertyBuiltInArgument propertyArgument)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

		return "new OP(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLDataPropertyBuiltInArgument propertyArgument)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

		return "new DP(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLAnnotationPropertyBuiltInArgument propertyArgument)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(propertyArgument.getIRI());

		return "new AP(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLDatatypeBuiltInArgument datatypeArgument)
	{
		String prefixedName = getIRIResolver().iri2PrefixedName(datatypeArgument.getIRI());

		return "new D(" + addQuotes(prefixedName) + ")";
	}

	@Override
	public String convert(SWRLLiteralBuiltInArgument argument)
	{
		return getDroolsOWLLiteral2DRLConverter().convert(argument.getLiteral());
	}

	@Override
	public String convert(SQWRLCollectionVariableBuiltInArgument argument)
	{
		throw new RuntimeException("collection built-in arguments not yet implemented");
	}

	private String addQuotes(String s)
	{
		return "\"" + s + "\"";
	}

	@Override public String visit(SWRLClassBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public String visit(SWRLNamedIndividualBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public String visit(SWRLObjectPropertyBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public String visit(SWRLDataPropertyBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public String visit(SWRLAnnotationPropertyBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public String visit(SWRLDatatypeBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public String visit(SWRLLiteralBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public String visit(SWRLVariableBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public String visit(SWRLMultiValueVariableBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public String visit(SQWRLCollectionVariableBuiltInArgument argument)
	{
		return convert(argument);
	}
}
