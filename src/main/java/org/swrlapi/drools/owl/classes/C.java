package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.builtins.arguments.SWRLClassBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.OE;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

import java.util.Objects;

/**
 * Class representing an OWL named class in Drools. A named class is a type of class expression in OWL.
 *
 * @see org.semanticweb.owlapi.model.OWLClass
 */
public class C implements CE, OE
{
  private static final long serialVersionUID = 1L;

  @NonNull public final String id;

  public C(@NonNull String id)
  {
    this.id = id;
  }

  @NonNull public String getid()
  {
    return this.id;
  }

  @NonNull @Override public String getceid()
  {
    return this.id;
  }

  /*
   * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
   */
  public C(BA ba)
  {
    if (ba instanceof C) {
      C c = (C)ba;
      this.id = c.getid();
    } else
      throw new TargetSWRLRuleEngineInternalException(
        "expecting OWL named class from bound built-in argument, got " + ba.getClass().getCanonicalName());
  }

  @NonNull @Override public OWLClass extract(@NonNull DroolsOWLEntityExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @NonNull @Override public SWRLClassBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @NonNull public static C getOWLThing()
  {
    return new C(OWLRDFVocabulary.OWL_THING.getPrefixedName());
  }

  @NonNull public static C getOWLNothing()
  {
    return new C(OWLRDFVocabulary.OWL_NOTHING.getPrefixedName());
  }

  @SideEffectFree @Deterministic @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    C c = (C)o;

    return Objects.equals(id, c.id);

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    return id != null ? id.hashCode() : 0;
  }
}
