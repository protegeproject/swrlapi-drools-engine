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

public class SQWRLCollectionsTestCase extends SWRLAPITestBase
{
	final String Namespace = "http://swrlapi.org/ontologies/SQWRLCollectionsTestCase.owl#";

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
	public void TestSQWRLClassCollectionsEqual() throws SWRLParseException, SQWRLException
	{
		declareOWLClasses("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT)"
				+ " ^ sqwrl:makeBag(?s2, AZT) ^ sqwrl:makeBag(?s2, AZT) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLIndivudualCollectionsEqual() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividuals("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT)"
				+ " ^ sqwrl:makeBag(?s2, AZT) ^ sqwrl:makeBag(?s2, AZT) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLByteCollectionsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery(
				"q1",
				". sqwrl:makeBag(?s1, \"32\"^^\"xsd:byte\") ^ sqwrl:makeBag(?s2, \"39\"^^\"xsd:byte\")"
						+ " ^ sqwrl:makeBag(?s1, \"32\"^^\"xsd:byte\") ^ sqwrl:makeBag(?s1, \"39\"^^\"xsd:byte\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLShortCollectionsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery(
				"q1",
				". sqwrl:makeBag(?s1, \"32\"^^\"xsd:short\") ^ sqwrl:makeBag(?s2, \"39\"^^\"xsd:short\")"
						+ " ^ sqwrl:makeBag(?s1, \"32\"^^\"xsd:short\") ^ sqwrl:makeBag(?s1, \"39\"^^\"xsd:short\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLIntCollectionsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, 3) ^ sqwrl:makeBag(?s1, 5)"
				+ " ^ sqwrl:makeBag(?s2, 3) ^ sqwrl:makeBag(?s2, 5) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLLongCollectionsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery(
				"q1",
				". sqwrl:makeBag(?s1, \"32\"^^\"xsd:long\") ^ sqwrl:makeBag(?s2, \"39\"^^\"xsd:long\")"
						+ " ^ sqwrl:makeBag(?s1, \"32\"^^\"xsd:long\") ^ sqwrl:makeBag(?s1, \"39\"^^\"xsd:long\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLFloatCollectionsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, 3.1) ^ sqwrl:makeBag(?s1, 5.1)"
				+ " ^ sqwrl:makeBag(?s2, 3.1) ^ sqwrl:makeBag(?s2, 5.1) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLDoubleCollectionsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery(
				"q1",
				". sqwrl:makeBag(?s1, \"32.1\"^^\"xsd:double\") ^ sqwrl:makeBag(?s2, \"39.3\"^^\"xsd:double\")"
						+ " ^ sqwrl:makeBag(?s1, \"32.1\"^^\"xsd:double\") ^ sqwrl:makeBag(?s1, \"39.3\"^^\"xsd:double\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLStringCollectionsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"A\") ^ sqwrl:makeBag(?s1, \"B\")"
				+ " ^ sqwrl:makeBag(?s2, \"A\") ^ sqwrl:makeBag(?s2, \"B\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLBooleanCollectionsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, true) ^ sqwrl:makeBag(?s1, false)"
				+ " ^ sqwrl:makeBag(?s2, true) ^ sqwrl:makeBag(?s2, false) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLURICollectionsEqual() throws SWRLParseException, SQWRLException
	{
		String homePage1 = "http://stanford.edu/~fred";
		String homePage2 = "http://stanford.edu/~bob";

		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"" + homePage1
				+ "\"^^\"xsd:anyURI\") ^ sqwrl:makeBag(?s2, \"" + homePage2 + "\"^^\"xsd:anyURI\")"
				+ " ^ sqwrl:makeBag(?s1, \"" + homePage1 + "\"^^\"xsd:anyURI\") ^ sqwrl:makeBag(?s2, \"" + homePage2
				+ "\"^^\"xsd:anyURI\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLCollectionSize() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividuals("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) . sqwrl:size(?size, ?s1) -> sqwrl:select(?size)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("size");
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 2);
	}

	@Test
	public void TestSQWRLCollectionSizeEqual() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividuals("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) . sqwrl:size(?size, ?s1) "
						+ " ^ swrlb:equal(?size, 2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLClassFirst() throws SWRLParseException, SQWRLException
	{
		declareOWLClasses("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) . sqwrl:first(?first, ?s1) -> sqwrl:select(?first)");

		Assert.assertTrue(result.next());
		Assert.assertEquals(result.getClass("first").getShortName(), "AZT");
	}

	@Test
	public void TestSQWRLIndividualFirst() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividuals("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) . sqwrl:first(?first, ?s1) -> sqwrl:select(?first)");

		Assert.assertTrue(result.next());
		Assert.assertEquals(result.getIndividual("first").getShortName(), "AZT");
	}

	@Test
	public void TestSQWRLByteFirst() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, \"23\"^^\"xsd:byte\") ^ sqwrl:makeBag(?s1, \"77\"^^\"xsd:byte\") "
						+ ". sqwrl:first(?first, ?s1) -> sqwrl:select(?first)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("first").isByte());
		Assert.assertEquals(result.getLiteral("first").getByte(), 23);
	}

	@Test
	public void TestSQWRLShortFirst() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, \"23\"^^\"xsd:short\") ^ sqwrl:makeBag(?s1, \"77\"^^\"xsd:short\") "
						+ ". sqwrl:first(?first, ?s1) -> sqwrl:select(?first)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("first").isShort());
		Assert.assertEquals(result.getLiteral("first").getShort(), 23);
	}

	@Test
	public void TestSQWRLIntFirst() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, 23) ^ sqwrl:makeBag(?s1, 77) . sqwrl:first(?first, ?s1) -> sqwrl:select(?first)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("first").isInt());
		Assert.assertEquals(result.getLiteral("first").getInt(), 23);
	}

	@Test
	public void TestSQWRLLongFirst() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, \"23\"^^\"xsd:long\") ^ sqwrl:makeBag(?s1, \"77\"^^\"xsd:long\") "
						+ ". sqwrl:first(?first, ?s1) -> sqwrl:select(?first)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("first").isLong());
		Assert.assertEquals(result.getLiteral("first").getLong(), 23L);
	}

	@Test
	public void TestSQWRLDoubleFirst() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, \"23.3\"^^\"xsd:double\") ^ sqwrl:makeBag(?s1, \"77.32\"^^\"xsd:double\") "
						+ ". sqwrl:first(?first, ?s1) -> sqwrl:select(?first)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("first").isDouble());
		Assert.assertEquals(result.getLiteral("first").getDouble(), 23.3d, DELTA);
	}

	@Test
	public void TestSQWRLLast() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividuals("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) . sqwrl:last(?last, ?s1) -> sqwrl:select(?last)");

		Assert.assertTrue(result.next());
		Assert.assertEquals(result.getIndividual("last").getShortName(), "DDI");
	}

	@Test
	public void TestSQWRLNth() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividuals("DDI", "AZT", "BBT");

		SQWRLResult result = executeSQWRLQuery("q1",
				" . sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) ^ sqwrl:makeBag(?s1, BBT) "
						+ " . sqwrl:nth(?second, ?s1, 2) -> sqwrl:select(?second)");

		Assert.assertTrue(result.next());
		Assert.assertEquals(result.getIndividual("second").getShortName(), "BBT");
	}

	@Test
	public void TestSQWRLNthLast() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividuals("DDI", "AZT", "BBT");

		SQWRLResult result = executeSQWRLQuery("q1",
				" . sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) ^ sqwrl:makeBag(?s1, BBT) "
						+ " . sqwrl:nthLast(?secondLast, ?s1, 2) -> sqwrl:select(?secondLast)");

		Assert.assertTrue(result.next());
		Assert.assertEquals(result.getIndividual("secondLast").getShortName(), "BBT");
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
