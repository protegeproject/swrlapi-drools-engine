package org.swrlapi.drools.owl2rl;

import org.junit.Test;
import org.swrlapi.owl2rl.OWL2RLNames.OWL2RLRule;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.swrlapi.owl2rl.OWL2RLNames.OWL2RLRule.*;

public class OWL2RLRuleCoverageTest
{
  @Test public void everyStaticRuleHasAnIsolatedCaseAndOtherPathsAreExplicit()
  {
    DroolsOWL2RLRules rules = new DroolsOWL2RLRules();
    rules.defineRules();
    Set<OWL2RLRule> covered = EnumSet.of(DT_TYPE2); // DatatypeMembershipTest inspects internal membership facts.
    addRules(covered, OWL2RLRuleTest.cases());
    addRules(covered, OWL2RLSchemaRuleTest.cases());
    addRules(covered, DatatypeRuleTest.cases());

    // Generated chains/keys, pairwise conversion/grouped rules, declarations and literal value operations
    // are covered through their converters and public API tests, rather than standalone DRL definitions.
    Set<OWL2RLRule> otherPaths = EnumSet.of(PRP_SPO2, PRP_KEY, EQ_REP_P, EQ_DIFF2, EQ_DIFF3,
      PRP_ADP, CAX_ADC, PRP_AP, DT_TYPE1, DT_EQ, DT_DIFF);
    assertEquals(67, covered.size());
    for (OWL2RLRule rule : OWL2RLRule.values()) {
      if (otherPaths.contains(rule))
        assertFalse("documented non-static path: " + rule, rules.hasRule(rule));
      else {
        assertTrue("missing executable rule: " + rule, rules.hasRule(rule));
        assertTrue("missing isolated case: " + rule, covered.contains(rule));
      }
    }
  }

  private void addRules(Set<OWL2RLRule> covered, Collection<Object[]> cases)
  {
    assertFalse("a coverage source must not be empty", cases.isEmpty());
    cases.forEach(row -> covered.add((OWL2RLRule)row[1]));
  }
}
