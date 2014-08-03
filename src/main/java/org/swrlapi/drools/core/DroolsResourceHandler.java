package org.swrlapi.drools.core;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.swrlapi.exceptions.TargetRuleEngineException;

import java.io.StringReader;

public class DroolsResourceHandler
{
	private final KnowledgeBuilder knowledgeBuilder;

	public DroolsResourceHandler(KnowledgeBuilder knowledgeBuilder)
	{
		this.knowledgeBuilder = knowledgeBuilder;
	}

	public void defineJavaResources()
	{
		defineGlobalJavaObjects();
		importOWLAndSWRLJavaClasses();
	}

	public void defineDRLRule(String ruleName, String ruleText) throws TargetRuleEngineException
	{
		try {
			//System.out.println("Rule " + ruleName + "\n" + ruleText);
			defineDRLResource(ruleText);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new TargetRuleEngineException(
					"internal error generating Drools rule \n" + ruleText + "\n" + e.getMessage(), e);
		}

		if (knowledgeBuilder.hasErrors())
			throw new TargetRuleEngineException("internal error generating Drools rule\n" + ruleText + "\n"
					+ knowledgeBuilder.getErrors().toString());
	}

	private void defineGlobalJavaObjects()
	{
		defineDRLResource("import org.swrlapi.drools.owl2rl.DroolsOWLAxiomHandler;");
		defineDRLResource("global DroolsOWLAxiomHandler inferrer;");

		defineDRLResource("import org.swrlapi.drools.sqwrl.DroolsSQWRLCollectionHandler;");
		defineDRLResource("global DroolsSQWRLCollectionHandler sqwrlInferrer;");

		defineDRLResource("import org.swrlapi.drools.core.DroolsSWRLBuiltInInvoker;");
		defineDRLResource("global DroolsSWRLBuiltInInvoker invoker;");
	}

	private void importOWLAndSWRLJavaClasses()
	{
		importCoreOWLJavaClasses();
		importOWLClassExpressionJavaClasses();
		importOWLAxiomJavaClasses();
		importOWLDataRangeJavaClasses();
		importSWRLJavaClasses();
		importSQWRLJavaClasses();
	}

	private void importSQWRLJavaClasses()
	{
		// Drools class representing SQWRL collections
		defineDRLResource("import org.swrlapi.drools.sqwrl.SQWRLC");
		defineDRLResource("import org.swrlapi.drools.sqwrl.VPATH");
	}

	private void importSWRLJavaClasses()
	{
		// Drools classes representing SWRL built-in arguments and other built-in support classes
		defineDRLResource("import org.swrlapi.drools.swrl.BA");
		defineDRLResource("import org.swrlapi.drools.swrl.BAP");
		defineDRLResource("import org.swrlapi.drools.swrl.UBA");
		defineDRLResource("import org.swrlapi.drools.swrl.BAVNs");
	}

	private void importOWLDataRangeJavaClasses()
	{
		// Drools classes representing OWL data ranges
		defineDRLResource("import org.swrlapi.drools.owl.dataranges.DR");
		defineDRLResource("import org.swrlapi.drools.owl.dataranges.DIO");
		defineDRLResource("import org.swrlapi.drools.owl.dataranges.DCO");
		defineDRLResource("import org.swrlapi.drools.owl.dataranges.DUO");
		defineDRLResource("import org.swrlapi.drools.owl.dataranges.DOO");
	}

	private void importOWLAxiomJavaClasses()
	{
		// Drools classes representing OWL axioms
		defineDRLResource("import org.swrlapi.drools.owl.axioms.AOPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.CAA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.CDA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.DCA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.DDPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.DIA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.DJOPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.DJDPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.DOPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.DPAA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.DPDA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.ECA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.EDPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.EOPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.FDPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.FOPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.IDA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.IOPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.IFOPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.IROPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.OPAA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.OPDA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.DPRA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.OPRA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.SCA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.SDPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.SIA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.SOPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.SPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.TOPA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.NOPAA");
		defineDRLResource("import org.swrlapi.drools.owl.axioms.NDPAA");
	}

	private void importOWLClassExpressionJavaClasses()
	{
		// Drools classes representing OWL class expressions
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.CE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.OOCOCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.OIOCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.OOOCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.OUOCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.OHVCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.DHVCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.OSVFCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.DSVFCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.OAVFCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.DAVFCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.OMaxCCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.DMaxCCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.OMaxCCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.OMaxQCCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.DMaxQCCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.DMinCCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.OCCE");
		defineDRLResource("import org.swrlapi.drools.owl.classexpressions.DCCE");
	}

	private void importCoreOWLJavaClasses()
	{
		// Drools class representing an OWL literal
		defineDRLResource("import org.swrlapi.drools.owl.core.L");

		// Drools classes representing OWL named entities
		defineDRLResource("import org.swrlapi.drools.owl.core.C");
		defineDRLResource("import org.swrlapi.drools.owl.core.I");
		defineDRLResource("import org.swrlapi.drools.owl.properties.OP");
		defineDRLResource("import org.swrlapi.drools.owl.properties.DP");
		defineDRLResource("import org.swrlapi.drools.owl.properties.AP");
		defineDRLResource("import org.swrlapi.drools.owl.core.D");
	}

	private void defineDRLResource(String resourceText)
	{
		knowledgeBuilder.add(createDRLResource(resourceText), ResourceType.DRL);
	}

	private Resource createDRLResource(String resourceText)
	{
		return ResourceFactory.newReaderResource(new StringReader(resourceText));
	}
}
