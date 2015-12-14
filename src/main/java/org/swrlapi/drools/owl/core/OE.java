package org.swrlapi.drools.owl.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.swrl.AA;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL entity in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLEntity
 */
public abstract class OE implements OO, AA, BA
{
  private static final long serialVersionUID = 1L;

  @NonNull protected String id;

  protected OE(@NonNull String id)
  {
    this.id = id;
  }

  @NonNull public String getName()
  {
    return this.id;
  }

  @NonNull public String getid()
  {
    return this.id;
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return this.id;
  }

  @NonNull public abstract OWLNamedObject extract(@NonNull DroolsOWLEntityExtractor extractor)
    throws TargetSWRLRuleEngineException;

  @NonNull @Override public abstract SWRLBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor)
    throws TargetSWRLRuleEngineException;

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object obj)
  {
    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    OE e = (OE)obj;
    return (getid().equals(e.getid()) || (getid() != null && getid().equals(e.getid())));
  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int hash = 731;

    hash = hash + (null == getid() ? 0 : getid().hashCode());

    return hash;
  }
}
