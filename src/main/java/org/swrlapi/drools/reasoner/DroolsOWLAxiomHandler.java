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

  @NonNull Set<String> getSubClasses(@NonNull String classID, boolean direct);

  @NonNull Set<String> getSuperClasses(@NonNull String classID, boolean direct);

  @NonNull Set<String> getDisjointClasses(@NonNull String classID);

  @NonNull Set<String> getEquivalentClasses(@NonNull String classID);

  boolean strictSubClassOf(@NonNull String classID1, @NonNull String classID2);

  boolean directSubClassOf(@NonNull String classID1, @NonNull String classID2);

  // Individuals

  boolean isDeclaredIndividual(@NonNull String individualID);

  @NonNull Set<String> getSameIndividual(@NonNull String individualID);

  @NonNull Set<String> getDifferentIndividuals(@NonNull String individualID);

  // Object properties

  boolean isDeclaredObjectProperty(@NonNull String propertyID);

  @NonNull Set<String> getSubObjectProperties(@NonNull String propertyID, boolean direct);

  @NonNull Set<String> getSuperObjectProperties(@NonNull String propertyID, boolean direct);

  @NonNull Set<String> getObjectPropertyRanges(@NonNull String propertyID, boolean direct);

  @NonNull Set<String> getObjectPropertyDomains(@NonNull String propertyID, boolean direct);

  @NonNull Set<String> getDisjointObjectProperties(@NonNull String propertyID);

  @NonNull Set<String> getEquivalentObjectProperties(@NonNull String propertyID);

  @NonNull Set<String> getInverseObjectProperties(@NonNull String propertyID);

  @NonNull Map<String, Set<String>> getObjectPropertyAssertions(
    @NonNull String propertyID); // individualID -> Set<individualID>

  @NonNull Set<String> getObjectPropertyValuesForIndividual(@NonNull String individualID,
    @NonNull String propertyID); // Set<individualID>

  boolean strictSubObjectPropertyOf(@NonNull String propertyID1, @NonNull String propertyID2);

  boolean directSubObjectPropertyOf(@NonNull String propertyID1, @NonNull String propertyID2);

  // Data properties

  boolean isDeclaredDataProperty(@NonNull String propertyID);

  @NonNull Set<String> getSubDataProperties(@NonNull String propertyID, boolean direct);

  @NonNull Set<String> getSuperDataProperties(@NonNull String propertyID, boolean direct);

  @NonNull Set<String> getDataPropertyDomains(@NonNull String propertyID, boolean direct);

  @NonNull Set<String> getDisjointDataProperties(@NonNull String propertyID);

  @NonNull Set<String> getEquivalentDataProperties(@NonNull String propertyID);

  @NonNull Map<String, Set<L>> getDataPropertyAssertions(@NonNull String propertyID); // individualID -> Set<L>

  @NonNull Set<L> getDataPropertyValuesForIndividual(@NonNull String individualID, @NonNull String propertyID);

  boolean strictSubDataPropertyOf(@NonNull String propertyID1, @NonNull String propertyID2);

  boolean directSubDataPropertyOf(@NonNull String propertyID1, @NonNull String propertyID2);

  // Annotation properties

  boolean isDeclaredAnnotation(@NonNull String propertyID);
}
