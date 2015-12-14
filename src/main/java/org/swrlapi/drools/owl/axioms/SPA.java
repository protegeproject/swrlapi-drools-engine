package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL symmetric object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom
 */
public class SPA extends DroolsUnaryPropertyAxiom
{
  private static final long serialVersionUID = 1L;

  public SPA(@NonNull String propertyID)
  {
    super(propertyID);
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "SPA" + super.toString();
  }

  @NonNull @Override public OWLSymmetricObjectPropertyAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }
}