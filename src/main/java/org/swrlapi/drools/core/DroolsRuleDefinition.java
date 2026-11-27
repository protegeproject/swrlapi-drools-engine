package org.swrlapi.drools.core;

import org.checkerframework.checker.nullness.qual.NonNull;

public class DroolsRuleDefinition
{
  private final @NonNull String ruleName, ruleText;

  public DroolsRuleDefinition(@NonNull String ruleName, @NonNull String ruleText)
  {
    this.ruleName = ruleName;
    this.ruleText = ruleText;
  }

  public @NonNull String getRuleName()
  {
    return this.ruleName;
  }

  public @NonNull String getRuleText()
  {
    return this.ruleText;
  }
}
