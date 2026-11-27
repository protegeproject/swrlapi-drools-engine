package org.swrlapi.drools.owl2rl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.swrlapi.drools.owl.axioms.*;
import org.swrlapi.drools.owl.classes.*;
import org.swrlapi.drools.owl.literals.L;
import org.swrlapi.owl2rl.OWL2RLNames;
import org.swrlapi.owl2rl.OWL2RLNames.OWL2RLRule;

import java.util.*;

import static org.junit.Assert.*;
import static org.swrlapi.owl2rl.OWL2RLNames.OWL2RLRule.*;

/** Table 9 conclusions and each missing-premise control, without other schema rules. */
@RunWith(Parameterized.class)
public class OWL2RLSchemaRuleTest
{
  private final OWL2RLRule rule;
  private final Object[] facts;
  private final Set<A> expected;

  public OWL2RLSchemaRuleTest(String name, OWL2RLRule rule, Object[] facts, Set<A> expected)
  {
    this.rule = rule;
    this.facts = facts;
    this.expected = expected;
  }

  private static Object[] row(String name, OWL2RLRule rule, Object[] facts, A... expected)
  {
    return new Object[] { name, rule, facts, new HashSet<>(Arrays.asList(expected)) };
  }

  @Parameterized.Parameters(name = "{0}") public static Collection<Object[]> cases()
  {
    L one = new L("1", "xsd:integer");
    List<Object[]> rows = Arrays.asList(
      row("class bounds", SCM_CLS, new Object[]{new CDA("A")}, new SCA("A", "A"), new ECA("A", "A"), new SCA("A", "owl:Thing"), new SCA("owl:Nothing", "A")),
      row("subclass transitivity", SCM_SCO, new Object[]{new SCA("A", "B"), new SCA("B", "C")}, new SCA("A", "C")),
      row("equivalent classes to subclasses", SCM_EQC1, new Object[]{new ECA("A", "B")}, new SCA("A", "B"), new SCA("B", "A")),
      row("mutual subclasses", SCM_EQC2, new Object[]{new SCA("A", "B"), new SCA("B", "A")}, new ECA("A", "B"), new ECA("B", "A")),
      row("object property reflexivity", SCM_OP, new Object[]{new OPDA("p")}, new SOPA("p", "p"), new EOPA("p", "p")),
      row("data property reflexivity", SCM_DP, new Object[]{new DPDA("p")}, new SDPA("p", "p"), new EDPA("p", "p")),
      row("object subproperty transitivity", SCM_SPO, new Object[]{new SOPA("p", "q"), new SOPA("q", "r")}, new SOPA("p", "r")),
      row("data subproperty transitivity", SCM_SPO, new Object[]{new SDPA("p", "q"), new SDPA("q", "r")}, new SDPA("p", "r")),
      row("equivalent object properties", SCM_EQP1, new Object[]{new EOPA("p", "q")}, new SOPA("p", "q"), new SOPA("q", "p")),
      row("equivalent data properties", SCM_EQP1, new Object[]{new EDPA("p", "q")}, new SDPA("p", "q"), new SDPA("q", "p")),
      row("mutual object subproperties", SCM_EQP2, new Object[]{new SOPA("p", "q"), new SOPA("q", "p")}, new EOPA("p", "q"), new EOPA("q", "p")),
      row("mutual data subproperties", SCM_EQP2, new Object[]{new SDPA("p", "q"), new SDPA("q", "p")}, new EDPA("p", "q"), new EDPA("q", "p")),
      row("object domain superclass", SCM_DOM1, new Object[]{new DOPA("p", "A"), new SCA("A", "B")}, new DOPA("p", "B")),
      row("data domain superclass", SCM_DOM1, new Object[]{new DDPA("p", "A"), new SCA("A", "B")}, new DDPA("p", "B")),
      row("object domain subproperty", SCM_DOM2, new Object[]{new DOPA("q", "A"), new SOPA("p", "q")}, new DOPA("p", "A")),
      row("data domain subproperty", SCM_DOM2, new Object[]{new DDPA("q", "A"), new SDPA("p", "q")}, new DDPA("p", "A")),
      row("range superclass", SCM_RNG1, new Object[]{new OPRA("p", "A"), new SCA("A", "B")}, new OPRA("p", "B")),
      row("object range subproperty", SCM_RNG2, new Object[]{new OPRA("q", "A"), new SOPA("p", "q")}, new OPRA("p", "A")),
      row("data range subproperty", SCM_RNG2, new Object[]{new DPRA("q", "xsd:integer"), new SDPA("p", "q")}, new DPRA("p", "xsd:integer")),
      row("object hasValue covariance", SCM_HV, new Object[]{new OHVCE("X", "p", "i"), new OHVCE("Y", "q", "i"), new SOPA("p", "q")}, new SCA("X", "Y")),
      row("data hasValue covariance", SCM_HV, new Object[]{new DHVCE("X", "p", one), new DHVCE("Y", "q", one), new SDPA("p", "q")}, new SCA("X", "Y")),
      row("existential filler covariance", SCM_SVF1, new Object[]{new OSVFCE("X", "p", "A"), new OSVFCE("Y", "p", "B"), new SCA("A", "B")}, new SCA("X", "Y")),
      row("object existential property covariance", SCM_SVF2, new Object[]{new OSVFCE("X", "p", "A"), new OSVFCE("Y", "q", "A"), new SOPA("p", "q")}, new SCA("X", "Y")),
      row("data existential property covariance", SCM_SVF2, new Object[]{new DSVFCE("X", "p", "xsd:integer"), new DSVFCE("Y", "q", "xsd:integer"), new SDPA("p", "q")}, new SCA("X", "Y")),
      row("universal filler covariance", SCM_AVF1, new Object[]{new OAVFCE("X", "p", "A"), new OAVFCE("Y", "p", "B"), new SCA("A", "B")}, new SCA("X", "Y")),
      row("object universal property contravariance", SCM_AVF2, new Object[]{new OAVFCE("X", "p", "A"), new OAVFCE("Y", "q", "A"), new SOPA("p", "q")}, new SCA("Y", "X")),
      row("data universal property contravariance", SCM_AVF2, new Object[]{new DAVFCE("X", "p", "xsd:integer"), new DAVFCE("Y", "q", "xsd:integer"), new SDPA("p", "q")}, new SCA("Y", "X")),
      row("intersection subclasses", SCM_INT, new Object[]{new OIOCE("X", new HashSet<>(Arrays.asList("A", "B")))}, new SCA("X", "A"), new SCA("X", "B")),
      row("union superclasses", SCM_UNI, new Object[]{new OUOCE("X", new HashSet<>(Arrays.asList("A", "B")))}, new SCA("A", "X"), new SCA("B", "X")));
    Set<OWL2RLRule> covered = EnumSet.noneOf(OWL2RLRule.class);
    rows.forEach(row -> covered.add((OWL2RLRule)row[1]));
    assertEquals("every Table 9 rule has an isolated case", EnumSet.copyOf(Arrays.asList(OWL2RLNames.Table9Rules)), covered);
    return rows;
  }

