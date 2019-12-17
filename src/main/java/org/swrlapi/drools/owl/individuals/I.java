package org.swrlapi.drools.owl.individuals;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.swrlapi.builtins.arguments.SWRLNamedIndividualBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.OE;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

import java.util.Objects;

/**
 * This class represents an OWL named individual in Drools
 *
 * @see org.semanticweb.owlapi.model.OWLNamedIndividual
 */
public class I implements OE
{
  private static final long serialVersionUID = 1L;

  @NonNull public final String id;

  public I(@NonNull String name)
  {
    this.id = name;
  }

  @NonNull public String getid() { return this.id; }

  /*
   * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
   */
  public I(@NonNull BA ba)
  {
    if (ba instanceof I) {
      I i = (I)ba;
      this.id = i.getid();
    } else
      throw new TargetSWRLRuleEngineInternalException(
        "expecting OWL named individual from bound built-in argument, got " + ba.getClass().getCanonicalName());
  }

  @NonNull @Override public OWLNamedIndividual extract(@NonNull DroolsOWLEntityExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @NonNull @Override public SWRLNamedIndividualBuiltInArgument extract(
    @NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @SideEffectFree @Deterministic @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    I i = (I)o;

    return Objects.equals(id, i.id);

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    return id != null ? id.hashCode() : 0;
  }
}
