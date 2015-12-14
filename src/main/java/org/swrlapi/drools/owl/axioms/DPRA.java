package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL data property range axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom
 */
public class DPRA extends DroolsBinaryObject<String, String> implements A
{
  private static final long serialVersionUID = 1L;

  public DPRA(@NonNull String propertyID, @NonNull String dataRangeID)
  {
    super(propertyID, dataRangeID);
  }

  @NonNull public String getpid()
  {
    return getT1();
  }

  @NonNull public String getrid()
  {
    return getT2();
  }

  @NonNull @Override public OWLDataPropertyRangeAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
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
    return "DPRA" + super.toString();
  }
}