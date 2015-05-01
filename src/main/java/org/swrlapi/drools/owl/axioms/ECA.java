package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL equivalent classes axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom
 */
public class ECA extends DroolsBinaryClassesAxiom
{
  private static final long serialVersionUID = 1L;

  public ECA(String class1ID, String class2ID)
  {
    super(class1ID, class2ID);
  }

  @Override
  public OWLEquivalentClassesAxiom extract(DroolsOWLAxiomExtractor extractor) throws TargetSWRLRuleEngineException
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
    return "ECA" + super.toString();
  }
}