package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing a sub data property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom
 */
public class SDPA extends DroolsBinaryObject<String, String> implements A
{
  private static final long serialVersionUID = 1L;

  public SDPA(@NonNull String property1ID, @NonNull String property2ID)
  {
    super(property1ID, property2ID);
  }

  @NonNull public String getsubpid()
  {
    return getT1();
  }

  @NonNull public String getsuperpid()
  {
    return getT2();
  }

  @NonNull @Override
  public String toString()
  {
    return "SDPA" + super.toString();
  }

  @NonNull @Override
  public OWLSubDataPropertyOfAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override
  public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }

}
