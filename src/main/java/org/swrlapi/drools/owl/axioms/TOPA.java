package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL transitive object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom
 */
public class TOPA extends DroolsUnaryPropertyAxiom
{
  private static final long serialVersionUID = 1L;

  public TOPA(@NonNull String propertyID)
  {
    super(propertyID);
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "TOPA" + super.toString();
  }

  @NonNull @Override public OWLTransitiveObjectPropertyAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }
}