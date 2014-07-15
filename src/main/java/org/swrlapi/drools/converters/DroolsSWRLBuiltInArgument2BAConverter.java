package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineSWRLBuiltInArgumentConverter;
import org.swrlapi.builtins.arguments.SQWRLCollectionVariableBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLAnnotationPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLObjectPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLVariableBuiltInArgument;
import org.swrlapi.drools.owl.core.L;
import org.swrlapi.drools.owl.properties.AP;
import org.swrlapi.drools.owl.core.C;
import org.swrlapi.drools.owl.core.D;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.drools.sqwrl.SQWRLC;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.drools.swrl.UBA;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class converts SWRL atom arguments to their Drools representation.
 */
public class DroolsSWRLBuiltInArgument2BAConverter extends DroolsConverterBase implements
		TargetRuleEngineSWRLBuiltInArgumentConverter<BA>
{
	public DroolsSWRLBuiltInArgument2BAConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	public BA convert(SWRLBuiltInArgument argument) throws TargetRuleEngineException
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
		} else
			throw new RuntimeException("unknown SWRL atom argument type " + argument.getClass().getCanonicalName());
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
	{
		return new SQWRLC(getDroolsSWRLVariableConverter().swrlVariable2VariableName(argument), argument.getQueryName(),
				argument.getCollectionName(), argument.getGroupID());
	}
}
