package org.swrlapi.drools.owl.properties;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.OE;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

/**
 * This class represents an OWL annotation property.
 *
 * @see org.semanticweb.owlapi.model.OWLAnnotationProperty
 */
public class AP implements P, OE
{
  private static final long serialVersionUID = 1L;

  @NonNull private final String name;

  public AP(@NonNull String name)
  {
    this.name = name;
  }

  public String getid() { return this.name; }

  /*
   * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
   */
  public AP(@NonNull BA ba)
  {
    if (ba instanceof DP) {
      DP p = (DP)ba;
      this.name = p.getid();
    } else
      throw new TargetSWRLRuleEngineInternalException(
        "expecting OWL annotation property from bound built-in argument, got " + ba.getClass().getCanonicalName());
  }

  @NonNull @Override public OWLNamedObject extract(@NonNull DroolsOWLEntityExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @NonNull @Override public SWRLBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object obj)
  {
    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    AP e = (AP)obj;
    return (getid().equals(e.getid()) || (getid() != null && getid().equals(e.getid())));
  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int hash = 731;

    hash = hash + (null == getid() ? 0 : getid().hashCode());

    return hash;
  }
}


