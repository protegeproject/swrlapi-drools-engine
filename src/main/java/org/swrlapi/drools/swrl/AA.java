package org.swrlapi.drools.swrl;

import org.semanticweb.owlapi.model.SWRLArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This interface represents OWLAPI SWRL arguments in Drools.
 * 
 * @see org.semanticweb.owlapi.model.SWRLArgument
 */
public interface AA
{
	SWRLArgument extract(DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException;
}
