package org.swrlapi.drools.owl2rl;

import java.util.*;

import org.drools.runtime.StatefulKnowledgeSession;
import org.swrlapi.drools.owl.axioms.*;
import org.swrlapi.owl2rl.OWL2RLInconsistency;

/**
 * Keeps track of OWL axioms that are inferred during rule execution. All Drools rules generated from SWRL rules and
 * the OWL 2 RL rules defined in {@link org.swrlapi.drools.owl2rl.DroolsOWL2RLRules} use an instance of this class
 * to assert inferred axioms.
 * </p>
 * This {@link #infer(org.swrlapi.drools.owl.axioms.A...)} method in this class is called during rule execution. It
 * keeps track of the inferred axioms and also inserts them in to a Drools knowledge session.
 *
 * @see org.swrlapi.drools.owl2rl.DroolsOWL2RLEngine
 */
public class DroolsOWLAxiomHandler implements DroolsOWL2RLAxiomVisitor
{
	private final Set<A> inferredOWLAxioms;
	private final Set<A> assertedOWLAxioms;
	private final Set<String> inconsistentMessages;
	private boolean isInconsistent;

	private StatefulKnowledgeSession knowledgeSession;

	public DroolsOWLAxiomHandler()
	{
		this.inferredOWLAxioms = new HashSet<A>();
		this.assertedOWLAxioms = new HashSet<A>();
		this.isInconsistent = false;
		this.inconsistentMessages = new HashSet<String>();
	}

	public void reset(StatefulKnowledgeSession knowledgeSession)
	{
		this.assertedOWLAxioms.clear();
		this.inferredOWLAxioms.clear();
		this.knowledgeSession = knowledgeSession;
		this.isInconsistent = false;
		this.inconsistentMessages.clear();
	}

	/**
	 * Supply the asserted OWL axioms.
	 */
	public void assertOWLAxioms(Set<A> newAssertedOWLAxioms)
	{
		this.assertedOWLAxioms.addAll(newAssertedOWLAxioms);

		for (A a : newAssertedOWLAxioms)
		  a.visit(this);
	}

	/**
	 * This method is called by Drools rules at runtime.
	 */
	public void infer(A... newInferredOWLAxioms)
	{
		if (this.knowledgeSession == null)
			throw new RuntimeException("internal error: knowledge session not initialized in axiom inferrer");

		for (A newInferredOWLAxiom : newInferredOWLAxioms) {
			if (!this.inferredOWLAxioms.contains(newInferredOWLAxiom)
					&& (!this.assertedOWLAxioms.contains(newInferredOWLAxiom))) {
				this.inferredOWLAxioms.add(newInferredOWLAxiom);
				this.knowledgeSession.insert(newInferredOWLAxiom);

				newInferredOWLAxiom.visit(this);
			}
		}
	}

	/**
	 * This method can be called after the rule engine has finished executing to see if an inconsistency was detected.
	 * been inferred.
	 */
	public boolean isInconsistent() { return this.isInconsistent; }

	/**
	 * This method can be called after the rule engine has finished executing to get all the OWL axioms that have
	 * been inferred.
	 */
	public Set<A> getInferredOWLAxioms()
	{
		return Collections.unmodifiableSet(this.inferredOWLAxioms);
	}

	public Set<String> getInconsistentMessages()
	{
		return Collections.unmodifiableSet(this.inconsistentMessages);
	}

