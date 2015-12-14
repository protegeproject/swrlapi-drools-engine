package org.swrlapi.drools.swrl;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This interface represents OWLAPI SWRL atom arguments in Drools.
 *
 * @see org.semanticweb.owlapi.model.SWRLArgument
 */
public interface AA
{
  @NonNull SWRLArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor)
    throws TargetSWRLRuleEngineException;
}
