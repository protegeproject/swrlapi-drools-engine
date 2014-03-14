package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.core.arguments.SWRLVariableAtomArgument;
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
import org.swrlapi.extractors.TargetRuleEngineExtractorBase;
import org.swrlapi.extractors.TargetRuleEngineSWRLAtomArgumentExtractor;

/**
 * This class creates Drools SWRL atom arguments represented, by the class {@link AA}, to their SWRLAPI representation.
 */
public class DroolsAA2SWRLAtomArgumentExtractor extends TargetRuleEngineExtractorBase implements
		TargetRuleEngineSWRLAtomArgumentExtractor
{
	private final DroolsL2OWLLiteralExtractor literalExtractor;

	public DroolsAA2SWRLAtomArgumentExtractor(SWRLRuleEngineBridge bridge, DroolsL2OWLLiteralExtractor literalExtractor)
	{
		super(bridge);

		this.literalExtractor = literalExtractor;
	}

	public SWRLVariableAtomArgument extract(VA va) throws TargetRuleEngineException
	{
		return getSWRLAtomArgumentFactory().getVariableArgument(va.getVariableName());
	}

	public SWRLBuiltInArgument extract(C c) throws TargetRuleEngineException
	{
		String classPrefixedName = c.getid();
		IRI classIRI = getIRI(classPrefixedName);

		return getSWRLBuiltInArgumentFactory().getClassArgument(classIRI);
	}

	public SWRLBuiltInArgument extract(I i) throws TargetRuleEngineException
	{
		String individualPrefixedName = i.getid();
		IRI individualIRI = getIRI(individualPrefixedName);

		return getSWRLBuiltInArgumentFactory().getIndividualArgument(individualIRI);
	}

	public SWRLBuiltInArgument extract(DP dp) throws TargetRuleEngineException
	{
		String propertyPrefixedName = dp.getid();
		IRI propertyIRI = getIRI(propertyPrefixedName);

		return getSWRLBuiltInArgumentFactory().getDataPropertyArgument(propertyIRI);
	}

	public SWRLBuiltInArgument extract(AP ap) throws TargetRuleEngineException
	{
		String propertyPrefixedName = ap.getid();
		IRI propertyIRI = getIRI(propertyPrefixedName);

		return getSWRLBuiltInArgumentFactory().getDataPropertyArgument(propertyIRI);
	}

	public SWRLBuiltInArgument extract(OP op) throws TargetRuleEngineException
	{
		String propertyPrefixedName = op.getid();
		IRI propertyIRI = getIRI(propertyPrefixedName);

		return getSWRLBuiltInArgumentFactory().getObjectPropertyArgument(propertyIRI);
	}

	public SWRLBuiltInArgument extract(D d) throws TargetRuleEngineException
	{
		String datatypePrefixedName = d.getid();
		IRI datatypeIRI = getIRI(datatypePrefixedName);

		return getSWRLBuiltInArgumentFactory().getDatatypeArgument(datatypeIRI);
	}

	public SWRLBuiltInArgument extract(L l) throws TargetRuleEngineException
	{
		OWLLiteral literal = getOWLLiteralExtractor().extract(l);

		return getSWRLBuiltInArgumentFactory().getLiteralArgument(literal);
	}

	public SWRLBuiltInArgument extract(UBA uba) throws TargetRuleEngineException
	{
		return getSWRLBuiltInArgumentFactory().getUnboundVariableArgument(uba.getVariableName());
	}

	public SWRLBuiltInArgument extract(SQWRLC sqwrlc) throws TargetRuleEngineException
	{
		return getSWRLBuiltInArgumentFactory().getSQWRLCollectionArgument(sqwrlc.getQueryName(),
				sqwrlc.getCollectionName(), sqwrlc.getCollectionID());
	}

	private DroolsL2OWLLiteralExtractor getOWLLiteralExtractor()
	{
		return this.literalExtractor;
	}
}
