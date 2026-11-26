package org.swrlapi.drools.owl2rl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.swrlapi.drools.owl.axioms.*;
import org.swrlapi.drools.owl.classes.*;
import org.swrlapi.drools.owl.literals.L;
import org.swrlapi.owl2rl.OWL2RLNames.OWL2RLRule;

import java.util.*;
import static org.junit.Assert.*;
import static org.swrlapi.owl2rl.OWL2RLNames.OWL2RLRule.*;

/** Execute each rule independently so schema rules cannot hide missing or excessive inferences. */
@RunWith(Parameterized.class)
public class OWL2RLRuleTest
{
  private final OWL2RLRule rule;
  private final Object[] facts;
  private final Set<A> expected;
  private final boolean inconsistent;

  public OWL2RLRuleTest(String name, OWL2RLRule rule, Object[] facts, Set<A> expected, boolean inconsistent)
  {
    this.rule = rule;
    this.facts = facts;
    this.expected = expected;
    this.inconsistent = inconsistent;
  }

  private static Object[] row(String name, OWL2RLRule rule, Object[] facts, A... expected)
  {
    return new Object[] { name, rule, facts, new HashSet<>(Arrays.asList(expected)), false };
  }

  private static Object[] conflict(String name, OWL2RLRule rule, Object... facts)
  {
    return new Object[] { name, rule, facts, Collections.emptySet(), true };
  }

  private static Set<String> names(String... names) { return new HashSet<>(Arrays.asList(names)); }

