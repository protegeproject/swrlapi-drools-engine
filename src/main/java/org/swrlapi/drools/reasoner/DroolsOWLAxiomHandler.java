package org.swrlapi.drools.reasoner;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.axioms.A;
import org.swrlapi.drools.owl.core.L;

import java.util.Map;
import java.util.Set;

/**
 * Keeps track of OWL axioms that are inferred during by a Drools-based OWL reasoner. Also contains the initial asserted
 * OWL axioms.
 *
 * @see org.swrlapi.drools.reasoner.DroolsOWLReasoner
 * @see org.swrlapi.drools.owl2rl.DroolsOWL2RLEngine
 * @see org.swrlapi.drools.owl2rl.DroolsOWL2RLRules
 */
public interface DroolsOWLAxiomHandler
{
  void infer(@NonNull A... newInferredOWLAxioms);

  void addAssertOWLAxioms(@NonNull Set<A> newAssertedOWLAxioms);

  void inferFalse(@NonNull String owl2RLRuleName, @NonNull String... arguments);

  boolean isInconsistent();

  // Axioms

  @NonNull Set<A> getAssertedOWLAxioms();

  @NonNull Set<A> getInferredOWLAxioms();

  boolean isEntailed(A a);

  boolean isEntailed(Set<? extends A> axioms);

  // Classes

  boolean isDeclaredClass(@NonNull String classID);

  @NonNull Set<String> getClassAssertions(@NonNull String classID);

  @NonNull Set<String> getSubClasses(String classID, boolean direct);

  @NonNull Set<String> getSuperClasses(String classID, boolean direct);

  @NonNull Set<String> getDisjointClasses(String classID);

  @NonNull Set<String> getEquivalentClasses(String classID);

  boolean strictSubClassOf(String classID1, String classID2);

  boolean directSubClassOf(String classID1, String classID2);

  // Individuals

  boolean isDeclaredIndividual(String individualID);

  @NonNull Set<String> getSameIndividual(String individualID);

  @NonNull Set<String> getDifferentIndividuals(String individualID);

  // Object properties

  boolean isDeclaredObjectProperty(String propertyID);

  @NonNull Set<String> getSubObjectProperties(String propertyID, boolean direct);

  @NonNull Set<String> getSuperObjectProperties(String propertyID, boolean direct);

  @NonNull Set<String> getObjectPropertyRanges(String propertyID, boolean direct);

  @NonNull Set<String> getObjectPropertyDomains(String propertyID, boolean direct);

  @NonNull Set<String> getDisjointObjectProperties(String propertyID);

  @NonNull Set<String> getEquivalentObjectProperties(String propertyID);

  @NonNull Set<String> getInverseObjectProperties(String propertyID);

  @NonNull Map<String, Set<String>> getObjectPropertyAssertions(String propertyID); // individualID -> Set<individualID>

  @NonNull Set<String> getObjectPropertyValuesForIndividual(String individualID, String propertyID); // Set<individualID>

  boolean strictSubObjectPropertyOf(String propertyID1, String propertyID2);

  boolean directSubObjectPropertyOf(String propertyID1, String propertyID2);

  // Data properties

  boolean isDeclaredDataProperty(String propertyID);

  @NonNull Set<String> getSubDataProperties(String propertyID, boolean direct);

  @NonNull Set<String> getSuperDataProperties(String propertyID, boolean direct);

  @NonNull Set<String> getDataPropertyDomains(String propertyID, boolean direct);

  @NonNull Set<String> getDisjointDataProperties(String propertyID);

  @NonNull Set<String> getEquivalentDataProperties(String propertyID);

  @NonNull Map<String, Set<L>> getDataPropertyAssertions(String propertyID); // individualID -> Set<L>

  @NonNull Set<L> getDataPropertyValuesForIndividual(String individualID, String propertyID);

  boolean strictSubDataPropertyOf(String propertyID1, String propertyID2);

  boolean directSubDataPropertyOf(String propertyID1, String propertyID2);

  // Annotation properties

  boolean isDeclaredAnnotation(@NonNull String propertyID);
}
