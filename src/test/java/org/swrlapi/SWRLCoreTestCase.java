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

public class SWRLCoreTestCase extends SWRLAPITestBase
{
	String Namespace = "http://swrlapi.org/ontologies/SWRLCoreTestCase.owl#";
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
	public void TestSWRLIntLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, 42) -> sqwrl:select(p1)");

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
	public void TestSWRLBooleanLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "isFrench", "true", "xsd:boolean");

		SQWRLResult result = executeSQWRLQuery("q1", "isFrench(p1, true) -> sqwrl:select(p1)");

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

	@Test
	public void TestSWRLFloatLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeightInCM", "177.0", "xsd:float");

		SQWRLResult result = executeSQWRLQuery("q1", "hasHeightInCM(p1, 177.0) -> sqwrl:select(p1)");

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
	public void TestSWRLStringLiteralMatch() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasName", "Bob", "xsd:string");

		SQWRLResult result = executeSQWRLQuery("q1", "hasName(p1, \"Bob\") -> sqwrl:select(p1)");

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
	public void TestSWRLCoreAddBuiltInWithQualifiedLongLiterals() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(\"4\"^^\"xsd:long\", \"2\"^^\"xsd:long\", \"2\"^^\"xsd:long\") -> sqwrl:select(\"Yes\")";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isString());
		Assert.assertEquals(literal.getString(), "Yes");
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithRawIntLiterals() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:add(4, 2, 2) -> sqwrl:select(\"Yes\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isString());
		Assert.assertEquals(literal.getString(), "Yes");
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithRawIntLiteralsAndVariableResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:add(?r, 2, 2) -> sqwrl:select(?r)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 4);
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithQualifiedDoubleLiterals() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(\"4.0\"^^\"xsd:double\", \"2.0\"^^\"xsd:double\", \"2.0\"^^\"xsd:double\")"
				+ " -> sqwrl:select(\"Yes\")";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isString());
		Assert.assertEquals(literal.getString(), "Yes");
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithRawDoubleLiterals() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:add(4.1, 2.1, 2.0) -> sqwrl:select(\"Yes\")");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral(0);
		Assert.assertTrue(literal.isString());
		Assert.assertEquals(literal.getString(), "Yes");
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithRawFloatLiteralsAndVariableResult() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:add(?r, 2.1, 2.0) -> sqwrl:select(?r)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isFloat());
		Assert.assertEquals(literal.getFloat(), 4.1, DELTA);
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
