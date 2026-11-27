package org.swrlapi.drools.owl2rl;

import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.swrlapi.drools.owl.axioms.*;
import org.swrlapi.drools.owl.classes.*;
import org.swrlapi.drools.owl.literals.L;
import org.swrlapi.drools.owl.literals.LiteralTypeAssertion;
import org.swrlapi.owl2rl.OWL2RLNames.OWL2RLRule;
import static org.junit.Assert.*;
import static org.swrlapi.owl2rl.OWL2RLNames.OWL2RLRule.*;

/** Value semantics exercised without alternate inference paths. */
@RunWith(Parameterized.class)
public class DatatypeRuleTest
{
  private final OWL2RLRule rule;
  private final Object[] facts;
  private final boolean inconsistent;
  private final Set<A> expected;

  public DatatypeRuleTest(String name, OWL2RLRule rule, Object[] facts, boolean inconsistent, Set<A> expected) {
    this.rule = rule; this.facts = facts; this.inconsistent = inconsistent; this.expected = expected;
  }

  private static Object[] row(String name, OWL2RLRule rule, boolean conflict, Object[] facts, A... expected) {
    return new Object[]{name, rule, facts, conflict, new HashSet<>(Arrays.asList(expected))};
  }

  @Parameterized.Parameters(name = "{0}") public static Collection<Object[]> cases() {
    L one = new L("01", "xsd:integer"), equal = new L("1.0", "xsd:decimal"), two = new L("2", "xsd:int");
    List<Object[]> cases = new ArrayList<>();
    L[] others = {equal, two, new L("1", "xsd:string"), new L("unknown", "urn:custom")};
    for (int n = 0; n < others.length; n++) {
      L other = others[n];
      cases.add(row("functional pair " + n, PRP_FP, n == 1 || n == 2,
        new Object[]{new FDPA("p"), new DPAA("i", "p", one), new DPAA("i", "p", other)}));
      cases.add(row("negative pair " + n, PRP_NPA2, n == 0,
        new Object[]{new NDPAA("i", "p", one), new DPAA("i", "p", other)}));
      cases.add(row("disjoint pair " + n, PRP_PDW, n == 0,
        new Object[]{new DJDPA("p", "q"), new DPAA("i", "p", one), new DPAA("i", "q", other)}));
      cases.add(row("hasValue pair " + n, CLS_HV2, false,
        new Object[]{new DHVCE("C", "p", one), new DPAA("i", "p", other)},
        n == 0 ? new A[]{new CAA("C", "i")} : new A[]{}));
      cases.add(row("maximum one pair " + n, CLS_MAXC2, n == 1 || n == 2,
        new Object[]{new DMaxCCE("C", "p", 1), new CAA("C", "i"), new DPAA("i", "p", one), new DPAA("i", "p", other)}));
    }
    cases.add(row("functional missing declaration", PRP_FP, false,
      new Object[]{new DPAA("i", "p", one), new DPAA("i", "p", two)}));
    cases.add(row("functional distinct subjects", PRP_FP, false,
      new Object[]{new FDPA("p"), new DPAA("i", "p", one), new DPAA("j", "p", two)}));
    cases.add(row("functional distinct properties", PRP_FP, false,
      new Object[]{new FDPA("p"), new DPAA("i", "p", one), new DPAA("i", "q", two)}));
    cases.add(row("negative distinct subjects", PRP_NPA2, false,
      new Object[]{new NDPAA("i", "p", one), new DPAA("j", "p", equal)}));
    cases.add(row("disjoint distinct subjects", PRP_PDW, false,
      new Object[]{new DJDPA("p", "q"), new DPAA("i", "p", one), new DPAA("j", "q", equal)}));
    cases.add(row("hasValue distinct property", CLS_HV2, false,
      new Object[]{new DHVCE("C", "p", one), new DPAA("i", "q", equal)}));
    cases.add(row("maximum missing class membership", CLS_MAXC2, false,
      new Object[]{new DMaxCCE("C", "p", 1), new DPAA("i", "p", one), new DPAA("i", "p", two)}));
    cases.add(row("functional indeterminate timezone", PRP_FP, false,
      new Object[]{new FDPA("p"), new DPAA("i", "p", new L("2026-01-01T12:00:00", "xsd:dateTime")),
        new DPAA("i", "p", new L("2026-01-01T12:00:00Z", "xsd:dateTime"))}));
    cases.add(row("hasValue schema uses value equality", SCM_HV, false,
      new Object[]{new DHVCE("C", "p", one), new DHVCE("D", "q", equal), new SDPA("p", "q")}, new SCA("C", "D")));
    cases.add(row("hasValue schema rejects unequal values", SCM_HV, false,
      new Object[]{new DHVCE("C", "p", one), new DHVCE("D", "q", two), new SDPA("p", "q")}));
    for (int card : new int[]{0, 1}) {
      OWL2RLRule selected = card == 0 ? CLS_MAXQC1 : CLS_MAXQC3;
      Object restriction = new QualifiedDataMaxCardinality("C", "p", "xsd:integer", card);
      for (boolean membership : new boolean[]{false, true}) {
        List<Object> facts = new ArrayList<>(Arrays.asList(restriction, new CAA("C", "i"),
          new DPAA("i", "p", one), new DPAA("i", "p", two)));
        if (membership) {
          facts.add(new LiteralTypeAssertion(one, "xsd:integer"));
          facts.add(new LiteralTypeAssertion(two, "xsd:integer"));
        }
        cases.add(row("qualified " + card + " membership " + membership, selected, membership, facts.toArray()));
        cases.add(row("unqualified rule excludes qualified " + card + " membership " + membership,
          card == 0 ? CLS_MAXC1 : CLS_MAXC2, false, facts.toArray()));
      }
    }
    cases.add(row("qualified one requires both memberships", CLS_MAXQC3, false,
      new Object[]{new QualifiedDataMaxCardinality("C", "p", "xsd:integer", 1), new CAA("C", "i"),
        new DPAA("i", "p", one), new DPAA("i", "p", two), new LiteralTypeAssertion(one, "xsd:integer")}));
    cases.add(row("some values requires membership", CLS_SFV1, false,
      new Object[]{new DSVFCE("C", "p", "xsd:integer"), new DPAA("i", "p", one)}));
    cases.add(row("some values with membership", CLS_SFV1, false,
      new Object[]{new DSVFCE("C", "p", "xsd:integer"), new DPAA("i", "p", one), new LiteralTypeAssertion(one, "xsd:integer")}, new CAA("C", "i")));
    cases.add(row("legacy qualified top data range is Literal", CLS_MAXQC2, true,
      new Object[]{new DMaxQCCE("C", "p", "rdfs:Literal", 0), new CAA("C", "i"), new DPAA("i", "p", one)}));
    cases.add(row("owl Thing is not the top data range", CLS_MAXQC2, false,
      new Object[]{new DMaxQCCE("C", "p", "owl:Thing", 0), new CAA("C", "i"), new DPAA("i", "p", one)}));
    return cases;
  }

  @Test public void valueSemanticsInIsolation() {
    DroolsOWL2RLRules rules = new DroolsOWL2RLRules(); rules.defineRules();
    assertFalse("rule must be present", rules.getRules(rule).isEmpty());
    StringBuilder drl = new StringBuilder("package test; import org.swrlapi.drools.owl.axioms.*; import org.swrlapi.drools.owl.classes.*; global org.swrlapi.drools.owl2rl.OWL2RLRuleTest.Recorder inferrer;\n");
    rules.getRules(rule).forEach(definition -> drl.append(definition.getRuleText()).append('\n'));
    KieSession session = new KieHelper().addContent(drl.toString(), ResourceType.DRL).build().newKieSession();
    try {
      OWL2RLRuleTest.Recorder recorder = new OWL2RLRuleTest.Recorder(session, facts);
      session.setGlobal("inferrer", recorder);
      for (Object fact : facts) session.insert(fact);
      assertTrue("rules must terminate", session.fireAllRules(1000) < 1000);
      assertEquals("inconsistency", inconsistent, recorder.inconsistent);
      assertEquals("exact conclusions", expected, recorder.inferred);
    } finally { session.dispose(); }
  }
}
