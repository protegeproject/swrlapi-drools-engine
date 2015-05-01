package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl.core.L;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL negative data property assertion axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom
 */
public class NDPAA extends DroolsTernaryObject<I, String, L> implements A
{
  private static final long serialVersionUID = 1L;

  public NDPAA(String subjectName, String propertyID, L object)
  {
    super(new I(subjectName), propertyID, object);
  }

  public NDPAA(I subject, String propertyID, L object)
  {
    super(subject, propertyID, object);
  }

  public I gets()
  {
    return getT1();
  }

  public String getpid()
  {
    return getT2();
  }

  public L geto()
  {
    return getT3();
  }

  @Override
  public OWLNegativeDataPropertyAssertionAxiom extract(DroolsOWLAxiomExtractor converter)
      throws TargetSWRLRuleEngineException
  {
    return converter.extract(this);
  }

  @Override
  public void visit(AVisitor visitor)
  {
    visitor.visit(this);
  }

  @Override
  public String toString()
  {
    return "NDPAA" + super.toString();
  }
}
