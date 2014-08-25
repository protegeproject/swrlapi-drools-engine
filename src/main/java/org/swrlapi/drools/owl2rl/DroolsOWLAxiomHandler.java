package org.swrlapi.drools.owl2rl;

import org.swrlapi.drools.owl.axioms.A;
import org.swrlapi.drools.owl.core.L;

import java.util.Map;
import java.util.Set;

/**
 * Keeps track of OWL axioms that are inferred during rule execution. Also contains the initial asserted OWL axioms.
 * <p>
 * Used by a {@link org.swrlapi.drools.reasoner.DroolsOWLReasoner}.
 *
 * @see org.swrlapi.drools.reasoner.DroolsOWLReasoner
 * @see org.swrlapi.drools.owl2rl.DroolsOWL2RLEngine
 * @see org.swrlapi.drools.owl2rl.DroolsOWL2RLRules
 */
public interface DroolsOWLAxiomHandler
{
	boolean isInconsistent();

	// Axioms

	Set<A> getAssertedOWLAxioms();

	Set<A> getInferredOWLAxioms();

	boolean isEntailed(A a);

	boolean isEntailed(Set<? extends A> axioms);

	// Classes

	boolean isDeclaredClass(String classID);

	Set<String> getClassAssertions(String classID);

	Set<String> getSubClasses(String classID, boolean direct);

	Set<String> getSuperClasses(String classID, boolean direct);

	Set<String> getDisjointClasses(String classID);

	Set<String> getEquivalentClasses(String classID);

	boolean strictSubClassOf(String classID1, String classID2);

	boolean directSubClassOf(String classID1, String classID2);

	// Individuals

	boolean isDeclaredIndividual(String individualID);

	Set<String> getSameIndividual(String individualID);

	Set<String> getDifferentIndividuals(String individualID);

	// Object properties

	boolean isDeclaredObjectProperty(String propertyID);

	Set<String> getSubObjectProperties(String propertyID, boolean direct);

	Set<String> getSuperObjectProperties(String propertyID, boolean direct);

	Set<String> getObjectPropertyRanges(String propertyID, boolean direct);

	Set<String> getObjectPropertyDomains(String propertyID, boolean direct);

	Set<String> getDisjointObjectProperties(String propertyID);

	Set<String> getEquivalentObjectProperties(String propertyID);

	Set<String> getInverseObjectProperties(String propertyID);

	Map<String, Set<String>> getObjectPropertyAssertions(String propertyID); // individualID -> Set<individualID>

	Set<String> getObjectPropertyValuesForIndividual(String individualID, String propertyID); // Set<individualID>

	boolean strictSubObjectPropertyOf(String propertyID1, String propertyID2);

	boolean directSubObjectPropertyOf(String propertyID1, String propertyID2);

	// Data properties

	boolean isDeclaredDataProperty(String propertyID);

	Set<String> getSubDataProperties(String propertyID, boolean direct);

	Set<String> getSuperDataProperties(String propertyID, boolean direct);

	Set<String> getDataPropertyDomains(String propertyID, boolean direct);

	Set<String> getDisjointDataProperties(String propertyID);

	Set<String> getEquivalentDataProperties(String propertyID);

	Map<String, Set<L>> getDataPropertyAssertions(String propertyID); // individualID -> Set<L>

	Set<L> getDataPropertyValuesForIndividual(String individualID, String propertyID);

	boolean strictSubDataPropertyOf(String propertyID1, String propertyID2);

	boolean directSubDataPropertyOf(String propertyID1, String propertyID2);

	// Annotation properties

	boolean isDeclaredAnnotation(String propertyID);
}
