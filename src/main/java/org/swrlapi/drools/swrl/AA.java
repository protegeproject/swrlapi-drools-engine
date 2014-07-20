package org.swrlapi.drools.swrl;

import org.semanticweb.owlapi.model.SWRLArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface represents a arguments to SWRL atoms in Drools.
 * 
 * @see org.semanticweb.owlapi.model.SWRLArgument
 */
public interface AA
{
	SWRLArgument extract(DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetRuleEngineException;
}
