package org.swrlapi.drools.owl2rl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.drools.core.DroolsRuleDefinition;
import org.swrlapi.owl2rl.OWL2RLNames;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class that defines Drools OWL 2 RL rules.
 * <p>
 * See the <a href="http://www.w3.org/TR/owl2-profiles/">OWL 2 RL Specification</a> for a description of the rules.
 * <p>
 * All axioms inferred by these rules are handled by a {@link org.swrlapi.drools.reasoner.DefaultDroolsOWLAxiomHandler}.
 * <p>
 * Property chain and key axioms are not currently handled (specified by the the pro-spo2 and prp-key rules in the
 * Specification). The value space of literals is also not validated (specified by rule dt-not-type). All other rules
 * are implemented.
 * <p>
 * In its current state, this is a fairly naive implementation of the rules in the OWL 2 RL Specification. Many
 * optimizations are possible.
 *
 * @see org.swrlapi.drools.owl2rl.DroolsOWL2RLEngine
 * @see org.swrlapi.drools.reasoner.DefaultDroolsOWLAxiomHandler
 * @see org.swrlapi.drools.core.DroolsSWRLRuleEngine
 */
public class DroolsOWL2RLRules
{
  @NonNull private final Map<OWL2RLNames.OWL2RLRule, @NonNull Set<@NonNull DroolsRuleDefinition>> rules;

  public DroolsOWL2RLRules()
  {
    this.rules = new HashMap<>();
  }

  /**
   * The reason for the strange-looking $variable.id formulation for named properties in the consequent of some rules is
   * that Drools does not always seem to correctly determine the appropriate specialized variable type when Java
   * generics are used and gives a rule compilation error when looking for a constructor with the specialized type;
   * pulling the entity name from the variable works around this shortcoming because the constructor in which it is used
   * creates the correct type.
   */
  public void defineRules()
  {
    defineOWL2RLTable4DroolsRules();
    defineOWL2RLTable5DroolsRules();
    defineOWL2RLTable6DroolsRules();
    defineOWL2RLTable7DroolsRules();
    defineOWL2RLTable8DroolsRules();
    defineOWL2RLTable9DroolsRules();
  }

  public boolean hasRule(OWL2RLNames.OWL2RLRule rule)
  {
    return this.rules.containsKey(rule);
  }

  @NonNull public Set<@NonNull DroolsRuleDefinition> getRules(OWL2RLNames.OWL2RLRule rule)
  {
    if (this.rules.containsKey(rule))
      return this.rules.get(rule);
    else
      return Collections.emptySet();
  }

  private void defineOWL2RLTable4DroolsRules()
  {
    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.EQ_REF, "eq_ref_c",
        "rule eq_ref_c when CAA($s:cid, $o:i) then SIA sia=new SIA($o, $o); inferrer.infer(sia); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.EQ_REF, "eq_ref_op",
        "rule eq_ref_op when OPAA($s:s, $p:pid, $o:o) then "
            + "SIA sia1=new SIA($s, $s); SIA sia2=new SIA($o, $o); inferrer.infer(sia1, sia2); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.EQ_REF, "eq_ref_dp",
        "rule eq_ref_dp when DPAA($s:s, $p:pid, $o:o) then SIA sia=new SIA($s, $s); inferrer.infer(sia); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.EQ_SYM, "eq_sym",
        "rule eq_sym when SIA($x:i1, $y:i2) then SIA sia=new SIA($y, $x); inferrer.infer(sia); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.EQ_TRANS, "eq_trans",
        "rule eq_trans when SIA($s1:i1, $s2:i2) SIA(i1==$s2, $s3:i2) "
            + "then SIA sia=new SIA($s1, $s3); inferrer.infer(sia); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.EQ_REP_S, "eq_rep_s_c",
        "rule eq_rep_s_c when SIA($s:i1, $sp:i2) CAA($c:cid, i==$s) "
            + "then CAA caa=new CAA($c, $sp); inferrer.infer(caa); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.EQ_REP_S, "eq_rep_s_op",
        "rule eq_rep_s_op when SIA($s:i1, $sp:i2) OPAA(s==$s, $p:pid, $o:o) "
            + "then OPAA opaa=new OPAA($sp, $p, $o); inferrer.infer(opaa); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.EQ_REP_S, "eq_rep_s_dp",
        "rule eq_rep_s_dp when SIA($s:i1, $sp:i2) DPAA(s==$s, $p:pid, $o:o) "
            + "then DPAA dpaa=new DPAA($sp, $p, $o); inferrer.infer(dpaa); end");

