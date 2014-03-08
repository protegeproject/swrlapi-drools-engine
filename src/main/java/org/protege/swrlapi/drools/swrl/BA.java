package org.protege.swrlapi.drools.swrl;

import org.protege.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.protege.swrlapi.drools.extractors.DroolsAA2SWRLAtomArgumentExtractor;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface represents a SWRL built-in argument.
 */
public interface BA extends AA
{
	SWRLBuiltInArgument extract(DroolsAA2SWRLAtomArgumentExtractor extractor) throws TargetRuleEngineException;
}