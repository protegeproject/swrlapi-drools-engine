package org.swrlapi.drools.reasoner;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.swrlapi.drools.owl.axioms.A;
import org.swrlapi.drools.owl.literals.L;

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

  void addAssertOWLAxioms(@NonNull Set<@NonNull A> newAssertedOWLAxioms);

  void inferFalse(@NonNull String owl2RLRuleName, @NonNull String... arguments);

  boolean isInconsistent();

  // Axioms

  @NonNull Set<@NonNull A> getAssertedOWLAxioms();

  @NonNull Set<@NonNull A> getInferredOWLAxioms();

  boolean isEntailed(A a);

  boolean isEntailed(Set<? extends @NonNull A> axioms);

  // Classes

  boolean isDeclaredClass(@NonNull String classID);

  @NonNull Set<@NonNull String> getClassAssertions(@NonNull String classID);

  @NonNull Set<@NonNull String> getSubClasses(@NonNull String classID, boolean direct);

  @NonNull Set<@NonNull String> getSuperClasses(@NonNull String classID, boolean direct);

  @NonNull Set<@NonNull String> getDisjointClasses(@NonNull String classID);

  @NonNull Set<@NonNull String> getEquivalentClasses(@NonNull String classID);

  boolean strictSubClassOf(@NonNull String classID1, @NonNull String classID2);

  boolean directSubClassOf(@NonNull String classID1, @NonNull String classID2);

  // Individuals

  boolean isDeclaredIndividual(@NonNull String individualID);

  @NonNull Set<@NonNull String> getSameIndividual(@NonNull String individualID);

  @NonNull Set<@NonNull String> getDifferentIndividuals(@NonNull String individualID);

  // Object properties

  boolean isDeclaredObjectProperty(@NonNull String propertyID);

  @NonNull Set<@NonNull String> getSubObjectProperties(@NonNull String propertyID, boolean direct);

  @NonNull Set<@NonNull String> getSuperObjectProperties(@NonNull String propertyID, boolean direct);

  @NonNull Set<@NonNull String> getObjectPropertyRanges(@NonNull String propertyID, boolean direct);

  @NonNull Set<@NonNull String> getObjectPropertyDomains(@NonNull String propertyID, boolean direct);

  @NonNull Set<@NonNull String> getDisjointObjectProperties(@NonNull String propertyID);

  @NonNull Set<@NonNull String> getEquivalentObjectProperties(@NonNull String propertyID);

  @NonNull Set<@NonNull String> getInverseObjectProperties(@NonNull String propertyID);

  @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> getObjectPropertyAssertions(
      @NonNull String propertyID); // individualID -> Set<individualID>

  @NonNull Set<@NonNull String> getObjectPropertyValuesForIndividual(@NonNull String individualID,
      @NonNull String propertyID); // Set<individualID>

  boolean strictSubObjectPropertyOf(@NonNull String propertyID1, @NonNull String propertyID2);

  boolean directSubObjectPropertyOf(@NonNull String propertyID1, @NonNull String propertyID2);

  // Data properties

  boolean isDeclaredDataProperty(@NonNull String propertyID);

  @NonNull Set<@NonNull String> getSubDataProperties(@NonNull String propertyID, boolean direct);

  @NonNull Set<@NonNull String> getSuperDataProperties(@NonNull String propertyID, boolean direct);

  @NonNull Set<@NonNull String> getDataPropertyDomains(@NonNull String propertyID, boolean direct);

  @NonNull Set<@NonNull String> getDisjointDataProperties(@NonNull String propertyID);

  @NonNull Set<@NonNull String> getEquivalentDataProperties(@NonNull String propertyID);

  @NonNull Map<@NonNull String, @NonNull Set<@NonNull L>> getDataPropertyAssertions(
      @NonNull String propertyID); // individualID -> Set<L>

  @NonNull Set<@NonNull L> getDataPropertyValuesForIndividual(@NonNull String individualID, @NonNull String propertyID);

  boolean strictSubDataPropertyOf(@NonNull String propertyID1, @NonNull String propertyID2);

  boolean directSubDataPropertyOf(@NonNull String propertyID1, @NonNull String propertyID2);

  // Annotation properties

  boolean isDeclaredAnnotation(@NonNull String propertyID);
}
