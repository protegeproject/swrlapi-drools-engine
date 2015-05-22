package org.swrlapi.drools.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractorBase;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
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
class DefaultDroolsOWLEntityExtractor extends TargetRuleEngineExtractorBase implements DroolsOWLEntityExtractor
{
  public DefaultDroolsOWLEntityExtractor(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull @Override public OWLClass extract(@NonNull C cls) throws TargetSWRLRuleEngineException
  {
    IRI classIRI = prefixedName2IRI(cls.getName());
    return getOWLDataFactory().getOWLClass(classIRI);
  }

  @NonNull @Override public OWLNamedIndividual extract(@NonNull I individual) throws TargetSWRLRuleEngineException
  {
    IRI individualIRI = prefixedName2IRI(individual.getName());
    return getOWLDataFactory().getOWLNamedIndividual(individualIRI);
  }

  @NonNull @Override public OWLDataProperty extract(@NonNull DP property) throws TargetSWRLRuleEngineException
  {
    IRI propertyIRI = prefixedName2IRI(property.getName());
    return getOWLDataFactory().getOWLDataProperty(propertyIRI);
  }

  @NonNull @Override public OWLAnnotationProperty extract(@NonNull AP property) throws TargetSWRLRuleEngineException
  {
    IRI propertyIRI = prefixedName2IRI(property.getName());
    return getOWLDataFactory().getOWLAnnotationProperty(propertyIRI);
  }

  @NonNull @Override public OWLObjectProperty extract(@NonNull OP property) throws TargetSWRLRuleEngineException
  {
    IRI propertyIRI = prefixedName2IRI(property.getName());
    return getOWLDataFactory().getOWLObjectProperty(propertyIRI);
  }

  @NonNull @Override public OWLDatatype extract(@NonNull D datatype) throws TargetSWRLRuleEngineException
  {
    IRI datatypeIRI = prefixedName2IRI(datatype.getName());
    return getOWLDataFactory().getOWLDatatype(datatypeIRI);
  }
}
