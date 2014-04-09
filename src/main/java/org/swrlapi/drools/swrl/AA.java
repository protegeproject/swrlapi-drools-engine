package org.swrlapi.drools.swrl;

import org.semanticweb.owlapi.model.SWRLArgument;
import org.swrlapi.drools.extractors.DroolsAA2SWRLAtomArgumentExtractor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface represents a SWRL arguments to atoms in Drools.
 * 
 * @see SWRLArgument
 */
public interface AA
{
	SWRLArgument extract(DroolsAA2SWRLAtomArgumentExtractor extractor) throws TargetRuleEngineException;
}
