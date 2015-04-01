package org.swrlapi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.core.xsd.XSDDateTime;
import org.swrlapi.drools.core.DroolsFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.test.SWRLAPITestBase;

public class SWRLTemporalBuiltInsTestCase extends SWRLAPITestBase
{
	final String Namespace = "http://swrlapi.org/ontologies/SWRLTemporalBuiltInsTestCase.owl#";

	private SQWRLQueryEngine sqwrlQueryEngine;

	@Before
	public void setUp() throws OWLOntologyCreationException
	{
		SWRLAPIOWLOntology swrlapiOWLOntology = createEmptySWRLAPIOWLOntology(Namespace);

		sqwrlQueryEngine = swrlapiOWLOntology.createSQWRLQueryEngine(DroolsFactory.getSWRLRuleEngineCreator());
	}

	@Test
	public void TestSWRLTemporalBeforeBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "temporal:before(\"01-01-10\", \"01-01-13\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLTemporalAfterBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1", "temporal:after(\"01-01-13\", \"01-01-10\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLTemporalDurationBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				"temporal:duration(?r, \"1999-11-01T10:00:01.3\", \"2000-19-01T10:00:01.3\", \"Years\") -> sqwrl:select(?r)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("r").isLong());
		Assert.assertEquals(result.getLiteral("r").getLong(), 1);
	}

	@Test
	public void TestSWRLTemporalEqualsBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				"temporal:equals(\"1999-11-01T10:00:01.3\", \"1999-11-01T10:00:01.3\", \"Years\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLTemporalEqualsBuiltInYearsGranularity() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				"temporal:equals(\"1999-10-10T11:10:22.1\", \"1999-11-01T10:00:01.3\", \"Years\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLTemporalEqualsBuiltInMonthsGranularity() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				"temporal:equals(\"1999-10-10T11:10:22.1\", \"1999-10-01T10:00:01.3\", \"Years\") -> sqwrl:select(0)");

		Assert.assertTrue(result.next());
	}

	@Test
	public void TestSWRLTemporalAddBuiltIn() throws SWRLParseException, SQWRLException
	{
		SQWRLResult result = executeSQWRLQuery("q1",
				"temporal:add(?r, \"1999-11-01T10:00\", 4, \"Years\") -> sqwrl:select(?r)");

		Assert.assertTrue(result.next());
		Assert.assertTrue(result.getLiteral("r").isDateTime());
		Assert.assertEquals(result.getLiteral("r").getDateTime(), new XSDDateTime("2003-11-01T10:00:00.0"));
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
