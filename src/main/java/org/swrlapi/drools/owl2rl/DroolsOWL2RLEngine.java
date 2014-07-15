package org.swrlapi.drools.owl2rl;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.swrlapi.owl2rl.AbstractOWL2RLEngine;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;

/**
 * Class providing Drools implementation of OWL 2 RL rules. In its current state, this is a fairly naive
 * implementation of the rules in the OWL 2 RL specification. Many optimizations are possible.
 * <p/>
 * The reason the the strange-looking $variable.id formulation for named entities in the consequent of some rules is
 * that Drools does not always seem to correctly determine the appropriate specialized variable type when Java generics
 * are used and gives a rule compilation error when looking for a constructor with the specialized type; pulling the
 * entity name from the variable works around this because the constructor in which it is used creates the correct type.
 */
public class DroolsOWL2RLEngine extends AbstractOWL2RLEngine
{
	private final Map<Rule, Set<DroolsRuleDefinition>> droolsRules;

	public DroolsOWL2RLEngine(OWL2RLPersistenceLayer persistenceLayer)
	{
		super(persistenceLayer, generateUnsupportedRules(), generatePermanentlyOnRules(), generateGroupedRuleSets());

		this.droolsRules = new HashMap<Rule, Set<DroolsRuleDefinition>>();

		defineOWL2RLDroolsRules();
	}

	public Set<DroolsRuleDefinition> getEnabledRuleDefinitions()
	{
		Set<DroolsRuleDefinition> enabledRuleDefinitions = new HashSet<DroolsRuleDefinition>();

		for (Rule rule : getEnabledRules())
			if (this.droolsRules.containsKey(rule))
				enabledRuleDefinitions.addAll(this.droolsRules.get(rule));

		return enabledRuleDefinitions;
	}

	private void defineOWL2RLDroolsRules()
	{
		defineOWL2RLTable4DroolsRules();
		defineOWL2RLTable5DroolsRules();
		defineOWL2RLTable6DroolsRules();
		defineOWL2RLTable7DroolsRules();
		defineOWL2RLTable8DroolsRules();
		defineOWL2RLTable9DroolsRules();
	}

	/**
	 * These are rules that are always enabled and that cannot be disabled.
	 */
	private static Set<Rule> generatePermanentlyOnRules()
	{
		return EnumSet.of(Rule.CLS_THING, Rule.CLS_NOTHING1, Rule.CLS_NOTHING2, Rule.DT_TYPE1, Rule.DT_TYPE2, Rule.DT_EQ,
				Rule.DT_DIFF);
	}

	/**
	 * These are rules that must be enabled and disabled in groups.
	 */
	private static Set<Set<Rule>> generateGroupedRuleSets()
	{
		Set<Set<Rule>> groupedRuleSets = new HashSet<Set<Rule>>();

		groupedRuleSets.add(EnumSet.of(Rule.EQ_DIFF1, Rule.EQ_DIFF2, Rule.EQ_DIFF3));
		groupedRuleSets.add(EnumSet.of(Rule.PRP_EQP1, Rule.PRP_EQP2, Rule.EQ_REP_P));
		groupedRuleSets.add(EnumSet.of(Rule.PRP_PDW, Rule.PRP_ADP));
		groupedRuleSets.add(EnumSet.of(Rule.CAX_ADC, Rule.CAX_DW));

		return groupedRuleSets;
	}

