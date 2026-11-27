package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.drools.owl.literals.L;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL data property assertion axiom.
 *
 * @see org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom
 */
public class DPAA extends DroolsTernaryObject<I, String, L> implements A
{
  private static final long serialVersionUID = 1L;

  public DPAA(@NonNull String subjectName, @NonNull String propertyID, @NonNull L object)
  {
    super(new I(subjectName), propertyID, object);
  }

  public DPAA(@NonNull I subject, @NonNull String propertyID, @NonNull L object)
  {
    super(subject, propertyID, object);
  }

  public @NonNull I gets()
  {
    return getT1();
  }

  public @NonNull String getsid()
  {
    return getT1().getid();
  }

  public @NonNull String getpid()
  {
    return getT2();
  }

  public @NonNull L geto()
  {
    return getT3();
  }

  @Override public @NonNull OWLDataPropertyAssertionAxiom extract(@NonNull DroolsOWLAxiomExtractor converter)
    throws TargetSWRLRuleEngineException
  {
    return converter.extract(this);
  }

  @Override public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }

  @SideEffectFree @Override  public @NonNull String toString()
  {
    return "DPAA" + super.toString();
  }
}
