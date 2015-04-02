package org.swrlapi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.core.xsd.XSDDuration;
import org.swrlapi.drools.core.DroolsFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.test.SWRLAPITestBase;

public class SWRLBuiltInsTestCase extends SWRLAPITestBase
{
	final String Namespace = "http://swrlapi.org/ontologies/SWRLBuiltInsTestCase.owl#";

	SQWRLQueryEngine sqwrlQueryEngine;

	@Before
	public void setUp() throws OWLOntologyCreationException
	{
		SWRLAPIOWLOntology swrlapiOWLOntology = createEmptySWRLAPIOWLOntology(Namespace);

		sqwrlQueryEngine = swrlapiOWLOntology.createSQWRLQueryEngine(DroolsFactory.getSWRLRuleEngineCreator());
	}

	@Test
	public void TestSWRLBuiltInsBasicInvocation() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:add(4, 2, 2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLBuiltInsByteBoundResult() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(?r, \"2\"^^xsd:byte, \"2\"^^xsd:byte) -> sqwrl:select(?r)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isByte());
		Assert.assertEquals(literal.getByte(), 4);
	}

	@Test
	public void TestSWRLBuiltInsShortBoundResult() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(?r, \"2\"^^xsd:short, \"2\"^^xsd:short) -> sqwrl:select(?r)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isShort());
		Assert.assertEquals(literal.getShort(), 4);
	}

	@Test
	public void TestSWRLBuiltInsIntBoundResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:add(?r, 2, 2) -> sqwrl:select(?r)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 4);
	}

	@Test
	public void TestSWRLBuiltInsFloatBoundResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:add(?r, 2.1, 2.0) -> sqwrl:select(?r)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isFloat());
		Assert.assertEquals(literal.getFloat(), 4.1f, DELTA);
	}

	@Test
	public void TestSWRLBuiltInsLongBoundResult() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(?r, \"2\"^^xsd:long, \"2\"^^xsd:long) -> sqwrl:select(?r)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isLong());
		Assert.assertEquals(literal.getLong(), 4);
	}

	@Test
	public void TestSWRLBuiltInsDoubleBoundResult() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(?r, \"2.0\"^^xsd:double, \"2.0\"^^xsd:double) -> sqwrl:select(?r)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isDouble());
		Assert.assertEquals(literal.getDouble(), 4.0d, DELTA);
	}

	@Test
	public void TestSWRLBuiltInsBooleanBoundTrueResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:booleanNot(?r, false) -> sqwrl:select(?r)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isBoolean());
		Assert.assertEquals(literal.getBoolean(), true);
	}

	@Test
	public void TestSWRLBuiltInsBooleanBoundFalseResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:booleanNot(?r, true) -> sqwrl:select(?r)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isBoolean());
		Assert.assertEquals(literal.getBoolean(), false);
	}

	@Test
	public void TestSWRLBuiltInsDateTimeBoundResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				"temporal:add(?r, \"1999-11-01T10:00:01.0\"^^xsd:dateTime, 0, \"Years\") -> sqwrl:select(?r)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("r").isDateTime());
		Assert.assertEquals(result.getLiteral("r").getDateTime(), new XSDDateTime("1999-11-01T10:00:01.0"));
	}

	@Test
	public void TestSWRLBuiltInsDurationBoundResult() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:yearMonthDuration(?x, 3, 4) -> sqwrl:select(?x)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("x").isDuration());
		Assert.assertEquals(result.getLiteral("x").getDuration(), new XSDDuration("P3Y4M"));
	}

	@Test
	public void TestSWRLBuiltInsCascadingShortVariable() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(?x, \"2\"^^xsd:short, \"2\"^^xsd:short) ^ "
				+ "swrlb:multiply(?y, ?x, \"2\"^^xsd:short) -> sqwrl:select(?y)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("y").isShort());
		Assert.assertEquals(result.getLiteral("y").getShort(), 8);
	}

	@Test
	public void TestSWRLBuiltInsCascadingIntVariable() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(?x, 2, 2) ^ swrlb:multiply(?y, ?x, 2) -> sqwrl:select(?y)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("y").isInt());
		Assert.assertEquals(result.getLiteral("y").getInt(), 8);
	}

	@Test
	public void TestSWRLBuiltInsCascadingLongVariable() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(?x, \"2\"^^xsd:long, \"2\"^^xsd:long) ^ "
				+ "swrlb:multiply(?y, ?x, \"2\"^^xsd:long) -> sqwrl:select(?y)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("y").isLong());
		Assert.assertEquals(result.getLiteral("y").getLong(), 8L);
	}

	@Test
	public void TestSWRLBuiltInsCascadingFloatVariable() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(?x, 2.0, 2.0) ^ swrlb:multiply(?y, ?x, 2.0) -> sqwrl:select(?y)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("y").isFloat());
		Assert.assertEquals(result.getLiteral("y").getFloat(), 8.0f, DELTA);
	}

	@Test
	public void TestSWRLBuiltInsCascadingDoubleVariable() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(?x, \"2.0\"^^xsd:double, \"2.0\"^^xsd:double) ^ "
				+ "swrlb:multiply(?y, ?x, \"2.0\"^^xsd:double) -> sqwrl:select(?y)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("y").isDouble());
		Assert.assertEquals(result.getLiteral("y").getDouble(), 8.0d, DELTA);
	}

	@Test
	public void TestSWRLBuiltInsCascadingBooleanVariable() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:booleanNot(?x, true) ^ swrlb:booleanNot(?y, ?x) -> sqwrl:select(?y)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("y").isBoolean());
		Assert.assertEquals(result.getLiteral("y").getBoolean(), true);
	}

	@Test
	public void TestSWRLBuiltInsCascadingStringVariable() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:stringConcat(?x, \"The\", \"Cat\") ^ swrlb:stringConcat(?y, ?x, \"Sat\") -> sqwrl:select(?y)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("y").isString());
		Assert.assertEquals(result.getLiteral("y").getString(), "TheCatSat");
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
