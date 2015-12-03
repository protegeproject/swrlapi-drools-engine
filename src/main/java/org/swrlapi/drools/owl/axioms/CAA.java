package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import dataflow.quals.SideEffectFree;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL class assertion axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLClassAssertionAxiom
 */
public class CAA extends DroolsBinaryObject<String, I> implements A
{
  private static final long serialVersionUID = 1L;

  public CAA(@NonNull String classID, @NonNull I individual)
  {
    super(classID, individual);
  }

  public CAA(@NonNull String classID, @NonNull String individualID)
  {
    this(classID, new I(individualID));
  }

  @NonNull public String getcid()
  {
    return getT1();
  }

  @NonNull public I getI()
  {
    return getT2();
  }

  @NonNull public String getiid()
  {
    return getT2().getid();
  }

  @NonNull @Override public OWLClassAssertionAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
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
    return "CAA(" + getcid() + ", " + getI() + ")";
  }
}
