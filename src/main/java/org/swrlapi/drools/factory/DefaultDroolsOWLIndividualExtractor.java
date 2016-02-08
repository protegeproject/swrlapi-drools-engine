package org.swrlapi.drools.factory;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractorBase;
import org.swrlapi.drools.extractors.DroolsOWLIndividualExtractor;
import org.swrlapi.drools.owl.individuals.I;

/**
 * Converts a Drools OWL individual representation to an OWLAPI OWL individual.
 *
 * @see org.semanticweb.owlapi.model.OWLIndividual
 * @see I
 */
public class DefaultDroolsOWLIndividualExtractor extends TargetRuleEngineExtractorBase
  implements DroolsOWLIndividualExtractor
{
  public DefaultDroolsOWLIndividualExtractor(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull @Override public OWLIndividual extract(@NonNull I i)
  { // TODO Does not deal with anonymous individuals
    return getOWLDataFactory().getOWLNamedIndividual(prefixedName2IRI(i.getName()));
  }
}
