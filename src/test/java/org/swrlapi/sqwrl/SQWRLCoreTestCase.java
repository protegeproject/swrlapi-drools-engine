package org.swrlapi.sqwrl;

import java.net.URI;
import java.net.URISyntaxException;

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
	public void TestSQWRLCoreColumnName() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(3, 4) ^ sqwrl:columnNames(\"col1\", \"col2\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal1 = result.getLiteral("col1");
		Assert.assertTrue(literal1.isInt());
		Assert.assertEquals(literal1.getInt(), 3);
		SQWRLLiteralResultValue literal2 = result.getLiteral("col2");
		Assert.assertTrue(literal2.isInt());
		Assert.assertEquals(literal2.getInt(), 4);
	}

	@Test
	public void TestSQWRLCoreByteResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(\"34\"^^\"xsd:byte\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isByte());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:byte");
		Assert.assertEquals(literal.getByte(), 34);
	}

	@Test
	public void TestSQWRLCoreShortResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(\"34\"^^\"xsd:short\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isShort());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:short");
		Assert.assertEquals(literal.getShort(), 34);
	}

	@Test
	public void TestSQWRLCoreQualifiedIntResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(\"34\"^^\"xsd:int\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:int");
		Assert.assertEquals(literal.getInt(), 34);
	}

	@Test
	public void TestSQWRLCoreRawIntResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(34)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:int");
		Assert.assertEquals(literal.getInt(), 34);
	}

	@Test
	public void TestSQWRLCoreLongResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(\"34\"^^\"xsd:long\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isLong());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:long");
		Assert.assertEquals(literal.getLong(), 34L);
	}

	@Test
	public void TestSQWRLCoreRawFloatResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(34.0)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isFloat());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:float");
		Assert.assertEquals(literal.getFloat(), 34.0, DELTA);
	}

	@Test
	public void TestSQWRLCoreQualifiedFloatResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(\"34.0\"^^\"xsd:float\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isFloat());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:float");
		Assert.assertEquals(literal.getFloat(), 34.0, DELTA);
	}

	@Test
	public void TestSQWRLCoreDoubleResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(\"34.0\"^^\"xsd:double\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDouble());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:double");
		Assert.assertEquals(literal.getDouble(), 34.0, DELTA);
	}

	@Test
	public void TestSQWRLCoreQualifiedBooleanResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(\"true\"^^\"xsd:boolean\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isBoolean());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:boolean");
		Assert.assertEquals(literal.getBoolean(), true);
	}

	@Test
	public void TestSQWRLCoreRawBooleanResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(true)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isBoolean());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:boolean");
		Assert.assertEquals(literal.getBoolean(), true);
	}

	@Test
	public void TestSQWRLCoreQualifiedStringResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(\"Cat\"^^\"xsd:string\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isString());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:string");
		Assert.assertEquals(literal.getString(), "Cat");
	}

	@Test
	public void TestSQWRLCoreRawStringResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(\"Cat\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isString());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:string");
		Assert.assertEquals(literal.getString(), "Cat");
	}

	@Test
	public void TestSQWRLCoreURIResult() throws SWRLParseException, SQWRLException, URISyntaxException
	{
		String homepage = "http://stanford.edu/~fred";
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(\"" + homepage + "\"^^\"xsd:anyURI\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isAnyURI());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:anyURI");
		Assert.assertEquals(literal.getAnyURI(), new URI(homepage));
	}

	@Test
	public void TestSQWRLCoreTimeResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(\"10:10:11\"^^\"xsd:time\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isTime());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:time");
		Assert.assertEquals(literal.getTime(), new XSDTime("10:10:11"));
	}

	@Test
	public void TestSQWRLCoreDateResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(\"1999-01-01\"^^\"xsd:date\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDate());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:date");
		Assert.assertEquals(literal.getDate(), new XSDDate("1999-01-01"));
	}

	@Test
	public void TestSQWRLCoreDateTimeResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(\"1999-01-01T10:10:11\"^^\"xsd:dateTime\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDateTime());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:dateTime");
		Assert.assertEquals(literal.getDateTime(), new XSDDateTime("1999-01-01T10:10:11"));
	}

	@Test
	public void TestSQWRLCoreDurationResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "-> sqwrl:select(\"P24Y\"^^\"xsd:duration\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDuration());
		Assert.assertEquals(literal.getDatatypePrefixedName(), "xsd:duration");
		Assert.assertEquals(literal.getDuration(), new XSDDuration("P24Y"));
	}

	@Test
	public void TestSQWRLCoreCount() throws SWRLParseException, SQWRLException
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
	public void TestSQWRLCoreAvg() throws SWRLParseException, SQWRLException
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
	public void TestSQWRLCoreByteMin() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "10", "xsd:byte");
		declareOWLDataPropertyAssertion("p2", "hasAge", "20", "xsd:byte");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:byte");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:min(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isByte());
		Assert.assertEquals(literal.getByte(), 10);
	}

	@Test
	public void TestSQWRLCoreShortMin() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "10", "xsd:short");
		declareOWLDataPropertyAssertion("p2", "hasAge", "20", "xsd:short");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:short");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:min(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isShort());
		Assert.assertEquals(literal.getShort(), 10);
	}

	@Test
	public void TestSQWRLCoreIntMin() throws SWRLParseException, SQWRLException
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
	public void TestSQWRLCoreLongMin() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "10", "xsd:long");
		declareOWLDataPropertyAssertion("p2", "hasAge", "20", "xsd:long");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:long");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:min(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isLong());
		Assert.assertEquals(literal.getLong(), 10L);
	}

	@Test
	public void TestSQWRLCoreFloatMin() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeight", "101.0", "xsd:float");
		declareOWLDataPropertyAssertion("p2", "hasHeight", "102.3", "xsd:float");
		declareOWLDataPropertyAssertion("p3", "hasHeight", "104.2", "xsd:float");

		SQWRLResult result = executeSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:min(?height)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isFloat());
		Assert.assertEquals(literal.getFloat(), 101.0, DELTA);
	}

	@Test
	public void TestSQWRLCoreDoubleMin() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeight", "101.0", "xsd:double");
		declareOWLDataPropertyAssertion("p2", "hasHeight", "102.3", "xsd:double");
		declareOWLDataPropertyAssertion("p3", "hasHeight", "104.2", "xsd:double");

		SQWRLResult result = executeSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:min(?height)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDouble());
		Assert.assertEquals(literal.getDouble(), 101.0, DELTA);
	}

	@Test
	public void TestSQWRLCoreMixedTypedMin() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "23", "xsd:byte");
		declareOWLDataPropertyAssertion("p2", "hasage", "44", "xsd:short");
		declareOWLDataPropertyAssertion("p3", "hasAge", "55", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:min(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isByte());
		Assert.assertEquals(literal.getByte(), 23);
	}

	@Test(expected = SQWRLException.class)
	public void TestSQWRLCoreInvalidMin() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasName", "Bob", "xsd:string");
		declareOWLDataPropertyAssertion("p2", "hasName", "Fred", "xsd:string");
		declareOWLDataPropertyAssertion("p3", "hasName", "Joe", "xsd:String");

		executeSQWRLQuery("q1", "hasName(?p, ?name)-> sqwrl:min(?name)");
	}

	@Test
	public void TestSQWRLCoreByteMax() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "10", "xsd:byte");
		declareOWLDataPropertyAssertion("p2", "hasAge", "20", "xsd:byte");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:byte");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:max(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isByte());
		Assert.assertEquals(literal.getByte(), 30);
	}

	@Test
	public void TestSQWRLCoreShortMax() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "10", "xsd:short");
		declareOWLDataPropertyAssertion("p2", "hasAge", "20", "xsd:short");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:short");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:max(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isShort());
		Assert.assertEquals(literal.getShort(), 30);
	}

	@Test
	public void TestSQWRLCoreIntMax() throws SWRLParseException, SQWRLException
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

	// TODO Failing
	// @Test
	// public void TestSQWRLCoreFloatMax() throws SWRLParseException, SQWRLException
	// {
	// declareOWLDataPropertyAssertion("p1", "hasHeight", "101.0", "xsd:float");
	// declareOWLDataPropertyAssertion("p2", "hasHeight", "102.3", "xsd:float");
	// declareOWLDataPropertyAssertion("p3", "hasHeight", "104.1", "xsd:float");
	//
	// SQWRLResult result = executeSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:max(?height)");
	//
	// Assert.assertTrue(result.next());
	// SQWRLLiteralResultValue literal = result.getLiteral(0);
	// Assert.assertTrue(literal.isFloat());
	// Assert.assertEquals(literal.getFloat(), 104.1, DELTA);
	// }

	@Test
	public void TestSQWRLCoreDoubleMax() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeight", "101.0", "xsd:double");
		declareOWLDataPropertyAssertion("p2", "hasHeight", "102.3", "xsd:double");
		declareOWLDataPropertyAssertion("p3", "hasHeight", "104.1", "xsd:double");

		SQWRLResult result = executeSQWRLQuery("q1", "hasHeight(?p, ?height)-> sqwrl:max(?height)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isDouble());
		Assert.assertEquals(literal.getDouble(), 104.1, DELTA);
	}

	@Test
	public void TestSQWRLCoreMixedTypedMax() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "23", "xsd:byte");
		declareOWLDataPropertyAssertion("p2", "hasage", "44", "xsd:short");
		declareOWLDataPropertyAssertion("p3", "hasAge", "55", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:max(?age)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 55);
	}

	@Test
	public void TestSQWRLCoreOrderByByte() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "20", "xsd:byte");
		declareOWLDataPropertyAssertion("p2", "hasAge", "10", "xsd:byte");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:byte");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderBy(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isByte());
		Assert.assertEquals(result.getLiteral("age").getByte(), 10);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isByte());
		Assert.assertEquals(result.getLiteral("age").getByte(), 20);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isByte());
		Assert.assertEquals(result.getLiteral("age").getByte(), 30);
	}

	@Test
	public void TestSQWRLCoreOrderByShort() throws SWRLParseException, SQWRLException
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
	public void TestSQWRLCoreOrderByString() throws SWRLParseException, SQWRLException
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
	public void TestSQWRLCoreOrderByURI() throws SWRLParseException, SQWRLException, URISyntaxException
	{
		String p1HomePage = "http://stanford.edu/~joe";
		String p2HomePage = "http://stanford.edu/~fred";
		String p3HomePage = "http://stanford.edu/~bob";

		declareOWLDataPropertyAssertion("p1", "hasHomePage", p1HomePage, "xsd:anyURI");
		declareOWLDataPropertyAssertion("p2", "hasHomePage", p2HomePage, "xsd:anyURI");
		declareOWLDataPropertyAssertion("p3", "hasHomePage", p3HomePage, "xsd:anyURI");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasHomePage(?p, ?homepage)-> sqwrl:select(?p, ?homepage) ^ sqwrl:orderBy(?homepage)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("homepage").isAnyURI());
		Assert.assertEquals(result.getLiteral("homepage").getAnyURI(), new URI(p3HomePage));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("homepage").isAnyURI());
		Assert.assertEquals(result.getLiteral("homepage").getAnyURI(), new URI(p2HomePage));

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("homepage").isAnyURI());
		Assert.assertEquals(result.getLiteral("homepage").getAnyURI(), new URI(p1HomePage));
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
	public void TestSQWRLOrderByDescendingByte() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "20", "xsd:byte");
		declareOWLDataPropertyAssertion("p2", "hasAge", "10", "xsd:byte");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:byte");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderByDescending(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isByte());
		Assert.assertEquals(result.getLiteral("age").getByte(), 30);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isByte());
		Assert.assertEquals(result.getLiteral("age").getByte(), 20);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isByte());
		Assert.assertEquals(result.getLiteral("age").getByte(), 10);
	}

	@Test
	public void TestSQWRLOrderByDescendingShort() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "20", "xsd:short");
		declareOWLDataPropertyAssertion("p2", "hasAge", "10", "xsd:short");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:short");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderByDescending(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isShort());
		Assert.assertEquals(result.getLiteral("age").getShort(), 30);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isShort());
		Assert.assertEquals(result.getLiteral("age").getShort(), 20);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isShort());
		Assert.assertEquals(result.getLiteral("age").getShort(), 10);
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
	public void TestSQWRLOrderByDescendingLong() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "20", "xsd:long");
		declareOWLDataPropertyAssertion("p2", "hasAge", "10", "xsd:long");
		declareOWLDataPropertyAssertion("p3", "hasAge", "30", "xsd:long");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(?p, ?age)-> sqwrl:select(?p, ?age) ^ sqwrl:orderByDescending(?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p3");
		Assert.assertTrue(result.getLiteral("age").isLong());
		Assert.assertEquals(result.getLiteral("age").getLong(), 30L);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isLong());
		Assert.assertEquals(result.getLiteral("age").getLong(), 20L);

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual("p").isIndividual());
		Assert.assertEquals(result.getIndividual("p").getShortName(), "p2");
		Assert.assertTrue(result.getLiteral("age").isLong());
		Assert.assertEquals(result.getLiteral("age").getLong(), 10L);
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
