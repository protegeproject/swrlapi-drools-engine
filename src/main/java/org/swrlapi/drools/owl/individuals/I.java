package org.swrlapi.drools.owl.individuals;

import checkers.nullness.quals.NonNull;
import dataflow.quals.SideEffectFree;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.extractors.DroolsOWLIndividualExtractor;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.OE;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

// TODO This class represents both named and anonymous OWL individuals
// - but an anonymous individual should not be a subclass of an OWL entity.

/**
 * This class represents an OWL named or anonymous individual.
 *
 * @see org.semanticweb.owlapi.model.OWLIndividual
 */
public class I extends OE
{
  private static final long serialVersionUID = 1L;

  public I(@NonNull String id)
  {
    super(id);
  }

  /*
   * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
   */
  public I(@NonNull BA ba)
  {
    super("<InProcess>");

    if (ba instanceof I) {
      I i = (I)ba;
      this.id = i.getName();
    } else
      throw new TargetSWRLRuleEngineInternalException("expecting OWL individual from bound built-in argument, got "
          + ba.getClass().getCanonicalName());
  }

  @NonNull public OWLIndividual extract(@NonNull DroolsOWLIndividualExtractor extractor)
  {
    return extractor.extract(this);
  }

  @NonNull @Override
  public OWLNamedIndividual extract(@NonNull DroolsOWLEntityExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @NonNull @Override
  public SWRLBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @SideEffectFree @NonNull @Override
  public String toString()
  {
    return super.toString();
  }
}
