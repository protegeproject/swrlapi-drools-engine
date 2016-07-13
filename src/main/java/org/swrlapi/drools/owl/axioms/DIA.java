package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL different individual axiom.
 * <p>
 * Rather than holding an arbitrary number of individuals, we hold only pairs and expect that the axiom translator
 * generates all possible pairs from an original OWL axiom.
 * <p>
 * We need to have 4 possible constructors for the different argument combinations. This approach makes the generated
 * Drools rules more readable.
 *
 * @see org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom
 */
public class DIA extends DroolsBinaryNamedIndividualsAxiom
{
  private static final long serialVersionUID = 1L;

  public DIA(@NonNull I individual1, @NonNull I individual2)
  {
    super(individual1, individual2);
  }

  public DIA(@NonNull String individual1ID, @NonNull String individual2ID)
  {
    this(new I(individual1ID), new I(individual2ID));
  }

  public DIA(@NonNull I individual1, @NonNull String individual2ID)
  {
    this(individual1, new I(individual2ID));
  }

  public DIA(@NonNull String individual1ID, @NonNull I individual2)
  {
    this(new I(individual1ID), individual2);
  }

  @NonNull @Override public OWLDifferentIndividualsAxiom extract(@NonNull DroolsOWLAxiomExtractor converter)
    throws TargetSWRLRuleEngineException
  {
    return converter.extract(this);
  }

  @Override public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "DIA" + super.toString();
  }
}