  @Parameterized.Parameters(name = "{0}") public static Collection<Object[]> cases()
  {
    L literal = new L("value", "xsd:string");
    return Arrays.asList(
      row("irreflexivity allows distinct endpoints", PRP_IRP, new Object[]{new IROPA("p"), new OPAA("i", "p", "j")}),
      conflict("invalid literal", DT_NOT_TYPE, new DPAA("i", "p", new L("256", "xsd:unsignedByte"))),
      conflict("invalid negative literal", DT_NOT_TYPE, new NDPAA("i", "p", new L("bad", "xsd:integer"))),
      conflict("range violation", DT_NOT_TYPE, new org.swrlapi.drools.owl.literals.LiteralTypeAssertion(new L("1.5", "xsd:decimal"), "xsd:integer")),
      row("valid numeric subtype", DT_NOT_TYPE, new Object[]{new org.swrlapi.drools.owl.literals.LiteralTypeAssertion(new L("1.0", "xsd:decimal"), "xsd:integer")}),
      conflict("all values violation", DT_NOT_TYPE, new org.swrlapi.drools.owl.literals.LiteralTypeAssertion(new L("1", "xsd:integer"), "xsd:boolean")),
      row("intersection decomposes", CLS_INT2, new Object[]{new OIOCE("X", names("A", "B")), new CAA("X", "i")}, new CAA("A", "i"), new CAA("B", "i")),
      row("intersection requires every operand", CLS_INT1, new Object[]{new OIOCE("X", names("A", "B")), new CAA("A", "i")}),
      row("intersection operands must share individual", CLS_INT1, new Object[]{new OIOCE("X", names("A", "B")), new CAA("A", "i"), new CAA("B", "j")}),
      row("intersection composes", CLS_INT1, new Object[]{new OIOCE("X", names("A", "B")), new CAA("A", "i"), new CAA("B", "i")}, new CAA("X", "i")),
      row("union first operand", CLS_UNI, new Object[]{new OUOCE("X", names("A", "B")), new CAA("A", "i")}, new CAA("X", "i")),
      row("union second operand", CLS_UNI, new Object[]{new OUOCE("X", names("A", "B")), new CAA("B", "i")}, new CAA("X", "i")),
      row("union unrelated class", CLS_UNI, new Object[]{new OUOCE("X", names("A", "B")), new CAA("C", "i")}),
      row("union does not decompose", CLS_UNI, new Object[]{new OUOCE("X", names("A", "B")), new CAA("X", "i")}),
      row("object range", PRP_RNG, new Object[]{new OPRA("p", "A"), new OPAA("i", "p", "j")}, new CAA("A", "j")),
      row("object domain", PRP_DOM, new Object[]{new DOPA("p", "A"), new OPAA("i", "p", "j")}, new CAA("A", "i")),
      row("data domain", PRP_DOM, new Object[]{new DDPA("p", "A"), new DPAA("i", "p", literal)}, new CAA("A", "i")),
      row("symmetric property", PRP_SYMP, new Object[]{new SPA("p"), new OPAA("i", "p", "j")}, new OPAA("j", "p", "i")),
      row("transitive property", PRP_TRP, new Object[]{new TOPA("p"), new OPAA("i", "p", "j"), new OPAA("j", "p", "k")}, new OPAA("i", "p", "k")),
      row("transitivity requires same property", PRP_TRP, new Object[]{new TOPA("p"), new OPAA("i", "p", "j"), new OPAA("j", "q", "k")}),
      row("sub object property", PRP_SPO1, new Object[]{new SOPA("p", "q"), new OPAA("i", "p", "j")}, new OPAA("i", "q", "j")),
      row("sub data property", PRP_SPO1, new Object[]{new SDPA("p", "q"), new DPAA("i", "p", literal)}, new DPAA("i", "q", literal)),
      row("equivalent object property forward", PRP_EQP1, new Object[]{new EOPA("p", "q"), new OPAA("i", "p", "j")}, new OPAA("i", "q", "j")),
      row("equivalent object property reverse", PRP_EQP2, new Object[]{new EOPA("p", "q"), new OPAA("i", "q", "j")}, new OPAA("i", "p", "j")),
      row("equivalent data property forward", PRP_EQP1, new Object[]{new EDPA("p", "q"), new DPAA("i", "p", literal)}, new DPAA("i", "q", literal)),
      row("equivalent data property reverse", PRP_EQP2, new Object[]{new EDPA("p", "q"), new DPAA("i", "q", literal)}, new DPAA("i", "p", literal)),
      row("inverse forward", PRP_INV1, new Object[]{new IOPA("p", "q"), new OPAA("i", "p", "j")}, new OPAA("j", "q", "i")),
      row("inverse reverse", PRP_INV2, new Object[]{new IOPA("p", "q"), new OPAA("i", "q", "j")}, new OPAA("j", "p", "i")),
      row("functional property", PRP_FP, new Object[]{new FOPA("p"), new OPAA("i", "p", "j"), new OPAA("i", "p", "k")}, new SIA("j", "k")),
      row("inverse functional property", PRP_IFP, new Object[]{new IFOPA("p"), new OPAA("j", "p", "i"), new OPAA("k", "p", "i")}, new SIA("j", "k")),
      conflict("irreflexive", PRP_IRP, new IROPA("p"), new OPAA("i", "p", "i")),
      conflict("asymmetric", PRP_ASYP, new AOPA("p"), new OPAA("i", "p", "j"), new OPAA("j", "p", "i")),
      conflict("negative object assertion", PRP_NPA1, new NOPAA("i", "p", "j"), new OPAA("i", "p", "j")),
      conflict("negative data assertion", PRP_NPA2, new NDPAA("i", "p", literal), new DPAA("i", "p", literal)),
      conflict("disjoint object properties", PRP_PDW, new DJOPA("p", "q"), new OPAA("i", "p", "j"), new OPAA("i", "q", "j")),
      conflict("disjoint data properties", PRP_PDW, new DJDPA("p", "q"), new DPAA("i", "p", literal), new DPAA("i", "q", literal)),
      conflict("complement", CLS_COM, new OCOCE("X", "A"), new CAA("X", "i"), new CAA("A", "i")),
      row("some values", CLS_SFV1, new Object[]{new OSVFCE("X", "p", "A"), new OPAA("i", "p", "j"), new CAA("A", "j")}, new CAA("X", "i")),
      row("some values requires filler", CLS_SFV1, new Object[]{new OSVFCE("X", "p", "A"), new OPAA("i", "p", "j")}),
      row("some Thing values", CLS_SFV2, new Object[]{new OSVFCE("X", "p", "owl:Thing"), new OPAA("i", "p", "j")}, new CAA("X", "i")),
      row("all values", CLS_AVF, new Object[]{new OAVFCE("X", "p", "A"), new CAA("X", "i"), new OPAA("i", "p", "j")}, new CAA("A", "j")),
      row("object has value forward", CLS_HV1, new Object[]{new OHVCE("X", "p", "j"), new CAA("X", "i")}, new OPAA("i", "p", "j")),
      row("data has value forward", CLS_HV1, new Object[]{new DHVCE("X", "p", literal), new CAA("X", "i")}, new DPAA("i", "p", literal)),
      row("object has value reverse", CLS_HV2, new Object[]{new OHVCE("X", "p", "j"), new OPAA("i", "p", "j")}, new CAA("X", "i")),
      row("data has value reverse", CLS_HV2, new Object[]{new DHVCE("X", "p", literal), new DPAA("i", "p", literal)}, new CAA("X", "i")),
      conflict("max zero", CLS_MAXC1, new OMaxCCE("X", "p", 0), new CAA("X", "i"), new OPAA("i", "p", "j")),
      row("max one", CLS_MAXC2, new Object[]{new OMaxCCE("X", "p", 1), new CAA("X", "i"), new OPAA("i", "p", "j"), new OPAA("i", "p", "k")}, new SIA("j", "k")),
      conflict("qualified max zero", CLS_MAXQC1, new OMaxQCCE("X", "p", "A", 0), new CAA("X", "i"), new OPAA("i", "p", "j"), new CAA("A", "j")),
      row("qualified max one", CLS_MAXQC3, new Object[]{new OMaxQCCE("X", "p", "A", 1), new CAA("X", "i"), new OPAA("i", "p", "j"), new OPAA("i", "p", "k"), new CAA("A", "j"), new CAA("A", "k")}, new SIA("j", "k")),
      row("enumeration", CLS_OO, new Object[]{new OOOCE("X", names("i", "j"))}, new CAA("X", "i"), new CAA("X", "j")),
      row("subclass", CAX_SCO, new Object[]{new SCA("A", "B"), new CAA("A", "i")}, new CAA("B", "i")),
      row("equivalent class forward", CAX_EQC1, new Object[]{new ECA("A", "B"), new CAA("A", "i")}, new CAA("B", "i")),
      row("equivalent class reverse", CAX_EQC2, new Object[]{new ECA("A", "B"), new CAA("B", "i")}, new CAA("A", "i")),
      conflict("disjoint classes", CAX_DW, new DCA("A", "B"), new CAA("A", "i"), new CAA("B", "i")));
  }

