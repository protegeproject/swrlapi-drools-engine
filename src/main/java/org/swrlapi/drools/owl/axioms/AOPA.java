package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import dataflow.quals.SideEffectFree;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsUnaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL asymmetric object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom
 */
public class AOPA extends DroolsUnaryObject<String> implements A
{
  private static final long serialVersionUID = 1L;

  public AOPA(@NonNull String propertyID)
  {
    super(propertyID);
  }

  @NonNull public String getpid()
  {
    return getT1();
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "AOPA" + super.toString();
  }

  @NonNull @Override public OWLAsymmetricObjectPropertyAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }
}