package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an equivalent object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom
 */
public class EOPA extends DroolsBinaryPropertiesAxiom
{
  private static final long serialVersionUID = 1L;

  public EOPA(@NonNull String property1ID, @NonNull String property2ID)
  {
    super(property1ID, property2ID);
  }

  @NonNull @Override public OWLEquivalentObjectPropertiesAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
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
    return "EOPA" + super.toString();
  }
}
