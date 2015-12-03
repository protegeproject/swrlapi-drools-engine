package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import dataflow.quals.SideEffectFree;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing a sub object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom
 */
public class SOPA extends DroolsBinaryObject<String, String> implements A
{
  private static final long serialVersionUID = 1L;

  public SOPA(@NonNull String property1ID, @NonNull String property2ID)
  {
    super(property1ID, property2ID);
  }

  @NonNull public String getsubpid()
  {
    return getT1();
  }

  @NonNull public String getsuperpid()
  {
    return getT2();
  }

  @NonNull @Override public OWLSubObjectPropertyOfAxiom extract(@NonNull DroolsOWLAxiomExtractor converter)
    throws TargetSWRLRuleEngineException
  {
    return converter.extract(this);
  }

  @Override public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "SOPA" + super.toString();
  }
}