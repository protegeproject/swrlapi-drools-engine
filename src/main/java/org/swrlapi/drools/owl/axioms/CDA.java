package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import dataflow.quals.SideEffectFree;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL class declaration.
 *
 * @see org.semanticweb.owlapi.model.OWLDeclarationAxiom
 */
public class CDA extends DroolsUnaryClassAxiom
{
  private static final long serialVersionUID = 1L;

  public CDA(@NonNull String classID)
  {
    super(classID);
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "CDA(" + super.toString() + ")";
  }

  @NonNull @Override public OWLDeclarationAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }
}