	/**
	 * This method is called by an OWL 2 RL inconsistency detection rule when an inconsistency is detected.
	 * The parameters contains details of the offending rule and the OWL entities involved in the detected inconsistency.
	 */
	public void inferFalse(String owl2RLRuleName, String... arguments)
	{
		String inconsistentMessage = "OWL 2 RL rule detected an inconsistency in the ontology.\n "
				+ "See http://www.w3.org/TR/owl-profiles/#Reasoning_in_OWL_2_RL_and_RDF_Graphs_using_Rules for a list of inconsistency detection rules.\n"
				+ "Rule that detected an inconsistency: " + owl2RLRuleName;
		Iterator<String> argumentsIterator = Arrays.asList(arguments).iterator();

		if (OWL2RLInconsistency.hasInconsistencyRuleArgumentsDescription(owl2RLRuleName)) {
			OWL2RLInconsistency.OWL2RLRuleArguments ruleArguments = OWL2RLInconsistency.getRuleArguments(owl2RLRuleName);

			if (ruleArguments.hasClassArguments()) {
				inconsistentMessage += "\n Classes:";
				for (int argumentCount = 0; (argumentCount < ruleArguments.getNumberOfClassArguments())
						&& argumentsIterator.hasNext(); argumentCount++) {
					inconsistentMessage += " " + argumentsIterator.next();
				}
			}

			if (ruleArguments.hasIndividualArguments()) {
				inconsistentMessage += "\n Individuals:";
				for (int argumentCount = 0; (argumentCount < ruleArguments.getNumberOfIndividualArguments())
						&& argumentsIterator.hasNext(); argumentCount++) {
					inconsistentMessage += " " + argumentsIterator.next();
				}
			}

			if (ruleArguments.hasObjectPropertyArguments()) {
				inconsistentMessage += "\n Object Properties:";
				for (int argumentCount = 0; (argumentCount < ruleArguments.getNumberOfObjectPropertyArguments())
						&& argumentsIterator.hasNext(); argumentCount++) {
					inconsistentMessage += " " + argumentsIterator.next();
				}
			}

			if (ruleArguments.hasDataPropertyArguments()) {
				inconsistentMessage += "\n Data Properties:";
				for (int argumentCount = 0; (argumentCount < ruleArguments.getNumberOfObjectPropertyArguments())
						&& argumentsIterator.hasNext(); argumentCount++) {
					inconsistentMessage += " " + argumentsIterator.next();
				}
			}
			this.isInconsistent = true;
		}
		this.inconsistentMessages.add(inconsistentMessage);
	}

	@Override public void visit(CDA cda)
	{

	}

	@Override public void visit(OPDA cda)
	{

	}

	@Override public void visit(DPDA cda)
	{

	}

	@Override public void visit(APDA cda)
	{

	}

	@Override public void visit(IDA cda)
	{

	}

	@Override public void visit(SCA sca)
	{

	}

	@Override public void visit(NOPAA nopa)
	{

	}

	@Override public void visit(AOPA aopa)
	{

	}

	@Override public void visit(DCA dca)
	{

	}

	@Override public void visit(DDPA ddpa)
	{

	}

	@Override public void visit(DOPA dopa)
	{

	}

	@Override public void visit(EOPA eopa)
	{

	}

	@Override public void visit(NDPAA ndpaa)
	{

	}

	@Override public void visit(DIA dia)
	{

	}

	@Override public void visit(DJDPA djdpa)
	{

	}

	@Override public void visit(DJOPA djopa)
	{

	}

	@Override public void visit(OPRA opra)
	{

	}

	@Override public void visit(OPAA opaa)
	{

	}

	@Override public void visit(FOPA fopa)
	{

	}

	@Override public void visit(SOPA sopa)
	{

	}

	@Override public void visit(SPA spa)
	{

	}

	@Override public void visit(DPRA dpra)
	{

	}

	@Override public void visit(FDPA fdpa)
	{

	}

	@Override public void visit(EDPA edpa)
	{

	}

	@Override public void visit(CAA caa)
	{

	}

	@Override public void visit(ECA eca)
	{

	}

	@Override public void visit(DPAA dpaa)
	{

	}

	@Override public void visit(TOPA TOPA)
	{

	}

	@Override public void visit(IROPA iropa)
	{

	}

	@Override public void visit(SDPA sdpa)
	{

	}

	@Override public void visit(IFOPA IFOPA)
	{

	}

	@Override public void visit(SIA sia)
	{

	}

	@Override public void visit(IOPA iopa)
	{

	}
}
