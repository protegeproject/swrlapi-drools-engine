package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL individual declaration.
 *
 * @see org.semanticweb.owlapi.model.OWLDeclarationAxiom
 */
public class IDA extends DA<I>
{
  private static final long serialVersionUID = 1L;

  public IDA(@NonNull String individualID)
  {
    super(new I(individualID));
  }

  public IDA(@NonNull I individual)
  {
    super(individual);
  }

  @NonNull public I getI()
  {
    return getE();
  }

  @NonNull @SideEffectFree @Override public String toString()
  {
    return "IDA(" + super.toString() + ")";
  }

  @NonNull @Override public OWLDeclarationAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }
}
