package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL range object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom
 */
public class OPRA extends DroolsBinaryObject<String, String> implements A
{
  private static final long serialVersionUID = 1L;

  public OPRA(@NonNull String propertyID, @NonNull String rangeClassID)
  {
    super(propertyID, rangeClassID);
  }

  @NonNull   public String getpid()
  {
    return getT1();
  }

  @NonNull public String getrid()
  {
    return getT2();
  }

  @NonNull @Override
  public OWLObjectPropertyRangeAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override
  public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }

  @NonNull @Override
  public String toString()
  {
    return "OPRA(" + getpid() + ", " + getrid() + ")";
  }
}