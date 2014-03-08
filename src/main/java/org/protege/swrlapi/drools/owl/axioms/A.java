package org.protege.swrlapi.drools.owl.axioms;

import org.protege.swrlapi.drools.extractors.DroolsA2OWLAxiomExtractor;
import org.protege.swrlapi.drools.owl.OO;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * This interface represents an OWL axiom in Drools.
 */
public interface A extends OO
{
	OWLAxiom extract(DroolsA2OWLAxiomExtractor extractor) throws TargetRuleEngineException;
}