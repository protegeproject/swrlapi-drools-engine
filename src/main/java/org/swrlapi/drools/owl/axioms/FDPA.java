package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 *
 * @see org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom
 */
public class FDPA extends DroolsUnaryPropertyAxiom
{
  private static final long serialVersionUID = 1L;

  public FDPA(@NonNull String propertyID)
  {
    super(propertyID);
  }

  @NonNull @Override
  public OWLFunctionalDataPropertyAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor) throws TargetSWRLRuleEngineException
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
    return "FDPA" + super.toString();
  }
}