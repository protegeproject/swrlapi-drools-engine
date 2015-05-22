package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an equivalent data property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom
 */
public class EDPA extends DroolsBinaryPropertiesAxiom
{
  private static final long serialVersionUID = 1L;

  public EDPA(@NonNull String property1ID, @NonNull String property2ID)
  {
    super(property1ID, property2ID);
  }

  @NonNull @Override
  public OWLEquivalentDataPropertiesAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
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
    return "EDPA" + super.toString();
  }
}
