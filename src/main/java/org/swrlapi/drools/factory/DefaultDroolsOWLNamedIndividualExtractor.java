package org.swrlapi.drools.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractorBase;
import org.swrlapi.drools.extractors.DroolsOWLNamedIndividualExtractor;
import org.swrlapi.drools.owl.individuals.I;

/**
 * Converts a Drools OWL individual representation to an OWLAPI OWL named individual
 *
 * @see org.semanticweb.owlapi.model.OWLNamedIndividual
 * @see I
 */
public class DefaultDroolsOWLNamedIndividualExtractor extends TargetRuleEngineExtractorBase
  implements DroolsOWLNamedIndividualExtractor
{
  public DefaultDroolsOWLNamedIndividualExtractor(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @Override public @NonNull OWLNamedIndividual extract(@NonNull I i)
  {
    return getOWLDataFactory().getOWLNamedIndividual(prefixedName2IRI(i.getid()));
  }
}
