package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLAtomArgumentConverter;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
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

	@Override
	public VA convert(SWRLVariable argument) throws TargetRuleEngineException
	{
		String variableName = getDroolsSWRLVariableConverter().swrlVariable2VariableName(argument);
		return new VA(variableName);
	}

	@Override
	public I convert(SWRLIndividualArgument argument) throws TargetRuleEngineException
	{
		OWLIndividual individual = argument.getIndividual();

		return getDroolsOWLIndividual2IConverter().convert(individual);
	}

	@Override
	public L convert(SWRLLiteralArgument argument) throws TargetRuleEngineException
	{
		return getDroolsOWLLiteral2LConverter().convert(argument.getLiteral());
	}

	@Override
	public C convert(SWRLClassBuiltInArgument argument) throws TargetRuleEngineException
	{
		OWLClass cls = argument.getOWLClass();

		return getDroolsOWLEntity2OEConverter().convert(cls);
	}

	@Override
	public I convert(SWRLNamedIndividualBuiltInArgument argument) throws TargetRuleEngineException
	{
		OWLNamedIndividual individual = argument.getOWLNamedIndividual();

		return getDroolsOWLEntity2OEConverter().convert(individual);
	}

	@Override
	public OP convert(SWRLObjectPropertyBuiltInArgument argument) throws TargetRuleEngineException
	{
		OWLObjectProperty property = argument.getOWLObjectProperty();

		return getDroolsOWLEntity2OEConverter().convert(property);
	}

	@Override
	public DP convert(SWRLDataPropertyBuiltInArgument argument) throws TargetRuleEngineException
	{
		OWLDataProperty property = argument.getOWLDataProperty();

		return getDroolsOWLEntity2OEConverter().convert(property);
	}

	@Override
	public AP convert(SWRLAnnotationPropertyBuiltInArgument argument) throws TargetRuleEngineException
	{
		OWLAnnotationProperty property = argument.getOWLAnnotationProperty();

		return getDroolsOWLEntity2OEConverter().convert(property);
	}

	@Override
	public D convert(SWRLDatatypeBuiltInArgument argument) throws TargetRuleEngineException
	{
		OWLDatatype datatype = argument.getOWLDatatype();

		return getDroolsOWLEntity2OEConverter().convert(datatype);
	}

	@Override
	public L convert(SWRLLiteralBuiltInArgument argument) throws TargetRuleEngineException
	{
		OWLLiteral literal = argument.getLiteral();

		return getDroolsOWLLiteral2LConverter().convert(literal);
	}

	@Override
	public UBA convert(SWRLVariableBuiltInArgument argument) throws TargetRuleEngineException
	{
		if (argument.isUnbound())
			return new UBA(getDroolsSWRLVariableConverter().swrlVariable2VariableName(argument));
		else
			throw new TargetRuleEngineException("expecting unbound argument, got bound argument " + argument);
	}

	@Override
	public BA convert(SQWRLCollectionVariableBuiltInArgument argument) throws TargetRuleEngineException
	{ // TODO ? Yes it does!?
		throw new TargetRuleEngineNotImplementedFeatureException("Drools does not support SQWRL collections yet");
	}
}
