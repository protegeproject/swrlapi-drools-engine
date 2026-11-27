package org.swrlapi.drools.core;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.factory.SWRLAPIInternalFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EngineVersionTest
{
  @Test public void classesWithoutManifestHaveExplicitUnknownVersion() throws Exception
  {
    assertNull(DroolsSWRLRuleEngine.class.getPackage().getImplementationVersion());
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLAPIOWLOntology wrapper = SWRLAPIInternalFactory.createSWRLAPIOntology(ontology,
      SWRLAPIFactory.createIRIResolver());
    SWRLRuleEngineBridge bridge = SWRLAPIInternalFactory.createSWRLBridge(wrapper,
      SWRLAPIInternalFactory.createOWL2RLPersistenceLayer(ontology));
    DroolsSWRLRuleEngine engine = new DroolsSWRLRuleEngine(bridge);
    assertEquals("unknown", engine.getTargetRuleEngineVersion());
  }
}
