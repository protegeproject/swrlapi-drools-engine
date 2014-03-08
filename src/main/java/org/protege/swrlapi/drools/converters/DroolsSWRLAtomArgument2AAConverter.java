package org.protege.swrlapi.drools.converters;

import org.protege.swrlapi.converters.TargetRuleEngineConverterBase;
import org.protege.swrlapi.converters.TargetRuleEngineSWRLAtomArgumentConverter;
import org.protege.swrlapi.core.SWRLRuleEngineBridge;
import org.protege.swrlapi.core.arguments.SQWRLCollectionBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyAtomArgument;
import org.protege.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLAtomArgument;
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
import org.protege.swrlapi.drools.owl.L;
import org.protege.swrlapi.drools.owl.entities.AP;
import org.protege.swrlapi.drools.owl.entities.C;
import org.protege.swrlapi.drools.owl.entities.D;
import org.protege.swrlapi.drools.owl.entities.DP;
import org.protege.swrlapi.drools.owl.entities.I;
import org.protege.swrlapi.drools.owl.entities.OP;
import org.protege.swrlapi.drools.swrl.AA;
import org.protege.swrlapi.drools.swrl.BA;
import org.protege.swrlapi.drools.swrl.UBA;
import org.protege.swrlapi.drools.swrl.VA;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.protege.swrlapi.exceptions.TargetRuleEngineNotImplementedFeatureException;

/**
 * This class converts SWRL atom arguments to their Drools representation.
 */
public class DroolsSWRLAtomArgument2AAConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineSWRLAtomArgumentConverter<AA>
{
	private final DroolsOWLLiteral2LConverter literalConvertor;

	public DroolsSWRLAtomArgument2AAConverter(SWRLRuleEngineBridge bridge, DroolsOWLLiteral2LConverter literalConvertor)
	{
		super(bridge);
		this.literalConvertor = literalConvertor;
	}

	public AA convert(SWRLAtomArgument argument) throws TargetRuleEngineException
	{
		if (argument instanceof SWRLAtomArgument) {
			return convert(argument);
		} else if (argument instanceof SWRLVariableAtomArgument) {
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
	public VA convert(SWRLVariableAtomArgument argument) throws TargetRuleEngineException
	{
		return new VA(argument.getVariableName());
	}

	@Override
	public C convert(SWRLClassAtomArgument argument) throws TargetRuleEngineException
	{
		return new C(argument.getPrefixedName());
	}

	@Override
	public I convert(SWRLIndividualAtomArgument argument) throws TargetRuleEngineException
	{
		return new I(argument.getPrefixedName());
	}

	@Override
	public DP convert(SWRLDataPropertyAtomArgument argument) throws TargetRuleEngineException
	{
		return new DP(argument.getPrefixedName());
	}

	@Override
	public OP convert(SWRLObjectPropertyAtomArgument argument) throws TargetRuleEngineException
	{
		return new OP(argument.getPrefixedName());
	}

	@Override
	public AP convert(SWRLAnnotationPropertyAtomArgument argument) throws TargetRuleEngineException
	{
		return new AP(argument.getPrefixedName());
	}

	@Override
	public L convert(SWRLLiteralAtomArgument argument) throws TargetRuleEngineException
	{
		return getOWLLiteralConvertor().convert(argument.getLiteral());
	}

	@Override
	public D convert(SWRLDatatypeAtomArgument argument) throws TargetRuleEngineException
	{
		return new D(argument.getPrefixedName());
	}

	@Override
	public C convert(SWRLClassBuiltInArgument argument) throws TargetRuleEngineException
	{
		return new C(argument.getPrefixedName());
	}

	@Override
	public I convert(SWRLIndividualBuiltInArgument argument) throws TargetRuleEngineException
	{
		return new I(argument.getPrefixedName());
	}

	@Override
	public OP convert(SWRLObjectPropertyBuiltInArgument argument) throws TargetRuleEngineException
	{
		return new OP(argument.getPrefixedName());
	}

	@Override
	public DP convert(SWRLDataPropertyBuiltInArgument argument) throws TargetRuleEngineException
	{
		return new DP(argument.getPrefixedName());
	}

	@Override
	public AP convert(SWRLAnnotationPropertyBuiltInArgument argument) throws TargetRuleEngineException
	{
		return new AP(argument.getPrefixedName());
	}

	@Override
	public D convert(SWRLDatatypeBuiltInArgument argument) throws TargetRuleEngineException
	{
		return new D(argument.getPrefixedName());
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
		throw new TargetRuleEngineNotImplementedFeatureException("Drools does not support SQWRL collections yet");
	}

	private DroolsOWLLiteral2LConverter getOWLLiteralConvertor()
	{
		return this.literalConvertor;
	}
}
