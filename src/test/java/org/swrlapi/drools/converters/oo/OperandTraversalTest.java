package org.swrlapi.drools.converters.oo;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.drools.converters.id.DroolsOWLDataRangeHandler;
import org.swrlapi.drools.owl.classes.OIOCE;
import org.swrlapi.drools.owl.classes.OUOCE;
import org.swrlapi.drools.owl.dataranges.DIO;
import org.swrlapi.drools.owl.dataranges.DUO;
import org.swrlapi.factory.SWRLAPIInternalFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class OperandTraversalTest
{
  private final OWLDataFactory df = OWLManager.getOWLDataFactory();
  private DroolsOWLClassExpressionHandler classes;
  private DroolsOWLDataRangeHandler ranges;

  @Before public void createConverters() throws Exception
  {
    OWLOntology ontology = OWLManager.createOWLOntologyManager().createOntology();
    SWRLRuleEngineBridge bridge = SWRLAPIInternalFactory.createSWRLBridge(
      SWRLAPIInternalFactory.createSWRLAPIOntology(ontology),
      SWRLAPIInternalFactory.createOWL2RLPersistenceLayer(ontology));
    ranges = new DroolsOWLDataRangeHandler(bridge);
    classes = new DroolsOWLClassExpressionHandler(bridge, new DroolsOWLIndividual2IConverter(bridge),
      new DroolsOWLPropertyExpressionHandler(bridge), ranges, new DroolsOWLLiteral2LConverter(bridge));
  }

  @Test public void intersectionAndUnionRetainNestedOperandsAndCachedIdentity() throws Exception
  {
    OWLClass a = df.getOWLClass(IRI.create("urn:operands#A"));
    OWLClass b = df.getOWLClass(IRI.create("urn:operands#B"));
    OWLClassExpression restriction = df.getOWLObjectSomeValuesFrom(
      df.getOWLObjectProperty(IRI.create("urn:operands#p")), b);
    OWLObjectIntersectionOf intersection = df.getOWLObjectIntersectionOf(a, restriction);
    OWLObjectUnionOf union = df.getOWLObjectUnionOf(intersection, b);
    OIOCE intersectionFact = classes.visit(intersection);
    OUOCE unionFact = classes.visit(union);

    assertEquals(new HashSet<>(Arrays.asList(a, restriction)), resolveClasses(intersectionFact.getceids()));
    assertEquals(new HashSet<>(Arrays.asList(intersection, b)), resolveClasses(unionFact.getceids()));
    assertEquals(intersection, classes.resolveOWLObjectIntersectionOf(intersectionFact.getceid()));
    assertEquals(union, classes.resolveOWLObjectUnionOf(unionFact.getceid()));
    assertSame(intersectionFact, classes.visit(intersection));
    assertSame(unionFact, classes.visit(union));
  }

  @Test public void dataRangeOperandsRetainTheirOriginalDatatypes()
  {
    OWLDatatype integer = df.getIntegerOWLDatatype(), string = df.getStringOWLDatatype();
    OWLDataIntersectionOf intersection = df.getOWLDataIntersectionOf(integer, string);
    OWLDataUnionOf union = df.getOWLDataUnionOf(integer, string);
    String intersectionID = ranges.convert(intersection), unionID = ranges.convert(union);
    DIO intersectionFact = (DIO)ranges.getDataRanges().stream()
      .filter(r -> r.getdrid().equals(intersectionID)).findFirst().get();
    DUO unionFact = (DUO)ranges.getDataRanges().stream()
      .filter(r -> r.getdrid().equals(unionID)).findFirst().get();
    Set<OWLDataRange> expected = new HashSet<>(Arrays.asList(integer, string));

    assertEquals(expected, intersectionFact.getIntersectionOfDataRangeIDs().stream()
      .map(ranges::resolveOWLDataRange).collect(Collectors.toSet()));
    assertEquals(expected, unionFact.getUnionOfDataRangeIDs().stream()
      .map(ranges::resolveOWLDataRange).collect(Collectors.toSet()));
    assertEquals(intersection, ranges.resolveOWLDataRange(intersectionID));
    assertEquals(union, ranges.resolveOWLDataRange(unionID));
    assertEquals(intersectionID, ranges.convert(intersection));
    assertEquals(unionID, ranges.convert(union));
  }

  @Test public void dataMaximumCardinalityRetainsQualifiedFillerAndRoundTrips() throws Exception
  {
    OWLDataProperty p = df.getOWLDataProperty(IRI.create("urn:operands#p"));
    OWLDataRange filler = df.getOWLDataIntersectionOf(df.getIntegerOWLDatatype(),
      df.getOWLDatatype(IRI.create("http://www.w3.org/2001/XMLSchema#positiveInteger")));
    for (int cardinality : new int[]{0, 1, 2}) {
      OWLDataMaxCardinality original = df.getOWLDataMaxCardinality(cardinality, p, filler);
      org.swrlapi.drools.owl.classes.DMaxCCE fact = classes.convert(original);
      assertTrue(fact instanceof org.swrlapi.drools.owl.classes.QualifiedDataMaxCardinality);
      String fillerID = ((org.swrlapi.drools.owl.classes.QualifiedDataMaxCardinality)fact).getFiller();
      assertEquals(filler, ranges.resolveOWLDataRange(fillerID));
      assertEquals(original, classes.convert(fact));
      assertSame(fact, classes.convert(original));
      assertEquals(Integer.valueOf(cardinality), fact.getCard());
      assertTrue(classes.getCEs().contains(fact));
    }
    OWLDataMaxCardinality unqualified = df.getOWLDataMaxCardinality(1, p);
    assertFalse(classes.convert(unqualified) instanceof org.swrlapi.drools.owl.classes.QualifiedDataMaxCardinality);
    assertEquals(unqualified, classes.convert(classes.convert(unqualified)));
  }

  private Set<OWLClassExpression> resolveClasses(Set<String> ids) throws Exception
  {
    Set<OWLClassExpression> result = new HashSet<>();
    for (String id : ids)
      result.add(classes.resolveOWLClassExpression(id));
    return result;
  }
}
