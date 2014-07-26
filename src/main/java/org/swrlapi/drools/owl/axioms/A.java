package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.OO;
import org.swrlapi.drools.owl2rl.DroolsOWL2RLAxiomVisitor;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface represents an OWL axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLAxiom
 */
public interface A extends OO
{
	OWLAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetRuleEngineException;

	void visit(DroolsOWL2RLAxiomVisitor visitor);
}