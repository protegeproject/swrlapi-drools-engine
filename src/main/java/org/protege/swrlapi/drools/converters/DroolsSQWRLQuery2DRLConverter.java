package org.protege.swrlapi.drools.converters;

import java.util.HashSet;
import java.util.Set;

import org.protege.swrlapi.converters.TargetRuleEngineConverterBase;
import org.protege.swrlapi.core.SWRLRuleEngineBridge;
import org.protege.swrlapi.drools.DroolsNames;
import org.protege.swrlapi.drools.DroolsSWRLRuleEngine;
import org.protege.swrlapi.exceptions.BuiltInException;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.protege.swrlapi.ext.SWRLAPIBuiltInAtom;
import org.protege.swrlapi.sqwrl.SQWRLNames;
import org.protege.swrlapi.sqwrl.SQWRLQuery;
import org.protege.swrlapi.sqwrl.TargetRuleEngineSQWRLQueryConverter;
import org.semanticweb.owlapi.model.SWRLAtom;

public class DroolsSQWRLQuery2DRLConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineSQWRLQueryConverter
{
	private final DroolsSWRLBodyAtom2DRLConverter bodyAtomConverter;
	private final DroolsSWRLHeadAtom2DRLConverter headAtomConverter;
	private final DroolsSWRLRuleEngine droolsEngine;

	public DroolsSQWRLQuery2DRLConverter(SWRLRuleEngineBridge bridge, DroolsSWRLRuleEngine droolsEngine)
	{
		super(bridge);

		this.droolsEngine = droolsEngine;
		this.bodyAtomConverter = new DroolsSWRLBodyAtom2DRLConverter(bridge);
		this.headAtomConverter = new DroolsSWRLHeadAtom2DRLConverter(bridge);
	}

	public void reset()
	{
		this.bodyAtomConverter.reset();
		this.headAtomConverter.reset();
	}

	@Override
	public void convert(SQWRLQuery query) throws TargetRuleEngineException, BuiltInException
	{
		getSWRLBodyAtomConverter().reset();
		getSWRLHeadAtomConverter().reset();

		sqwrlQuery2DRL(query);
	}

	private void sqwrlQuery2DRL(SQWRLQuery query) throws TargetRuleEngineException
	{
		if (!query.hasCollections())
			sqwrlNonCollectionQuery2DRL(query);
		else
			sqwrlCollectionQuery2DRL(query);
	}

	// System.err.println("----------------------------------------------------------------------------------------------------");
	// System.err.println("SWRL: " + rule.getRuleText());
	// System.err.println("DRL:\n" + drlRule);

	private void sqwrlNonCollectionQuery2DRL(SQWRLQuery query) throws TargetRuleEngineException
	{
		Set<String> variableNames = new HashSet<String>();
		String ruleName = query.getName();
		String drlRule = getQueryPreamble(ruleName);

		for (SWRLAtom atom : query.getBodyAtoms())
			drlRule += "\n   " + getSWRLBodyAtomConverter().convert(atom, variableNames) + " ";

		drlRule = addQueryThenClause(drlRule);

		for (SWRLAtom atom : query.getHeadAtoms())
			drlRule += "\n   " + getSWRLHeadAtomConverter().convert(atom) + " ";

		drlRule = addQueryEndClause(drlRule);

		getDroolsEngine().defineDRLSQWRLPhase1Rule(query.getName(), ruleName, drlRule);
	}

	private void sqwrlCollectionQuery2DRL(SQWRLQuery query) throws TargetRuleEngineException
	{
		Set<String> variableNames = new HashSet<String>();
		String queryName = query.getName();
		String phase1RuleName = queryName + "-makeCollection";
		String phase2RuleName = queryName + "-operateCollection";
		String drlPhase1Rule = getQueryPreamble(phase1RuleName);
		String drlPhase2Rule = getQueryPreamble(phase2RuleName);

		for (SWRLAtom atom : query.getSQWRLPhase1BodyAtoms())
			drlPhase1Rule += "\n  " + getSWRLBodyAtomConverter().convert(atom, variableNames) + " ";

		drlPhase1Rule = addQueryThenClause(drlPhase1Rule);

		if (query.hasCollections()) { // Assert existence of all relevant collections returned from collection construction
																	// built-ins
			try {
				for (SWRLAPIBuiltInAtom atom : query.getBuiltInAtomsFromBody(SQWRLNames.getCollectionMakeBuiltInNames())) {
					String collectionVariableName = atom.getArgumentVariableName(0);
					drlPhase1Rule += "\n  sqwrlInferrer.infer($" + collectionVariableName + "); ";
				}
			} catch (RuntimeException e) {
				throw new TargetRuleEngineException("error processing SQWRL collection make in query " + queryName + ": "
						+ e.getMessage(), e);
			}
		}
		drlPhase1Rule = addQueryEndClause(drlPhase1Rule);
		getDroolsEngine().defineDRLSQWRLPhase1Rule(queryName, phase1RuleName, drlPhase1Rule);

		variableNames.clear();

		if (query.hasCollections()) { // Match relevant collections
			try {
				for (SWRLAPIBuiltInAtom atom : query.getBuiltInAtomsFromBody(SQWRLNames.getCollectionMakeBuiltInNames())) {
					String collectionVariableName = atom.getArgumentVariableName(0);
					if (!variableNames.contains(collectionVariableName)) {
						String collectionName = collectionVariableName;
						drlPhase2Rule += "\n  $" + collectionVariableName + ":" + DroolsNames.SQWRLCollectionClassName + "("
								+ DroolsNames.QueryNameFieldName + "==\"" + queryName + "\", " + DroolsNames.CollectionNameFieldName
								+ "==\"" + collectionName + "\")";
						variableNames.add(collectionVariableName);
					}
				}
			} catch (RuntimeException e) {
				throw new TargetRuleEngineException("error processing SQWRL collection operate in query " + queryName + ": "
						+ e.getMessage(), e);
			}
		}
		for (SWRLAtom atom : query.getSQWRLPhase2BodyAtoms())
			drlPhase2Rule += "\n  " + getSWRLBodyAtomConverter().convert(atom, variableNames) + " ";

		drlPhase2Rule = addQueryThenClause(drlPhase2Rule);
		for (SWRLAtom atom : query.getHeadAtoms())
			drlPhase2Rule += "\n  " + getSWRLHeadAtomConverter().convert(atom);

		drlPhase2Rule = addQueryEndClause(drlPhase2Rule);
		getDroolsEngine().defineDRLSQWRLPhase2Rule(queryName, phase2RuleName, drlPhase2Rule);
	}

	private String getQueryPreamble(String queryName)
	{
		return "rule \"" + queryName + "\" \nwhen ";
	}

	private String addQueryEndClause(String queryText)
	{
		return queryText + "\nend";
	}

	private String addQueryThenClause(String queryText)
	{
		return queryText + "\nthen ";
	}

	private DroolsSWRLBodyAtom2DRLConverter getSWRLBodyAtomConverter()
	{
		return this.bodyAtomConverter;
	}

	private DroolsSWRLHeadAtom2DRLConverter getSWRLHeadAtomConverter()
	{
		return this.headAtomConverter;
	}

	private DroolsSWRLRuleEngine getDroolsEngine()
	{
		return this.droolsEngine;
	}
}
