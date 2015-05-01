package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLEntityConverter;

/**
 * This class converts OWLAPI OWL named objects to their Drools DRL representation for use in rules.
 *
 * @see org.semanticweb.owlapi.model.OWLEntity
 */
public class DroolsOWLNamedObject2DRLConverter extends TargetRuleEngineConverterBase implements
TargetRuleEngineOWLEntityConverter<String>
{
  public DroolsOWLNamedObject2DRLConverter(SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @Override
  public String convert(OWLClass cls)
  {
    return getIRIResolver().iri2PrefixedName(cls.getIRI());
  }

  @Override
  public String convert(OWLNamedIndividual individual)
  {
    return getIRIResolver().iri2PrefixedName(individual.getIRI());
  }

  @Override
  public String convert(OWLObjectProperty property)
  {
    return getIRIResolver().iri2PrefixedName(property.getIRI());
  }

  @Override
  public String convert(OWLDataProperty property)
  {
    return getIRIResolver().iri2PrefixedName(property.getIRI());
  }

  @Override
  public String convert(OWLAnnotationProperty property)
  {
    return getIRIResolver().iri2PrefixedName(property.getIRI());
  }

  @Override
  public String convert(OWLDatatype datatype)
  {
    return getIRIResolver().iri2PrefixedName(datatype.getIRI());
  }
}
