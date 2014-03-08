package org.protege.swrlapi.drools.swrl;

import org.protege.swrlapi.core.arguments.SWRLAtomArgument;
import org.protege.swrlapi.drools.extractors.DroolsAA2SWRLAtomArgumentExtractor;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface represents a SWRL atom argument in Drools.
 */
public interface AA
{
	SWRLAtomArgument extract(DroolsAA2SWRLAtomArgumentExtractor extractor) throws TargetRuleEngineException;
}
