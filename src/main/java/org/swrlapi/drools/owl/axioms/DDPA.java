package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL domain data property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom
 */
public class DDPA extends DroolsBinaryObject<String, String> implements A
{
  private static final long serialVersionUID = 1L;

  public DDPA(@NonNull String propertyID, @NonNull String domainID)
  {
    super(propertyID, domainID);
  }

  public @NonNull String getpid()
  {
    return getT1();
  }

  public @NonNull String getdid()
  {
    return getT2();
  }

  @Override public @NonNull OWLDataPropertyDomainAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }

  @SideEffectFree @Override public @NonNull String toString()
  {
    return "DDPA(" + getpid() + ", " + getdid() + ")";
  }
}