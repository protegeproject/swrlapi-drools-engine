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

public class SWRLCoreBuiltInsTestCase extends SWRLAPITestBase
{
	final String Namespace = "http://swrlapi.org/ontologies/SWRLCoreBuiltInsTestCase.owl#";

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
	public void TestSWRLCoreLessThanBuiltInWithByte() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:byte");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(p1, ?age) ^ swrlb:lessThan(?age, \"43\"^^\"xsd:byte\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreGreaterThanBuiltInWithByte() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:byte");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(p1, ?age) ^ swrlb:greaterThan(?age, \"41\"^^\"xsd:byte\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreEqualBuiltInWithByte() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:byte");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(p1, ?age) ^ swrlb:equal(?age, \"42\"^^\"xsd:byte\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreLessThanBuiltInWithShort() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:short");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(p1, ?age) ^ swrlb:lessThan(?age, \"43\"^^\"xsd:short\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreGreaterThanBuiltInWithShort() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:short");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(p1, ?age) ^ swrlb:greaterThan(?age, \"41\"^^\"xsd:short\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
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
	public void TestSWRLCoreLessThanBuiltInWithInt() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:lessThan(?age, 43) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreGreaterThanBuiltInWithInt() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:int");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, 41) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
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
	public void TestSWRLCoreLessThanBuiltInWithLong() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:long");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(p1, ?age) ^ swrlb:lessThan(?age, \"43\"^^\"xsd:long\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreGreaterThanBuiltInWithLong() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:long");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(p1, ?age) ^ swrlb:greaterThan(?age, \"41\"^^\"xsd:long\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
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
	public void TestSWRLCoreGreaterThanBuiltInWithFloat() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42.0", "xsd:float");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:greaterThan(?age, 41.0) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
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
	public void TestSWRLCoreLessThanBuiltInWithFloat() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42.0", "xsd:float");

		SQWRLResult result = executeSQWRLQuery("q1", "hasAge(p1, ?age) ^ swrlb:lessThan(?age, 43.0) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreLessThanBuiltInWithDouble() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:double");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(p1, ?age) ^ swrlb:lessThan(?age, \"43\"^^\"xsd:double\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreGreaterThanBuiltInWithDouble() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "42", "xsd:double");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(p1, ?age) ^ swrlb:greaterThan(?age, \"41\"^^\"xsd:double\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreEqualBuiltInWithDouble() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasHeightInCM", "177.0", "xsd:double");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasHeightInCM(p1, ?height) ^ swrlb:equal(?height, \"177.0\"^^\"xsd:double\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
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
	public void TestSWRLCoreLessThanBuiltInWithString() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasName", "Bob", "xsd:string");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasName(p1, ?name) ^ swrlb:lessThan(?name, \"Fred\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreGreaterThanBuiltInWithString() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasName", "Bob", "xsd:string");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasName(p1, ?name) ^ swrlb:greaterThan(?name, \"Adam\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreEqualBuiltInWithString() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasName", "Bob", "xsd:string");

		SQWRLResult result = executeSQWRLQuery("q1", "hasName(p1, ?name) ^ swrlb:equal(?name, \"Bob\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreEqualBuiltInWithXSDDate() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasDOB", "2001-01-05", "xsd:date");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasDOB(p1, ?dob) ^ swrlb:equal(?dob, \"2001-01-05\"^^\"xsd:date\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreEqualBuiltInWithXSDDateTime() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasTOB", "2001-01-05T10:10:10", "xsd:dateTime");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasTOB(p1, ?tob) ^ swrlb:equal(?tob, \"2001-01-05T10:10:10\"^^\"xsd:dateTime\")-> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreEqualBuiltInWithXSDDuration() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasAge", "P42Y", "xsd:duration");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasAge(p1, ?age) ^ swrlb:equal(?age, \"P42Y\"^^\"xsd:duration\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreEqualBuiltInWithXSDTime() throws SWRLParseException, SQWRLException
	{
		declareOWLDataPropertyAssertion("p1", "hasBirthTime", "10:10:10.33", "xsd:time");

		SQWRLResult result = executeSQWRLQuery("q1",
				"hasBirthTime(p1, ?bt) ^ swrlb:equal(?bt, \"10:10:10.33\"^^\"xsd:time\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithShort() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(\"4\"^^\"xsd:short\", \"2\"^^\"xsd:short\", \"2\"^^\"xsd:short\") -> sqwrl:select(0)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreAddBuiltInWithInt() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:add(4, 2, 2) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
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
	public void TestSWRLCoreAddBuiltInWithDoubles() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:add(\"4.0\"^^\"xsd:double\", \"2.0\"^^\"xsd:double\", \"2.0\"^^\"xsd:double\")"
				+ " -> sqwrl:select(0)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreBooleanNotBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:booleanNot(true, false) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreStringConcatBuiltIn() throws SWRLParseException, SQWRLException
	{
		String query = "swrlb:stringConcat(?r, \"A\", \"B\") -> sqwrl:select(?r)";
		SQWRLResult result = executeSQWRLQuery("q1", query);

		Assert.assertTrue(result.next());
		SQWRLLiteralResultValue literal = result.getLiteral("r");
		Assert.assertTrue(literal.isString());
		Assert.assertEquals(literal.getString(), "AB");
	}

	@Test
	public void TestSWRLCoreStringEqualIgnoreCaseBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:stringEqualIgnoreCase(\"AAA\", \"aaa\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreSubStringBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:substring(\"123\", \"12345\", 0, 3) -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreStringLengthBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:stringLength(3, \"ABC\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreUpperCaseBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:upperCase(\"ABC\", \"abc\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreLowerCaseBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:lowerCase(\"abc\", \"ABC\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreContainsBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:contains(\"abcd\", \"bc\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreContainsIgnoreCaseBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:containsIgnoreCase(\"abcd\", \"BC\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreStartsWithBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:startsWith(\"abcd\", \"ab\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreEndsWithBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:endsWith(\"abcd\", \"cd\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	// TODO Built-in implementation not correct
	// @Test
	// public void TestSWRLCoreTranslateBuiltIn() throws SWRLParseException, SQWRLException
	// {
	// SQWRLResult result = executeSQWRLQuery("q1",
	// "swrlb:translate(\"AAA\", \"--aaa--\", \"abc-\", \"ABC\") -> sqwrl:select(0)");
	//
	// Assert.assertTrue(result.next());
	// }

	@Test
	public void TestSWRLCoreSubstringAfterBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				"swrlb:substringAfter(\"ddd\", \"aaaddd\", \"aaa\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreSubstringBeforeBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				"swrlb:substringBefore(\"aaa\", \"aaaddd\", \"ddd\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreMatchesBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:matches(\"abracadabra\", \"^a.*a$\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLCoreReplaceBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				"swrlb:replace(\"a*cada*\", \"abracadabra\", \"bra\", \"*\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLNormalizeSpaceBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "swrlb:normalizeSpace(\"fred\", \"  fred  \") -> sqwrl:select(0)");

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
