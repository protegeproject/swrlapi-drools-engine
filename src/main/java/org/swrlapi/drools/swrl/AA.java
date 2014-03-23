package org.swrlapi.drools.swrl;

import org.semanticweb.owlapi.model.SWRLArgument;
import org.swrlapi.drools.extractors.DroolsAA2SWRLAtomArgumentExtractor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface represents a SWRL atom argument in Drools.
 */
public interface AA
{
	SWRLArgument extract(DroolsAA2SWRLAtomArgumentExtractor extractor) throws TargetRuleEngineException;
}
