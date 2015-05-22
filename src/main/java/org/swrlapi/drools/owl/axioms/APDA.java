package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL annotation property declaration.
 *
 * @see org.semanticweb.owlapi.model.OWLDeclarationAxiom
 */
public class APDA extends DroolsUnaryPropertyAxiom
{
  private static final long serialVersionUID = 1L;

  public APDA(@NonNull String propertyID)
  {
    super(propertyID);
  }

  @NonNull @Override
  public String toString()
  {
    return "APDA(" + super.toString() + ")";
  }

  @NonNull @Override
  public OWLDeclarationAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override
  public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }
}
