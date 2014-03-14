package org.swrlapi.drools.owl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.drools.runtime.StatefulKnowledgeSession;
import org.swrlapi.drools.owl.axioms.A;
import org.swrlapi.owl2rl.OWL2RLEngine;

/**
 * This infer() method in this class is called to inform Drools of inferred OWL axioms during rule execution.
 */
public class DroolsOWLAxiomInferrer
{
	private final OWL2RLEngine owl2RLEngine;

	private final Set<A> inferredOWLAxioms;
	private Set<A> assertedOWLAxioms;

	private StatefulKnowledgeSession knowledgeSession;

	public DroolsOWLAxiomInferrer(OWL2RLEngine owl2RLEngine)
	{
		this.inferredOWLAxioms = new HashSet<A>();
		this.assertedOWLAxioms = new HashSet<A>();
		this.owl2RLEngine = owl2RLEngine;
	}

	public void reset(StatefulKnowledgeSession knowledgeSession)
	{
		this.assertedOWLAxioms.clear();
		this.inferredOWLAxioms.clear();
		this.knowledgeSession = knowledgeSession;
	}

	public void setAssertedOWLAxioms(Set<A> newAssertedOWLAxioms)
	{
		this.assertedOWLAxioms = newAssertedOWLAxioms;
	}

	public void infer(A... newInferredOWLAxioms)
	{
		if (this.knowledgeSession == null)
			throw new RuntimeException("internal error: knowledge session not initialized in axiom inferrer");

		for (A newInferredOWLAxiom : newInferredOWLAxioms) {
			if (!this.inferredOWLAxioms.contains(newInferredOWLAxiom)
					&& (!this.assertedOWLAxioms.contains(newInferredOWLAxiom))) {
				this.inferredOWLAxioms.add(newInferredOWLAxiom);
				this.knowledgeSession.insert(newInferredOWLAxiom);
			}
		}
	}

	public Set<A> getInferredOWLAxioms()
	{
		return this.inferredOWLAxioms;
	}

	/**
	 * This method is called by an OWL 2 RL inconsistency detection rule when an inconsistency is detected. It throws and
	 * exception that halts the inference process. The exception contains details of the offending rule and the OWL
	 * entities involved in the detected inconsistency.
	 */
	public void inferFalse(String owl2RLRuleName, String... arguments)
	{
		String inconsistentErrorMessage = "OWL 2 RL rule detected an inconsistency in the ontology.\n "
				+ "See http://www.w3.org/TR/owl-profiles/#Reasoning_in_OWL_2_RL_and_RDF_Graphs_using_Rules for a list of inconsistency detection rules.\n"
				+ "Rule that detected an inconsistency: " + owl2RLRuleName;
		Iterator<String> argumentsIterator = Arrays.asList(arguments).iterator();

		if (this.owl2RLEngine.hasFalseArgumentsDescription(owl2RLRuleName)) {
			OWL2RLEngine.OWL2RLFalseArgumentsDescription argumentsDescription = this.owl2RLEngine
					.getFalseArgumentsDescription(owl2RLRuleName);

			if (argumentsDescription.hasClassArguments()) {
				inconsistentErrorMessage += "\n Classes:";
				for (int argumentCount = 0; (argumentCount < argumentsDescription.getNumberOfClassArguments())
						&& argumentsIterator.hasNext(); argumentCount++) {
					inconsistentErrorMessage += " " + argumentsIterator.next().toString();
				}
			}

			if (argumentsDescription.hasIndividualArguments()) {
				inconsistentErrorMessage += "\n Individuals:";
				for (int argumentCount = 0; (argumentCount < argumentsDescription.getNumberOfIndividualArguments())
						&& argumentsIterator.hasNext(); argumentCount++) {
					inconsistentErrorMessage += " " + argumentsIterator.next().toString();
				}
			}

			if (argumentsDescription.hasObjectPropertyArguments()) {
				inconsistentErrorMessage += "\n Object Properties:";
				for (int argumentCount = 0; (argumentCount < argumentsDescription.getNumberOfObjectPropertyArguments())
						&& argumentsIterator.hasNext(); argumentCount++) {
					inconsistentErrorMessage += " " + argumentsIterator.next().toString();
				}
			}

			if (argumentsDescription.hasDataPropertyArguments()) {
				inconsistentErrorMessage += "\n Data Properties:";
				for (int argumentCount = 0; (argumentCount < argumentsDescription.getNumberOfObjectPropertyArguments())
						&& argumentsIterator.hasNext(); argumentCount++) {
					inconsistentErrorMessage += " " + argumentsIterator.next().toString();
				}
			}
		}

		throw new RuntimeException(inconsistentErrorMessage);
	}
}
