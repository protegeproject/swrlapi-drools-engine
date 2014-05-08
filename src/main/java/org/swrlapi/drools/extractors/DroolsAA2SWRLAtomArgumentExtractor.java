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
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.owl.L;
import org.swrlapi.drools.owl.entities.AP;
import org.swrlapi.drools.owl.entities.C;
import org.swrlapi.drools.owl.entities.D;
import org.swrlapi.drools.owl.entities.DP;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.drools.sqwrl.SQWRLC;
import org.swrlapi.drools.swrl.AA;
import org.swrlapi.drools.swrl.UBA;
import org.swrlapi.drools.swrl.VA;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.extractors.TargetRuleEngineSWRLAtomArgumentExtractor;

/**
 * This class creates Drools SWRL atom arguments represented, by the class {@link AA}, to their SWRLAPI representation.
 */
public class DroolsAA2SWRLAtomArgumentExtractor extends DroolsExtractorBase implements
		TargetRuleEngineSWRLAtomArgumentExtractor
{
	public DroolsAA2SWRLAtomArgumentExtractor(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	public SWRLVariable extract(VA va) throws TargetRuleEngineException
	{
		IRI iri = prefixedName2IRI(va.getVariableName());
		return getOWLDataFactory().getSWRLVariable(iri);
	}

	public SWRLBuiltInArgument extract(C c) throws TargetRuleEngineException
	{
		OWLClass cls = getDroolsOWLEntityExtractor().extract(c);

		return getSWRLBuiltInArgumentFactory().getClassBuiltInArgument(cls);
	}

	public SWRLBuiltInArgument extract(I i) throws TargetRuleEngineException
	{
		OWLNamedIndividual individual = getDroolsOWLEntityExtractor().extract(i);

		return getSWRLBuiltInArgumentFactory().getNamedIndividualBuiltInArgument(individual);
	}

	public SWRLBuiltInArgument extract(OP op) throws TargetRuleEngineException
	{
		OWLObjectProperty property = getDroolsOWLEntityExtractor().extract(op);

		return getSWRLBuiltInArgumentFactory().getObjectPropertyBuiltInArgument(property);
	}

	public SWRLBuiltInArgument extract(DP dp) throws TargetRuleEngineException
	{
		OWLDataProperty property = getDroolsOWLEntityExtractor().extract(dp);

		return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(property);
	}

	public SWRLBuiltInArgument extract(AP ap) throws TargetRuleEngineException
	{
		OWLAnnotationProperty property = getDroolsOWLEntityExtractor().extract(ap);

		return getSWRLBuiltInArgumentFactory().getAnnotationPropertyBuiltInArgument(property);
	}

	public SWRLBuiltInArgument extract(D d) throws TargetRuleEngineException
	{
		OWLDatatype datatype = getDroolsOWLEntityExtractor().extract(d);

		return getSWRLBuiltInArgumentFactory().getDatatypeBuiltInArgument(datatype);
	}

	public SWRLBuiltInArgument extract(L l) throws TargetRuleEngineException
	{
		OWLLiteral literal = getDroolsOWLLiteralExtractor().extract(l);

		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
	}

	public SWRLBuiltInArgument extract(UBA uba) throws TargetRuleEngineException
	{
		String variableName = uba.getVariableName();
		IRI variableIRI = getDroolsSWRLVariableExtractor().variableName2VariableIRI(variableName);

		return getSWRLBuiltInArgumentFactory().getUnboundVariableBuiltInArgument(variableIRI);
	}

	public SWRLBuiltInArgument extract(SQWRLC sqwrlc) throws TargetRuleEngineException
	{
		String variableName = sqwrlc.getVariableName();
		IRI variableIRI = getDroolsSWRLVariableExtractor().variableName2VariableIRI(variableName);

		return getSWRLBuiltInArgumentFactory().getSQWRLCollectionVariableBuiltInArgument(variableIRI,
				sqwrlc.getQueryName(), sqwrlc.getCollectionName(), sqwrlc.getCollectionID());
	}
}
