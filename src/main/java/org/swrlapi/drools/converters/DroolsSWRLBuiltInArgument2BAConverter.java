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
import org.swrlapi.drools.owl.L;
import org.swrlapi.drools.owl.entities.AP;
import org.swrlapi.drools.owl.entities.C;
import org.swrlapi.drools.owl.entities.D;
import org.swrlapi.drools.owl.entities.DP;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.drools.sqwrl.SQWRLC;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.drools.swrl.UBA;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class converts SWRL atom arguments to their Drools representation.
 */
public class DroolsSWRLBuiltInArgument2BAConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineSWRLBuiltInArgumentConverter<BA>
{
	private final DroolsOWLLiteral2LConverter literalConvertor;

	public DroolsSWRLBuiltInArgument2BAConverter(SWRLRuleEngineBridge bridge, DroolsOWLLiteral2LConverter literalConvertor)
	{
		super(bridge);
		this.literalConvertor = literalConvertor;
	}

	public BA convert(SWRLBuiltInArgument argument) throws TargetRuleEngineException
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
	public C convert(SWRLClassBuiltInArgument argument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(argument.getIRI());

		return new C(prefixedName);
	}

	@Override
	public I convert(SWRLIndividualBuiltInArgument argument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(argument.getIRI());

		return new I(prefixedName);
	}

	@Override
	public OP convert(SWRLObjectPropertyBuiltInArgument argument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(argument.getIRI());

		return new OP(prefixedName);
	}

	@Override
	public DP convert(SWRLDataPropertyBuiltInArgument argument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(argument.getIRI());

		return new DP(prefixedName);
	}

	@Override
	public AP convert(SWRLAnnotationPropertyBuiltInArgument argument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(argument.getIRI());

		return new AP(prefixedName);
	}

	@Override
	public D convert(SWRLDatatypeBuiltInArgument argument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLNamedObjectResolver().iri2PrefixedName(argument.getIRI());

		return new D(prefixedName);
	}

	@Override
	public L convert(SWRLLiteralBuiltInArgument argument) throws TargetRuleEngineException
	{
		return getOWLLiteralConvertor().convert(argument.getLiteral());
	}

	@Override
	public UBA convert(SWRLVariableBuiltInArgument argument) throws TargetRuleEngineException
	{
		if (argument.isUnbound())
			return new UBA(argument.getVariableName());
		else
			throw new TargetRuleEngineException("expecting unbound argument, got bound argument " + argument);
	}

	@Override
	public BA convert(SQWRLCollectionBuiltInArgument argument) throws TargetRuleEngineException
	{
		return new SQWRLC(argument.getQueryName(), argument.getCollectionName(), argument.getGroupID());
	}

	private DroolsOWLLiteral2LConverter getOWLLiteralConvertor()
	{
		return this.literalConvertor;
	}
}
