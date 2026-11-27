package org.swrlapi.drools.owl2rl;

import java.util.*;
import org.junit.Test;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.swrlapi.drools.owl.axioms.DPAA;
import org.swrlapi.drools.owl.dataranges.*;
import org.swrlapi.drools.owl.literals.L;
import org.swrlapi.drools.owl.literals.LiteralTypeAssertion;
import static org.junit.Assert.*;
import static org.swrlapi.owl2rl.OWL2RLNames.OWL2RLRule.DT_TYPE2;

public class DatatypeMembershipTest
{
  @Test public void derivesOnlyProvedMembershipsIncludingNestedIntersections() {
    L positive = new L("1.0", "xsd:decimal"), negative = new L("-1", "xsd:integer");
    L invalid = new L("bad", "xsd:int"), unknown = new L("1", "urn:custom");
    Object[] facts = {new DPAA("a", "p", positive), new DPAA("a", "p", negative),
      new DPAA("a", "p", invalid), new DPAA("a", "p", unknown), new D("xsd:integer"), new D("xsd:positiveInteger"),
      new D("xsd:decimal"), new D("urn:custom"),
      new DIO("inner", new HashSet<>(Arrays.asList("xsd:integer", "xsd:positiveInteger"))),
      new DIO("outer", new HashSet<>(Arrays.asList("inner", "xsd:decimal"))),
      new DIO("unknown", new HashSet<>(Arrays.asList("xsd:integer", "urn:custom")))};
    Set<LiteralTypeAssertion> expected = new HashSet<>();
    for (String type : new String[]{"xsd:integer", "xsd:positiveInteger", "xsd:decimal", "inner", "outer"})
      expected.add(new LiteralTypeAssertion(positive, type));
    for (String type : new String[]{"xsd:integer", "xsd:decimal"}) expected.add(new LiteralTypeAssertion(negative, type));
    assertEquals(expected, memberships(facts));
  }

  @Test public void noLiteralAssertionsMeansNoMemberships() {
    assertTrue(memberships(new Object[]{new D("xsd:integer"),
      new DIO("intersection", new HashSet<>(Arrays.asList("xsd:integer", "xsd:decimal")))}).isEmpty());
  }

  private Set<LiteralTypeAssertion> memberships(Object[] facts) {
    DroolsOWL2RLRules rules = new DroolsOWL2RLRules(); rules.defineRules();
    assertFalse(rules.getRules(DT_TYPE2).isEmpty());
    StringBuilder drl = new StringBuilder("package test; import org.swrlapi.drools.owl.axioms.*;\n");
    rules.getRules(DT_TYPE2).forEach(definition -> drl.append(definition.getRuleText()).append('\n'));
    KieSession session = new KieHelper().addContent(drl.toString(), ResourceType.DRL).build().newKieSession();
    try {
      for (Object fact : facts) session.insert(fact);
      assertTrue(session.fireAllRules(1000) < 1000);
      Set<LiteralTypeAssertion> result = new HashSet<>();
      for (Object fact : session.getObjects())
        if (fact instanceof LiteralTypeAssertion) result.add((LiteralTypeAssertion)fact);
      return result;
    } finally { session.dispose(); }
  }
}
