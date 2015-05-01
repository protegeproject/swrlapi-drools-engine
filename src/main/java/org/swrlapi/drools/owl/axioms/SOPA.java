package org.swrlapi.drools.owl.axioms;

import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing a sub object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom
 */
public class SOPA extends DroolsBinaryObject<String, String> implements A
{
  private static final long serialVersionUID = 1L;

  public SOPA(String property1ID, String property2ID)
  {
    super(property1ID, property2ID);
  }

  public String getsubpid()
  {
    return getT1();
  }

  public String getsuperpid()
  {
    return getT2();
  }

  @Override
  public OWLSubObjectPropertyOfAxiom extract(DroolsOWLAxiomExtractor converter) throws TargetSWRLRuleEngineException
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
    return "SOPA" + super.toString();
  }
}