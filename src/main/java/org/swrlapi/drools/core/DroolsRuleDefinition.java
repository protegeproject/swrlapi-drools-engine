package org.swrlapi.drools.core;

public class DroolsRuleDefinition
{
	private final String ruleName, ruleText;

	public DroolsRuleDefinition(String ruleName, String ruleText)
	{
		this.ruleName = ruleName;
		this.ruleText = ruleText;
	}

	public String getRuleName()
	{
		return this.ruleName;
	}

	public String getRuleText()
	{
		return this.ruleText;
	}
}
