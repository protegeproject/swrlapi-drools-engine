package org.swrlapi.drools.owl.axioms;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL negative object property assertion axiom in Drools.
 * <p>
 * We have 4 possible constructors for the different argument combinations. This approach provides more flexibility when
 * generating Drools rules and makes the generated rules more readable.
 *
 * @see org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom
 */
public class NOPAA extends DroolsTernaryObject<I, String, I> implements A
{
  private static final long serialVersionUID = 1L;

  public NOPAA(@NonNull I subject, @NonNull String propertyID, @NonNull I object)
  {
    super(subject, propertyID, object);
  }

  public NOPAA(@NonNull I subject, @NonNull String propertyID, @NonNull String objectName)
  {
    this(subject, propertyID, new I(objectName));
  }

  public NOPAA(@NonNull String subjectName, @NonNull String propertyID, @NonNull I object)
  {
    this(new I(subjectName), propertyID, object);
  }

  public NOPAA(@NonNull String subjectName, @NonNull String propertyID, @NonNull String objectName)
  {
    this(new I(subjectName), propertyID, new I(objectName));
  }

  @NonNull public I gets()
  {
    return getT1();
  }

  @NonNull public String getpid()
  {
    return getT2();
  }

  @NonNull public I geto()
  {
    return getT3();
  }

  @NonNull @Override
  public OWLNegativeObjectPropertyAssertionAxiom extract(@NonNull DroolsOWLAxiomExtractor extractor)
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
    return "NOPAA" + super.toString();
  }
}
