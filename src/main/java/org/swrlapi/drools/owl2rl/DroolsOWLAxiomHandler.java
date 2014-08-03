package org.swrlapi.drools.owl2rl;

import org.swrlapi.drools.owl.axioms.A;
import org.swrlapi.drools.owl.core.L;

import java.util.Map;
import java.util.Set;

/**
 * Keeps track of OWL axioms that are inferred during rule execution.
 *
 * @see org.swrlapi.drools.owl2rl.DroolsOWL2RLEngine
 * @see org.swrlapi.drools.owl2rl.DroolsOWL2RLRules
 * @see org.swrlapi.drools.reasoner.DroolsOWLReasoner
 */
public interface DroolsOWLAxiomHandler
{
	boolean isInconsistent();

	boolean isEntailed(A a);

	boolean isEntailed(Set<? extends A> axioms);

	Set<A> getInferredOWLAxioms();

	Set<A> getAssertedOWLAxioms();

	boolean isDeclaredClass(String classID);

	boolean isDeclaredIndividual(String individualID);

	boolean isDeclaredObjectProperty(String propertyID);

	boolean isDeclaredDataProperty(String propertyID);

	boolean isDeclaredAnnotation(String propertyID);

	Set<String> getClassAssertions(String classID);

	Set<String> getSubClasses(String classID);

	Set<String> getSuperClasses(String classID);

	Set<String> getDisjointClasses(String classID);

	Set<String> getEquivalentClasses(String classID);

	Set<String> getSameIndividual(String individualID);

	Set<String> getDifferentIndividuals(String individualID);

	Set<String> getSubObjectProperties(String propertyID);

	Set<String> getObjectPropertyRanges(String propertyID);

	Set<String> getObjectPropertyDomains(String propertyID);

	Set<String> getDisjointObjectProperties(String propertyID);

	Set<String> getEquivalentObjectProperties(String propertyID);

	Set<String> getInverseObjectProperties(String propertyID);

	Map<String, Set<String>> getObjectPropertyAssertions(String propertyID);

	Set<String> getSubDataProperties(String propertyID);

	Set<String> getSuperDataProperties(String propertyID);

	Set<String> getDataPropertyDomains(String propertyID);

	Set<String> getDisjointDataProperties(String propertyID);

	Set<String> getEquivalentDataProperties(String propertyID);

	Map<String, Set<L>> getDataPropertyAssertions(String propertyID);
}
