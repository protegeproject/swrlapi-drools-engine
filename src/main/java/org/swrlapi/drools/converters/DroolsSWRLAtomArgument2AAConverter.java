package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.converters.TargetRuleEngineSWRLAtomArgumentConverter;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.core.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.core.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.core.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.core.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.core.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.drools.owl.L;
import org.swrlapi.drools.owl.entities.AP;
import org.swrlapi.drools.owl.entities.C;
import org.swrlapi.drools.owl.entities.D;
import org.swrlapi.drools.owl.entities.DP;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.drools.swrl.AA;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.drools.swrl.UBA;
import org.swrlapi.drools.swrl.VA;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.exceptions.TargetRuleEngineNotImplementedFeatureException;

/**
 * This class converts SWRL arguments to their Drools representation.
 * 
 * @see SWRLArgument, AA
 */
public class DroolsSWRLAtomArgument2AAConverter extends DroolsConverterBase implements
		TargetRuleEngineSWRLAtomArgumentConverter<AA>
{
	public DroolsSWRLAtomArgument2AAConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	public AA convert(SWRLArgument argument) throws TargetRuleEngineException
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
		} else if (argument instanceof SWRLVariable) {
			return convert((SWRLVariable)argument);
		} else if (argument instanceof SWRLIndividualArgument) {
			return convert((SWRLIndividualArgument)argument);
		} else if (argument instanceof SWRLLiteralArgument) {
			return convert((SWRLLiteralArgument)argument);
		} else
			throw new RuntimeException("unknown SWRL argument type " + argument.getClass().getCanonicalName());
	}

	@Override
	public VA convert(SWRLVariable argument) throws TargetRuleEngineException
	{
		String variableName = swrlVariable2VariableName(argument);
		return new VA(variableName);
	}

	@Override
	public I convert(SWRLIndividualArgument argument) throws TargetRuleEngineException
	{
		OWLIndividual individual = argument.getIndividual();
		String prefixedName = individual.toStringID();

		return new I(prefixedName);
	}

	@Override
	public L convert(SWRLLiteralArgument argument) throws TargetRuleEngineException
	{
		return getDroolsOWLLiteral2LConverter().convert(argument.getLiteral());
	}

	@Override
	public C convert(SWRLClassBuiltInArgument argument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLIRIResolver().iri2ShortName(argument.getIRI());

		return new C(prefixedName);
	}

	@Override
	public I convert(SWRLNamedIndividualBuiltInArgument argument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLIRIResolver().iri2ShortName(argument.getIRI());

		return new I(prefixedName);
	}

	@Override
	public OP convert(SWRLObjectPropertyBuiltInArgument argument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLIRIResolver().iri2ShortName(argument.getIRI());

		return new OP(prefixedName);
	}

	@Override
	public DP convert(SWRLDataPropertyBuiltInArgument argument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLIRIResolver().iri2ShortName(argument.getIRI());

		return new DP(prefixedName);
	}

	@Override
	public AP convert(SWRLAnnotationPropertyBuiltInArgument argument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLIRIResolver().iri2ShortName(argument.getIRI());

		return new AP(prefixedName);
	}

	@Override
	public D convert(SWRLDatatypeBuiltInArgument argument) throws TargetRuleEngineException
	{
		String prefixedName = getOWLIRIResolver().iri2ShortName(argument.getIRI());

		return new D(prefixedName);
	}

	@Override
	public L convert(SWRLLiteralBuiltInArgument argument) throws TargetRuleEngineException
	{
		return getDroolsOWLLiteral2LConverter().convert(argument.getLiteral());
	}

	@Override
	public UBA convert(SWRLVariableBuiltInArgument argument) throws TargetRuleEngineException
	{
		if (argument.isUnbound())
			return new UBA(swrlVariable2VariableName(argument));
		else
			throw new TargetRuleEngineException("expecting unbound argument, got bound argument " + argument);
	}

	@Override
	public BA convert(SQWRLCollectionVariableBuiltInArgument argument) throws TargetRuleEngineException
	{ // TODO ? Yes it does!?
		throw new TargetRuleEngineNotImplementedFeatureException("Drools does not support SQWRL collections yet");
	}
}
