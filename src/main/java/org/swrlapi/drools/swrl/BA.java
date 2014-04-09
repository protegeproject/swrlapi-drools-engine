package org.swrlapi.drools.swrl;

import org.swrlapi.core.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsAA2SWRLAtomArgumentExtractor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface represents a SWRL built-in argument in Drools.
 * 
 * @see SWRLBuiltInArgument
 */
public interface BA extends AA
{
	@Override
	SWRLBuiltInArgument extract(DroolsAA2SWRLAtomArgumentExtractor extractor) throws TargetRuleEngineException;
}