    // EQ_REP_P will be handled by PRP_EQP1 and PRP_EQP2.

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.EQ_REP_O, "eq_rep_o",
        "rule eq_rep_o when SIA($o:i1, $op:i2) OPAA($s:s, $p:pid, o==$o) "
            + "then OPAA opaa=new OPAA($s, $p, $op); inferrer.infer(opaa); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.EQ_DIFF1, "eq_diff1",
        "rule eq_diff1 when SIA($x:i1, $y:i2) DIA(i1==$x, i2==$y) then inferrer.inferFalse(\""
            + OWL2RLNames.OWL2RLRule.EQ_DIFF1.toString() + "\", $x.id, $y.id); end");

    // EQ_DIFF2 and EQ_DIFF3 will be handled by EQ_DIFF1 because Drools is supplied an exhaustive pairwise element
    // extraction from an owl:AllDifferent axiom.
  }

  private void defineOWL2RLTable5DroolsRules()
  {
    // Annotation properties are declared during the OWL to Drools conversion process, thus handling the PRP_AP rule.

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.PRP_DOM,
        "prp_dom_op",
        "rule prp_dom_op when DOPA($p:pid, $c:did) OPAA($x:s, pid==$p, $y:o) then CAA caa=new CAA($c, $x); inferrer.infer(caa); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.PRP_DOM,
        "prp_dom_dp",
        "rule prp_dom_dp when DDPA($p:pid, $c:did) DPAA($x:s, pid==$p, $y:o) then CAA caa=new CAA($c, $x); inferrer.infer(caa); end");

    // T(?x, ?p, ?y) T(?y, rdf:type, ?c) -> T(?p, rdfs:range, ?c)
    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.PRP_RNG,
        "prp_rng",
        "rule prp_rng when OPRA($p:pid, $r:rid) OPAA($x:s, pid==$p, $y:o) then CAA caa=new CAA($r, $y); inferrer.infer(caa); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.PRP_FP,
        "prp_fp",
        "rule prp_fp when FOPA($p:pid) OPAA($x:s, pid==$p, $y1:o) OPAA(s==$x, pid==$p, $y2:o) then SIA sia=new SIA($y1, $y2); inferrer.infer(sia); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.PRP_IFP,
        "prp_ifp",
        "rule prp_ifp_op when IFOPA($p:pid) OPAA($x1:s, pid==$p, $y:o) OPAA($x2:s, pid==$p, o==$y) then SIA sia=new SIA($x1, $x2); inferrer.infer(sia); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.PRP_IRP, "prp_irp",
        "rule prp_irp when IROPA($p:pid) OPAA($s:s, pid==$p, $x:o) then inferrer.inferFalse(\""
            + OWL2RLNames.OWL2RLRule.PRP_IRP.toString() + "\", $x.id, $p); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.PRP_SYMP,
        "prp_symp",
        "rule prp_symp when SPA($p:pid) OPAA($x:s, pid==$p, $y:o) then OPAA opaa=new OPAA($y, $p, $x); inferrer.infer(opaa); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.PRP_ASYP, "prp_asyp",
        "rule prp_asyp when AOPA($p:pid) OPAA($x:s, pid==$p, $y:o) OPAA(s==$y, pid==$p, o==$x) then inferrer.inferFalse(\""
            + OWL2RLNames.OWL2RLRule.PRP_ASYP.toString() + "\", $x.id, $y.id, $p); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.PRP_TRP,
        "prp_trp",
        "rule prp_trp when TOPA($p:pid) OPAA($x:s, pid==$p, $y:o) OPAA(s==$y, pid==$p, $z:o) then OPAA opaa=new OPAA($x, $p, $z); inferrer.infer(opaa); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.PRP_SPO1,
        "prp_spo1_op",
        "rule prp_spo1_op when SOPA($p1:subpid, $p2:superpid) OPAA($x:s, pid==$p1, $y:o) then OPAA opaa=new OPAA($x, $p2, $y); inferrer.infer(opaa); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.PRP_SPO1,
        "prp_spo1_dp",
        "rule prp_spo1_dp when SDPA($p1:subpid, $p2:superpid) DPAA($x:s, pid==$p1, $y:o) then DPAA dpaa=new DPAA($x, $p2, $y); inferrer.infer(dpaa); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.PRP_EQP1,
        "prp_eqp1_op",
        "rule prp_eqp1_op when EOPA($p1:p1id, $p2:p2id) OPAA($x:s, pid==$p1, $y:o) then OPAA opaa=new OPAA($x, $p2, $y); inferrer.infer(opaa); end");
    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.PRP_EQP1,
        "prp_eqp1_dp",
        "rule prp_eqp1_dp when EDPA($p1:p1id, $p2:p2id) DPAA($x:s, pid==$p1, $y:o) then DPAA dpaa=new DPAA($x, $p2, $y); inferrer.infer(dpaa); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.PRP_EQP2,
        "prp_eqp2_op",
        "rule prp_eqp2_op when EOPA($p1:p1id, $p2:p2id) OPAA($x:s, pid==$p2, $y:o) then OPAA opaa=new OPAA($x, $p1, $y); inferrer.infer(opaa); end");
    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.PRP_EQP2,
        "prp_eqp2_dp",
        "rule prp_eqp2_dp when EDPA($p1:p1id, $p2:p2id) DPAA($x:s, pid==$p2, $y:o) then DPAA dpaa=new DPAA($x, $p1, $y); inferrer.infer(dpaa); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.PRP_PDW, "prp_pdw",
        "rule prp_pdw when DJOPA($p1:p1id, $p2:p2id) OPAA($u:s, pid==$p1, $v:o) OPAA($u==s, pid==$p2, o==$v) "
            + " then inferrer.inferFalse(\"" + OWL2RLNames.OWL2RLRule.PRP_PDW.toString()
            + "\", $u.id, $v.id, $p1, $p2); end");

    // PRP_ADP effectively handled by PRP_PDW above via pairwise extraction

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.PRP_INV1,
        "prp_inv1",
        "rule prp_inv1 when IOPA($p1:p1id, $p2:p2id) OPAA($x:s, pid==$p1, $y:o) then OPAA opaa=new OPAA($y, $p2, $x); inferrer.infer(opaa); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.PRP_INV2,
        "prp_inv2",
        "rule prp_inv2 when IOPA($p1:p1id, $p2:p2id) OPAA($x:s, pid==$p2, $y:o) then OPAA opaa=new OPAA($y, $p1, $x); inferrer.infer(opaa); end");

    // T(?x, owl:sourceIndividual, ?i1) T(?x, owl:assertionProperty, ?p) T(?x, owl:targetIndividual, ?i2) T(?i1, ?p,
    // ?i2) -> false
    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.PRP_NPA1, "prp_npa1",
        "rule prp_npa1 when NOPAA($i1:s, $p:pid, $i2:o) OPAA(s==$i1, pid==$p, o==$i2) then inferrer.inferFalse(\""
            + OWL2RLNames.OWL2RLRule.PRP_NPA1.toString() + "\", $p, $i1.id, $i2.id); end");

    // T(?x, owl:sourceIndividual, ?i) T(?x, owl:assertionProperty, ?p) T(?x, owl:targetValue, ?lt) T(?i, ?p, ?lt) ->
    // false
    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.PRP_NPA2, "prp_npa2",
        "rule prp_npa2 when NDPAA($i:s, $p:pid, $l:o) DPAA(s==$i, pid==$p, o==$l) then inferrer.inferFalse(\""
            + OWL2RLNames.OWL2RLRule.PRP_NPA2.toString() + "\", $p, $i.id, $l.value); end");
  }

  private void defineOWL2RLTable6DroolsRules()
  {
    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.CLS_THING, "cls_thing",
        "rule cls_thing when then CDA cda = new CDA(\"owl:Thing\"); inferrer.infer(cda); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.CLS_NOTHING1, "cls_nothing1",
        "rule cls_nothing1 when then CDA cda=new CDA(\"owl:Nothing\"); inferrer.infer(cda); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.CLS_NOTHING2, "cls_nothing2",
        "rule cls_nothing2 when CAA(cid==\"owl:Nothing\", $i:i) then inferrer.inferFalse(\""
            + OWL2RLNames.OWL2RLRule.CLS_NOTHING2.toString() + "\", $i.id); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_INT1,
        "cls_int1",
        "rule cls_int1 when OIOCE($x:ceid, $c1:c1) CAA(cid==$c1, $y:i) forall ( OIOCE(ceid==$x, $cc:c1) CAA(cid==$cc, i==$y)  ) "
            + "then CAA caa=new CAA($x, $y); inferrer.infer(caa); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_INT2,
        "cls_int2",
        "rule cls_int2 when OIOCE($c:ceid, $c1:c1) CAA(cid==$c, $y:i) then CAA caa1=new CAA($c1, $y); inferrer.infer(caa1); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.CLS_UNI, "cls_uni",
        "rule cls_uni when OUOCE($c:ceid, $c1:c1) CAA(cid==$c1, $y:i) then CAA caa=new CAA($c, $y); inferrer.infer(caa); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.CLS_COM, "cls_com",
        "rule cls_com when OOCOCE($c1:ceid, $c2:complement) CAA(cid==$c1, $x:i) CAA(cid==$c2, i==$x) then inferrer.inferFalse(\""
            + OWL2RLNames.OWL2RLRule.CLS_COM.toString() + "\", $c1, $c2); end");

    // T(?x, owl:someValuesFrom, ?y) T(?x, owl:onProperty, ?p) T(?u, ?p, ?v) T(?v, rdf:type, ?y) -> T(?u, rdf:type, ?x)
    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_SFV1,
        "cls_sfv1",
        "rule cls_sfv1 when OSVFCE($x:ceid, $p:pid, $y:v) OPAA($u:s, pid==$p, $v:o) CAA(cid==$y, i==$v) then CAA caa=new CAA($x, $u); inferrer.infer(caa); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_SFV2,
        "cls_sfv2",
        "rule cls_sfv2 when OSVFCE($x:ceid, $p:pid, v==\"owl:Thing\") OPAA($u:s, pid==$p, $v:o) then CAA caa=new CAA($x, $u); inferrer.infer(caa); end");

    // T(?x, owl:allValuesFrom, ?y) T(?x, owl:onProperty, ?p) T(?u, rdf:type, ?x) T(?u, ?p, ?v) -> T(?v, rdf:type, ?y)
    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_AVF,
        "cls_avf",
        "rule cls_avf when OAVFCE($x:ceid, $p:pid, $y:v) CAA(cid==$x, $u:i) OPAA(s==$u, pid==$p, $v:o) then CAA caa=new CAA($y, $v); inferrer.infer(caa); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_HV1,
        "cls_hv1_op",
        "rule cls_hv1_op when OHVCE($x:ceid, $p:pid, $y:v) CAA(cid==$x, $u:i) then OPAA opaa=new OPAA($u, $p, $y); inferrer.infer(opaa); end");
    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_HV1,
        "cls_hv1_dp",
        "rule cls_hv1_dp when DHVCE($x:ceid, $p:pid, $y:v) CAA(cid==$x, $u:i) then DPAA dpaa=new DPAA($u, $p, $y); inferrer.infer(dpaa); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_HV2,
        "rule cls_hv2_op when OHVCE($x:ceid, $p:pid, $y:v) OPAA($u:s, pid==$p, o==$y) then CAA caa=new CAA($x, $u); inferrer.infer(caa); end",
        "rule cls_hv2_dp when DHVCE($x:ceid, $p:pid, $y:v) DPAA($u:s, pid==$p, o==$y) then CAA caa=new CAA($x, $u); inferrer.infer(caa); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_MAXC1,
        "cls_maxc1_op",
        "rule cls_maxc1_op when OMaxCCE($x:ceid, $p:pid, card==0) CAA(cid==$x, $u:i) OPAA(s==$u, pid==$p, $y:o) then inferrer.inferFalse(\""
            + OWL2RLNames.OWL2RLRule.CLS_MAXC1.toString() + "\", $x, $p, $u.id, $y.id); end");
    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_MAXC1,
        "cls_maxc1_dp",
        "rule cls_maxc1_dp when DMaxCCE($x:ceid, $p:pid, card==0) CAA(cid==$x, $u:i) DPAA(s==$u, pid==$p, $y:o) then inferrer.inferFalse(\""
            + OWL2RLNames.OWL2RLRule.CLS_MAXC1.toString() + "\", $x, $p, $u.id, $y.value); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_MAXC2,
        "cls_maxc2",
        "rule cls_maxc2 when OMaxCCE($x:ceid, $p:pid, card==1) CAA(cid==$x, $u:i) OPAA(s==$u, pid==$p, $y1:o) OPAA(s==$u, pid==$p, $y2:o) "
            + "then SIA sia=new SIA($y1, $y2); inferrer.infer(sia); end");

    // T(?x, owl:maxQualifiedCardinality, "0"^^xsd:nonNegativeInteger) T(?x, owl:onProperty, ?p) T(?x, owl:onClass, ?c)
    // T(?u, rdf:type, ?x) T(?u, ?p, ?y) T(?y, rdf:type, ?c) -> false

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_MAXQC1,
        "cls_maxqc1",
        "rule cls_maxqc1 when OMaxQCCE($x:ceid, $p:pid, $f:f, card==0) CAA(cid==$x, $u:i) OPAA(s==$u, pid==$p, $y:o) CAA(cid==$f, i==$y) then inferrer.inferFalse(\""
            + OWL2RLNames.OWL2RLRule.CLS_MAXQC1.toString() + "\", $x, $f, $p, $u.id, $y.id); end");

    // T(?x, owl:maxQualifiedCardinality, "0"^^xsd:nonNegativeInteger) T(?x, owl:onProperty, ?p)
    // T(?x, owl:onClass, owl:Thing) T(?u, rdf:type, ?x) T(?u, ?p, ?y) -> false

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_MAXQC2,
        "cls_maxqc2_op",
        "rule cls_maxqc2 when OMaxQCCE($x:ceid, $p:pid, f==\"owl:Thing\", card==0) CAA(cid==$x, $u:i) OPAA(s==$u, pid==$p, $y:o) "
            + "then inferrer.inferFalse(\""
            + OWL2RLNames.OWL2RLRule.CLS_MAXQC2.toString()
            + "\", $x, $p, $u.id, $y.id); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_MAXQC2,
        "cls_maxqc2_dp",
        "rule cls_maxqc2 when DMaxQCCE($x:ceid, $p:pid, f==\"owl:Thing\", card==0) CAA(cid==$x, $u:i) DPAA(s==$u, pid==$p, $y:o) "
            + "then inferrer.inferFalse(\""
            + OWL2RLNames.OWL2RLRule.CLS_MAXQC2.toString()
            + "\", $x, $p, $u.id, $y.toString()); end");

    // T(?x, owl:maxQualifiedCardinality, "1"^^xsd:nonNegativeInteger) T(?x, owl:onProperty, ?p) T(?x, owl:onClass, ?c)
    // T(?u, rdf:type, ?x) T(?u, ?p, ?y1) T(?y1, rdf:type, ?c) T(?u, ?p, ?y2) T(?y2, rdf:type, ?c)
    // -> T(?y1, owl:sameAs, ?y2)
    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.CLS_MAXQC3, "cls_maxqc3_op",
        "rule cls_maxqc3 when OMaxQCCE($x:ceid, $p:pid, $f:f, card==1) CAA(cid==$x, $u:i) OPAA(s==$u, pid==$p, $y1:o) "
            + "CAA(cid==$f, i==$y1) OPAA(s==$u, pid==$p, $y2:o) CAA(cid==$f, i==$y2) "
            + "then SIA sia=new SIA($y1, $y2); inferrer.infer(sia); end");

    // T(?x, owl:maxQualifiedCardinality, "1"^^xsd:nonNegativeInteger) T(?x, owl:onProperty, ?p)
    // T(?x, owl:onClass, owl:Thing) T(?u, rdf:type, ?x) T(?u, ?p, ?y1) T(?u, ?p, ?y2) -> false
    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_MAXQC4,
        "cls_maxqc4",
        "rule cls_maxqc4 when OMaxQCCE($x:ceid, $p:pid, f==\"owl:Thing\", card==1) CAA(cid==$x, $u:i) OPAA(s==$u, pid==$p, $y1:o) OPAA(s==$u, pid==$p, $y2:o) "
            + "then SIA sia=new SIA($y1, $y2); inferrer.infer(sia); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CLS_OO,
        "cls_oo",
        "rule cls_oo when OOOCE($c:ceid, $i:i) then CAA caa1=new CAA($c, $i); inferrer.infer(caa1); end");
  }

  private void defineOWL2RLTable7DroolsRules()
  {
    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CAX_SCO,
        "cax_sco",
        "rule cax_sco  when SCA($c1:subcid, $c2:supercid) CAA(cid==$c1, $x:i) then CAA caa=new CAA($c2, $x); inferrer.infer(caa); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CAX_EQC1,
        "cax_eqc1",
        "rule cax_eqc1 when ECA($c1:c1id, $c2:c2id) CAA(cid==$c1, $x:i) then CAA caa=new CAA($c2, $x); inferrer.infer(caa); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.CAX_EQC2,
        "cax_eqc2",
        "rule cax_eqc2 when ECA($c1:c1id, $c2:c2id) CAA(cid==$c2, $x:i) then CAA caa=new CAA($c1, $x); inferrer.infer(caa); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.CAX_DW, "cax_dw",
        "rule cax_dw when DCA($c1:c1id, $c2:c2id) CAA(cid==$c1, $x:i) CAA(cid==$c2, i==$x) then inferrer.inferFalse(\""
            + OWL2RLNames.OWL2RLRule.CAX_DW.toString() + "\", $c1, $c2, $x.id); end");

    // CAX_ADC will be handled by CAX_DW because Drools is supplied an exhaustive pairwise element extraction of DCAs
    // from an owl:AllDisjointClasses axiom.
  }

  private void defineOWL2RLTable8DroolsRules()
  {
    // TODO DT_TYPE1, DT_TYPE2, DT_EQ, and DT_DIFF implemented programatically, though not robust at the
    // moment. See L.equals.
    // TODO DT_NOT_TYPE not yet implemented.
  }

  private void defineOWL2RLTable9DroolsRules()
  {
    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_CLS, "scm_cls", "rule scm_cls when CDA($c:cid) "
        + "then SCA sca1=new SCA($c, $c); ECA eca=new ECA($c, $c); SCA sca2=new SCA($c, \"owl:Thing\"); "
        + "SCA sca3=new SCA(\"owl:Nothing\", $c); inferrer.infer(sca1, eca, sca2, sca3); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_SCO, "scm_sco",
        "rule scm_sco when SCA($c1:subcid, $c2:supercid) SCA(subcid==$c2, $c3:supercid) "
            + "then SCA sca=new SCA($c1, $c3); inferrer.infer(sca); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_EQC1, "scm_eqc1",
        "rule scm_eqc1 when ECA($c1:c1id, $c2:c2id) "
            + "then SCA sca1=new SCA($c1, $c2); SCA sca2=new SCA($c2, $c1); inferrer.infer(sca1, sca2); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_EQC2, "scm_eqc2",
        "rule scm_eqc2 when SCA($c1:subcid, $c2:supercid) SCA(subcid==$c2, supercid==$c1) "
            + " then ECA eca=new ECA($c1, $c2); inferrer.infer(eca); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_OP, "scm_op", "rule scm_op when OPDA($p:pid) "
        + "then SOPA sopa=new SOPA($p, $p); EOPA eopa=new EOPA($p, $p); inferrer.infer(sopa, eopa); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_DP, "scm_dp", "rule scm_dp when DPDA($p:pid) "
        + "then SDPA sdpa=new SDPA($p, $p); EDPA edpa=new EDPA($p, $p); inferrer.infer(sdpa, edpa); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_SPO, "scm_spo_op",
        "rule scm_spo_op when SOPA($p1:subpid, $p2:superpid) SOPA(subpid==$p2, $p3:superpid) "
            + "then SOPA sopa=new SOPA($p1, $p3); inferrer.infer(sopa); end");
    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_SPO, "scm_spo_dp",
        "rule scm_spo_dp when SDPA($p1:subpid, $p2:superpid) SDPA(subpid==$p2, $p3:superpid) "
            + "then SDPA sdpa=new SDPA($p1, $p3); inferrer.infer(sdpa); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_EQP1, "scm_eqp1_op",
        "rule scm_eqp1_op when EOPA($p1:p1id, $p2:p2id) "
            + "then SOPA sopa1=new SOPA($p1, $p2); SOPA sopa2=new SOPA($p2, $p1); inferrer.infer(sopa1, sopa2); end");
    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_EQP1, "scm_eqp1_dp",
        "rule scm_eqp1_dp when EDPA($p1:p1id, $p2:p2id) "
            + "then SDPA sdpa1=new SDPA($p1, $p2); SDPA sdpa2=new SDPA($p2, $p1); inferrer.infer(sdpa1, sdpa2); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_EQP2, "scm_eqp2_op",
        "rule scm_eqp2_op when SOPA($p1:subpid, $p2:superpid) SOPA(subpid==$p2, superpid==$p1) "
            + "then EOPA eopa=new EOPA($p1, $p2); inferrer.infer(eopa); end");
    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_EQP2, "scm_eqp2_dp",
        "rule scm_eqp2_dp when SDPA($p1:subpid, $p2:superpid) SDPA(subpid==$p2, superpid==$p1) "
            + "then EDPA edpa=new EDPA($p1, $p2); inferrer.infer(edpa); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_DOM1, "scm_dom1_op",
        "rule scm_dom1_op when DOPA($p:pid, $c1:did) SCA(subcid==$c1, $c2:supercid) "
            + "then DOPA dopa=new DOPA($p, $c2); inferrer.infer(dopa); end");
    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_DOM1, "scm_dom1_dp",
        "rule scm_dom1_dp when DDPA($p:pid, $c1:did) SCA(subcid==$c1, $c2:supercid) "
            + "then DDPA ddpa=new DDPA($p, $c2); inferrer.infer(ddpa); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_DOM2, "scm_dom2_op",
        "rule scm_dom2_op when DOPA($p2:pid, $c:did) SOPA($p1:subpid, superpid==$p2) "
            + "then DOPA dopa=new DOPA($p1, $c); inferrer.infer(dopa); end");
    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_DOM2, "scm_dom2_dp",
        "rule scm_dom2_dp when DDPA($p2:pid, $c:did) SDPA($p1:subpid, superpid==$p2) "
            + "then DDPA ddpa=new DDPA($p1, $c); inferrer.infer(ddpa); end");

    // T(?p, rdfs:range, ?c1) T(?c1, rdfs:subClassOf, ?c2) -> T(?p, rdfs:range, ?c2)
    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_RNG1, "scm_rng1_op",
        "rule scm_rng1_op when OPRA($p:pid, $c1:rid) SCA(subcid==$c1, $c2:supercid) "
            + "then OPRA opra=new OPRA($p, $c2); inferrer.infer(opra); end");
    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_RNG1, "scm_rng1_dp",
        "rule scm_rng1_dp when DPRA($p:pid, $c1:rid) SCA(subcid==$c1, $c2:supercid) "
            + "then DPRA dpra=new DPRA($p, $c2); inferrer.infer(dpra); end");

    // T(?p2, rdfs:range, ?c) T(?p1, rdfs:subPropertyOf, ?p2) -> T(?p1, rdfs:range, ?c)
    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_RNG2, "scm_rng2_op",
        "rule scm_rng2_op when OPRA($p2:pid, $c:rid) SOPA($p1:subpid, superpid==$p2) "
            + "then OPRA opra=new OPRA($p1, $c); inferrer.infer(opra); end");
    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_RNG2, "scm_rng2_dp",
        "rule scm_rng2_dp when DPRA($p2:pid, $c:rid) SDPA($p1:subpid, superpid==$p2) "
            + "then DPRA dpra=new DPRA($p1, $c); inferrer.infer(dpra); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_HV, "scm_hv_op",
        "rule scm_hv_op when OHVCE($c1:ceid, $p1:pid, $i:v) OHVCE($c2:ceid, $p2:pid, v==$i) SOPA(subpid==$p1, superpid==$p2) "
            + "then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_HV, "scm_hv_dp",
        "rule scm_hv_dp when DHVCE($c1:ceid, $p1:pid, $i:v) DHVCE($c2:ceid, $p2:pid, v==$i) SDPA(subpid==$p1, superpid==$p2) "
            + "then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_SVF1, "scm_svf1",
        "rule scm_svf1 when OSVFCE($c1:ceid, $p:pid, $y1:v) OSVFCE($c2:ceid, pid==$p, $y2:v) SCA(subcid==$y1, supercid==$y2) "
            + "then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.SCM_SVF2,
        "scm_svf2_op",
        "rule scm_svf2_op when OSVFCE($c1:ceid, $p1:pid, $y:v) OSVFCE($c2:ceid, $p2:pid, v==$y) SOPA(subpid==$p1, superpid==$p2) "
            + "then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.SCM_SVF2,
        "scm_svf2_dp",
        "rule scm_svf2_dp when DSVFCE($c1:ceid, $p1:pid, $y:v) DSVFCE($c2:ceid, $p2:pid, v==$y) SDPA(subpid==$p1, superpid==$p2) "
            + "then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_AVF1, "scm_afv1",
        "rule scm_avf1 when OAVFCE($c1:ceid, $p:pid, $y1:v) OAVFCE($c2:ceid, pid==$p, $y2:v) SCA(subcid==$y1, supercid==$y2) "
            + "then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.SCM_AVF2,
        "scm_avf2_op",
        "rule scm_avf2_op when OAVFCE($c1:ceid, $p1:pid, $y:v) OAVFCE($c2:ceid, $p2:pid, v==$y) SOPA(subpid==$p1, superpid==$p2) "
            + " then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

    createOWL2RLRuleDefinition(
        OWL2RLNames.OWL2RLRule.SCM_AVF2,
        "scm_avf2_dop",
        "rule scm_avf2_dp when DAVFCE($c1:ceid, $p1:pid, $y:v) DAVFCE($c2:ceid, $p2:pid, v==$y) SDPA(subpid==$p1, superpid==$p2) "
            + " then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_INT, "scm_int",
        "rule scm_int when OIOCE($c:ceid, $c1:c1) then SCA sca1=new SCA($c, $c1); inferrer.infer(sca1); end");

    createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule.SCM_UNI, "scm_uni",
        "rule scm_uni when OUOCE($c:ceid, $c1:c1) then SCA sca1=new SCA($c1, $c); inferrer.infer(sca1); end");
  }

  private void createOWL2RLRuleDefinition(OWL2RLNames.OWL2RLRule rule, @NonNull String ruleName, @NonNull String ruleText)
  {
    DroolsRuleDefinition newRuleDefinition = new DroolsRuleDefinition(ruleName, ruleText);

    if (this.rules.containsKey(rule))
      this.rules.get(rule).add(newRuleDefinition);
    else {
      Set<@NonNull DroolsRuleDefinition> newRuleDefinitions = new HashSet<>();
      newRuleDefinitions.add(newRuleDefinition);
      this.rules.put(rule, newRuleDefinitions);
    }
  }
}
