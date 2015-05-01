package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.OWLIndividual;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractorBase;
import org.swrlapi.drools.owl.core.I;

/**
 * Converts a Drools OWL individual representation to an OWLAPI OWL individual.
 *
 * @see org.semanticweb.owlapi.model.OWLIndividual
 * @see org.swrlapi.drools.owl.core.I
 */
public class DefaultDroolsOWLIndividualExtractor extends TargetRuleEngineExtractorBase implements
DroolsOWLIndividualExtractor
{
  public DefaultDroolsOWLIndividualExtractor(SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @Override
  public OWLIndividual extract(I i)
  {
    if (getIRIResolver().isOWLNamedIndividual(i.getName()))
      return getOWLDataFactory().getOWLNamedIndividual(prefixedName2IRI(i.getName()));
    else
      return getOWLDataFactory().getOWLAnonymousIndividual(i.getName());
  }
}
