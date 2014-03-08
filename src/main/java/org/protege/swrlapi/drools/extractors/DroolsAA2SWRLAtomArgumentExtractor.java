package org.protege.swrlapi.drools.extractors;

import org.protege.swrlapi.core.SWRLRuleEngineBridge;
import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.core.arguments.SWRLVariableAtomArgument;
import org.protege.swrlapi.drools.owl.L;
import org.protege.swrlapi.drools.owl.entities.AP;
import org.protege.swrlapi.drools.owl.entities.C;
import org.protege.swrlapi.drools.owl.entities.D;
import org.protege.swrlapi.drools.owl.entities.DP;
import org.protege.swrlapi.drools.owl.entities.I;
import org.protege.swrlapi.drools.owl.entities.OP;
import org.protege.swrlapi.drools.sqwrl.SQWRLC;
import org.protege.swrlapi.drools.swrl.AA;
import org.protege.swrlapi.drools.swrl.UBA;
import org.protege.swrlapi.drools.swrl.VA;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.protege.swrlapi.extractors.TargetRuleEngineExtractorBase;
import org.protege.swrlapi.extractors.TargetRuleEngineSWRLAtomArgumentExtractor;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;

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
		return getSWRLAtomArgumentFactory().createVariableArgument(va.getVariableName());
	}

	public SWRLBuiltInArgument extract(C c) throws TargetRuleEngineException
	{
		String classPrefixedName = c.getid();
		IRI classIRI = getIRI(classPrefixedName);

		return getSWRLBuiltInArgumentFactory().createClassArgument(classIRI);
	}

	public SWRLBuiltInArgument extract(I i) throws TargetRuleEngineException
	{
		String individualPrefixedName = i.getid();
		IRI individualIRI = getIRI(individualPrefixedName);

		return getSWRLBuiltInArgumentFactory().createIndividualArgument(individualIRI);
	}

	public SWRLBuiltInArgument extract(DP dp) throws TargetRuleEngineException
	{
		String propertyPrefixedName = dp.getid();
		IRI propertyIRI = getIRI(propertyPrefixedName);

		return getSWRLBuiltInArgumentFactory().createDataPropertyArgument(propertyIRI);
	}

	public SWRLBuiltInArgument extract(AP ap) throws TargetRuleEngineException
	{
		String propertyPrefixedName = ap.getid();
		IRI propertyIRI = getIRI(propertyPrefixedName);

		return getSWRLBuiltInArgumentFactory().createDataPropertyArgument(propertyIRI);
	}

	public SWRLBuiltInArgument extract(OP op) throws TargetRuleEngineException
	{
		String propertyPrefixedName = op.getid();
		IRI propertyIRI = getIRI(propertyPrefixedName);

		return getSWRLBuiltInArgumentFactory().createObjectPropertyArgument(propertyIRI);
	}

	public SWRLBuiltInArgument extract(D d) throws TargetRuleEngineException
	{
		String datatypePrefixedName = d.getid();
		IRI datatypeIRI = getIRI(datatypePrefixedName);

		return getSWRLBuiltInArgumentFactory().createDatatypeArgument(datatypeIRI);
	}

	public SWRLBuiltInArgument extract(L l) throws TargetRuleEngineException
	{
		OWLLiteral literal = getOWLLiteralExtractor().extract(l);

		return getSWRLBuiltInArgumentFactory().createLiteralArgument(literal);
	}

	public SWRLBuiltInArgument extract(UBA uba) throws TargetRuleEngineException
	{
		return getSWRLBuiltInArgumentFactory().createUnboundVariableArgument(uba.getVariableName());
	}

	public SWRLBuiltInArgument extract(SQWRLC sqwrlc) throws TargetRuleEngineException
	{
		return getSWRLBuiltInArgumentFactory().createSQWRLCollectionArgument(sqwrlc.getQueryName(),
				sqwrlc.getCollectionName(), sqwrlc.getCollectionID());
	}

	private DroolsL2OWLLiteralExtractor getOWLLiteralExtractor()
	{
		return this.literalExtractor;
	}
}
