package org.swrlapi.drools.converters.oo;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.drools.converters.id.DroolsOWLDataRangeHandler;
import org.swrlapi.drools.core.DroolsSWRLRuleEngine;
import org.swrlapi.drools.owl.axioms.A;
import org.swrlapi.drools.owl.axioms.DJOPA;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.factory.SWRLAPIInternalFactory;
import org.swrlapi.owl2rl.OWL2RLPersistenceLayer;
import org.swrlapi.test.IntegrationTestBase;

import java.util.HashSet;
import java.util.Set;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLManager.getOWLDataFactory;

public class DroolsOWLAxiom2AConverterTest extends IntegrationTestBase
{
  private static final String PID1 = "p1";
  private static final String PID2 = "p2";

  @Test public void textConvertOWLDisjointObjectPropertiesAxiom() throws Exception
  {
    DroolsOWLAxiom2AConverter droolsOWLAxiom2AConverter = createDroolsOWLAxiom2AConverter();
    OWLObjectPropertyExpression property1 =  ObjectProperty(iri(PID1));
    OWLObjectPropertyExpression property2 = ObjectProperty(iri(PID2));
    Set<@NonNull OWLObjectPropertyExpression> properties = new HashSet<>();
    properties.add(property1);
    properties.add(property2);

    OWLDisjointObjectPropertiesAxiom axiom = getOWLDataFactory().getOWLDisjointObjectPropertiesAxiom(properties);

    droolsOWLAxiom2AConverter.convert(axiom);

    Set<@NonNull A> assertedOWLAxioms = droolsOWLAxiom2AConverter.getAssertedOWLAxioms();

    Assert.assertTrue(assertedOWLAxioms.size() == 2);
    for (A a : assertedOWLAxioms) // TODO Should be more exhaustive here
      Assert.assertTrue(a instanceof DJOPA);
  }

  private DroolsOWLAxiom2AConverter createDroolsOWLAxiom2AConverter()
    throws OWLOntologyCreationException, SWRLBuiltInException
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLAPIOWLOntology swrlapiOWLOntology = SWRLAPIInternalFactory.createSWRLAPIOntology(ontology);
    OWL2RLPersistenceLayer owl2rlPersistenceLayer = SWRLAPIInternalFactory.createOWL2RLPersistenceLayer(ontology);
    SWRLRuleEngineBridge bridge = SWRLAPIInternalFactory.createSWRLBridge(swrlapiOWLOntology, owl2rlPersistenceLayer);
    DroolsSWRLRuleEngine droolsSWRLRuleEngine = new DroolsSWRLRuleEngine(bridge);
    DroolsOWLClassExpressionHandler droolsOWLClassExpressionHandler = createDroolsOWLClassExpressionHandler(bridge);
    DroolsOWLPropertyExpressionHandler droolsOWLPropertyExpressionHandler = new DroolsOWLPropertyExpressionHandler(bridge);
    DroolsOWLDataRangeHandler droolsOWLDataRangeHandler = new DroolsOWLDataRangeHandler(bridge);

    return new DroolsOWLAxiom2AConverter(bridge, droolsSWRLRuleEngine, droolsOWLClassExpressionHandler,
      droolsOWLPropertyExpressionHandler, droolsOWLDataRangeHandler);
  }

  private DroolsOWLClassExpressionHandler createDroolsOWLClassExpressionHandler(@NonNull SWRLRuleEngineBridge bridge)
  {
    DroolsOWLIndividual2IConverter droolsOWLIndividual2IConverter = new DroolsOWLIndividual2IConverter(bridge);
    DroolsOWLPropertyExpressionHandler droolsOWLPropertyExpressionHandler = new DroolsOWLPropertyExpressionHandler(bridge);
    DroolsOWLDataRangeHandler droolsOWLDataRangeHandler = new DroolsOWLDataRangeHandler(bridge);
    DroolsOWLLiteral2LConverter droolsOWLLiteral2LConverter = new DroolsOWLLiteral2LConverter(bridge);

    return new DroolsOWLClassExpressionHandler(bridge, droolsOWLIndividual2IConverter,
      droolsOWLPropertyExpressionHandler, droolsOWLDataRangeHandler, droolsOWLLiteral2LConverter);
  }
}