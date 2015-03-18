package org.swrlapi;

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
import org.swrlapi.drools.core.DroolsSWRLRuleEngineCreator;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;
import org.swrlapi.test.SWRLAPITestBase;

public class SWRLCoreTestCase extends SWRLAPITestBase
{
	final String Namespace = "http://swrlapi.org/ontologies/SWRLCoreTestCase.owl#";
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
	public void TestClassAtomInAntecedentWithNamedIndividual() throws SWRLParseException, SQWRLException
	{
		declareOWLClassAssertion("Male", "p1");

		SQWRLResult result = executeSQWRLQuery("q1", "Male(p1) -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestClassAtomInAntecedentWithVariable() throws SWRLParseException, SQWRLException
	{
		declareOWLClassAssertion("Male", "p1");

		SQWRLResult result = executeSQWRLQuery("q1", "Male(?m) -> sqwrl:select(?m)");

		Assert.assertTrue(result.next());

		Assert.assertEquals(result.getIndividual("m").getShortName(), "p1");
	}

	@Test
	public void TestSameAs() throws SWRLParseException, SQWRLException
	{
		declareOWLClassAssertion("Male", "p1");

		SQWRLResult result = executeSQWRLQuery("q1", "sameAs(p1, p1) -> sqwrl:select(\"Yes\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isString());
		Assert.assertEquals(literal.getString(), "Yes");
	}

	@Test
	public void TestSWRLIndividualNameMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLObjectPropertyAssertion("p1", "hasUncle", "p2");

		SQWRLResult result = executeSQWRLQuery("q1", "hasUncle(p1, p2) -> sqwrl:select(p1, p2)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
		Assert.assertTrue(result.getIndividual(1).isIndividual());
		Assert.assertEquals(result.getIndividual(1).getShortName(), "p2");
	}

	@Test
	public void TestSWRLIndividualNameBind() throws SWRLParseException, SQWRLException
	{
		declareOWLObjectPropertyAssertion("p1", "hasUncle", "p2");

		SQWRLResult result = executeSQWRLQuery("q1", "hasUncle(p1, ?uncle) -> sqwrl:select(p1, ?uncle)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
		Assert.assertTrue(result.getIndividual("uncle").isIndividual());
		Assert.assertEquals(result.getIndividual("uncle").getShortName(), "p2");
	}

	@Test
	public void TestSWRLShortLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:short");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, \"42\"^^\"xsd:short\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLShortLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:short");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isShort());
		Assert.assertEquals(result.getLiteral("age").getShort(), 42);
	}

	@Test
	public void TestSWRLRawIntLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, 42) -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLQualifiedIntLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, \"42\"^^\"xsd:int\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLIntLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isInt());
		Assert.assertEquals(result.getLiteral("age").getInt(), 42);
	}

	@Test
	public void TestSWRLLongLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:long");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, \"42\"^^\"xsd:long\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLLongLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:long");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isLong());
		Assert.assertEquals(result.getLiteral("age").getLong(), 42);
	}

	@Test
	public void TestSWRLRawBooleanLiteralTrueMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "isFrench", "true", "xsd:boolean");

		SQWRLResult result = executeSQWRLQuery("q1", "isFrench(p1, true) -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLQualifiedBooleanLiteralTrueMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "isFrench", "true", "xsd:boolean");

		SQWRLResult result = executeSQWRLQuery("q1", "isFrench(p1, \"true\"^^\"xsd:boolean\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLRawBooleanLiteralFalseMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "isFrench", "false", "xsd:boolean");

		SQWRLResult result = executeSQWRLQuery("q1", "isFrench(p1, false) -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLQualifiedBooleanLiteralFalseMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "isFrench", "false", "xsd:boolean");

		SQWRLResult result = executeSQWRLQuery("q1", "isFrench(p1, \"false\"^^\"xsd:boolean\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLBooleanLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "isFrench", "true", "xsd:boolean");

		SQWRLResult result = executeSQWRLQuery("q1", "isFrench(p1, ?french) -> sqwrl:select(p1, ?french)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("french").isBoolean());
		Assert.assertEquals(result.getLiteral("french").getBoolean(), true);
	}

	// TODO This should work!
	// @Test
	// public void TestSWRLRawFloatLiteralMatch() throws SWRLParseException, SQWRLException
	// {
	// declareOWLDataPropertyAssertion("p1", "hasHeightInCM", "180.0", "xsd:float");
	//
	// SQWRLResult result = executeSQWRLQuery("q1", "hasHeightInCM(p1, 180.0) -> sqwrl:select(p1)");
	//
	// Assert.assertTrue(result.next());
	// Assert.assertTrue(result.getIndividual(0).isIndividual());
	// Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	// }

	@Test
	public void TestSWRLQualifiedFloatLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeightInCM", "177.0", "xsd:float");

		SQWRLResult result = executeSQWRLQuery("q1", "hasHeightInCM(p1, \"177.0\"^^\"xsd:float\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLFloatLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeightInCM", "177.0", "xsd:float");

		SQWRLResult result = executeSQWRLQuery("q1", "hasHeightInCM(p1, ?height) -> sqwrl:select(p1, ?height)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("height").isFloat());
		Assert.assertEquals(result.getLiteral("height").getFloat(), 177.0, DELTA);
	}

	@Test
	public void TestSWRLStringLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasName", "Bob", "xsd:string");

		SQWRLResult result = executeSQWRLQuery("q1", "hasName(p1, ?name) -> sqwrl:select(p1, ?name)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("name").isString());
		Assert.assertEquals(result.getLiteral("name").getString(), "Bob");
	}

	@Test
	public void TestSWRLRawStringLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasName", "Bob", "xsd:string");

		SQWRLResult result = executeSQWRLQuery("q1", "hasName(p1, \"Bob\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLQualifiedStringLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasName", "Bob", "xsd:string");

		SQWRLResult result = executeSQWRLQuery("q1", "hasName(p1, \"Bob\"^^\"xsd:string\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLXSDDateLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasDOB", "2001-01-05", "xsd:date");

		SQWRLResult result = executeSQWRLQuery("q1", "hasDOB(p1, \"2001-01-05\"^^\"xsd:date\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLXSDDateLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasDOB", "2001-01-05", "xsd:date");

		SQWRLResult result = executeSQWRLQuery("q1", "hasDOB(p1, ?dob) -> sqwrl:select(p1, ?dob)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("dob").isDate());
		Assert.assertEquals(result.getLiteral("dob").getDate(), new XSDDate("2001-01-05"));
	}

	@Test
	public void TestSWRLXSDDateTimeLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasTOB", "2001-01-05T10:10:10", "xsd:dateTime");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasTOB(p1, \"2001-01-05T10:10:10\"^^\"xsd:dateTime\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLXSDDateTimeLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasTOB", "2001-01-05T10:10:10", "xsd:dateTime");

		SQWRLResult result = executeSQWRLQuery("q1", "hasTOB(p1, ?tob) -> sqwrl:select(p1, ?tob)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("tob").isDateTime());
		Assert.assertEquals(result.getLiteral("tob").getDateTime(), new XSDDateTime("2001-01-05T10:10:10"));
	}

	@Test
	public void TestSWRLXSDDurationLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "P42Y", "xsd:duration");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, \"P42Y\"^^\"xsd:duration\") -> sqwrl:select(p1)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
	}

	@Test
	public void TestSWRLXSDDurationLiteralBind() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "P42Y", "xsd:duration");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) -> sqwrl:select(p1, ?age)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getIndividual(0).isIndividual());
		Assert.assertEquals(result.getIndividual(0).getShortName(), "p1");
		Assert.assertTrue(result.getLiteral("age").isDuration());
		Assert.assertEquals(result.getLiteral("age").getDuration(), new XSDDuration("P42Y"));
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
