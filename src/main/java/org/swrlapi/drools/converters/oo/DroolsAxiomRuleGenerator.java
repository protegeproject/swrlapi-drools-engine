package org.swrlapi.drools.converters.oo;

import org.semanticweb.owlapi.model.*;
import org.swrlapi.drools.core.DroolsSWRLRuleEngine;
import org.swrlapi.owl2rl.OWL2RLNames.OWL2RLRule;

/** Compiles variable-length chain and key axioms into ordinary agenda rules. */
final class DroolsAxiomRuleGenerator
{
  private final DroolsSWRLRuleEngine engine;
  private final DroolsOWLClassExpressionHandler classes;
  private final DroolsOWLPropertyExpressionHandler properties;
  private int index;

  DroolsAxiomRuleGenerator(DroolsSWRLRuleEngine engine, DroolsOWLClassExpressionHandler classes,
    DroolsOWLPropertyExpressionHandler properties) {
    this.engine = engine;
    this.classes = classes;
    this.properties = properties;
  }
  void reset() { index = 0; }

  void convert(OWLSubPropertyChainOfAxiom axiom) {
    if (!engine.getOWL2RLEngine().isRuleEnabled(OWL2RLRule.PRP_SPO2)) return;
    StringBuilder body = new StringBuilder();
    int n = 0;
    for (OWLObjectPropertyExpression property : axiom.getPropertyChain()) {
      body.append(objectPattern(property, "$v" + n, "$v" + (n + 1), n == 0, true));
      n++;
    }
    if (n == 0) throw new IllegalArgumentException("An object property chain cannot be empty");
    OWLObjectPropertyExpression head = axiom.getSuperProperty();
    String pid = quote(properties.convert(head.getNamedProperty()).getid());
    String start = head.isAnonymous() ? "$v" + n : "$v0";
    String end = head.isAnonymous() ? "$v0" : "$v" + n;
    define("prp_spo2", body.toString(), "inferrer.infer(new OPAA(" + start + ", " + pid + ", " + end + "));");
  }

  void convert(OWLHasKeyAxiom axiom) {
    if (!engine.getOWL2RLEngine().isRuleEnabled(OWL2RLRule.PRP_KEY)) return;
    String cid = quote(classes.convert(axiom.getClassExpression()).getceid());
    StringBuilder body = new StringBuilder("CAA(cid == " + cid + ", $x : i) CAA(cid == " + cid
      + ", $y : i, i != $x) IDA(i == $x) IDA(i == $y) ");
    int n = 0;
    // OWLAPI 5.5.1 getObjectPropertyExpressions() returns the signature: it
    // loses inverses and can include properties from the key class expression.
    for (OWLPropertyExpression expression : axiom.getPropertyExpressions()) {
      if (!(expression instanceof OWLObjectPropertyExpression)) continue;
      OWLObjectPropertyExpression property = (OWLObjectPropertyExpression)expression;
      String value = "$o" + n++;
      body.append(objectPattern(property, "$x", value, false, true));
      body.append(objectPattern(property, "$y", value, false, false));
      body.append("IDA(i == ").append(value).append(") ");
    }
    for (OWLDataPropertyExpression property : axiom.getDataPropertyExpressions()) {
      String pid = quote(properties.convert(property).getid());
      String left = "$l" + n, right = "$r" + n++;
      body.append("DPAA(s == $x, pid == ").append(pid).append(", ").append(left).append(" : o) ");
      body.append("DPAA(s == $y, pid == ").append(pid).append(", ").append(right).append(" : o) ");
      body.append("eval(org.swrlapi.drools.owl.literals.LiteralValues.sameValue(")
        .append(left).append(", ").append(right).append(")) ");
    }
    define("prp_key", body.toString(), "inferrer.infer(new SIA($x, $y));");
  }

  private String objectPattern(OWLObjectPropertyExpression property, String subject, String object,
    boolean bindSubject, boolean bindObject) {
    String s = (bindSubject ? subject + " : " : "") + (property.isAnonymous() ? "o" : "s")
      + (bindSubject ? "" : " == " + subject);
    String o = (bindObject ? object + " : " : "") + (property.isAnonymous() ? "s" : "o")
      + (bindObject ? "" : " == " + object);
    return "OPAA(pid == " + quote(properties.convert(property.getNamedProperty()).getid()) + ", " + s + ", " + o + ") ";
  }

  private void define(String prefix, String body, String consequence) {
    engine.defineDRLRule("rule " + prefix + "_axiom_" + index++ + " when " + body + " then " + consequence + " end");
  }

  private static String quote(String value) {
    return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "\\r").replace("\n", "\\n") + "\"";
  }
}