  @Test public void derivesExpectedConclusionsInIsolation()
  {
    DroolsOWL2RLRules rules = new DroolsOWL2RLRules();
    rules.defineRules();
    StringBuilder drl = new StringBuilder("package test; import org.swrlapi.drools.owl.axioms.*; import org.swrlapi.drools.owl.classes.*; global org.swrlapi.drools.owl2rl.OWL2RLRuleTest.Recorder inferrer;\n");
    rules.getRules(this.rule).forEach(definition -> drl.append(definition.getRuleText()).append('\n'));
    KieSession session = new KieHelper().addContent(drl.toString(), ResourceType.DRL).build().newKieSession();
    try {
      Recorder recorder = new Recorder(session, this.facts);
      session.setGlobal("inferrer", recorder);
      for (Object fact : this.facts)
        session.insert(fact);
      session.fireAllRules(1000);
      assertEquals("inconsistency", this.inconsistent, recorder.inconsistent);
      assertTrue("missing conclusions " + this.expected + " from " + recorder.inferred, recorder.inferred.containsAll(this.expected));
      if (this.expected.isEmpty() && !this.inconsistent)
        assertTrue("unexpected inference " + recorder.inferred, recorder.inferred.isEmpty());
    } finally { session.dispose(); }
  }

  public static class Recorder
  {
    final Set<A> inferred = new HashSet<>();
    final Set<Object> known = new HashSet<>();
    final KieSession session;
    boolean inconsistent;
    Recorder(KieSession session, Object[] facts) { this.session = session; this.known.addAll(Arrays.asList(facts)); }
    public void infer(A... axioms) {
      for (A axiom : axioms) {
        this.inferred.add(axiom);
        if (this.known.add(axiom)) this.session.insert(axiom);
      }
    }
    public void inferFalse(String rule, String... arguments) { this.inconsistent = true; }
  }
}
