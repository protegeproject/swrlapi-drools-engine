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
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.test.SWRLAPITestBase;

public class SWRLCoreBuiltInTestCase extends SWRLAPITestBase
{
	final String Namespace = "http://swrlapi.org/ontologies/SWRLCoreBuiltInTestCase.owl#";
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
	public void TestSWRLCoreEqualBuiltInWithShort() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:short");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(p1, ?age) ^ swrlb:equal(?age, \"42\"^^\"xsd:short\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLCoreEqualBuiltInWithInt() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:equal(?age, 42) -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLCoreEqualBuiltInWithLong() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:long");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(p1, ?age) ^ swrlb:equal(?age, \"42\"^^\"xsd:long\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLCoreEqualBuiltInWithBoolean() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "isFrench", "true", "xsd:boolean");

		SQWRLResult result = executeSQWRLQuery("q1",
				"isFrench(p1, ?french) ^ swrlb:equal(?french, true)-> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLCoreEqualBuiltInWithFloat() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeightInCM", "177.0", "xsd:float");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasHeightInCM(p1, ?height) ^ swrlb:equal(?height, 177.0) -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLCoreEqualBuiltInWithDouble() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeightInCM", "177.0", "xsd:double");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasHeightInCM(p1, ?height) ^ swrlb:equal(?height, \"177.0\"^^\"xsd:double\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLECorequalBuiltInWithString() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasName", "Bob", "xsd:string");

		SQWRLResult result = executeSQWRLQuery("q1", "hasName(p1, ?name) ^ swrlb:equal(?name, \"Bob\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLCoreEqualBuiltInWithXSDDate() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasDOB", "2001-01-05", "xsd:date");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasDOB(p1, ?dob) ^ swrlb:equal(?dob, \"2001-01-05\"^^\"xsd:date\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLCoreEqualBuiltInWithXSDDateTime() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasTOB", "2001-01-05T10:10:10", "xsd:dateTime");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasTOB(p1, ?tob) ^ swrlb:equal(?tob, \"2001-01-05T10:10:10\"^^\"xsd:dateTime\")-> sqwrl:select(p1, ?tob)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLCoreEqualBuiltInWithXSDDuration() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "P42Y", "xsd:duration");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(p1, ?age) ^ swrlb:equal(?age, \"P42Y\"^^\"xsd:duration\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithShort() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(\"4\"^^\"xsd:short\", \"2\"^^\"xsd:short\", \"2\"^^\"xsd:short\") -> sqwrl:select(\"Yes\")";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithShortAndBoundResult() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(?r, \"2\"^^\"xsd:short\", \"2\"^^\"xsd:short\") -> sqwrl:select(?r)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isShort());
		Assert.assertEquals(literal.getShort(), 4);
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithInt() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:add(4, 2, 2) -> sqwrl:select(\"Yes\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isString());
		Assert.assertEquals(literal.getString(), "Yes");
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithIntAndBoundResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:add(?r, 2, 2) -> sqwrl:select(?r)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 4);
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithFloat() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:add(4.1, 2.1, 2.0) -> sqwrl:select(\"Yes\")");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithLong() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(\"4\"^^\"xsd:long\", \"2\"^^\"xsd:long\", \"2\"^^\"xsd:long\") -> sqwrl:select(\"Yes\")";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithLongAndBoundResult() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(?r, \"2\"^^\"xsd:long\", \"2\"^^\"xsd:long\") -> sqwrl:select(?r)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isLong());
		Assert.assertEquals(literal.getLong(), 4);
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithDoubles() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(\"4.0\"^^\"xsd:double\", \"2.0\"^^\"xsd:double\", \"2.0\"^^\"xsd:double\")"
				+ " -> sqwrl:select(\"Yes\")";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithFloatAndBoundResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:add(?r, 2.1, 2.0) -> sqwrl:select(?r)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isFloat());
		Assert.assertEquals(literal.getFloat(), 4.1, DELTA);
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithDoubleAndBoundResult() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(?r, \"2.0\"^^\"xsd:double\", \"2.0\"^^\"xsd:double\") -> sqwrl:select(?r)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isDouble());
		Assert.assertEquals(literal.getDouble(), 4.0, DELTA);
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
