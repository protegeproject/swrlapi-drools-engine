package org.swrlapi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLRuleEngineFactory;
import org.swrlapi.drools.core.DroolsSWRLRuleEngineCreator;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.SWRLAPITestBase;

public class OWL2RLTestCase extends SWRLAPITestBase
{
	final String Namespace = "http://swrlapi.org/ontologies/OWL2RLTestCase.owl#";

	SQWRLQueryEngine sqwrlQueryEngine;

	@Before
	public void setUp() throws OWLOntologyCreationException
	{
		SWRLAPIOWLOntology swrlapiOWLOntology = createEmptyOntology(Namespace);

		SWRLRuleEngineFactory swrlRuleEngineFactory = SWRLAPIFactory.createSWRLRuleEngineFactory();
		swrlRuleEngineFactory.registerRuleEngine(new DroolsSWRLRuleEngineCreator());

		sqwrlQueryEngine = swrlRuleEngineFactory.createSQWRLQueryEngine(swrlapiOWLOntology);
	}

	@Test
	public void TEST_EQU_REF_C() throws SWRLParseException, SQWRLException
	{
		declareOWLClassAssertion("C", "i");

		SQWRLResult result = executeSQWRLQuery("q1", "sameAs(i, i) -> sqwrl:select(\"Yes!\")");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TEST_EQU_REF_OP() throws SWRLParseException, SQWRLException
	{
		declareOWLObjectPropertyAssertion("s", "p", "o");

		SQWRLResult result = executeSQWRLQuery("q1", "sameAs(s, s) ^ sameAs(o, o) -> sqwrl:select(\"Yes!\")");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TEST_EQU_REF_DP() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("s", "p", "1", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "sameAs(s, s) -> sqwrl:select(\"Yes!\")");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TEST_EQU_SYM() throws SWRLParseException, SQWRLException
	{
		declareOWLSameAsAssertion("i1", "i2");

		SQWRLResult result = executeSQWRLQuery("q1", "sameAs(i2, i1) -> sqwrl:select(\"Yes!\")");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TEST_EQU_TRANS() throws SWRLParseException, SQWRLException
	{
		declareOWLSameAsAssertion("i1", "i2");
		declareOWLSameAsAssertion("i2", "i3");

		SQWRLResult result = executeSQWRLQuery("q1", "sameAs(i1, i3) -> sqwrl:select(\"Yes!\")");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TEST_EQU_REP_S_C() throws SWRLParseException, SQWRLException
	{
		declareOWLSameAsAssertion("i1", "i2");
		declareOWLClassAssertion("C", "i1");

		SQWRLResult result = executeSQWRLQuery("q1", "C(i2) -> sqwrl:select(\"Yes!\")");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TEST_EQU_REP_S_OP() throws SWRLParseException, SQWRLException
	{
		declareOWLSameAsAssertion("i1", "i2");
		declareOWLObjectPropertyAssertion("i1", "p", "o");

		SQWRLResult result = executeSQWRLQuery("q1", "p(i2, o) -> sqwrl:select(\"Yes!\")");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TEST_EQU_REP_S_DP() throws SWRLParseException, SQWRLException
	{
		declareOWLSameAsAssertion("i1", "i2");
		declareOWLDataPropertyAssertion("i1", "p", "3", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "p(i2, 3) -> sqwrl:select(\"Yes!\")");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TEST_EQU_REP_O() throws SWRLParseException, SQWRLException
	{
		declareOWLSameAsAssertion("i1", "i2");
		declareOWLObjectPropertyAssertion("s", "p", "i2");

		SQWRLResult result = executeSQWRLQuery("q1", "p(s, i1) -> sqwrl:select(\"Yes!\")");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TEST_PRP_DOM_OP() throws SWRLParseException, SQWRLException
	{
		declareOWLObjectPropertyDomainAxiom("p", "C");
		declareOWLObjectPropertyAssertion("s", "p", "o");

		SQWRLResult result = executeSQWRLQuery("q1", "C(s) -> sqwrl:select(\"Yes!\")");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TEST_PRP_DOM_DP() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyDomainAxiom("p", "C");
		declareOWLDataPropertyAssertion("s", "p", "1", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "C(s) -> sqwrl:select(\"Yes!\")");

		Assert.assertTrue(result.next());
	}

	private SQWRLResult executeSQWRLQuery(String queryName) throws SQWRLException
	{
		return sqwrlQueryEngine.runSQWRLQuery(queryName);
	}

	protected SQWRLResult executeSQWRLQuery(String queryName, String query) throws SQWRLException, SWRLParseException
	{
		createSQWRLQuery(queryName, query);

		return executeSQWRLQuery(queryName);
	}

	// TODO Turn the following into tests.
	// // T(?x, ?p, ?y) T(?y, rdf:type, ?c) -> T(?p, rdfs:range, ?c)
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.PRP_RNG, "prp_rng",
	// "rule prp_rng when OPRA($p:pid, $r:rid) OPAA($x:s, pid==$p, $y:o) then CAA caa=new CAA($r, $y); inferrer.infer(caa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.PRP_FP, "prp_fp",
	// "rule prp_fp when FOPA($p:pid) OPAA($x:s, pid==$p, $y1:o) OPAA(s==$x, pid==$p, $y2:o) then SIA sia=new SIA($y1, $y2); inferrer.infer(sia); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.PRP_IFP, "prp_ifp",
	// "rule prp_ifp_op when IFOPA($p:pid) OPAA($x1:s, pid==$p, $y:o) OPAA($x2:s, pid==$p, o==$y) then SIA sia=new SIA($x1, $x2); inferrer.infer(sia); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.PRP_SYMP, "prp_symp",
	// "rule prp_symp when SPA($p:pid) OPAA($x:s, pid==$p, $y:o) then OPAA opaa=new OPAA($y, $p, $x); inferrer.infer(opaa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.PRP_TRP, "prp_trp",
	// "rule prp_trp when TOPA($p:pid) OPAA($x:s, pid==$p, $y:o) OPAA(s==$y, pid==$p, $z:o) then OPAA opaa=new OPAA($x, $p, $z); inferrer.infer(opaa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.PRP_SPO1, "prp_spo1_op",
	// "rule prp_spo1_op when SOPA($p1:subpid, $p2:superpid) OPAA($x:s, pid==$p1, $y:o) then OPAA opaa=new OPAA($x, $p2, $y); inferrer.infer(opaa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.PRP_SPO1, "prp_spo1_dp",
	// "rule prp_spo1_dp when SDPA($p1:subpid, $p2:superpid) DPAA($x:s, pid==$p1, $y:o) then DPAA dpaa=new DPAA($x, $p2, $y); inferrer.infer(dpaa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.PRP_EQP1, "prp_eqp1_op",
	// "rule prp_eqp1_op when EOPA($p1:p1id, $p2:p2id) OPAA($x:s, pid==$p1, $y:o) then OPAA opaa=new OPAA($x, $p2, $y); inferrer.infer(opaa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.PRP_EQP1, "prp_eqp1_dp",
	// "rule prp_eqp1_dp when EDPA($p1:p1id, $p2:p2id) DPAA($x:s, pid==$p1, $y:o) then DPAA dpaa=new DPAA($x, $p2, $y); inferrer.infer(dpaa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.PRP_EQP2, "prp_eqp2_op",
	// "rule prp_eqp2_op when EOPA($p1:p1id, $p2:p2id) OPAA($x:s, pid==$p2, $y:o) then OPAA opaa=new OPAA($x, $p1, $y); inferrer.infer(opaa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.PRP_EQP2, "prp_eqp2_dp",
	// "rule prp_eqp2_dp when EDPA($p1:p1id, $p2:p2id) DPAA($x:s, pid==$p2, $y:o) then DPAA dpaa=new DPAA($x, $p1, $y); inferrer.infer(dpaa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.PRP_INV1, "prp_inv1",
	// "rule prp_inv1 when IOPA($p1:p1id, $p2:p2id) OPAA($x:s, pid==$p1, $y:o) then OPAA opaa=new OPAA($y, $p2, $x); inferrer.infer(opaa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.PRP_INV2, "prp_inv2",
	// "rule prp_inv2 when IOPA($p1:p1id, $p2:p2id) OPAA($x:s, pid==$p2, $y:o) then OPAA opaa=new OPAA($y, $p1, $x); inferrer.infer(opaa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CLS_THING, "cls_thing",
	// "rule cls_thing when then CDA cda = new CDA(\"owl:Thing\"); inferrer.infer(cda); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CLS_NOTHING1, "cls_nothing1",
	// "rule cls_nothing1 when then CDA cda=new CDA(\"owl:Nothing\"); inferrer.infer(cda); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CLS_INT1, "cls_int1",
	// "rule cls_int1 when OIOCE($x:ceid, $c1:c1) CAA(cid==$c1, $y:i) forall ( OIOCE(ceid==$x, $cc:c1) CAA(cid==$cc, i==$y)  ) "
	// + "then CAA caa=new CAA($x, $y); inferrer.infer(caa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CLS_INT2, "cls_int2",
	// "rule cls_int2 when OIOCE($c:ceid, $c1:c1) CAA(cid==$c, $y:i) then CAA caa1=new CAA($c1, $y); inferrer.infer(caa1); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CLS_UNI, "cls_uni",
	// "rule cls_uni when OUOCE($c:ceid, $c1:c1) CAA(cid==$c1, $y:i) then CAA caa=new CAA($c, $y); inferrer.infer(caa); end");
	//
	// // T(?x, owl:someValuesFrom, ?y) T(?x, owl:onProperty, ?p) T(?u, ?p, ?v) T(?v, rdf:type, ?y) -> T(?u, rdf:type, ?x)
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CLS_SFV1, "cls_sfv1",
	// "rule cls_sfv1 when OSVFCE($x:ceid, $p:pid, $y:v) OPAA($u:s, pid==$p, $v:o) CAA(cid==$y, i==$v) then CAA caa=new CAA($x, $u); inferrer.infer(caa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CLS_SFV2, "cls_sfv2",
	// "rule cls_sfv2 when OSVFCE($x:ceid, $p:pid, v==\"owl:Thing\") OPAA($u:s, pid==$p, $v:o) then CAA caa=new CAA($x, $u); inferrer.infer(caa); end");
	//
	// // T(?x, owl:allValuesFrom, ?y) T(?x, owl:onProperty, ?p) T(?u, rdf:type, ?x) T(?u, ?p, ?v) -> T(?v, rdf:type, ?y)
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CLS_AVF, "cls_avf",
	// "rule cls_avf when OAVFCE($x:ceid, $p:pid, $y:v) CAA(cid==$x, $u:i) OPAA(s==$u, pid==$p, $v:o) then CAA caa=new CAA($y, $v); inferrer.infer(caa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CLS_HV1, "cls_hv1_op",
	// "rule cls_hv1_op when OHVCE($x:ceid, $p:pid, $y:v) CAA(cid==$x, $u:i) then OPAA opaa=new OPAA($u, $p, $y); inferrer.infer(opaa); end");
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CLS_HV1, "cls_hv1_dp",
	// "rule cls_hv1_dp when DHVCE($x:ceid, $p:pid, $y:v) CAA(cid==$x, $u:i) then DPAA dpaa=new DPAA($u, $p, $y); inferrer.infer(dpaa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CLS_HV2,
	// "rule cls_hv2_op when OHVCE($x:ceid, $p:pid, $y:v) OPAA($u:s, pid==$p, o==$y) then CAA caa=new CAA($x, $u); inferrer.infer(caa); end",
	// "rule cls_hv2_dp when DHVCE($x:ceid, $p:pid, $y:v) DPAA($u:s, pid==$p, o==$y) then CAA caa=new CAA($x, $u); inferrer.infer(caa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CLS_MAXC2, "cls_maxc2",
	// "rule cls_maxc2 when OMaxCCE($x:ceid, $p:pid, card==1) CAA(cid==$x, $u:i) OPAA(s==$u, pid==$p, $y1:o) OPAA(s==$u, pid==$p, $y2:o) "
	// + "then SIA sia=new SIA($y1, $y2); inferrer.infer(sia); end");
	//
	// // T(?x, owl:maxQualifiedCardinality, "1"^^xsd:nonNegativeInteger) T(?x, owl:onProperty, ?p) T(?x, owl:onClass, ?c)
	// // T(?u, rdf:type, ?x) T(?u, ?p, ?y1) T(?y1, rdf:type, ?c) T(?u, ?p, ?y2) T(?y2, rdf:type, ?c)
	// // -> T(?y1, owl:sameAs, ?y2)
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CLS_MAXQC3, "cls_maxqc3_op",
	// "rule cls_maxqc3 when OMaxQCCE($x:ceid, $p:pid, $f:f, card==1) CAA(cid==$x, $u:i) OPAA(s==$u, pid==$p, $y1:o) "
	// + "CAA(cid==$f, i==$y1) OPAA(s==$u, pid==$p, $y2:o) CAA(cid==$f, i==$y2) "
	// + "then SIA sia=new SIA($y1, $y2); inferrer.infer(sia); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CLS_OO, "cls_oo",
	// "rule cls_oo when OOOCE($c:ceid, $y1:i1, $y2:i2) then CAA caa1=new CAA($c, $y1); CAA caa2=new CAA($c, $y2); inferrer.infer(caa1, caa2); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CAX_SCO, "cax_sco",
	// "rule cax_sco  when SCA($c1:subcid, $c2:supercid) CAA(cid==$c1, $x:i) then CAA caa=new CAA($c2, $x); inferrer.infer(caa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CAX_EQC1, "cax_eqc1",
	// "rule cax_eqc1 when ECA($c1:c1id, $c2:c2id) CAA(cid==$c1, $x:i) then CAA caa=new CAA($c2, $x); inferrer.infer(caa); end");
	//
	// createOWL2RLRuleDefinition(OWL2RLNames.Rule.CAX_EQC2, "cax_eqc2",
	// "rule cax_eqc2 when ECA($c1:c1id, $c2:c2id) CAA(cid==$c2, $x:i) then CAA caa=new CAA($c1, $x); inferrer.infer(caa); end");

}
