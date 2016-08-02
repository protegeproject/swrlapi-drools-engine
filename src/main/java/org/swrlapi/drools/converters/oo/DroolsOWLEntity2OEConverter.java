package org.swrlapi.drools.converters.oo;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLEntityConverter;
import org.swrlapi.drools.owl.classes.C;
import org.swrlapi.drools.owl.core.OE;
import org.swrlapi.drools.owl.dataranges.D;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.drools.owl.properties.AP;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.OP;

/**
 * This class converts OWLAPI OWL entities to their Drools representation.
 *
 * @see org.semanticweb.owlapi.model.OWLEntity
 * @see OE
 */
public class DroolsOWLEntity2OEConverter extends TargetRuleEngineConverterBase
  implements TargetRuleEngineOWLEntityConverter<OE>
{
  public DroolsOWLEntity2OEConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull @Override public C convert(@NonNull OWLClass cls)
  {
    String prefixedName = iri2PrefixedName(cls.getIRI());
    return new C(prefixedName);
  }

  @NonNull @Override public I convert(@NonNull OWLNamedIndividual individual)
  {
    String prefixedName = iri2PrefixedName(individual.getIRI());
    return new I(prefixedName);
  }

  @NonNull @Override public OP convert(@NonNull OWLObjectProperty property)
  {
    String prefixedName = iri2PrefixedName(property.getIRI());
    return new OP(prefixedName);
  }

  @NonNull @Override public DP convert(@NonNull OWLDataProperty property)
  {
    String prefixedName = iri2PrefixedName(property.getIRI());
    return new DP(prefixedName);
  }

  @NonNull @Override public AP convert(@NonNull OWLAnnotationProperty property)
  {
    String prefixedName = iri2PrefixedName(property.getIRI());
    return new AP(prefixedName);
  }

  @NonNull @Override public D convert(@NonNull OWLDatatype datatype)
  {
    String prefixedName = iri2PrefixedName(datatype.getIRI());
    return new D(prefixedName);
  }
}
