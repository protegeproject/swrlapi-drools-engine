package org.swrlapi.sqwrl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLRuleEngineFactory;
import org.swrlapi.drools.core.DroolsSWRLRuleEngineCreator;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.test.SWRLAPITestBase;

public class SQWRLCoreTestCase extends SWRLAPITestBase
{
	String Namespace = "http://swrlapi.org/ontologies/SQWRLCoreTestCase.owl#";
	double DELTA = 1e-6;

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
	public void TestSQWRLCount() throws SWRLParseException, SQWRLException
	{
		declareOWLClassAssertion("Male", "p1");
		declareOWLClassAssertion("Male", "p2");

		SQWRLResult result = executeSQWRLQuery("q1", "Male(?m) -> sqwrl:count(?m)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 2);
	}

	@Test
	public void TestSQWRLAvg() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "10", "xsd:int");
		declareOWLDataPropertyAssertion("p2", "hasAge", "20", "xsd:int");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:avg(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDouble());
		Assert.assertEquals(literal.getDouble(), 20.0, DELTA);
	}

	@Test
	public void TestSQWRLMin() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "10", "xsd:int");
		declareOWLDataPropertyAssertion("p2", "hasAge", "20", "xsd:int");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:min(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 10);
	}

	@Test
	public void TestSQWRLMax() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "10", "xsd:int");
		declareOWLDataPropertyAssertion("p2", "hasAge", "20", "xsd:int");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:max(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 30);
	}

	@Test
	public void TestSQWRLOrderByInt() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "20", "xsd:int");
		declareOWLDataPropertyAssertion("p2", "hasAge", "10", "xsd:int");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderBy(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isInt());
		Assert.assertEquals(result.getLiteral("age").getInt(), 10);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isInt());
		Assert.assertEquals(result.getLiteral("age").getInt(), 20);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isInt());
		Assert.assertEquals(result.getLiteral("age").getInt(), 30);
	}

	@Test
	public void TestSQWRLOrderByDescendingInt() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "20", "xsd:int");
		declareOWLDataPropertyAssertion("p2", "hasAge", "10", "xsd:int");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderByDescending(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isInt());
		Assert.assertEquals(result.getLiteral("age").getInt(), 30);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isInt());
		Assert.assertEquals(result.getLiteral("age").getInt(), 20);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isInt());
		Assert.assertEquals(result.getLiteral("age").getInt(), 10);
	}

	@Test
	public void TestSQWRLOrderByString() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasName", "Fred", "xsd:string");
		declareOWLDataPropertyAssertion("p2", "hasName", "Bob", "xsd:string");
		declareOWLDataPropertyAssertion("p3", "hasName", "Ann", "xsd:string");

		SQWRLResult result = executeSQWRLQuery("q1", "hasName(?p, ?name)-> sqwrl:select(?p, ?name) ^ sqwrl:orderBy(?name)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("name").isString());
		Assert.assertEquals(result.getLiteral("name").getString(), "Ann");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("name").isString());
		Assert.assertEquals(result.getLiteral("name").getString(), "Bob");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("name").isString());
		Assert.assertEquals(result.getLiteral("name").getString(), "Fred");
	}

	@Test
	public void TestSQWRLOrderByDescendingString() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasName", "Fred", "xsd:string");
		declareOWLDataPropertyAssertion("p2", "hasName", "Bob", "xsd:string");
		declareOWLDataPropertyAssertion("p3", "hasName", "Ann", "xsd:string");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasName(?p, ?name)-> sqwrl:select(?p, ?name) ^ sqwrl:orderByDescending(?name)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("name").isString());
		Assert.assertEquals(result.getLiteral("name").getString(), "Fred");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("name").isString());
		Assert.assertEquals(result.getLiteral("name").getString(), "Bob");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("name").isString());
		Assert.assertEquals(result.getLiteral("name").getString(), "Ann");
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
