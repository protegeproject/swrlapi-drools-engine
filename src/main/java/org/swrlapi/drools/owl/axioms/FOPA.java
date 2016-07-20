package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents on OWL functional object property axiom.
 *
 * @see org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom
 */
public class FOPA extends DroolsUnaryPropertyAxiom
{
  private static final long serialVersionUID = 1L;

  public FOPA(@NonNull String propertyID)
  {
    super(propertyID);
  }

  @NonNull @Override public OWLFunctionalObjectPropertyAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }

  @NonNull @SideEffectFree @Override public String toString()
  {
    return "FOPA" + super.toString();
  }
}