	private static Set<Rule> generateUnsupportedRules()
	{
		return EnumSet.of(Rule.PRP_AP, Rule.PRP_SPO2, Rule.PRP_KEY, Rule.DT_NOT_TYPE);

		// http://www.w3.org/TR/owl2-profiles/

		// Table 5
		// TODO Rule.PRP_AP:
		// -> T(ap, rdf:type, owl:AnnotationProperty)

		// TODO Rule.PRP_SPO2 - Property chains
		// See DroolsOWLClassExpressionConverter conversion of OWLObjectOnOf for pairwise extraction
		// T(?p, owl:propertyChainAxiom, ?x) LIST[c?x, ?p1,...,?pn] T(?u1, ?p1, ?u2) T(?u2, ?p2, ?u3) ...
		// T( ? un,?pn,?un + 1) -> T(?u1, ?p, ?un+1)

		// TODO Rule.PRP_KEY - Keys
		// T(?c, owl:hasKey, ?u) LIST[?u, ?p1, ..., ?pn] T(?x, rdf:type, ?c) T(?x, ?p1, ?z1) ...
		// T(?x, ?pn, ?zn) T(?y, rdf:type, ?c) T(?y, ?p1, ?z1) ...
		// T(?y, ?pn, ?zn) -> T(?x, owl:sameAs, ?y)

		// TODO Rule.PRP_RNG partial; data property range not supported. DPAA ^ L(value, datatype) -> DPRA?
		// T(?x, ?p, ?y)	 T(?y, rdf:type, ?c) -> T(?p, rdfs:range, ?c)

		// Table 6
		// TODO CLS_SFV1 - partial: cls_sfv1_dp not implemented. -> L(?u, ?x) explode if invalid?
		// T(?x, owl:someValuesFrom, ?y) T(?x, owl:onProperty, ?p) T(?u, ?p, ?v) T(?v, rdf:type, ?y) -> T(?u, rdf:type, ?x)
		// TODO CLS_AVF - partial: cls_avf_dp not implemented. -> L(?u, ?x) explode if invalid?
		// T(?x, owl:allValuesFrom, ?y) T(?x, owl:onProperty, ?p) T(?u, rdf:type, ?x) T(?u, ?p, ?v) -> T(?v, rdf:type, ?y)
		// TODO CLS_MAXQC1 - partial: cls_maxqc1_dp not implemented. -> L(?u, ?x) explode if invalid?

		// Table 8
		// TODO DT_NOT_TYPE Basically verify that the raw value of each literal is valid for its datatype
		// L(l., dt) ^ notValid(l, dt) -> explode?
		// T(lt, rdf:type, dt) -> false
		// For each literal lt and each datatype dt supported in OWL 2 RL such that the data value of lt is not
		// contained in the value space of dt.

		// Table 9
		// TODO SCM_RNG1 - partial: scm_rng1_dp not implemented. -> DPRA?
		// T(?p, rdfs:range, ?c1) T(?c1, rdfs:subClassOf, ?c2) -> T(?p, rdfs:range, ?c2)

		// TODO SCM_RNG2 - partial: scm_rng2_dp not implemented. -> DPRA?
		// T(?p2, rdfs:range, ?c) T(?p1, rdfs:subPropertyOf, ?p2) -> T(?p1, rdfs:range, ?c)
	}

	private void defineOWL2RLTable4DroolsRules()
	{
		createRuleDefinition(Rule.EQ_REF, "eq_ref_c",
				"rule eq_ref_c when CAA($s:c, $o:i) then SIA sia=new SIA($o, $o); inferrer.infer(sia); end");

		createRuleDefinition(Rule.EQ_REF, "eq_ref_op",
				"rule eq_ref_op when OPAA($s:s, $p:p, $o:o) then SIA sia1=new SIA($s, $s); SIA sia2=new SIA($o, $o); inferrer.infer(sia1, sia2); end");

		createRuleDefinition(Rule.EQ_REF, "eq_ref_dp",
				"rule eq_ref_dp when DPAA($s:s, $p:p, $o:o) then SIA sia=new SIA($s, $s); inferrer.infer(sia); end");

		createRuleDefinition(Rule.EQ_SYM, "eq_sym",
				"rule eq_sym when SIA($x:i1, $y:i2) then SIA sia=new SIA($y, $x); inferrer.infer(sia); end");

		createRuleDefinition(Rule.EQ_TRANS, "eq_trans",
				"rule eq_trans when SIA($s1:i1, $s2:i2) SIA(i1==$s2, $s3:i2) then SIA sia=new SIA($s1, $s3); inferrer.infer(sia); end");

		createRuleDefinition(Rule.EQ_REP_S, "eq_rep_s_c",
				"rule eq_rep_s_c when SIA($s:i1, $sp:i2) CAA($c:c, i==$s) then CAA caa=new CAA($c, $sp); inferrer.infer(caa); end");

		createRuleDefinition(Rule.EQ_REP_S, "eq_rep_s_op",
				"rule eq_rep_s_op when SIA($s:i1, $sp:i2) OPAA(s==$s, $p:p, $o:o) then OPAA opaa=new OPAA($sp, $p, $o); inferrer.infer(opaa); end");

		createRuleDefinition(Rule.EQ_REP_S, "eq_rep_s_dp",
				"rule eq_rep_s_dp when SIA($s:i1, $sp:i2) DPAA(s==$s, $p:p, $o:o) then DPAA dpaa=new DPAA($sp, $p, $o); inferrer.infer(dpaa); end");

		// EQ_REP_P will be handled by PRP_EQP1 and PRP_EQP2.

		createRuleDefinition(Rule.EQ_REP_O, "eq_rep_o",
				"rule eq_rep_o when SIA($o:i1, $op:i2) OPAA($s:s, $p:p, o==$o) then OPAA opaa=new OPAA($s, $p, $op); inferrer.infer(opaa); end");

		createRuleDefinition(Rule.EQ_DIFF1, "eq_diff1",
				"rule eq_diff1 when SIA($x:i1, $y:i2) DIA(i1==$x, i2==$y) then inferrer.inferFalse(\""
						+ Rule.EQ_DIFF1.toString() + "\", $x.id, $y.id); end");

		// EQ_DIFF2 and EQ_DIFF3 will be handled by EQ_DIFF1 because Drools is supplied an exhaustive pairwise element
		// extraction from an owl:AllDifferent axiom.
	}

