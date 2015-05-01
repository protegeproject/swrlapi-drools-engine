package org.swrlapi.drools.owl.axioms;

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

  public OPRA(String propertyID, String rangeClassID)
  {
    super(propertyID, rangeClassID);
  }

  public String getpid()
  {
    return getT1();
  }

  public String getrid()
  {
    return getT2();
  }

  @Override
  public OWLObjectPropertyRangeAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override
  public void visit(AVisitor visitor)
  {
    visitor.visit(this);
  }

  @Override
  public String toString()
  {
    return "OPRA(" + getpid() + ", " + getrid() + ")";
  }
}