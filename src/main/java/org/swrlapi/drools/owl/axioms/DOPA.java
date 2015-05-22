package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * Class representing an OWL domain object property axiom in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom
 */
public class DOPA extends DroolsBinaryObject<String, String> implements A
{
  private static final long serialVersionUID = 1L;

  public DOPA(@NonNull String propertyID, @NonNull String domainClassID)
  {
    super(propertyID, domainClassID);
  }

  public String getpid()
  {
    return getT1();
  }

  public String getdid()
  {
    return getT2();
  }

  @NonNull @Override
  public OWLObjectPropertyDomainAxiom extract(@NonNull DroolsOWLAxiomExtractor converter) throws TargetSWRLRuleEngineException
  {
    return converter.extract(this);
  }

  @Override
  public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }

  @NonNull @Override
  public String toString()
  {
    return "DOPA" + super.toString();
  }
}