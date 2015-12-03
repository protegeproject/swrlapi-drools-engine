package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import dataflow.quals.SideEffectFree;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL subclass axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLSubClassOfAxiom
 */
public class SCA extends DroolsBinaryObject<String, String> implements A
{
  private static final long serialVersionUID = 1L;

  public SCA(@NonNull String class1ID, @NonNull String class2ID)
  {
    super(class1ID, class2ID);
  }

  @NonNull public String getsubcid()
  {
    return getT1();
  }

  @NonNull public String getsupercid()
  {
    return getT2();
  }

  @NonNull @Override public OWLSubClassOfAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
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
    return "SCA" + super.toString();
  }
}