	private void defineOWL2RLTable5DroolsRules()
	{
		// TODO prp_ap

		createRuleDefinition(Rule.PRP_DOM, "prp_dom_op",
				"rule prp_dom_op when DOPA($p:p, $c:d) OPAA($x:s, p==$p, $y:o) then CAA caa=new CAA($c, $x); inferrer.infer(caa); end");

		createRuleDefinition(Rule.PRP_DOM, "prp_dom_dp",
				"rule prp_dom_dp when DDPA($p:p, $c:d) DPAA($x:s, p==$p, $y:o) then CAA caa=new CAA($c, $x); inferrer.infer(caa); end");

		// T(?x, ?p, ?y)	 T(?y, rdf:type, ?c) -> T(?p, rdfs:range, ?c)
		createRuleDefinition(Rule.PRP_RNG, "prp_rng_op",
				"rule prp_rng_op when OPRA($p:p, $r:r) OPAA($x:s, p==$p, $y:o) then CAA caa=new CAA($r, $y); inferrer.infer(caa); end");

		createRuleDefinition(Rule.PRP_FP, "prp_fp",
				"rule prp_fp when FOPA($p:p) OPAA($x:s, p==$p, $y1:o) OPAA(s==$x, p==$p, $y2:o) then SIA sia=new SIA($y1, $y2); inferrer.infer(sia); end");

		createRuleDefinition(Rule.PRP_IFP, "prp_ifp",
				"rule prp_ifp_op when IPA($p:p) OPAA($x1:s, p==$p, $y:o) OPAA($x2:s, p==$p, o==$y) then SIA sia=new SIA($x1, $x2); inferrer.infer(sia); end");

		createRuleDefinition(Rule.PRP_IRP, "prp_irp",
				"rule prp_irp when IRPA($p:p) OPAA($s:s, p==$p, $x:o) then inferrer.inferFalse(\"" + Rule.PRP_IRP.toString()
						+ "\", $x.id, $p.id); end");

		createRuleDefinition(Rule.PRP_SYMP, "prp_symp",
				"rule prp_symp when SPA($p:p) OPAA($x:s, p==$p, $y:o) then OPAA opaa=new OPAA($y, $p.id, $x); inferrer.infer(opaa); end");

		createRuleDefinition(Rule.PRP_ASYP, "prp_asyp",
				"rule prp_asyp when APA($p:p) OPAA($x:s, p==$p, $y:o) OPAA(s==$y, p==$p, o==$x) then inferrer.inferFalse(\""
						+ Rule.PRP_ASYP.toString() + "\", $x.id, $y.id, $p.id); end");

		createRuleDefinition(Rule.PRP_TRP, "prp_trp",
				"rule prp_trp when TPA($p:p) OPAA($x:s, p==$p, $y:o) OPAA(s==$y, p==$p, $z:o) then OPAA opaa=new OPAA($x, $p.id, $z); inferrer.infer(opaa); end");

		createRuleDefinition(Rule.PRP_SPO1, "prp_spo1_op",
				"rule prp_spo1_op when SOPA($p1:sub, $p2:sup) OPAA($x:s, p==$p1, $y:o) then OPAA opaa=new OPAA($x, $p2, $y); inferrer.infer(opaa); end");

		createRuleDefinition(Rule.PRP_SPO1, "prp_spo1_dp",
				"rule prp_spo1_dp when SDPA($p1:sub, $p2:sup) DPAA($x:s, p==$p1, $y:o) then DPAA dpaa=new DPAA($x, $p2, $y); inferrer.infer(dpaa); end");

		createRuleDefinition(Rule.PRP_EQP1, "prp_eqp1_op",
				"rule prp_eqp1_op when EOPA($p1:p1, $p2:p2) OPAA($x:s, p==$p1, $y:o) then OPAA opaa=new OPAA($x, $p2.id, $y); inferrer.infer(opaa); end");
		createRuleDefinition(Rule.PRP_EQP1, "prp_eqp1_dp",
				"rule prp_eqp1_dp when EDPA($p1:p1, $p2:p2) DPAA($x:s, p==$p1, $y:o) then DPAA dpaa=new DPAA($x, $p2.id, $y); inferrer.infer(dpaa); end");

		createRuleDefinition(Rule.PRP_EQP2, "prp_eqp2_op",
				"rule prp_eqp2_op when EOPA($p1:p1, $p2:p2) OPAA($x:s, p==$p2, $y:o) then OPAA opaa=new OPAA($x, $p1.id, $y); inferrer.infer(opaa); end");
		createRuleDefinition(Rule.PRP_EQP2, "prp_eqp2_dp",
				"rule prp_eqp2_dp when EDPA($p1:p1, $p2:p2) DPAA($x:s, p==$p2, $y:o) then DPAA dpaa=new DPAA($x, $p1.id, $y); inferrer.infer(dpaa); end");

		createRuleDefinition(Rule.PRP_PDW, "prp_pdw",
				"rule prp_pdw when DJOPA($p1:p1, $p2:p2) OPAA($u:s, p==$p1, $v:o) OPAA($u==s, p==$p2, o==$v) "
						+ " then inferrer.inferFalse(\"" + Rule.PRP_PDW.toString() + "\", $u.id, $v.id, $p1.id, $p2.id); end");

		// PRP_ADP effectively handled by PRP_PDW above via pairwise extraction

		createRuleDefinition(Rule.PRP_INV1, "prp_inv1",
				"rule prp_inv1 when IOPA($p1:p1, $p2:p2) OPAA($x:s, p==$p1, $y:o) then OPAA opaa=new OPAA($y, $p2.id, $x); inferrer.infer(opaa); end");

		createRuleDefinition(Rule.PRP_INV2, "prp_inv2",
				"rule prp_inv2 when IOPA($p1:p1, $p2:p2) OPAA($x:s, p==$p2, $y:o) then OPAA opaa=new OPAA($y, $p1.id, $x); inferrer.infer(opaa); end");

		// T(?x, owl:sourceIndividual, ?i1) T(?x, owl:assertionProperty, ?p) T(?x, owl:targetIndividual, ?i2) T(?i1, ?p, ?i2) -> false
		createRuleDefinition(Rule.PRP_NPA1, "prp_npa1",
				"rule prp_npa1 when NOPAA($i1:s, $p:p, $i2:o) OPAA(s==$i1, p==$p, o==$i2) then inferrer.inferFalse(\""
						+ Rule.PRP_NPA1.toString() + "\", $p.id, $i1.id, $i2.id); end");

		// T(?x, owl:sourceIndividual, ?i) T(?x, owl:assertionProperty, ?p) T(?x, owl:targetValue, ?lt) T(?i, ?p, ?lt) -> false
		createRuleDefinition(Rule.PRP_NPA2, "prp_npa2",
				"rule prp_npa2 when NDPAA($i:s, $p:p, $l:o) DPAA(s==$i, p==$p, o==$l) then inferrer.inferFalse(\""
						+ Rule.PRP_NPA2.toString() + "\", $p.id, $i.id, $l.value); end");
	}

