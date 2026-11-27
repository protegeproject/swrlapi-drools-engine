package org.swrlapi.drools.converters.oo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.core.IRIResolver;
import org.swrlapi.core.SWRLAPIOWLOntology;
import org.swrlapi.drools.converters.id.DroolsOWLDataRangeHandler;
import org.swrlapi.drools.core.DroolsSWRLRuleEngine;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.factory.DroolsFactory;
import org.swrlapi.drools.owl.axioms.*;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.factory.SWRLAPIInternalFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class NaryAxiomTraversalTest
{
  private static final String NS = "urn:nary-traversal#";
  private final OWLAxiom axiom;
  private final Class<? extends A> factType;
  private final Function<A, String> first;
  private final Function<A, String> second;
  private final boolean reflexive;

  public NaryAxiomTraversalTest(OWLAxiom axiom, Class<? extends A> factType,
    Function<A, String> first, Function<A, String> second, boolean reflexive)
  {
    this.axiom = axiom;
    this.factType = factType;
    this.first = first;
    this.second = second;
    this.reflexive = reflexive;
  }

  @Parameterized.Parameters(name = "{1}") public static Collection<Object[]> axioms()
  {
    OWLDataFactory df = OWLManager.getOWLDataFactory();
    OWLClass a = df.getOWLClass(IRI.create(NS + "A")), b = df.getOWLClass(IRI.create(NS + "B")),
      c = df.getOWLClass(IRI.create(NS + "C"));
    OWLNamedIndividual i = df.getOWLNamedIndividual(a.getIRI()), j = df.getOWLNamedIndividual(b.getIRI()),
      k = df.getOWLNamedIndividual(c.getIRI());
    OWLObjectProperty p = df.getOWLObjectProperty(a.getIRI()), q = df.getOWLObjectProperty(b.getIRI()),
      r = df.getOWLObjectProperty(c.getIRI());
    OWLDataProperty x = df.getOWLDataProperty(a.getIRI()), y = df.getOWLDataProperty(b.getIRI()),
      z = df.getOWLDataProperty(c.getIRI());
    return Arrays.asList(new Object[][] {
      { df.getOWLSameIndividualAxiom(i, j, k), SIA.class,
        (Function<A, String>)v -> ((SIA)v).geti1id(), (Function<A, String>)v -> ((SIA)v).geti2id(), true },
      { df.getOWLDifferentIndividualsAxiom(i, j, k), DIA.class,
        (Function<A, String>)v -> ((DIA)v).geti1id(), (Function<A, String>)v -> ((DIA)v).geti2id(), false },
      { df.getOWLEquivalentClassesAxiom(a, b, c), ECA.class,
        (Function<A, String>)v -> ((ECA)v).getc1id(), (Function<A, String>)v -> ((ECA)v).getc2id(), false },
      { df.getOWLDisjointClassesAxiom(a, b, c), DCA.class,
        (Function<A, String>)v -> ((DCA)v).getc1id(), (Function<A, String>)v -> ((DCA)v).getc2id(), false },
      { df.getOWLEquivalentObjectPropertiesAxiom(p, q, r), EOPA.class,
        (Function<A, String>)v -> ((EOPA)v).getp1id(), (Function<A, String>)v -> ((EOPA)v).getp2id(), false },
      { df.getOWLDisjointObjectPropertiesAxiom(p, q, r), DJOPA.class,
        (Function<A, String>)v -> ((DJOPA)v).getp1id(), (Function<A, String>)v -> ((DJOPA)v).getp2id(), false },
      { df.getOWLEquivalentDataPropertiesAxiom(x, y, z), EDPA.class,
        (Function<A, String>)v -> ((EDPA)v).getp1id(), (Function<A, String>)v -> ((EDPA)v).getp2id(), false },
      { df.getOWLDisjointDataPropertiesAxiom(x, y, z), DJDPA.class,
        (Function<A, String>)v -> ((DJDPA)v).getp1id(), (Function<A, String>)v -> ((DJDPA)v).getp2id(), false }
    });
  }

  @Test public void conversionRetainsEveryDirectedPairAndOnlyRequiredSelfPairs() throws Exception
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    IRIResolver resolver = SWRLAPIFactory.createIRIResolver(NS);
    SWRLAPIOWLOntology wrapper = SWRLAPIInternalFactory.createSWRLAPIOntology(ontology, resolver);
    SWRLRuleEngineBridge bridge = SWRLAPIInternalFactory.createSWRLBridge(wrapper,
      SWRLAPIInternalFactory.createOWL2RLPersistenceLayer(ontology));
    DroolsSWRLRuleEngine engine = new DroolsSWRLRuleEngine(bridge);
    DroolsOWLPropertyExpressionHandler properties = new DroolsOWLPropertyExpressionHandler(bridge);
    DroolsOWLDataRangeHandler ranges = new DroolsOWLDataRangeHandler(bridge);
    DroolsOWLClassExpressionHandler classes = new DroolsOWLClassExpressionHandler(bridge,
      new DroolsOWLIndividual2IConverter(bridge), properties, ranges, new DroolsOWLLiteral2LConverter(bridge));
    DroolsOWLAxiom2AConverter converter = new DroolsOWLAxiom2AConverter(bridge, engine, classes, properties, ranges);
    axiom.accept(converter);
    axiom.accept(converter);

    Set<String> expected = new HashSet<>();
    for (String left : Arrays.asList("A", "B", "C"))
      for (String right : Arrays.asList("A", "B", "C"))
        if (reflexive || !left.equals(right))
          expected.add(NS + left + "|" + NS + right);
    Set<A> facts = converter.getAssertedOWLAxioms();
    assertEquals(reflexive ? 9 : 6, facts.size());
    DroolsOWLAxiomExtractor extractor = DroolsFactory.getDroolsOWLAxiomExtractor(bridge, classes, properties, ranges);
    Set<String> actual = new HashSet<>();
    for (A fact : facts) {
      assertTrue(factType.isInstance(fact));
      actual.add(resolve(first.apply(fact), properties, resolver) + "|"
        + resolve(second.apply(fact), properties, resolver));
      OWLAxiom extracted = fact.extract(extractor);
      assertEquals("round-trip axiom type", axiom.getAxiomType(), extracted.getAxiomType());
      Set<IRI> endpoints = new HashSet<>(Arrays.asList(resolve(first.apply(fact), properties, resolver),
        resolve(second.apply(fact), properties, resolver)));
      assertEquals("both endpoints must survive extraction", endpoints,
        extracted.unsortedSignature().map(OWLEntity::getIRI).collect(Collectors.toSet()));
    }
    assertEquals(expected, actual);
  }

  private IRI resolve(String id, DroolsOWLPropertyExpressionHandler properties, IRIResolver resolver) throws Exception
  {
    if (factType == EOPA.class || factType == DJOPA.class)
      return properties.resolveOWLObjectPropertyExpression(id).asOWLObjectProperty().getIRI();
    if (factType == EDPA.class || factType == DJDPA.class)
      return properties.resolveOWLDataPropertyExpression(id).asOWLDataProperty().getIRI();
    return resolver.prefixedName2IRI(id).get();
  }
}
