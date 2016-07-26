package org.swrlapi.drools.converters.drl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.drools.converters.id.DroolsOWLClassExpression2IDConverter;
import org.swrlapi.drools.core.DroolsSWRLRuleEngine;
import org.swrlapi.exceptions.SWRLBuiltInException;

import java.util.HashSet;
import java.util.Set;

/**
 * This class converts SWRLAPI rules to their Drools representation.
 *
 * @see org.swrlapi.core.SWRLAPIRule
 */
public class DroolsSWRLRule2DRLConverter extends DroolsDRLConverterBase
{
  @NonNull private final DroolsSWRLBodyAtom2DRLConverter bodyAtom2DRLConverter;
  @NonNull private final DroolsSWRLHeadAtom2DRLConverter headAtom2DRLConverter;
  @NonNull private final DroolsSWRLRuleEngine droolsSWRLRuleEngine;

  public DroolsSWRLRule2DRLConverter(@NonNull SWRLRuleEngineBridge bridge,
    @NonNull DroolsSWRLRuleEngine droolsSWRLRuleEngine,
    @NonNull DroolsOWLClassExpression2IDConverter classExpression2DRLConverter,
    @NonNull DroolsOWLPropertyExpression2DRLConverter propertyExpression2DRLConverter)
  {
    super(bridge);

    this.bodyAtom2DRLConverter = new DroolsSWRLBodyAtom2DRLConverter(bridge, classExpression2DRLConverter,
      propertyExpression2DRLConverter);
    this.headAtom2DRLConverter = new DroolsSWRLHeadAtom2DRLConverter(bridge, classExpression2DRLConverter,
      propertyExpression2DRLConverter);

    this.droolsSWRLRuleEngine = droolsSWRLRuleEngine;
  }

  public void convert(@NonNull SWRLAPIRule rule) throws SWRLBuiltInException
  {
    String ruleName = rule.getRuleName();
    String drlRule = getRulePreamble(ruleName);
    Set<@NonNull String> previouslyEncounteredVariableNames = new HashSet<>();

    getDroolsSWRLBodyAtom2DRLConverter().reset();
    getDroolsSWRLHeadAtom2DRLConverter().reset();

    for (SWRLAtom atom : rule.getBodyAtoms())
      drlRule +=
        "\n   " + getDroolsSWRLBodyAtom2DRLConverter().convert(atom, previouslyEncounteredVariableNames) + " ";

    drlRule = addRuleThenClause(drlRule);

    // Old code to reference these variables before use or got null pointer error in Drools when invoking built-ins.
    // Seems to be unnecessary now.
    // for (String variablePrefixedName : variablePrefixedNames)
    // drlRule += getDroolsSWRLVariable2NameConverter().variableName2DRL(variableName);

    for (SWRLAtom atom : rule.getHeadAtoms())
      drlRule += "\n   " + getDroolsSWRLHeadAtom2DRLConverter().convert(atom) + " ";

    drlRule = addRuleEndClause(drlRule);

    // System.out.println("---------------------------------------------------------------------------------------");
    // System.out.println("DRL:\n" + drlRule);
               getDroolsSWRLRuleEngine().defineDRLRule(drlRule);
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

  @NonNull private DroolsSWRLBodyAtom2DRLConverter getDroolsSWRLBodyAtom2DRLConverter()
  {
    return this.bodyAtom2DRLConverter;
  }

  @NonNull private DroolsSWRLHeadAtom2DRLConverter getDroolsSWRLHeadAtom2DRLConverter()
  {
    return this.headAtom2DRLConverter;
  }

  @NonNull private DroolsSWRLRuleEngine getDroolsSWRLRuleEngine()
  {
    return this.droolsSWRLRuleEngine;
  }
}