	private void defineOWL2RLTable6DroolsRules()
	{
		createRuleDefinition(Rule.CLS_THING, "cls_thing",
				"rule cls_thing when then CDA cda = new CDA(\"owl:Thing\"); inferrer.infer(cda); end");

		createRuleDefinition(Rule.CLS_NOTHING1, "cls_nothing1",
				"rule cls_nothing1 when then CDA cda=new CDA(\"owl:Nothing\"); inferrer.infer(cda); end");

		createRuleDefinition(Rule.CLS_NOTHING2, "cls_nothing2",
				"rule cls_nothing2 when CAA(c==\"owl:Nothing\", $i:i) then inferrer.inferFalse(\""
						+ Rule.CLS_NOTHING2.toString() + "\", $i.id); end");

		createRuleDefinition(Rule.CLS_INT1, "cls_int1",
				"rule cls_int1 when OIOCE($x:ceid, $c1:c1) CAA(c==$c1, $y:i) forall ( OIOCE(ceid==$x, $cc:c1) CAA(c==$cc, i==$y)  ) "
						+ "then CAA caa=new CAA($x, $y); inferrer.infer(caa); end");

		createRuleDefinition(Rule.CLS_INT2, "cls_int2",
				"rule cls_int2 when OIOCE($c:ceid, $c1:c1) CAA(c==$c, $y:i) then CAA caa1=new CAA($c1, $y); inferrer.infer(caa1); end");

		createRuleDefinition(Rule.CLS_UNI, "cls_uni",
				"rule cls_uni when OUOCE($c:ceid, $c1:c1) CAA(c==$c1, $y:i) then CAA caa=new CAA($c, $y); inferrer.infer(caa); end");

		createRuleDefinition(Rule.CLS_COM, "cls_com",
				"rule cls_com when OCOCE($c1:ceid, $c2:c) CAA(c==$c1, $x:i) CAA(c==$c2, i==$x) then inferrer.inferFalse(\""
						+ Rule.CLS_COM.toString() + "\", $c1, $c2); end");

		createRuleDefinition(Rule.CLS_SFV1, "cls_sfv1",
				"rule cls_sfv1_op when OSVFCE($x:ceid, $p:p, $y:v) OPAA($u:s, p==$p, $v:o) CAA(c==$y, i==$v) then CAA caa=new CAA($x, $u); inferrer.infer(caa); end");

		createRuleDefinition(Rule.CLS_SFV2, "cls_sfv2",
				"rule cls_sfv2 when OSVFCE($x:ceid, $p:p, v==\"owl:Thing\") OPAA($u:s, p==$p, $v:o) then CAA caa=new CAA($x, $u); inferrer.infer(caa); end");

		createRuleDefinition(Rule.CLS_AVF, "cls_avf",
				"rule cls_avf_op when OAVFCE($x:ceid, $p:p, $y:v) CAA(c==$x, $u:i) OPAA(s==$u, p==$p, $v:o) then CAA caa=new CAA($y, $v); inferrer.infer(caa); end");

		createRuleDefinition(Rule.CLS_HV1, "cls_hv1_op",
				"rule cls_hv1_op when OHVCE($x:ceid, $p:p, $y:v) CAA(c==$x, $u:i) then OPAA opaa=new OPAA($u, $p.id, $y); inferrer.infer(opaa); end");
		createRuleDefinition(Rule.CLS_HV1, "cls_hv1_dp",
				"rule cls_hv1_dp when DHVCE($x:ceid, $p:p, $y:v) CAA(c==$x, $u:i) then DPAA dpaa=new DPAA($u, $p.id, $y); inferrer.infer(dpaa); end");

		createRuleDefinition(Rule.CLS_HV2,
				"rule cls_hv2_op when OHVCE($x:ceid, $p:p, $y:v) OPAA($u:s, p==$p, o==$y) then CAA caa=new CAA($x, $u); inferrer.infer(caa); end",
				"rule cls_hv2_dp when DHVCE($x:ceid, $p:p, $y:v) DPAA($u:s, p==$p, o==$y) then CAA caa=new CAA($x, $u); inferrer.infer(caa); end");

		createRuleDefinition(Rule.CLS_MAXC1, "cls_maxc1_op",
				"rule cls_maxc1_op when OMaxCCE($x:ceid, $p:p, card==0) CAA(c==$x, $u:i) OPAA(s==$u, p==$p, $y:o) then inferrer.inferFalse(\""
						+ Rule.CLS_MAXC1.toString() + "\", $x, $p.id, $u.id, $y.id); end");
		createRuleDefinition(Rule.CLS_MAXC1, "cls_maxc1_dp",
				"rule cls_maxc1_dp when DMaxCCE($x:ceid, $p:p, card==0) CAA(c==$x, $u:i) DPAA(s==$u, p==$p, $y:o) then inferrer.inferFalse(\""
						+ Rule.CLS_MAXC1.toString() + "\", $x, $p.id, $u.id, $y.value); end");

		createRuleDefinition(Rule.CLS_MAXC2, "cls_maxc2",
				"rule cls_maxc2 when OMaxCCE($x:ceid, $p:p, card==1) CAA(c==$x, $u:i) OPAA(s==$u, p==$p, $y1:o) OPAA(s==$u, p==$p, $y2:o) "
						+ "then SIA sia=new SIA($y1, $y2); inferrer.infer(sia); end");

		// T(?x, owl:maxQualifiedCardinality, "0"^^xsd:nonNegativeInteger) T(?x, owl:onProperty, ?p) T(?x, owl:onClass, ?c)
		// T(?u, rdf:type, ?x) T(?u, ?p, ?y) T(?y, rdf:type, ?c) -> false

		createRuleDefinition(Rule.CLS_MAXQC1, "cls_maxqc1_op",
				"rule cls_maxqc1_op when OMaxQCCE($x:ceid, $p:p, $f:f, card==0) CAA(c==$x, $u:i) OPAA(s==$u, p==$p, $y:o) CAA(c==$f, i==$y) then inferrer.inferFalse(\""
						+ Rule.CLS_MAXQC1.toString() + "\", $x, $f, $p.id, $u.id, $y.id); end");

		// T(?x, owl:maxQualifiedCardinality, "0"^^xsd:nonNegativeInteger) T(?x, owl:onProperty, ?p)
		// T(?x, owl:onClass, owl:Thing) T(?u, rdf:type, ?x) T(?u, ?p, ?y) -> false

		createRuleDefinition(Rule.CLS_MAXQC2, "cls_maxqc2_op",
				"rule cls_maxqc2 when OMaxQCCE($x:ceid, $p:p, f==\"owl:Thing\", card==0) CAA(c==$x, $u:i) OPAA(s==$u, p==$p, $y:o) then inferrer.inferFalse(\""
						+ Rule.CLS_MAXQC2.toString() + "\", $x, $p.id, $u.id, $y.id); end");

		createRuleDefinition(Rule.CLS_MAXQC2, "cls_maxqc2_dp",
				"rule cls_maxqc2 when DMaxQCCE($x:ceid, $p:p, f==\"owl:Thing\", card==0) CAA(c==$x, $u:i) DPAA(s==$u, p==$p, $y:o) then inferrer.inferFalse(\""
						+ Rule.CLS_MAXQC2.toString() + "\", $x, $p.id, $u.id, $y.toString()); end");

		// T(?x, owl:maxQualifiedCardinality, "1"^^xsd:nonNegativeInteger) T(?x, owl:onProperty, ?p) T(?x, owl:onClass, ?c)
		// T(?u, rdf:type, ?x) T(?u, ?p, ?y1) T(?y1, rdf:type, ?c) T(?u, ?p, ?y2) T(?y2, rdf:type, ?c)
		// -> T(?y1, owl:sameAs, ?y2)
		createRuleDefinition(Rule.CLS_MAXQC3, "cls_maxqc3_op",
				"rule cls_maxqc3 when OMaxQCCE($x:ceid, $p:p, $f:f, card==1) CAA(c==$x, $u:i) OPAA(s==$u, p==$p, $y1:o) CAA(c==$f, i==$y1) OPAA(s==$u, p==$p, $y2:o) CAA(c==$f, i==$y2) "
						+ "then SIA sia=new SIA($y1, $y2); inferrer.infer(sia); end");

		// T(?x, owl:maxQualifiedCardinality, "1"^^xsd:nonNegativeInteger) T(?x, owl:onProperty, ?p)
		// T(?x, owl:onClass, owl:Thing) T(?u, rdf:type, ?x) T(?u, ?p, ?y1) T(?u, ?p, ?y2) -> false
		createRuleDefinition(Rule.CLS_MAXQC4, "cls_maxqc4",
				"rule cls_maxqc4 when OMaxQCCE($x:ceid, $p:p, f==\"owl:Thing\", card==1) CAA(c==$x, $u:i) OPAA(s==$u, p==$p, $y1:o) OPAA(s==$u, p==$p, $y2:o) "
						+ "then SIA sia=new SIA($y1, $y2); inferrer.infer(sia); end");

		createRuleDefinition(Rule.CLS_OO, "cls_oo",
				"rule cls_oo when OOOCE($c:ceid, $y1:i1, $y2:i2) then CAA caa1=new CAA($c, $y1); CAA caa2=new CAA($c, $y2); inferrer.infer(caa1, caa2); end");
	}

