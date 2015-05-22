package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL irreflexive object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom
 */
public class IROPA extends DroolsUnaryPropertyAxiom
{
  private static final long serialVersionUID = 1L;

  public IROPA(@NonNull String propertyID)
  {
    super(propertyID);
  }

  @NonNull @Override
  public OWLIrreflexiveObjectPropertyAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
      throws TargetSWRLRuleEngineException
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
    return "IROPA" + super.toString();
  }
}