package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.OO;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This interface represents an OWL axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLAxiom
 */
public interface A extends OO
{
  @NonNull OWLAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor) throws TargetSWRLRuleEngineException;

  void visit(@NonNull AVisitor visitor);
}