	private void defineOWL2RLTable7DroolsRules()
	{
		createRuleDefinition(Rule.CAX_SCO, "cax_sco",
				"rule cax_sco  when SCA($c1:sub, $c2:sup) CAA(c==$c1, $x:i) then CAA caa=new CAA($c2, $x); inferrer.infer(caa); end");

		createRuleDefinition(Rule.CAX_EQC1, "cax_eqc1",
				"rule cax_eqc1 when ECA($c1:c1, $c2:c2) CAA(c==$c1, $x:i) then CAA caa=new CAA($c2, $x); inferrer.infer(caa); end");

		createRuleDefinition(Rule.CAX_EQC2, "cax_eqc2",
				"rule cax_eqc2 when ECA($c1:c1, $c2:c2) CAA(c==$c2, $x:i) then CAA caa=new CAA($c1, $x); inferrer.infer(caa); end");

		createRuleDefinition(Rule.CAX_DW, "cax_dw",
				"rule cax_dw when DCA($c1:c1, $c2:c2) CAA(c==$c1, $x:i) CAA(c==$c2, i==$x) then inferrer.inferFalse(\""
						+ Rule.CAX_DW.toString() + "\", $c1, $c2, $x.id); end");

		// CAX_ADC will be handled by CAX_DW because Drools is supplied an exhaustive pairwise element extraction of DCAs
		// from an owl:AllDisjointClasses axiom.
	}

