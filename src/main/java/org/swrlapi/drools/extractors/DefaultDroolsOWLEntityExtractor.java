package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractorBase;
import org.swrlapi.drools.owl.core.C;
import org.swrlapi.drools.owl.core.D;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl.properties.AP;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class converts the Drools representation of OWL entities to their OWLAPI representation.
 *
 * @see org.semanticweb.owlapi.model.OWLEntity
 * @see org.swrlapi.drools.owl.core.OE
 */
public class DefaultDroolsOWLEntityExtractor extends TargetRuleEngineExtractorBase implements DroolsOWLEntityExtractor
{
  public DefaultDroolsOWLEntityExtractor(SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @Override
  public OWLClass extract(C cls) throws TargetSWRLRuleEngineException
  {
    IRI classIRI = prefixedName2IRI(cls.getName());
    return getOWLDataFactory().getOWLClass(classIRI);
  }

  @Override
  public OWLNamedIndividual extract(I individual) throws TargetSWRLRuleEngineException
  {
    IRI individualIRI = prefixedName2IRI(individual.getName());
    return getOWLDataFactory().getOWLNamedIndividual(individualIRI);
  }

  @Override
  public OWLDataProperty extract(DP property) throws TargetSWRLRuleEngineException
  {
    IRI propertyIRI = prefixedName2IRI(property.getName());
    return getOWLDataFactory().getOWLDataProperty(propertyIRI);
  }

  @Override
  public OWLAnnotationProperty extract(AP property) throws TargetSWRLRuleEngineException
  {
    IRI propertyIRI = prefixedName2IRI(property.getName());
    return getOWLDataFactory().getOWLAnnotationProperty(propertyIRI);
  }

  @Override
  public OWLObjectProperty extract(OP property) throws TargetSWRLRuleEngineException
  {
    IRI propertyIRI = prefixedName2IRI(property.getName());
    return getOWLDataFactory().getOWLObjectProperty(propertyIRI);
  }

  @Override
  public OWLDatatype extract(D datatype) throws TargetSWRLRuleEngineException
  {
    IRI datatypeIRI = prefixedName2IRI(datatype.getName());
    return getOWLDataFactory().getOWLDatatype(datatypeIRI);
  }
}
