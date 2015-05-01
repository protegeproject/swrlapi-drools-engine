package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL symmetric object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom
 */
public class SPA extends DroolsUnaryPropertyAxiom
{
  private static final long serialVersionUID = 1L;

  public SPA(String propertyID)
  {
    super(propertyID);
  }

  @Override
  public String toString()
  {
    return "SPA" + super.toString();
  }

  @Override
  public OWLSymmetricObjectPropertyAxiom extract(DroolsOWLAxiomExtractor extractor)
      throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override
  public void visit(AVisitor visitor)
  {
    visitor.visit(this);
  }
}