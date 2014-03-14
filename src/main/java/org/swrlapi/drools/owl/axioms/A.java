package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.swrlapi.drools.owl.OO;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface represents an OWL axiom in Drools.
 */
public interface A extends OO
{
	OWLAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException;
}