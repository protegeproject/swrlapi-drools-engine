package org.swrlapi.drools.factory;

import checkers.nullness.quals.NonNull;
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
public class DefaultDroolsOWLIndividualExtractor extends TargetRuleEngineExtractorBase implements
  DroolsOWLIndividualExtractor
{
  public DefaultDroolsOWLIndividualExtractor(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull @Override
  public OWLIndividual extract(@NonNull I i)
  {
    if (getIRIResolver().isOWLNamedIndividual(i.getName()))
      return getOWLDataFactory().getOWLNamedIndividual(getIRIResolver().prefixedName2IRI(i.getName()));
    else
      return getOWLDataFactory().getOWLAnonymousIndividual(i.getName());
  }
}
