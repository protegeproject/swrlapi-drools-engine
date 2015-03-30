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
	public void TestSQWRLClassBagsEqual() throws SWRLParseException, SQWRLException
	{
		declareOWLClasses("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT)"
				+ " ^ sqwrl:makeBag(?s2, AZT) ^ sqwrl:makeBag(?s2, AZT) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLClassSetsEqual() throws SWRLParseException, SQWRLException
	{
		declareOWLClasses("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeSet(?s1, DDI) ^ sqwrl:makeSet(?s1, AZT)"
				+ " ^ sqwrl:makeSet(?s2, AZT) ^ sqwrl:makeSet(?s2, AZT) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLIndividualBagsEqual() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividuals("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT)"
				+ " ^ sqwrl:makeBag(?s2, AZT) ^ sqwrl:makeBag(?s2, AZT) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLIndividualSetsEqual() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividuals("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeSet(?s1, DDI) ^ sqwrl:makeSet(?s1, AZT)"
				+ " ^ sqwrl:makeSet(?s2, AZT) ^ sqwrl:makeSet(?s2, AZT) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLByteBagsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery(
				"q1",
				". sqwrl:makeBag(?s1, \"32\"^^\"xsd:byte\") ^ sqwrl:makeBag(?s2, \"39\"^^\"xsd:byte\")"
						+ " ^ sqwrl:makeBag(?s1, \"32\"^^\"xsd:byte\") ^ sqwrl:makeBag(?s1, \"39\"^^\"xsd:byte\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLByteSetsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery(
				"q1",
				". sqwrl:makeSet(?s1, \"32\"^^\"xsd:byte\") ^ sqwrl:makeSet(?s2, \"39\"^^\"xsd:byte\")"
						+ " ^ sqwrl:makeSet(?s1, \"32\"^^\"xsd:byte\") ^ sqwrl:makeSet(?s1, \"39\"^^\"xsd:byte\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLShortBagsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery(
				"q1",
				". sqwrl:makeBag(?s1, \"32\"^^\"xsd:short\") ^ sqwrl:makeBag(?s2, \"39\"^^\"xsd:short\")"
						+ " ^ sqwrl:makeBag(?s1, \"32\"^^\"xsd:short\") ^ sqwrl:makeBag(?s1, \"39\"^^\"xsd:short\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLShortSetsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery(
				"q1",
				". sqwrl:makeSet(?s1, \"32\"^^\"xsd:short\") ^ sqwrl:makeSet(?s2, \"39\"^^\"xsd:short\")"
						+ " ^ sqwrl:makeSet(?s1, \"32\"^^\"xsd:short\") ^ sqwrl:makeSet(?s1, \"39\"^^\"xsd:short\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLIntBagsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, 3) ^ sqwrl:makeBag(?s1, 5)"
				+ " ^ sqwrl:makeBag(?s2, 3) ^ sqwrl:makeBag(?s2, 5) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLIntSetsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeSet(?s1, 3) ^ sqwrl:makeSet(?s1, 5)"
				+ " ^ sqwrl:makeSet(?s2, 3) ^ sqwrl:makeSet(?s2, 5) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLLongBagsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery(
				"q1",
				". sqwrl:makeBag(?s1, \"32\"^^\"xsd:long\") ^ sqwrl:makeBag(?s2, \"39\"^^\"xsd:long\")"
						+ " ^ sqwrl:makeBag(?s1, \"32\"^^\"xsd:long\") ^ sqwrl:makeBag(?s1, \"39\"^^\"xsd:long\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLLongSetsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeSet(?s1, \"32\"^^\"xsd:long\") ^ sqwrl:makeSet(?s1, \"39\"^^\"xsd:long\")"
						+ " ^ sqwrl:makeSet(?s2, \"32\"^^\"xsd:long\") ^ sqwrl:makeSet(?s2, \"39\"^^\"xsd:long\") "
						+ " ^ sqwrl:makeSet(?s2, \"39\"^^\"xsd:long\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLFloatBagsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, 3.1) ^ sqwrl:makeBag(?s1, 5.1)"
				+ " ^ sqwrl:makeBag(?s2, 3.1) ^ sqwrl:makeBag(?s2, 5.1) ^ sqwrl:makeBag(?s2, 5.1) "
				+ " . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLFloatSetsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeSet(?s1, 3.1) ^ sqwrl:makeSet(?s1, 5.1)"
				+ " ^ sqwrl:makeSet(?s2, 3.1) ^ sqwrl:makeSet(?s2, 5.1) ^ sqwrl:makeSet(?s2, 5.1)"
				+ ". sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLDoubleBagsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery(
				"q1",
				". sqwrl:makeBag(?s1, \"32.1\"^^\"xsd:double\") ^ sqwrl:makeBag(?s2, \"39.3\"^^\"xsd:double\")"
						+ " ^ sqwrl:makeBag(?s1, \"32.1\"^^\"xsd:double\") ^ sqwrl:makeBag(?s1, \"39.3\"^^\"xsd:double\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLDoubleSetsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, \"32.1\"^^\"xsd:double\") ^ sqwrl:makeBag(?s1, \"39.3\"^^\"xsd:double\")"
						+ " ^ sqwrl:makeBag(?s2, \"32.1\"^^\"xsd:double\") ^ sqwrl:makeBag(?s2, \"39.3\"^^\"xsd:double\") "
						+ " sqwrl:makeBag(?s2, \"39.3\"^^\"xsd:double\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLStringBagsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"A\") ^ sqwrl:makeBag(?s1, \"B\")"
				+ " ^ sqwrl:makeBag(?s2, \"A\") ^ sqwrl:makeBag(?s2, \"B\") . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLStringSetsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeSet(?s1, \"A\") ^ sqwrl:makeSet(?s1, \"B\")"
				+ " ^ sqwrl:makeBag(?s2, \"A\") ^ sqwrl:makeBag(?s2, \"B\") ^ sqwrl:makeBag(?s2, \"B\")"
				+ " . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLBooleanBagsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, true) ^ sqwrl:makeBag(?s1, false)"
				+ " ^ sqwrl:makeBag(?s2, true) ^ sqwrl:makeBag(?s2, false) . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLBooleanSetsEqual() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeSet(?s1, true) ^ sqwrl:makeSet(?s1, false)"
				+ " ^ sqwrl:makeSet(?s2, true) ^ sqwrl:makeSet(?s2, false) ^ sqwrl:makeSet(?s2, false) "
				+ " . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLURIBagsEqual() throws SWRLParseException, SQWRLException
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
	public void TestSQWRLURISetsEqual() throws SWRLParseException, SQWRLException
	{
		String homePage1 = "http://stanford.edu/~fred";
		String homePage2 = "http://stanford.edu/~bob";

		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeSet(?s1, \"" + homePage1 + "\"^^\"xsd:anyURI\") "
				+ " ^ sqwrl:makeSet(?s1, \"" + homePage2 + "\"^^\"xsd:anyURI\")" + " ^ sqwrl:makeSet(?s2, \"" + homePage1
				+ "\"^^\"xsd:anyURI\") " + " ^ sqwrl:makeSet(?s2, \"" + homePage2 + "\"^^\"xsd:anyURI\") "
				+ " ^ sqwrl:makeSet(?s2, \"" + homePage2 + "\"^^\"xsd:anyURI\") "
				+ " . sqwrl:equal(?s1, ?s2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLBagsSize() throws SWRLParseException, SQWRLException
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
	public void TestSQWRLSetsSize() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividuals("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeSet(?s1, AZT) ^ sqwrl:makeSet(?s1, AZT)"
						+ " . sqwrl:size(?size, ?s1) -> sqwrl:select(?size)");

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("size");
		Assert.assertTrue(literal.isInt());
		Assert.assertEquals(literal.getInt(), 2);
	}

	@Test
	public void TestSQWRLBagsSizeEqual() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividuals("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) . sqwrl:size(?size, ?s1) "
						+ " ^ swrlb:equal(?size, 2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLSetSizeEqual() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividuals("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeSet(?s1, DDI) ^ sqwrl:makeSet(?s1, AZT) ^ sqwrl:makeSet(?s1, AZT)"
						+ " . sqwrl:size(?size, ?s1)  ^ swrlb:equal(?size, 2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSQWRLClassedBagFirst() throws SWRLParseException, SQWRLException
	{
		declareOWLClasses("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) . sqwrl:first(?first, ?s1) -> sqwrl:select(?first)");

		Assert.assertTrue(result.next());
		Assert.assertEquals(result.getClass("first").getShortName(), "AZT");
	}

	@Test
	public void TestSQWRLIndividualsBagFirst() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividuals("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) . sqwrl:first(?first, ?s1) -> sqwrl:select(?first)");

		Assert.assertTrue(result.next());
		Assert.assertEquals(result.getIndividual("first").getShortName(), "AZT");
	}

	@Test
	public void TestSQWRLByteBagMin() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, \"77\"^^\"xsd:byte\") ^ sqwrl:makeBag(?s1, \"23\"^^\"xsd:byte\") "
						+ ". sqwrl:min(?min, ?s1) -> sqwrl:select(?min)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("min").isByte());
		Assert.assertEquals(result.getLiteral("min").getByte(), 23);
	}

	@Test
	public void TestSQWRLShortBagMin() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, \"77\"^^\"xsd:short\") ^ sqwrl:makeBag(?s1, \"23\"^^\"xsd:short\") "
						+ ". sqwrl:min(?min, ?s1) -> sqwrl:select(?min)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("min").isShort());
		Assert.assertEquals(result.getLiteral("min").getShort(), 23);
	}

	@Test
	public void TestSQWRLIntBagMin() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, 77) ^ sqwrl:makeBag(?s1, 23) . sqwrl:min(?min, ?s1) -> sqwrl:select(?min)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("min").isInt());
		Assert.assertEquals(result.getLiteral("min").getInt(), 23);
	}

	@Test
	public void TestSQWRLLongBagMin() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, \"77\"^^\"xsd:long\") ^ sqwrl:makeBag(?s1, \"23\"^^\"xsd:long\") "
						+ ". sqwrl:min(?min, ?s1) -> sqwrl:select(?min)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("min").isLong());
		Assert.assertEquals(result.getLiteral("min").getLong(), 23L);
	}

	@Test
	public void TestSQWRLFloatBagMin() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, 77.4) ^ sqwrl:makeBag(?s1, 23.3) . sqwrl:min(?min, ?s1) -> sqwrl:select(?min)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("min").isFloat());
		Assert.assertEquals(result.getLiteral("min").getFloat(), 23.3f, DELTA);
	}

	@Test
	public void TestSQWRLDoubleBagMin() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, \"77.32\"^^\"xsd:double\") ^ sqwrl:makeBag(?s1, \"23.3\"^^\"xsd:double\") "
						+ ". sqwrl:min(?min, ?s1) -> sqwrl:select(?min)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("min").isDouble());
		Assert.assertEquals(result.getLiteral("min").getDouble(), 23.3d, DELTA);
	}

	@Test
	public void TestSQWRLStringBagFirsr() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, \"Fred\"^^\"xsd:string\") ^ sqwrl:makeBag(?s1, \"Bob\"^^\"xsd:string\") "
						+ ". sqwrl:first(?first, ?s1) -> sqwrl:select(?first)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("first").isString());
		Assert.assertEquals(result.getLiteral("first").getString(), "Bob");
	}

	@Test
	public void TestSQWRLStringSetFirst() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeSet(?s1, \"Fred\"^^\"xsd:string\") ^ sqwrl:makeSet(?s1, \"Bob\"^^\"xsd:string\") "
						+ ". sqwrl:first(?first, ?s1) -> sqwrl:select(?first)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("first").isString());
		Assert.assertEquals(result.getLiteral("firts").getString(), "Bob");
	}

	@Test
	public void TestSQWRLByteBagAvg() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^\"xsd:byte\") ^ "
				+ "sqwrl:makeBag(?s1, \"23\"^^\"xsd:byte\") ^ sqwrl:makeBag(?s1, \"25\"^^\"xsd:byte\") "
				+ ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("avg").isByte());
		Assert.assertEquals(result.getLiteral("avg").getByte(), 24);
	}

	@Test
	public void TestSQWRLByteSetAvg() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeSet(?s1, \"24\"^^\"xsd:byte\") ^ "
				+ "sqwrl:makeSet(?s1, \"23\"^^\"xsd:byte\") ^ sqwrl:makeSet(?s1, \"25\"^^\"xsd:byte\") "
				+ "sqwrl:makeSet(?s1, \"25\"^^\"xsd:byte\") . sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("avg").isByte());
		Assert.assertEquals(result.getLiteral("avg").getByte(), 24);
	}

	@Test
	public void TestSQWRLShortBagAvg() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^\"xsd:short\") ^ "
				+ "sqwrl:makeBag(?s1, \"23\"^^\"xsd:short\")  ^ sqwrl:makeBag(?s1, \"25\"^^\"xsd:short\") "
				+ ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("avg").isShort());
		Assert.assertEquals(result.getLiteral("avg").getShort(), 24);
	}

	@Test
	public void TestSQWRLShortSetAvg() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^\"xsd:short\") ^ "
				+ "sqwrl:makeBag(?s1, \"23\"^^\"xsd:short\") ^ sqwrl:makeBag(?s1, \"23\"^^\"xsd:short\")"
				+ "sqwrl:makeBag(?s1, \"25\"^^\"xsd:short\") . sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("avg").isShort());
		Assert.assertEquals(result.getLiteral("avg").getShort(), 24);
	}

	@Test
	public void TestSQWRLIntBagAvg() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, 24) ^ " + "sqwrl:makeBag(?s1, 23) "
				+ "sqwrl:makeBag(?s1, 25) " + ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("avg").isInt());
		Assert.assertEquals(result.getLiteral("avg").getInt(), 24);
	}

	@Test
	public void TestSQWRLIntSetAvg() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeSet(?s1, 24) ^ sqwrl:makeSet(?s1, 23) "
				+ "^ sqwrl:makeSet(?s1, 25) ^ sqwrl:makeSet(?s1, 25) . sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("avg").isInt());
		Assert.assertEquals(result.getLiteral("avg").getInt(), 24);
	}

	@Test
	public void TestSQWRLLongBagAvg() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24\"^^\"xsd:long\") ^ "
				+ "sqwrl:makeBag(?s1, \"23\"^^\"xsd:long\") " + "sqwrl:makeBag(?s1, \"25\"^^\"xsd:long\") "
				+ ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("avg").isLong());
		Assert.assertEquals(result.getLiteral("avg").getLong(), 24L);
	}

	@Test
	public void TestSQWRLLongSetAvg() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeSet(?s1, \"24\"^^\"xsd:long\") ^ "
				+ "sqwrl:makeSet(?s1, \"23\"^^\"xsd:long\") ^ sqwrl:makeSet(?s1, \"25\"^^\"xsd:long\") "
				+ "sqwrl:makeSet(?s1, \"25\"^^\"xsd:long\") . sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("avg").isLong());
		Assert.assertEquals(result.getLiteral("avg").getLong(), 24L);
	}

	@Test
	public void TestSQWRLFloatBagAvg() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, 24.2) ^ " + "sqwrl:makeBag(?s1, 23.2) "
				+ "sqwrl:makeBag(?s1, 25.2) " + ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("avg").isFloat());
		Assert.assertEquals(result.getLiteral("avg").getFloat(), 24.2f, DELTA);
	}

	@Test
	public void TestSQWRLFloatSetAvg() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeSet(?s1, 24.2) ^ " + "sqwrl:makeSet(?s1, 23.2) "
				+ "sqwrl:makeBag(?s1, 25.2) ^ sqwrl:makeBag(?s1, 25.2) . sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("avg").isFloat());
		Assert.assertEquals(result.getLiteral("avg").getFloat(), 24.2f, DELTA);
	}

	@Test
	public void TestSQWRLDoubleBagAvg() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24.2\"^^\"xsd:double\") ^ "
				+ "sqwrl:makeBag(?s1, \"23.2\"^^\"xsd:double\") ^ sqwrl:makeBag(?s1, \"25.2\"^^\"xsd:double\") "
				+ ". sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("avg").isDouble());
		Assert.assertEquals(result.getLiteral("avg").getDouble(), 24.2d, DELTA);
	}

	@Test
	public void TestSQWRLDoubleSetAvg() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", ". sqwrl:makeBag(?s1, \"24.2\"^^\"xsd:double\") ^ "
				+ "sqwrl:makeBag(?s1, \"23.2\"^^\"xsd:double\")  ^ sqwrl:makeBag(?s1, \"25.2\"^^\"xsd:double\") "
				+ "sqwrl:makeBag(?s1, \"25.2\"^^\"xsd:double\") . sqwrl:avg(?avg, ?s1) -> sqwrl:select(?avg)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("avg").isDouble());
		Assert.assertEquals(result.getLiteral("avg").getDouble(), 24.2d, DELTA);
	}

	@Test
	public void TestSQWRLBagClassesLast() throws SWRLParseException, SQWRLException
	{
		declareOWLClasses("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, DDI) ^ sqwrl:makeBag(?s1, AZT) . sqwrl:last(?last, ?s1) -> sqwrl:select(?last)");

		Assert.assertTrue(result.next());
		Assert.assertEquals(result.getClass("last").getShortName(), "DDI");
	}

	@Test
	public void TestSQWRLBagIndividualLast() throws SWRLParseException, SQWRLException
	{
		declareOWLNamedIndividuals("DDI", "AZT");

		SQWRLResult result = executeSQWRLQuery("q1",
				". sqwrl:makeBag(?s1, AZT) ^ sqwrl:makeBag(?s1, DDI) . sqwrl:last(?last, ?s1) -> sqwrl:select(?last)");

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
