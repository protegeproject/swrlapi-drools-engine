package org.swrlapi.drools.owl2rl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.drools.core.DroolsRuleDefinition;
import org.swrlapi.owl2rl.AbstractOWL2RLEngine;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Class providing a Drools implementation of an OWL 2 RL-based reasoner.
 * <p>
 * This engine is created by a {@link org.swrlapi.drools.core.DroolsSWRLRuleEngine}. The OWL 2 RL rules are defined in
 * the {@link org.swrlapi.drools.owl2rl.DroolsOWL2RLRules} class. All axioms inferred by Drools rules are handled by a
 * {@link org.swrlapi.drools.reasoner.DefaultDroolsOWLAxiomHandler}.
 *
 * @see org.swrlapi.owl2rl.AbstractOWL2RLEngine
 * @see org.swrlapi.drools.owl2rl.DroolsOWL2RLRules
 * @see org.swrlapi.drools.core.DroolsSWRLRuleEngine
 * @see org.swrlapi.drools.reasoner.DefaultDroolsOWLAxiomHandler
 */
public class DroolsOWL2RLEngine extends AbstractOWL2RLEngine
{
  @NonNull private final DroolsOWL2RLRules droolsOWL2RLRules;

  public DroolsOWL2RLEngine(@NonNull OWL2RLPersistenceLayer persistenceLayer)
  {
    super(persistenceLayer, generateUnsupportedRules(), generatePermanentlyOnRules(), generateGroupedRuleSets());

    this.droolsOWL2RLRules = new DroolsOWL2RLRules();

    this.droolsOWL2RLRules.defineRules();
  }

  @NonNull public Set<@NonNull DroolsRuleDefinition> getEnabledRuleDefinitions()
  {
    Set<@NonNull DroolsRuleDefinition> enabledRuleDefinitions = new HashSet<>();

    for (OWL2RLRule rule : getEnabledRules()) {
      if (this.droolsOWL2RLRules.hasRule(rule))
        enabledRuleDefinitions.addAll(this.droolsOWL2RLRules.getRules(rule));
    }

    return enabledRuleDefinitions;
  }

  /**
   * These are rules that are always enabled and that cannot be disabled.
   */
  @NonNull private static Set<OWL2RLRule> generatePermanentlyOnRules()
  {
    return EnumSet.of(OWL2RLRule.CLS_THING, OWL2RLRule.CLS_NOTHING1, OWL2RLRule.CLS_NOTHING2, OWL2RLRule.DT_TYPE1,
      OWL2RLRule.DT_TYPE2, OWL2RLRule.DT_EQ, OWL2RLRule.DT_DIFF);
  }

  /**
   * These are rules that must be enabled or disabled in groups.
   */
  @NonNull private static Set<@NonNull Set<OWL2RLRule>> generateGroupedRuleSets()
  {
    Set<@NonNull Set<OWL2RLRule>> groupedRuleSets = new HashSet<>();

    groupedRuleSets.add(EnumSet.of(OWL2RLRule.EQ_DIFF1, OWL2RLRule.EQ_DIFF2, OWL2RLRule.EQ_DIFF3));
    groupedRuleSets.add(EnumSet.of(OWL2RLRule.PRP_EQP1, OWL2RLRule.PRP_EQP2, OWL2RLRule.EQ_REP_P));
    groupedRuleSets.add(EnumSet.of(OWL2RLRule.PRP_PDW, OWL2RLRule.PRP_ADP));
    groupedRuleSets.add(EnumSet.of(OWL2RLRule.CAX_ADC, OWL2RLRule.CAX_DW));

    return groupedRuleSets;
  }

  @NonNull private static Set<OWL2RLRule> generateUnsupportedRules()
  {
    return EnumSet.noneOf(OWL2RLRule.class);
  }
}
