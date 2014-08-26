package org.swrlapi.drools.converters;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.SWRLAtom;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.drools.core.DroolsSWRLRuleEngine;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class converts SWRLAPI rules to their Drools representation.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 */
public class DroolsSWRLRuleConverter extends DroolsConverterBase
{
	private final DroolsSWRLBodyAtom2DRLConverter bodyAtomConverter;
	private final DroolsSWRLHeadAtom2DRLConverter headAtomConverter;

	private final DroolsSWRLRuleEngine droolsEngine;

	public DroolsSWRLRuleConverter(SWRLRuleEngineBridge bridge, DroolsSWRLRuleEngine droolsEngine,
			DroolsOWLClassExpressionConverter classExpressionConverter,
			DroolsOWLPropertyExpressionConverter propertyExpressionConverter)
	{
		super(bridge);

		this.bodyAtomConverter = new DroolsSWRLBodyAtom2DRLConverter(bridge, classExpressionConverter,
				propertyExpressionConverter);
		this.headAtomConverter = new DroolsSWRLHeadAtom2DRLConverter(bridge, classExpressionConverter,
				propertyExpressionConverter);

		this.droolsEngine = droolsEngine;
	}

	public void convert(SWRLAPIRule rule)
	{
		String ruleName = rule.getRuleName();
		String drlRule = getRulePreamble(ruleName);
		Set<String> previouslyEncounteredVariablePrefixedNames = new HashSet<String>();

		getDroolsSWRLBodyAtomConverter().reset();
		getDroolsSWRLHeadAtomConverter().reset();

		for (SWRLAtom atom : rule.getBodyAtoms())
			drlRule += "\n   " + getDroolsSWRLBodyAtomConverter().convert(atom, previouslyEncounteredVariablePrefixedNames)
					+ " ";

		drlRule = addRuleThenClause(drlRule);

		// Old code to reference these variables before use or got null pointer error in Drools when invoking built-ins.
		// Seems to be unnecessary now.
		// for (String variablePrefixedName : variablePrefixedNames)
		// drlRule += getDroolsSWRLVariableConverter().variablePrefixedName2DRL(variablePrefixedName);

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