  @Test public void derivesOnlyExpectedConclusionsAndRequiresEveryPremise()
  {
    DroolsOWL2RLRules rules = new DroolsOWL2RLRules();
    rules.defineRules();
    assertFalse("rule must have executable definitions", rules.getRules(this.rule).isEmpty());
    StringBuilder drl = new StringBuilder("package test; import org.swrlapi.drools.owl.axioms.*; import org.swrlapi.drools.owl.classes.*; global org.swrlapi.drools.owl2rl.OWL2RLRuleTest.Recorder inferrer;\n");
    rules.getRules(this.rule).forEach(definition -> drl.append(definition.getRuleText()).append('\n'));
    KieBase base = new KieHelper().addContent(drl.toString(), ResourceType.DRL).build();
    check(base, Arrays.asList(this.facts), this.expected);
    for (int missing = 0; missing < this.facts.length; missing++) {
      List<Object> incomplete = new ArrayList<>(Arrays.asList(this.facts));
      incomplete.remove(missing);
      check(base, incomplete, Collections.emptySet());
    }
  }

  private void check(KieBase base, List<Object> premises, Set<A> conclusions)
  {
    KieSession session = base.newKieSession();
    try {
      OWL2RLRuleTest.Recorder recorder = new OWL2RLRuleTest.Recorder(session, premises.toArray());
      session.setGlobal("inferrer", recorder);
      premises.forEach(session::insert);
      assertTrue("must reach fixpoint", session.fireAllRules(1000) < 1000);
      assertFalse("schema must not create a contradiction", recorder.inconsistent);
      assertEquals("conclusions with premises " + premises, conclusions, recorder.inferred);
    } finally {
      session.dispose();
    }
  }
}
