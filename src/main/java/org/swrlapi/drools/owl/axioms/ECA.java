package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import dataflow.quals.SideEffectFree;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL equivalent classes axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom
 */
public class ECA extends DroolsBinaryClassesAxiom
{
  private static final long serialVersionUID = 1L;

  public ECA(@NonNull String class1ID, @NonNull String class2ID)
  {
    super(class1ID, class2ID);
  }

  @NonNull @Override public OWLEquivalentClassesAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "ECA" + super.toString();
  }
}