package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL disjoint classes axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDisjointClassesAxiom
 */
public class DCA extends DroolsBinaryClassesAxiom
{
  private static final long serialVersionUID = 1L;

  public DCA(@NonNull String class1ID, @NonNull String class2ID)
  {
    super(class1ID, class2ID);
  }

  @NonNull @Override public OWLDisjointClassesAxiom extract(@NonNull DroolsOWLAxiomExtractor converter)
    throws TargetSWRLRuleEngineException
  {
    return converter.extract(this);
  }

  @Override public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }

  @NonNull @Override public String toString()
  {
    return "DCA" + super.toString();
  }
}