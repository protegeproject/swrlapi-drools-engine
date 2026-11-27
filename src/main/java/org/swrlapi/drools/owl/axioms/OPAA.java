package org.swrlapi.drools.owl.axioms;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL object property assertion axiom.
 * <p>
 * We have 4 possible constructors for the different argument combinations. This approach provides more flexibility when
 * generating Drools rules and makes the generated rules more readable.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom
 */
public class OPAA extends DroolsTernaryObject<I, String, I> implements A
{
  private static final long serialVersionUID = 1L;

  public OPAA(@NonNull I subject, @NonNull String propertyID, @NonNull I object)
  {
    super(subject, propertyID, object);
  }

  public OPAA(@NonNull I subject, @NonNull String propertyID, @NonNull String objectName)
  {
    super(subject, propertyID, new I(objectName));
  }

  public OPAA(@NonNull String subjectName, @NonNull String propertyID, @NonNull I object)
  {
    super(new I(subjectName), propertyID, object);
  }

  public OPAA(@NonNull String subjectName, @NonNull String propertyID, @NonNull String objectName)
  {
    super(new I(subjectName), propertyID, new I(objectName));
  }

  public @NonNull I gets()
  {
    return getT1();
  }

  public @NonNull String getsid()
  {
    return getT1().getid();
  }

  public @NonNull String getpid()
  {
    return getT2();
  }

  public @NonNull I geto()
  {
    return getT3();
  }

  public @NonNull String getoid()
  {
    return getT3().getid();
  }

  @Override public @NonNull OWLObjectPropertyAssertionAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override public void visit(@NonNull AVisitor visitor)
  {
    visitor.visit(this);
  }

  @SideEffectFree @Override public @NonNull String toString()
  {
    return "OPAA" + super.toString();
  }
}
