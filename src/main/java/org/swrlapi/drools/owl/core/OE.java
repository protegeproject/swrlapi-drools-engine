package org.swrlapi.drools.owl.core;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.swrl.AA;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This interface represents an OWL entity in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLEntity
 */
public interface OE extends OO, AA, BA
{
  @NonNull String getName();

  @NonNull OWLNamedObject extract(@NonNull DroolsOWLEntityExtractor extractor) throws TargetSWRLRuleEngineException;

  @NonNull SWRLBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor)
    throws TargetSWRLRuleEngineException;

  @SideEffectFree @NonNull @Override public String toString();
}
