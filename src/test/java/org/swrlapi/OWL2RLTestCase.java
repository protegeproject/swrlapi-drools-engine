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
}
