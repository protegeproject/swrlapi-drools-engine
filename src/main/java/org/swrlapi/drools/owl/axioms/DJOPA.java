package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing a disjoint object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom
 */
public class DJOPA extends DroolsBinaryPropertiesAxiom
{
  private static final long serialVersionUID = 1L;

  public DJOPA(@NonNull String property1ID, @NonNull String property2ID)
  {
    super(property1ID, property2ID);
  }

  @NonNull @Override public OWLDisjointObjectPropertiesAxiom extract(@NonNull DroolsOWLAxiomExtractor converter)
    throws TargetSWRLRuleEngineException
  {
    return converter.extract(this);
  }

  @Override public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }

  @NonNull @SideEffectFree @Override public String toString()
  {
    return "DJOPA" + super.toString();
  }
}
