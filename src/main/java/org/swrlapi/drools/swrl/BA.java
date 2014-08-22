package org.swrlapi.drools.swrl;

import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface represents a SWRLAPI SWRL built-in argument in Drools.
 * 
 * @see org.swrlapi.builtins.arguments.SWRLBuiltInArgument
 */
public interface BA extends AA
{
	@Override
	SWRLBuiltInArgument extract(DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetRuleEngineException;
}