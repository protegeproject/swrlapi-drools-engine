package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.extractors.TargetRuleEngineSWRLBuiltInArgumentExtractor;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.owl.core.L;
import org.swrlapi.drools.owl.properties.AP;
import org.swrlapi.drools.owl.core.C;
import org.swrlapi.drools.owl.core.D;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.drools.sqwrl.SQWRLC;
import org.swrlapi.drools.swrl.UBA;
import org.swrlapi.drools.swrl.VA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class creates SWRLAPI built-in arguments from their Drool's representation.
 *
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 */
public class DroolsSWRLBuiltInArgumentExtractor extends DroolsExtractorBase implements
		TargetRuleEngineSWRLBuiltInArgumentExtractor
{
	public DroolsSWRLBuiltInArgumentExtractor(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	public SWRLVariable extract(VA va) throws TargetSWRLRuleEngineException
	{
		IRI iri = prefixedName2IRI(va.getVariableName());
		return getOWLDataFactory().getSWRLVariable(iri);
	}

	public SWRLBuiltInArgument extract(C c) throws TargetSWRLRuleEngineException
	{
		OWLClass cls = getDroolsOWLEntityExtractor().extract(c);

		return getSWRLBuiltInArgumentFactory().getClassBuiltInArgument(cls);
	}

	public SWRLBuiltInArgument extract(I i) throws TargetSWRLRuleEngineException
	{
		OWLNamedIndividual individual = getDroolsOWLEntityExtractor().extract(i);

		return getSWRLBuiltInArgumentFactory().getNamedIndividualBuiltInArgument(individual);
	}

	public SWRLBuiltInArgument extract(OP op) throws TargetSWRLRuleEngineException
	{
		OWLObjectProperty property = getDroolsOWLEntityExtractor().extract(op);

		return getSWRLBuiltInArgumentFactory().getObjectPropertyBuiltInArgument(property);
	}

	public SWRLBuiltInArgument extract(DP dp) throws TargetSWRLRuleEngineException
	{
		OWLDataProperty property = getDroolsOWLEntityExtractor().extract(dp);

		return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(property);
	}

	public SWRLBuiltInArgument extract(AP ap) throws TargetSWRLRuleEngineException
	{
		OWLAnnotationProperty property = getDroolsOWLEntityExtractor().extract(ap);

		return getSWRLBuiltInArgumentFactory().getAnnotationPropertyBuiltInArgument(property);
	}

	public SWRLBuiltInArgument extract(D d) throws TargetSWRLRuleEngineException
	{
		OWLDatatype datatype = getDroolsOWLEntityExtractor().extract(d);

		return getSWRLBuiltInArgumentFactory().getDatatypeBuiltInArgument(datatype);
	}

	public SWRLBuiltInArgument extract(L l) throws TargetSWRLRuleEngineException
	{
		OWLLiteral literal = getDroolsOWLLiteralExtractor().extract(l);

		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
	}

	public SWRLBuiltInArgument extract(UBA uba) throws TargetSWRLRuleEngineException
	{
		String variableName = uba.getVariableName();
		IRI variableIRI = getDroolsSWRLVariableExtractor().variableName2VariableIRI(variableName);

		return getSWRLBuiltInArgumentFactory().getUnboundVariableBuiltInArgument(variableIRI);
	}

	public SWRLBuiltInArgument extract(SQWRLC sqwrlc) throws TargetSWRLRuleEngineException
	{
		String variableName = sqwrlc.getVariableName();
		IRI variableIRI = getDroolsSWRLVariableExtractor().variableName2VariableIRI(variableName);

		return getSWRLBuiltInArgumentFactory().getSQWRLCollectionVariableBuiltInArgument(variableIRI,
				sqwrlc.getQueryName(), sqwrlc.getCollectionName(), sqwrlc.getCollectionID());
	}
}
