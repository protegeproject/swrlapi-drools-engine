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
import org.swrlapi.builtins.arguments.SWRLBuiltInArgumentVisitorEx;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDataPropertyBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLLiteralBuiltInArgument;
import org.swrlapi.builtins.arguments.SWRLMultiValueVariableBuiltInArgument;
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
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

/**
 * This class converts OWLAPI SWRL atom arguments to Drools {@link org.swrlapi.drools.swrl.BA} instances.
 *
 * @see org.swrlapi.drools.swrl.BA
 */
public class DroolsSWRLBuiltInArgument2BAConverter extends DroolsConverterBase implements
		TargetRuleEngineSWRLBuiltInArgumentConverter<BA>, SWRLBuiltInArgumentVisitorEx<BA>
{
	public DroolsSWRLBuiltInArgument2BAConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	public BA convert(SWRLBuiltInArgument argument) { return argument.accept(this); }

	@Override
	public C convert(SWRLClassBuiltInArgument argument)
	{
		OWLClass cls = argument.getOWLClass();

		return getDroolsOWLEntity2OEConverter().convert(cls);
	}

	@Override
	public I convert(SWRLNamedIndividualBuiltInArgument argument)
	{
		OWLNamedIndividual individual = argument.getOWLNamedIndividual();

		return getDroolsOWLEntity2OEConverter().convert(individual);
	}

	@Override
	public OP convert(SWRLObjectPropertyBuiltInArgument argument)
	{
		OWLObjectProperty property = argument.getOWLObjectProperty();

		return getDroolsOWLEntity2OEConverter().convert(property);
	}

	@Override
	public DP convert(SWRLDataPropertyBuiltInArgument argument)
	{
		OWLDataProperty property = argument.getOWLDataProperty();

		return getDroolsOWLEntity2OEConverter().convert(property);
	}

	@Override
	public AP convert(SWRLAnnotationPropertyBuiltInArgument argument)
	{
		OWLAnnotationProperty property = argument.getOWLAnnotationProperty();

		return getDroolsOWLEntity2OEConverter().convert(property);
	}

	@Override
	public D convert(SWRLDatatypeBuiltInArgument argument)
	{
		OWLDatatype datatype = argument.getOWLDatatype();

		return getDroolsOWLEntity2OEConverter().convert(datatype);
	}

	@Override
	public L convert(SWRLLiteralBuiltInArgument argument)
	{
		OWLLiteral literal = argument.getLiteral();

		return getDroolsOWLLiteral2LConverter().convert(literal);
	}

	@Override
	public UBA convert(SWRLVariableBuiltInArgument argument)
	{
		if (argument.isUnbound())
			return new UBA(getDroolsSWRLVariableConverter().swrlVariable2VariableName(argument));
		else
			throw new TargetSWRLRuleEngineInternalException("expecting unbound argument, got bound argument " + argument);
	}

	@Override
	public BA convert(SQWRLCollectionVariableBuiltInArgument argument)
	{
		return new SQWRLC(getDroolsSWRLVariableConverter().swrlVariable2VariableName(argument), argument.getQueryName(),
				argument.getCollectionName(), argument.getGroupID());
	}

	@Override public BA visit(SWRLClassBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public BA visit(SWRLNamedIndividualBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public BA visit(SWRLObjectPropertyBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public BA visit(SWRLDataPropertyBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public BA visit(SWRLAnnotationPropertyBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public BA visit(SWRLDatatypeBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public BA visit(SWRLLiteralBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public BA visit(SWRLVariableBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public BA visit(SWRLMultiValueVariableBuiltInArgument argument)
	{
		return convert(argument);
	}

	@Override public BA visit(SQWRLCollectionVariableBuiltInArgument argument)
	{
		return convert(argument);
	}
}
