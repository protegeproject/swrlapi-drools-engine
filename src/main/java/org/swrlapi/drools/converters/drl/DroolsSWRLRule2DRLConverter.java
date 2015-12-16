package org.swrlapi.drools.converters.drl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.core.SWRLAPIRule;

import java.util.HashSet;
import java.util.Set;

/**
 * This class converts SWRLAPI rules to their Drools representation.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 */
public class DroolsSWRLRule2DRLConverter extends DroolsDRLConverterBase
{
  @NonNull private final DroolsSWRLBodyAtom2DRLConverter bodyAtomConverter;
  @NonNull private final DroolsSWRLHeadAtom2DRLConverter headAtomConverter;

  public DroolsSWRLRule2DRLConverter(@NonNull SWRLRuleEngineBridge bridge,
    @NonNull DroolsOWLClassExpression2DRLConverter classExpressionConverter,
    @NonNull DroolsOWLPropertyExpression2DRLConverter propertyExpressionConverter)
  {
    super(bridge);

    this.bodyAtomConverter = new DroolsSWRLBodyAtom2DRLConverter(bridge, classExpressionConverter,
      propertyExpressionConverter);
    this.headAtomConverter = new DroolsSWRLHeadAtom2DRLConverter(bridge, classExpressionConverter,
      propertyExpressionConverter);
  }

  public String convert(@NonNull SWRLAPIRule rule)
  {
    String ruleName = rule.getRuleName();
    String drlRule = getRulePreamble(ruleName);
    Set<@NonNull String> previouslyEncounteredVariablePrefixedNames = new HashSet<>();

    getDroolsSWRLBodyAtomConverter().reset();
    getDroolsSWRLHeadAtomConverter().reset();

    for (SWRLAtom atom : rule.getBodyAtoms())
      drlRule +=
        "\n   " + getDroolsSWRLBodyAtomConverter().convert(atom, previouslyEncounteredVariablePrefixedNames) + " ";

    drlRule = addRuleThenClause(drlRule);

    // Old code to reference these variables before use or got null pointer error in Drools when invoking built-ins.
    // Seems to be unnecessary now.
    // for (String variablePrefixedName : variablePrefixedNames)
    // drlRule += getDroolsSWRLVariable2NameConverter().variablePrefixedName2DRL(variablePrefixedName);

    for (SWRLAtom atom : rule.getHeadAtoms())
      drlRule += "\n   " + getDroolsSWRLHeadAtomConverter().convert(atom) + " ";

    drlRule = addRuleEndClause(drlRule);

    // System.out.println("---------------------------------------------------------------------------------------");
    // System.out.println("DRL:\n" + drlRule);
    return drlRule;
  }

  @NonNull private String getRulePreamble(@NonNull String ruleName)
  {
    return "rule \"" + ruleName + "\" \nwhen ";
  }

  @NonNull private String addRuleEndClause(@NonNull String ruleText)
  {
    return ruleText + "\nend";
  }

  @NonNull private String addRuleThenClause(@NonNull String ruleText)
  {
    return ruleText + "\nthen ";
  }

  @NonNull private DroolsSWRLBodyAtom2DRLConverter getDroolsSWRLBodyAtomConverter()
  {
    return this.bodyAtomConverter;
  }

  @NonNull private DroolsSWRLHeadAtom2DRLConverter getDroolsSWRLHeadAtomConverter()
  {
    return this.headAtomConverter;
  }
}
