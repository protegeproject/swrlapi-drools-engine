package org.swrlapi.drools.converters;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.drools.DroolsSWRLRuleEngine;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.ext.SWRLAPIRule;

public class DroolsSWRLRuleConverter extends DroolsConverterBase
{
	private final DroolsSWRLBodyAtom2DRLConverter bodyAtomConverter;
	private final DroolsSWRLHeadAtom2DRLConverter headAtomConverter;

	private final DroolsSWRLRuleEngine droolsEngine;

	public DroolsSWRLRuleConverter(SWRLRuleEngineBridge bridge, DroolsSWRLRuleEngine droolsEngine)
	{
		super(bridge);

		this.bodyAtomConverter = new DroolsSWRLBodyAtom2DRLConverter(bridge);
		this.headAtomConverter = new DroolsSWRLHeadAtom2DRLConverter(bridge);

		this.droolsEngine = droolsEngine;
	}

	public void convert(SWRLAPIRule rule) throws TargetRuleEngineException
	{
		String ruleName = rule.getRuleName();
		String drlRule = getRulePreamble(ruleName);
		Set<String> variableNames = new HashSet<String>();

		getDroolsSWRLBodyAtomConverter().reset();
		getDroolsSWRLHeadAtomConverter().reset();

		for (SWRLAtom atom : rule.getBodyAtoms())
			drlRule += "\n   " + getDroolsSWRLBodyAtomConverter().convert(atom, variableNames) + " ";

		drlRule = addRuleThenClause(drlRule);

		// TODO HACK!!! Need to reference these variables before use or will get null pointer error when invoking built-ins
		// for (String variableName : variableNames)
		// drlRule += "$" + variableName + ";";

		for (SWRLAtom atom : rule.getHeadAtoms())
			drlRule += "\n   " + getDroolsSWRLHeadAtomConverter().convert(atom) + " ";

		drlRule = addRuleEndClause(drlRule);

		// System.err.println("----------------------------------------------------------------------------------------------------");
		// System.err.println("SWRL: " + rule.getRuleText());
		// System.err.println("DRL:\n" + drlRule);
		getDroolsEngine().defineDRLRule(ruleName, drlRule);
	}

	private String getRulePreamble(String ruleName)
	{
		return "rule \"" + ruleName + "\" \nwhen ";
	}

	private String addRuleEndClause(String ruleText)
	{
		return ruleText + "\nend";
	}

	private String addRuleThenClause(String ruleText)
	{
		return ruleText + "\nthen ";
	}

	private DroolsSWRLBodyAtom2DRLConverter getDroolsSWRLBodyAtomConverter()
	{
		return this.bodyAtomConverter;
	}

	private DroolsSWRLHeadAtom2DRLConverter getDroolsSWRLHeadAtomConverter()
	{
		return this.headAtomConverter;
	}

	private DroolsSWRLRuleEngine getDroolsEngine()
	{
		return this.droolsEngine;
	}
}
