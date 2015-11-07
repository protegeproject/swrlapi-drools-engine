package org.swrlapi.drools.converters;

import checkers.nullness.quals.NonNull;
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
 * This class converts OWLAPI OWL entities to their Drools DRL representation for use in rules.
 *
 * @see org.semanticweb.owlapi.model.OWLEntity
 */
public class DroolsOWLEntity2NameConverter extends TargetRuleEngineConverterBase
  implements TargetRuleEngineOWLEntityConverter<String>
{
  public DroolsOWLEntity2NameConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull @Override public String convert(@NonNull OWLClass cls)
  {
    return getIRIResolver().iri2PrefixedName(cls.getIRI());
  }

  @NonNull @Override public String convert(@NonNull OWLNamedIndividual individual)
  {
    return getIRIResolver().iri2PrefixedName(individual.getIRI());
  }

  @NonNull @Override public String convert(@NonNull OWLObjectProperty property)
  {
    return getIRIResolver().iri2PrefixedName(property.getIRI());
  }

  @NonNull @Override public String convert(@NonNull OWLDataProperty property)
  {
    return getIRIResolver().iri2PrefixedName(property.getIRI());
  }

  @NonNull @Override public String convert(@NonNull OWLAnnotationProperty property)
  {
    return getIRIResolver().iri2PrefixedName(property.getIRI());
  }

  @NonNull @Override public String convert(@NonNull OWLDatatype datatype)
  {
    return getIRIResolver().iri2PrefixedName(datatype.getIRI());
  }
}