	private void defineOWL2RLTable8DroolsRules()
	{
		// TODO DT_TYPE1, DT_TYPE2, DT_EQ, and DT_DIFF implemented programatically, though only very partial at the moment.
		// TODO DT_NOT_TYPE not implemented.
	}

	private void defineOWL2RLTable9DroolsRules()
	{
		createRuleDefinition(
				Rule.SCM_CLS,
				"scm_cls",
				"rule scm_cls when CDA($c:c) then SCA sca1=new SCA($c, $c); ECA eca=new ECA($c, $c); SCA sca2=new SCA($c, \"owl:Thing\"); SCA sca3=new SCA(\"owl:Nothing\", $c); "
						+ "inferrer.infer(sca1, eca, sca2, sca3); end");

		createRuleDefinition(
				Rule.SCM_SCO,
				"scm_sco",
				"rule scm_sco when SCA($c1:sub, $c2:sup) SCA(sub==$c2, $c3:sup) then SCA sca=new SCA($c1, $c3); inferrer.infer(sca); end");

		createRuleDefinition(
				Rule.SCM_EQC1,
				"scm_eqc1",
				"rule scm_eqc1 when ECA($c1:c1, $c2:c2) then SCA sca1=new SCA($c1, $c2); SCA sca2=new SCA($c2, $c1); inferrer.infer(sca1, sca2); end");

		createRuleDefinition(
				Rule.SCM_EQC2,
				"scm_eqc2",
				"rule scm_eqc2 when SCA($c1:sub, $c2:sup) SCA(sub==$c2, sup==$c1) then ECA eca=new ECA($c1, $c2); inferrer.infer(eca); end");

		createRuleDefinition(
				Rule.SCM_OP,
				"scm_op",
				"rule scm_op when OPDA($p:p) then SOPA sopa=new SOPA($p, $p); EOPA eopa=new EOPA($p, $p); inferrer.infer(sopa, eopa); end");

		createRuleDefinition(
				Rule.SCM_DP,
				"scm_dp",
				"rule scm_dp when DPDA($p:p) then SDPA sdpa=new SDPA($p, $p); EDPA edpa=new EDPA($p, $p); inferrer.infer(sdpa, edpa); end");

		createRuleDefinition(
				Rule.SCM_SPO,
				"scm_spo_op",
				"rule scm_spo_op when SOPA($p1:sub, $p2:sup) SOPA(sub==$p2, $p3:sup) then SOPA sopa=new SOPA($p1, $p3); inferrer.infer(sopa); end");
		createRuleDefinition(
				Rule.SCM_SPO,
				"scm_spo_dp",
				"rule scm_spo_dp when SDPA($p1:sub, $p2:sup) SDPA(sub==$p2, $p3:sup) then SDPA sdpa=new SDPA($p1, $p3); inferrer.infer(sdpa); end");

		createRuleDefinition(
				Rule.SCM_EQP1,
				"scm_eqp1_op",
				"rule scm_eqp1_op when EOPA($p1:p1, $p2:p2) then SOPA sopa1=new SOPA($p1.id, $p2.id); SOPA sopa2=new SOPA($p2.id, $p1.id); inferrer.infer(sopa1, sopa2); end");
		createRuleDefinition(
				Rule.SCM_EQP1,
				"scm_eqp1_dp",
				"rule scm_eqp1_dp when EDPA($p1:p1, $p2:p2) then SDPA sdpa1=new SDPA($p1.id, $p2.id); SDPA sdpa2=new SDPA($p2.id, $p1.id); inferrer.infer(sdpa1, sdpa2); end");

		createRuleDefinition(
				Rule.SCM_EQP2,
				"scm_eqp2_op",
				"rule scm_eqp2_op when SOPA($p1:sub, $p2:sup) SOPA(sub==$p2, sup==$p1) then EOPA eopa=new EOPA($p1, $p2); inferrer.infer(eopa); end");
		createRuleDefinition(
				Rule.SCM_EQP2,
				"scm_eqp2_dp",
				"rule scm_eqp2_dp when SDPA($p1:sub, $p2:sup) SDPA(sub==$p2, sup==$p1) then EDPA edpa=new EDPA($p1, $p2); inferrer.infer(edpa); end");

		createRuleDefinition(
				Rule.SCM_DOM1,
				"scm_dom1_op",
				"rule scm_dom1_op when DOPA($p:p, $c1:d) SCA(sub==$c1, $c2:sup) then DOPA dopa=new DOPA($p, $c2); inferrer.infer(dopa); end");
		createRuleDefinition(
				Rule.SCM_DOM1,
				"scm_dom1_dp",
				"rule scm_dom1_dp when DDPA($p:p, $c1:d) SCA(sub==$c1, $c2:sup) then DDPA ddpa=new DDPA($p, $c2); inferrer.infer(ddpa); end");

		createRuleDefinition(
				Rule.SCM_DOM2,
				"scm_dom2_op",
				"rule scm_dom2_op when DOPA($p2:p, $c:d) SOPA($p1:sub, sup==$p2) then DOPA dopa=new DOPA($p1, $c); inferrer.infer(dopa); end");
		createRuleDefinition(
				Rule.SCM_DOM2,
				"scm_dom2_dp",
				"rule scm_dom2_dp when DDPA($p2:p, $c:d) SDPA($p1:sub, sup==$p2) then DDPA ddpa=new DDPA($p1, $c); inferrer.infer(ddpa); end");

		createRuleDefinition(
				Rule.SCM_RNG1,
				"scm_rng2_op",
				"rule scm_rng1_op when OPRA($p:p, $c1:r) SCA(sub==$c1, $c2:sup) then OPRA ropa=new OPRA($p, $c2); inferrer.infer(ropa); end");

		createRuleDefinition(
				Rule.SCM_RNG2,
				"scm_rng2_op",
				"rule scm_rng2_op when OPRA($p2:p, $c:r) SOPA($p1:sub, sup==$p2) then OPRA ropa=new OPRA($p1, $c); inferrer.infer(ropa); end");

		createRuleDefinition(Rule.SCM_HV, "scm_hv_op",
				"rule scm_hv_op when OHVCE($c1:ceid, $p1:p, $i:v) OHVCE($c2:ceid, $p2:p, v==$i) SOPA(sub==$p1, sup==$p2) "
						+ "then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

		createRuleDefinition(Rule.SCM_HV, "scm_hv_dp",
				"rule scm_hv_dp when DHVCE($c1:ceid, $p1:p, $i:v) DHVCE($c2:ceid, $p2:p, v==$i) SDPA(sub==$p1, sup==$p2) "
						+ "then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

		createRuleDefinition(Rule.SCM_SVF1, "scm_svf1",
				"rule scm_svf1 when OSVFCE($c1:ceid, $p:p, $y1:v) OSVFCE($c2:ceid, p==$p, $y2:v) SCA(sub==$y1, sup==$y2) "
						+ "then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

		createRuleDefinition(Rule.SCM_SVF2, "scm_svf2_op",
				"rule scm_svf2_op when OSVFCE($c1:ceid, $p1:p, $y:v) OSVFCE($c2:ceid, $p2:p, v==$y) SOPA(sub==$p1, sup==$p2) "
						+ "then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

		createRuleDefinition(Rule.SCM_SVF2, "scm_svf2_dp",
				"rule scm_svf2_dp when DSVFCE($c1:ceid, $p1:p, $y:v) DSVFCE($c2:ceid, $p2:p, v==$y) SDPA(sub==$p1, sup==$p2) "
						+ "then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

		createRuleDefinition(Rule.SCM_AVF1, "scm_afv1",
				"rule scm_avf1 when OAVFCE($c1:ceid, $p:p, $y1:v) OAVFCE($c2:ceid, p==$p, $y2:v) SCA(sub==$y1, sup==$y2) then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

		createRuleDefinition(Rule.SCM_AVF2, "scm_avf2_op",
				"rule scm_avf2_op when OAVFCE($c1:ceid, $p1:p, $y:v) OAVFCE($c2:ceid, $p2:p, v==$y) SOPA(sub==$p1, sup==$p2) "
						+ " then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

		createRuleDefinition(Rule.SCM_AVF2, "scm_avf2_dop",
				"rule scm_avf2_dp when DAVFCE($c1:ceid, $p1:p, $y:v) DAVFCE($c2:ceid, $p2:p, v==$y) SDPA(sub==$p1, sup==$p2) "
						+ " then SCA sca=new SCA($c1, $c2); inferrer.infer(sca); end");

		createRuleDefinition(Rule.SCM_INT, "scm_int",
				"rule scm_int when OIOCE($c:ceid, $c1:c1) then SCA sca1=new SCA($c, $c1); inferrer.infer(sca1); end");

		createRuleDefinition(Rule.SCM_UNI, "scm_uni",
				"rule scm_uni when OUOCE($c:ceid, $c1:c1) then SCA sca1=new SCA($c1, $c); inferrer.infer(sca1); end");
	}

	public class DroolsRuleDefinition
	{
		private final String ruleName, ruleText;

		public DroolsRuleDefinition(String ruleName, String ruleText)
		{
			this.ruleName = ruleName;
			this.ruleText = ruleText;
		}

		public String getRuleName()
		{
			return this.ruleName;
		}

		public String getRuleText()
		{
			return this.ruleText;
		}
	}

	private void createRuleDefinition(Rule rule, String ruleName, String ruleText)
	{
		DroolsRuleDefinition newRuleDefinition = new DroolsRuleDefinition(ruleName, ruleText);

		if (this.droolsRules.containsKey(rule))
			this.droolsRules.get(rule).add(newRuleDefinition);
		else {
			Set<DroolsRuleDefinition> newRuleDefinitions = new HashSet<DroolsRuleDefinition>();
			newRuleDefinitions.add(newRuleDefinition);
			this.droolsRules.put(rule, newRuleDefinitions);
		}
	}
}
