package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
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
		IRI iri = shortName2IRI(va.getVariableName());
		return getOWLDataFactory().getSWRLVariable(iri);
	}

	public SWRLBuiltInArgument extract(C c) throws TargetRuleEngineException
	{
		String classPrefixedName = c.getName();
		IRI classIRI = shortName2IRI(classPrefixedName);

		return getSWRLBuiltInArgumentFactory().getClassBuiltInArgument(classIRI);
	}

	public SWRLBuiltInArgument extract(I i) throws TargetRuleEngineException
	{
		String individualPrefixedName = i.getName();
		IRI individualIRI = shortName2IRI(individualPrefixedName);

		return getSWRLBuiltInArgumentFactory().getNamedIndividualBuiltInArgument(individualIRI);
	}

	public SWRLBuiltInArgument extract(DP dp) throws TargetRuleEngineException
	{
		String propertyPrefixedName = dp.getName();
		IRI propertyIRI = shortName2IRI(propertyPrefixedName);

		return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(propertyIRI);
	}

	public SWRLBuiltInArgument extract(AP ap) throws TargetRuleEngineException
	{
		String propertyPrefixedName = ap.getName();
		IRI propertyIRI = shortName2IRI(propertyPrefixedName);

		return getSWRLBuiltInArgumentFactory().getDataPropertyBuiltInArgument(propertyIRI);
	}

	public SWRLBuiltInArgument extract(OP op) throws TargetRuleEngineException
	{
		String propertyPrefixedName = op.getName();
		IRI propertyIRI = shortName2IRI(propertyPrefixedName);

		return getSWRLBuiltInArgumentFactory().getObjectPropertyBuiltInArgument(propertyIRI);
	}

	public SWRLBuiltInArgument extract(D d) throws TargetRuleEngineException
	{
		String datatypePrefixedName = d.getName();
		IRI datatypeIRI = shortName2IRI(datatypePrefixedName);

		return getSWRLBuiltInArgumentFactory().getDatatypeBuiltInArgument(datatypeIRI);
	}

	public SWRLBuiltInArgument extract(L l) throws TargetRuleEngineException
	{
		OWLLiteral literal = getDroolsOWLLiteralExtractor().extract(l);

		return getSWRLBuiltInArgumentFactory().getLiteralBuiltInArgument(literal);
	}

	public SWRLBuiltInArgument extract(UBA uba) throws TargetRuleEngineException
	{
		String variableName = uba.getVariableName();
		IRI variableIRI = shortName2IRI(variableName);
		return getSWRLBuiltInArgumentFactory().getUnboundVariableBuiltInArgument(variableIRI);
	}

	public SWRLBuiltInArgument extract(SQWRLC sqwrlc) throws TargetRuleEngineException
	{
		String variableName = sqwrlc.getVariableName();
		IRI variableIRI = shortName2IRI(variableName);

		return getSWRLBuiltInArgumentFactory().getSQWRLCollectionVariableBuiltInArgument(variableIRI,
				sqwrlc.getQueryName(), sqwrlc.getCollectionName(), sqwrlc.getCollectionID());
	}
}
