package org.swrlapi.sqwrl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.core.SWRLAPIFactory;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.SWRLRuleEngineFactory;
import org.swrlapi.core.xsd.XSDDate;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.core.xsd.XSDTime;
import org.swrlapi.drools.core.DroolsSWRLRuleEngineCreator;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.test.SWRLAPITestBase;

public class SQWRLCoreTestCase extends SWRLAPITestBase
{
	final String Namespace = "http://swrlapi.org/ontologies/SQWRLCoreTestCase.owl#";
	final double DELTA = 1e-6;

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
	public void TestSQWRLOrderByShort() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "20", "xsd:short");
		declareOWLDataPropertyAssertion("p2", "hasAge", "10", "xsd:short");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:short");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderBy(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isShort());
		Assert.assertEquals(result.getLiteral("age").getShort(), 10);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isShort());
		Assert.assertEquals(result.getLiteral("age").getShort(), 20);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isShort());
		Assert.assertEquals(result.getLiteral("age").getShort(), 30);
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
	public void TestSQWRLOrderByLong() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "20", "xsd:long");
		declareOWLDataPropertyAssertion("p2", "hasAge", "10", "xsd:long");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:long");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderBy(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isLong());
		Assert.assertEquals(result.getLiteral("age").getLong(), 10);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isLong());
		Assert.assertEquals(result.getLiteral("age").getLong(), 20);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isLong());
		Assert.assertEquals(result.getLiteral("age").getLong(), 30);
	}

	@Test
	public void TestSQWRLOrderByFloat() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeight", "200.0", "xsd:float");
		declareOWLDataPropertyAssertion("p2", "hasHeight", "100.0", "xsd:float");
		declareOWLDataPropertyAssertion("p3", "hasHeight", "300.0", "xsd:float");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasHeight(?p, ?height)-> sqwrl:select(?p, ?height) ^ sqwrl:orderBy(?height)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("height").isFloat());
		Assert.assertEquals(result.getLiteral("height").getFloat(), 100.0, DELTA);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("height").isFloat());
		Assert.assertEquals(result.getLiteral("height").getFloat(), 200.0, DELTA);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("height").isFloat());
		Assert.assertEquals(result.getLiteral("height").getFloat(), 300.0, DELTA);
	}

	@Test
	public void TestSQWRLOrderByDouble() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeight", "200.0", "xsd:double");
		declareOWLDataPropertyAssertion("p2", "hasHeight", "100.0", "xsd:double");
		declareOWLDataPropertyAssertion("p3", "hasHeight", "300.0", "xsd:double");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasHeight(?p, ?height)-> sqwrl:select(?p, ?height) ^ sqwrl:orderBy(?height)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("height").isDouble());
		Assert.assertEquals(result.getLiteral("height").getDouble(), 100.0, DELTA);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("height").isDouble());
		Assert.assertEquals(result.getLiteral("height").getDouble(), 200.0, DELTA);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("height").isDouble());
		Assert.assertEquals(result.getLiteral("height").getDouble(), 300.0, DELTA);
	}

	@Test
	public void TestSQWRLOrderByDate() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasDOB", "1990-01-02", "xsd:date");
		declareOWLDataPropertyAssertion("p2", "hasDOB", "1989-10-10", "xsd:date");
		declareOWLDataPropertyAssertion("p3", "hasDOB", "1991-01-10", "xsd:date");

		SQWRLResult result = executeSQWRLQuery("q1", "hasDOB(?p, ?dob)-> sqwrl:select(?p, ?dob) ^ sqwrl:orderBy(?dob)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("dob").isDate());
		Assert.assertEquals(result.getLiteral("dob").getDate(), new XSDDate("1989-10-10"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("dob").isDate());
		Assert.assertEquals(result.getLiteral("dob").getDate(), new XSDDate("1990-01-02"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("dob").isDate());
		Assert.assertEquals(result.getLiteral("dob").getDate(), new XSDDate("1991-01-10"));
	}

	@Test
	public void TestSQWRLOrderByDateTime() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasTOB", "1990-01-02T10:10:09.2", "xsd:dateTime");
		declareOWLDataPropertyAssertion("p2", "hasTOB", "1989-10-10T09:08:08.3", "xsd:dateTime");
		declareOWLDataPropertyAssertion("p3", "hasTOB", "1991-01-10T11:11:11.11", "xsd:dateTime");

		SQWRLResult result = executeSQWRLQuery("q1", "hasTOB(?p, ?tob)-> sqwrl:select(?p, ?tob) ^ sqwrl:orderBy(?tob)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("tob").isDateTime());
		Assert.assertEquals(result.getLiteral("tob").getDateTime(), new XSDDateTime("1989-10-10T09:08:08.3"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("tob").isDateTime());
		Assert.assertEquals(result.getLiteral("tob").getDateTime(), new XSDDateTime("1990-01-02T10:10:09.2"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("tob").isDateTime());
		Assert.assertEquals(result.getLiteral("tob").getDateTime(), new XSDDateTime("1991-01-10T11:11:11.11"));
	}

	@Test
	public void TestSQWRLOrderByTime() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasTOB", "10:10:09.2", "xsd:time");
		declareOWLDataPropertyAssertion("p2", "hasTOB", "09:08:08.3", "xsd:time");
		declareOWLDataPropertyAssertion("p3", "hasTOB", "11:11:11.11", "xsd:time");

		SQWRLResult result = executeSQWRLQuery("q1", "hasTOB(?p, ?tob)-> sqwrl:select(?p, ?tob) ^ sqwrl:orderBy(?tob)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("tob").isTime());
		Assert.assertEquals(result.getLiteral("tob").getTime(), new XSDTime("09:08:08.3"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("tob").isTime());
		Assert.assertEquals(result.getLiteral("tob").getTime(), new XSDTime("10:10:09.2"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("tob").isTime());
		Assert.assertEquals(result.getLiteral("tob").getTime(), new XSDTime("11:11:11.11"));
	}

	@Test
	public void TestSQWRLOrderByDuration() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "P23Y", "xsd:duration");
		declareOWLDataPropertyAssertion("p2", "hasAge", "P21Y", "xsd:duration");
		declareOWLDataPropertyAssertion("p3", "hasAge", "P30Y", "xsd:duration");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderBy(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isDuration());
		Assert.assertEquals(result.getLiteral("age").getDuration(), new XSDDuration("P21Y"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isDuration());
		Assert.assertEquals(result.getLiteral("age").getDuration(), new XSDDuration("P23Y"));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isDuration());
		Assert.assertEquals(result.getLiteral("age").getDuration(), new XSDDuration("P30Y"));
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
