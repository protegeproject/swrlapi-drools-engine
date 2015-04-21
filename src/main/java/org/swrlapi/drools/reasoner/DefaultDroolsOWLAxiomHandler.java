package org.swrlapi.drools.reasoner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.drools.runtime.StatefulKnowledgeSession;
import org.swrlapi.drools.owl.axioms.A;
import org.swrlapi.drools.owl.axioms.AOPA;
import org.swrlapi.drools.owl.axioms.APDA;
import org.swrlapi.drools.owl.axioms.AVisitor;
import org.swrlapi.drools.owl.axioms.CAA;
import org.swrlapi.drools.owl.axioms.CDA;
import org.swrlapi.drools.owl.axioms.DCA;
import org.swrlapi.drools.owl.axioms.DDPA;
import org.swrlapi.drools.owl.axioms.DIA;
import org.swrlapi.drools.owl.axioms.DJDPA;
import org.swrlapi.drools.owl.axioms.DJOPA;
import org.swrlapi.drools.owl.axioms.DOPA;
import org.swrlapi.drools.owl.axioms.DPAA;
import org.swrlapi.drools.owl.axioms.DPDA;
import org.swrlapi.drools.owl.axioms.DPRA;
import org.swrlapi.drools.owl.axioms.ECA;
import org.swrlapi.drools.owl.axioms.EDPA;
import org.swrlapi.drools.owl.axioms.EOPA;
import org.swrlapi.drools.owl.axioms.FDPA;
import org.swrlapi.drools.owl.axioms.FOPA;
import org.swrlapi.drools.owl.axioms.IDA;
import org.swrlapi.drools.owl.axioms.IFOPA;
import org.swrlapi.drools.owl.axioms.IOPA;
import org.swrlapi.drools.owl.axioms.IROPA;
import org.swrlapi.drools.owl.axioms.NDPAA;
import org.swrlapi.drools.owl.axioms.NOPAA;
import org.swrlapi.drools.owl.axioms.OPAA;
import org.swrlapi.drools.owl.axioms.OPDA;
import org.swrlapi.drools.owl.axioms.OPRA;
import org.swrlapi.drools.owl.axioms.SCA;
import org.swrlapi.drools.owl.axioms.SDPA;
import org.swrlapi.drools.owl.axioms.SIA;
import org.swrlapi.drools.owl.axioms.SOPA;
import org.swrlapi.drools.owl.axioms.SPA;
import org.swrlapi.drools.owl.axioms.TOPA;
import org.swrlapi.drools.owl.core.L;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;
import org.swrlapi.owl2rl.OWL2RLInconsistency;

/**
 * This class is used to accumulate inferred OWL axioms during reasoning and rule execution. Drools rules generated from
 * SWRL rules and a reasoner (e.g., the OWL 2 RL rules defined in {@link org.swrlapi.drools.owl2rl.DroolsOWL2RLRules})
 * use a single instance of this class.
 * <p>
 * This {@link #infer(org.swrlapi.drools.owl.axioms.A...)} method in this class is called during reasoning and rule
 * execution. It keeps track of the inferred axioms and associated knowledge and also inserts the axioms in to a Drools
 * knowledge session.
 *
 * @see org.swrlapi.drools.reasoner.DroolsOWLReasoner
 */
public class DefaultDroolsOWLAxiomHandler implements DroolsOWLAxiomHandler, AVisitor
{
	private final Set<A> inferredOWLAxioms;
	private final Set<A> assertedOWLAxioms;

	private final Set<String> declaredClassIDs;
	private final Set<String> declaredIndividualIDs;
	private final Set<String> declaredObjectPropertyIDs;
	private final Set<String> declaredDataPropertyIDs;
	private final Set<String> declaredAnnotationPropertyIDs;

	private final Map<String, Set<String>> classAssertions;
	private final Map<String, Set<String>> subClasses;
	private final Map<String, Set<String>> superClasses;
	private final Map<String, Set<String>> disjointClasses;
	private final Map<String, Set<String>> equivalentClasses;

	private final Map<String, Set<String>> sameIndividual;
	private final Map<String, Set<String>> differentIndividuals;

	private final Map<String, Set<String>> subObjectProperties;
	private final Map<String, Set<String>> superObjectProperties;
	private final Map<String, Set<String>> disjointObjectProperties;
	private final Map<String, Set<String>> equivalentObjectProperties;
	private final Map<String, Set<String>> inverseObjectProperties;
	private final Map<String, Set<String>> objectPropertyRanges;
	private final Map<String, Set<String>> objectPropertyDomains;
	private final Map<String, Map<String, Set<String>>> objectPropertyAssertions;

	private final Map<String, Set<String>> subDataProperties;
	private final Map<String, Set<String>> superDataProperties;
	private final Map<String, Set<String>> disjointDataProperties;
	private final Map<String, Set<String>> equivalentDataProperties;
	private final Map<String, Set<String>> dataPropertyDomains;
	private final Map<String, Map<String, Set<L>>> dataPropertyAssertions;

	private final Set<String> inconsistentMessages;
	private boolean isInconsistent;

	private StatefulKnowledgeSession knowledgeSession;

	public DefaultDroolsOWLAxiomHandler()
	{
		this.inferredOWLAxioms = new HashSet<>();
		this.assertedOWLAxioms = new HashSet<>();
		this.isInconsistent = false;
		this.inconsistentMessages = new HashSet<>();
		this.declaredClassIDs = new HashSet<>();
		this.declaredIndividualIDs = new HashSet<>();
		this.declaredObjectPropertyIDs = new HashSet<>();
		this.declaredDataPropertyIDs = new HashSet<>();
		this.declaredAnnotationPropertyIDs = new HashSet<>();
		this.subClasses = new HashMap<>();
		this.subObjectProperties = new HashMap<>();
		this.subDataProperties = new HashMap<>();
		this.superClasses = new HashMap<>();
		this.superObjectProperties = new HashMap<>();
		this.superDataProperties = new HashMap<>();
		this.sameIndividual = new HashMap<>();
		this.differentIndividuals = new HashMap<>();
		this.disjointClasses = new HashMap<>();
		this.disjointObjectProperties = new HashMap<>();
		this.disjointDataProperties = new HashMap<>();
		this.equivalentClasses = new HashMap<>();
		this.equivalentObjectProperties = new HashMap<>();
		this.equivalentDataProperties = new HashMap<>();
		this.classAssertions = new HashMap<>();
		this.inverseObjectProperties = new HashMap<>();
		this.objectPropertyRanges = new HashMap<>();
		this.objectPropertyDomains = new HashMap<>();
		this.dataPropertyDomains = new HashMap<>();
		this.objectPropertyAssertions = new HashMap<>();
		this.dataPropertyAssertions = new HashMap<>();
	}

	public void reset(StatefulKnowledgeSession knowledgeSession)
	{
		this.knowledgeSession = knowledgeSession;

		this.assertedOWLAxioms.clear();
		this.inferredOWLAxioms.clear();
		this.isInconsistent = false;
		this.inconsistentMessages.clear();
		this.declaredClassIDs.clear();
		this.declaredIndividualIDs.clear();
		this.declaredObjectPropertyIDs.clear();
		this.declaredDataPropertyIDs.clear();
		this.declaredAnnotationPropertyIDs.clear();
		this.subClasses.clear();
		this.subObjectProperties.clear();
		this.subDataProperties.clear();
		this.superClasses.clear();
		this.superObjectProperties.clear();
		this.superDataProperties.clear();
		this.sameIndividual.clear();
		this.differentIndividuals.clear();
		this.disjointClasses.clear();
		this.disjointObjectProperties.clear();
		this.disjointDataProperties.clear();
		this.equivalentClasses.clear();
		this.equivalentObjectProperties.clear();
		this.equivalentDataProperties.clear();
		this.classAssertions.clear();
		this.inverseObjectProperties.clear();
		this.objectPropertyRanges.clear();
		this.objectPropertyDomains.clear();
		this.dataPropertyDomains.clear();
		this.objectPropertyAssertions.clear();
		this.dataPropertyAssertions.clear();
	}

	/**
	 * This method can be called after the rule engine has finished executing to see if an inconsistency was detected.
	 */
	@Override
	public boolean isInconsistent()
	{
		return this.isInconsistent;
	}

	// Axioms

	/**
	 * Supply the asserted OWL axioms.
	 */
	@Override
	public void addAssertOWLAxioms(Set<A> newAssertedOWLAxioms)
	{
		this.assertedOWLAxioms.addAll(newAssertedOWLAxioms);

		for (A a : newAssertedOWLAxioms)
			a.visit(this);
	}

	/**
	 * This method is called by Drools rules at runtime.
	 */
	@Override
	public void infer(A... newInferredOWLAxioms)
	{
		if (this.knowledgeSession == null)
			throw new TargetSWRLRuleEngineInternalException("knowledge session not initialized in axiom inferrer");

		for (A newInferredOWLAxiom : newInferredOWLAxioms) {
			if (!this.inferredOWLAxioms.contains(newInferredOWLAxiom)
					&& (!this.assertedOWLAxioms.contains(newInferredOWLAxiom))) {
				this.inferredOWLAxioms.add(newInferredOWLAxiom);
				this.knowledgeSession.insert(newInferredOWLAxiom);

				newInferredOWLAxiom.visit(this);
			}
		}
	}

	@Override
	public Set<A> getAssertedOWLAxioms()
	{
		return Collections.unmodifiableSet(this.assertedOWLAxioms);
	}

	/**
	 * This method can be called after the rule engine has finished executing to get all the OWL axioms that have been
	 * inferred.
	 */
	@Override
	public Set<A> getInferredOWLAxioms()
	{
		return Collections.unmodifiableSet(this.inferredOWLAxioms);
	}

	@Override
	public boolean isEntailed(A a)
	{
		return this.assertedOWLAxioms.contains(a) || this.inferredOWLAxioms.contains(a);
	}

	@Override
	public boolean isEntailed(Set<? extends A> axioms)
	{
		for (A a : axioms)
			if (isEntailed(a))
				return true;
		return false;
	}

	// Classes

	@Override
	public boolean isDeclaredClass(String classID)
	{
		return this.declaredClassIDs.contains(classID);
	}

	@Override
	public Set<String> getClassAssertions(String classID)
	{
		return this.classAssertions.get(classID);
	}

	@Override
	public Set<String> getSubClasses(String classID, boolean direct)
	{
		Set<String> subClasses = new HashSet<>();

		for (String subClassID : this.subClasses.get(classID)) {
			if (direct) {
				if (directSubClassOf(classID, subClassID))
					subClasses.add(subClassID);
			} else {
				if (strictSubClassOf(classID, subClassID))
					subClasses.add(subClassID);
			}
		}
		return subClasses;
	}

	@Override
	public Set<String> getSuperClasses(String classID, boolean direct)
	{
		Set<String> superClasses = new HashSet<>();

		for (String superClassID : this.superClasses.get(classID)) {
			if (direct) {
				if (directSubClassOf(superClassID, classID))
					superClasses.add(superClassID);
			} else {
				if (strictSubClassOf(superClassID, classID))
					superClasses.add(superClassID);
			}
		}
		return superClasses;
	}

	@Override
	public Set<String> getDisjointClasses(String classID)
	{
		return this.disjointClasses.get(classID);
	}

	@Override
	public Set<String> getEquivalentClasses(String classID)
	{
		return this.equivalentClasses.get(classID);
	}

	/**
	 * Given two class expressions CE1 and CE2 and an ontology O, CE1 is a strict subclass of CE2, written
	 * StrictSubClassOf(CE1 CE2) if O entails SubClassOf(CE1 CE2) and O does not entail SubClassOf(CE2 CE1)
	 */
	@Override
	public boolean strictSubClassOf(String ceid1, String ceid2)
	{
		checkSubClassIDs(ceid1, ceid2);

		return this.subClasses.get(ceid1).contains(ceid2) && !this.subClasses.get(ceid2).contains(ceid1);
	}

	/**
	 * Given two class expressions CE1 and CE2 and an ontology O, CE1 is a direct subclass of CE2, written
	 * DirectSubClassOf(CE1 CE2), with respect to O if O entails StrictSubClassOf(CE1 CE2) and there is no class name C in
	 * the signature of O such that O entails StrictSubClassOf(CE1 C) and O entails StrictSubClassOf(C CE2).
	 */
	@Override
	public boolean directSubClassOf(String ceid1, String ceid2)
	{
		checkSubClassIDs(ceid1, ceid2);

		if (strictSubClassOf(ceid1, ceid2)) {
			for (String superClassID : this.subClasses.get(ceid1)) {
				if (strictSubClassOf(superClassID, ceid2))
					return false;
			}
			return false;
		} else
			return false;
	}

	// Individuals

	@Override
	public boolean isDeclaredIndividual(String individualID)
	{
		return this.declaredIndividualIDs.contains(individualID);
	}

	@Override
	public Set<String> getSameIndividual(String individualID)
	{
		return this.sameIndividual.get(individualID);
	}

	@Override
	public Set<String> getDifferentIndividuals(String individualID)
	{
		return this.differentIndividuals.get(individualID);
	}

	// Object properties

	@Override
	public boolean isDeclaredObjectProperty(String propertyID)
	{
		return this.declaredObjectPropertyIDs.contains(propertyID);
	}

	@Override
	public Set<String> getSubObjectProperties(String propertyID, boolean direct)
	{
		Set<String> subProperties = new HashSet<>();

		for (String subPropertyID : this.subObjectProperties.get(propertyID)) {
			if (direct) {
				if (directSubObjectPropertyOf(propertyID, subPropertyID))
					subProperties.add(subPropertyID);
			} else {
				if (strictSubObjectPropertyOf(propertyID, subPropertyID))
					subProperties.add(subPropertyID);
			}
		}
		return subProperties;
	}

	@Override
	public Set<String> getSuperObjectProperties(String propertyID, boolean direct)
	{
		Set<String> superProperties = new HashSet<>();

		for (String superPropertyID : this.superObjectProperties.get(propertyID)) {
			if (direct) {
				if (directSubObjectPropertyOf(superPropertyID, propertyID))
					superProperties.add(superPropertyID);
			} else {
				if (strictSubObjectPropertyOf(superPropertyID, propertyID))
					superProperties.add(superPropertyID);
			}
		}
		return superProperties;
	}

	@Override
	public Set<String> getObjectPropertyRanges(String propertyID, boolean direct)
	{
		return this.objectPropertyRanges.get(propertyID); // TODO direct?
	}

	@Override
	public Set<String> getObjectPropertyDomains(String propertyID, boolean direct)
	{
		return this.objectPropertyRanges.get(propertyID); // TODO direct?
	}

	@Override
	public Set<String> getDisjointObjectProperties(String propertyID)
	{
		return this.disjointObjectProperties.get(propertyID);
	}

	@Override
	public Set<String> getEquivalentObjectProperties(String propertyID)
	{
		return this.equivalentObjectProperties.get(propertyID);
	}

	@Override
	public Set<String> getInverseObjectProperties(String propertyID)
	{
		return this.inverseObjectProperties.get(propertyID);
	}

	@Override
	public Map<String, Set<String>> getObjectPropertyAssertions(String propertyID)
	{
		return this.objectPropertyAssertions.get(propertyID);
	}

	@Override
	public Set<String> getObjectPropertyValuesForIndividual(String individualID, String propertyID)
	{
		Set<String> individualIDs = new HashSet<>();
		Map<String, Set<String>> values = this.objectPropertyAssertions.get(propertyID);

		if (values.containsKey(individualID))
			individualIDs.addAll(values.get(individualID));

		return individualIDs;
	}

	/**
	 * Given two object property expressions OPE1 and OPE2 and an ontology O, OPE1 is a strict subproperty of OPE2,
	 * written StrictSubObjectPropertyOf(OPE1 OPE2) if O entails SubObjectPropertyOf(OPE1 OPE2) and O does not entail
	 * SubObjectPropertyOf(OPE2 OPE1)
	 */
	@Override
	public boolean strictSubObjectPropertyOf(String opid1, String opid2)
	{
		checkSubObjectPropertyIDs(opid1, opid2);

		return this.subObjectProperties.get(opid1).contains(opid2) && !this.subObjectProperties.get(opid2).contains(opid1);
	}

	/**
	 * Given two object property expressions OPE1 and OPE2 and an ontology O, OPE1 is a direct subproperty of OPE2,
	 * written DirectSubObjectPropertyOf(OPE1 OPE2), with respect to O if O entails StrictSubObjectPropertyOf(OPE1 OPE2)
	 * and there is no object property name P in the signature of O such that O entails StrictSubObjectPropertyOf(OPE1 P)
	 * and O entails StrictSubObjectPropertyOf(P OPE2).
	 */
	@Override
	public boolean directSubObjectPropertyOf(String opid1, String opid2)
	{
		checkSubObjectPropertyIDs(opid1, opid2);

		if (strictSubObjectPropertyOf(opid1, opid2)) {
			for (String superObjectPropertyID : this.subObjectProperties.get(opid1)) {
				if (strictSubObjectPropertyOf(superObjectPropertyID, opid2))
					return false;
			}
			return false;
		} else
			return false;
	}

	// Data properties

	@Override
	public boolean isDeclaredDataProperty(String propertyID)
	{
		return this.declaredDataPropertyIDs.contains(propertyID);
	}

	@Override
	public Set<String> getSubDataProperties(String propertyID, boolean direct)
	{
		Set<String> subProperties = new HashSet<>();

		for (String subPropertyID : this.subDataProperties.get(propertyID)) {
			if (direct) {
				if (directSubDataPropertyOf(propertyID, subPropertyID))
					subProperties.add(subPropertyID);
			} else {
				if (strictSubDataPropertyOf(propertyID, subPropertyID))
					subProperties.add(subPropertyID);
			}
		}
		return subProperties;
	}

	@Override
	public Set<String> getSuperDataProperties(String propertyID, boolean direct)
	{
		Set<String> superProperties = new HashSet<>();

		for (String superPropertyID : this.superDataProperties.get(propertyID)) {
			if (direct) {
				if (directSubDataPropertyOf(superPropertyID, propertyID))
					superProperties.add(superPropertyID);
			} else {
				if (strictSubDataPropertyOf(superPropertyID, propertyID))
					superProperties.add(superPropertyID);
			}
		}
		return superProperties;
	}

	@Override
	public Set<String> getDataPropertyDomains(String propertyID, boolean direct)
	{
		return this.dataPropertyDomains.get(propertyID); // TODO direct?
	}

	@Override
	public Set<String> getDisjointDataProperties(String propertyID)
	{
		return this.disjointDataProperties.get(propertyID);
	}

	@Override
	public Set<String> getEquivalentDataProperties(String propertyID)
	{
		return this.equivalentDataProperties.get(propertyID);
	}

	@Override
	public Map<String, Set<L>> getDataPropertyAssertions(String propertyID)
	{
		return this.dataPropertyAssertions.get(propertyID);
	}

	@Override
	public Set<L> getDataPropertyValuesForIndividual(String individualID, String propertyID)
	{
		Set<L> literals = new HashSet<>();
		Map<String, Set<L>> values = this.dataPropertyAssertions.get(propertyID);

		if (values.containsKey(individualID))
			literals.addAll(values.get(individualID));

		return literals;
	}

	/**
	 * Given two data property expressions DPE1 and DPE2 and an ontology O, DPE1 is a strict subproperty of DPE2, written
	 * StrictSubDataPropertyOf(DPE1 DPE2) if O entails SubDataPropertyOf(DPE1 DPE2) and O does not entail
	 * SubDataPropertyOf(DPE2 DPE1)
	 */
	@Override
	public boolean strictSubDataPropertyOf(String opid1, String opid2)
	{
		checkSubDataPropertyIDs(opid1, opid2);

		return this.subDataProperties.get(opid1).contains(opid2) && !this.subDataProperties.get(opid2).contains(opid1);
	}

	/**
	 * Given two object property expressions DPE1 and DPE2 and an ontology O, DPE1 is a direct subproperty of DPE2,
	 * written DirectSubDataPropertyOf(DPE1 DPE2), with respect to O if O entails StrictSubDataPropertyOf(DPE1 DPE2) and
	 * there is no data property name P in the signature of O such that O entails StrictSubDataPropertyOf(DPE1 P) and O
	 * entails StrictSubDataPropertyOf(P DPE2).
	 */
	@Override
	public boolean directSubDataPropertyOf(String dpid1, String dpid2)
	{
		checkSubDataPropertyIDs(dpid1, dpid2);

		if (strictSubDataPropertyOf(dpid1, dpid2)) {
			for (String superDataPropertyID : this.subDataProperties.get(dpid1)) {
				if (strictSubDataPropertyOf(superDataPropertyID, dpid2))
					return false;
			}
			return false;
		} else
			return false;
	}

	// Annotations

	@Override
	public boolean isDeclaredAnnotation(String propertyID)
	{
		return this.declaredAnnotationPropertyIDs.contains(propertyID);
	}

	public Set<String> getInconsistentMessages()
	{
		return Collections.unmodifiableSet(this.inconsistentMessages);
	}

	/**
	 * This method is called by an OWL 2 RL inconsistency detection rule when an inconsistency is detected. The parameters
	 * contains details of the offending rule and the OWL entities involved in the detected inconsistency.
	 */
	@Override
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

	@Override
	public void visit(CDA cda)
	{
		this.declaredClassIDs.add(cda.getcid());
	}

	@Override
	public void visit(OPDA opda)
	{
		this.declaredObjectPropertyIDs.add(opda.getpid());
	}

	@Override
	public void visit(DPDA dpda)
	{
		this.declaredDataPropertyIDs.add(dpda.getpid());
	}

	@Override
	public void visit(APDA apda)
	{
		this.declaredAnnotationPropertyIDs.add(apda.getpid());
	}

	@Override
	public void visit(IDA ida)
	{
		this.declaredIndividualIDs.add(ida.getI().getid());
	}

	@Override
	public void visit(SCA sca)
	{
		String subClassID = sca.getsubcid();
		String superClassID = sca.getsupercid();

		if (this.subClasses.containsKey(superClassID)) {
			this.subClasses.get(superClassID).add(subClassID);
		} else {
			Set<String> subClasses = new HashSet<>();
			subClasses.add(subClassID);
			this.subClasses.put(superClassID, subClasses);
		}

		if (this.superClasses.containsKey(subClassID)) {
			this.superClasses.get(subClassID).add(superClassID);
		} else {
			Set<String> superClasses = new HashSet<>();
			superClasses.add(superClassID);
			this.superClasses.put(subClassID, superClasses);
		}
	}

	@Override
	public void visit(DCA dca)
	{
		String c1ID = dca.getc1id();
		String c2ID = dca.getc2id();

		if (this.disjointClasses.containsKey(c1ID)) {
			this.disjointClasses.get(c1ID).add(c2ID);
		} else {
			Set<String> classIDs = new HashSet<>();
			classIDs.add(c2ID);
			this.disjointClasses.put(c1ID, classIDs);
		}
	}

	@Override
	public void visit(DDPA ddpa)
	{
		String propertyID = ddpa.getpid();
		String domainID = ddpa.getdid();

		if (this.dataPropertyDomains.containsKey(propertyID)) {
			this.dataPropertyDomains.get(propertyID).add(domainID);
		} else {
			Set<String> classIDs = new HashSet<>();
			classIDs.add(domainID);
			this.dataPropertyDomains.put(propertyID, classIDs);
		}
	}

	@Override
	public void visit(DOPA dopa)
	{
		String propertyID = dopa.getpid();
		String domainID = dopa.getdid();

		if (this.objectPropertyDomains.containsKey(propertyID)) {
			this.objectPropertyDomains.get(propertyID).add(domainID);
		} else {
			Set<String> classIDs = new HashSet<>();
			classIDs.add(domainID);
			this.objectPropertyDomains.put(propertyID, classIDs);
		}
	}

	@Override
	public void visit(EOPA eopa)
	{
		String p1ID = eopa.getp1id();
		String p2ID = eopa.getp2id();

		if (this.equivalentObjectProperties.containsKey(p1ID)) {
			this.equivalentObjectProperties.get(p1ID).add(p2ID);
		} else {
			Set<String> properties = new HashSet<>();
			properties.add(p2ID);
			this.equivalentObjectProperties.put(p1ID, properties);
		}
	}

	@Override
	public void visit(DIA dia)
	{
		String i1ID = dia.geti1id();
		String i2ID = dia.geti2id();

		if (this.differentIndividuals.containsKey(i1ID)) {
			this.differentIndividuals.get(i1ID).add(i2ID);
		} else {
			Set<String> individuals = new HashSet<>();
			individuals.add(i2ID);
			this.differentIndividuals.put(i1ID, individuals);
		}
	}

	@Override
	public void visit(DJDPA djdpa)
	{
		String p1ID = djdpa.getp1id();
		String p2ID = djdpa.getp2id();

		if (this.disjointDataProperties.containsKey(p1ID)) {
			this.disjointDataProperties.get(p1ID).add(p2ID);
		} else {
			Set<String> properties = new HashSet<>();
			properties.add(p2ID);
			this.disjointDataProperties.put(p1ID, properties);
		}
	}

	@Override
	public void visit(DJOPA djopa)
	{
		String p1ID = djopa.getp1id();
		String p2ID = djopa.getp2id();

		if (this.disjointObjectProperties.containsKey(p1ID)) {
			this.disjointObjectProperties.get(p1ID).add(p2ID);
		} else {
			Set<String> properties = new HashSet<>();
			properties.add(p2ID);
			this.disjointObjectProperties.put(p1ID, properties);
		}
	}

	@Override
	public void visit(OPRA opra)
	{
		String propertyID = opra.getpid();
		String rangeID = opra.getrid();

		if (this.objectPropertyRanges.containsKey(propertyID)) {
			this.objectPropertyRanges.get(propertyID).add(rangeID);
		} else {
			Set<String> classIDs = new HashSet<>();
			classIDs.add(rangeID);
			this.objectPropertyRanges.put(propertyID, classIDs);
		}
	}

	@Override
	public void visit(OPAA opaa)
	{
		String subjectID = opaa.getsid();
		String propertyID = opaa.getpid();
		String objectID = opaa.getoid();

		if (this.objectPropertyAssertions.containsKey(subjectID)) {
			Map<String, Set<String>> property2Values = objectPropertyAssertions.get(subjectID);
			if (property2Values.containsKey(propertyID)) {
				Set<String> values = property2Values.get(propertyID);
				values.add(objectID);
			} else {
				Set<String> values = new HashSet<>();
				values.add(objectID);
				property2Values.put(propertyID, values);
			}
		} else {
			Map<String, Set<String>> property2Values = new HashMap<>();
			Set<String> values = new HashSet<>();
			values.add(objectID);
			property2Values.put(propertyID, values);
			this.objectPropertyAssertions.put(subjectID, property2Values);
		}
	}

	@Override
	public void visit(SOPA sopa)
	{
		String subPropertyID = sopa.getsubpid();
		String superPropertyID = sopa.getsuperpid();

		if (this.subObjectProperties.containsKey(superPropertyID)) {
			this.subObjectProperties.get(superPropertyID).add(subPropertyID);
		} else {
			Set<String> subProperties = new HashSet<>();
			subProperties.add(subPropertyID);
			this.subObjectProperties.put(superPropertyID, subProperties);
		}

		if (this.superObjectProperties.containsKey(subPropertyID)) {
			this.superObjectProperties.get(subPropertyID).add(superPropertyID);
		} else {
			Set<String> superProperties = new HashSet<>();
			superProperties.add(superPropertyID);
			this.superObjectProperties.put(subPropertyID, superProperties);
		}
	}

	@Override
	public void visit(EDPA edpa)
	{
		String p1ID = edpa.getp1id();
		String p2ID = edpa.getp2id();

		if (this.equivalentDataProperties.containsKey(p1ID)) {
			this.equivalentDataProperties.get(p1ID).add(p2ID);
		} else {
			Set<String> properties = new HashSet<>();
			properties.add(p2ID);
			this.equivalentDataProperties.put(p1ID, properties);
		}
	}

	@Override
	public void visit(CAA caa)
	{
		String classID = caa.getcid();
		String individualID = caa.getiid();

		if (this.classAssertions.containsKey(classID)) {
			this.classAssertions.get(classID).add(individualID);
		} else {
			Set<String> individuals = new HashSet<>();
			individuals.add(individualID);
			this.classAssertions.put(classID, individuals);
		}
	}

	@Override
	public void visit(ECA eca)
	{
		String c1ID = eca.getc1id();
		String c2ID = eca.getc2id();

		if (this.equivalentClasses.containsKey(c1ID)) {
			this.equivalentClasses.get(c1ID).add(c2ID);
		} else {
			Set<String> classIDs = new HashSet<>();
			classIDs.add(c2ID);
			this.equivalentClasses.put(c1ID, classIDs);
		}
	}

	@Override
	public void visit(DPAA dpaa)
	{
		String subjectID = dpaa.getsid();
		String propertyID = dpaa.getpid();
		L object = dpaa.geto();

		if (this.dataPropertyAssertions.containsKey(subjectID)) {
			Map<String, Set<L>> property2Values = dataPropertyAssertions.get(subjectID);
			if (property2Values.containsKey(propertyID)) {
				Set<L> values = property2Values.get(propertyID);
				values.add(object);
			} else {
				Set<L> values = new HashSet<>();
				values.add(object);
				property2Values.put(propertyID, values);
			}
		} else {
			Map<String, Set<L>> property2Values = new HashMap<>();
			Set<L> values = new HashSet<>();
			values.add(object);
			property2Values.put(propertyID, values);
			this.dataPropertyAssertions.put(subjectID, property2Values);
		}
	}

	@Override
	public void visit(SDPA sdpa)
	{
		String subPropertyID = sdpa.getsubpid();
		String superPropertyID = sdpa.getsuperpid();

		if (this.subDataProperties.containsKey(superPropertyID)) {
			this.subDataProperties.get(superPropertyID).add(subPropertyID);
		} else {
			Set<String> subProperties = new HashSet<>();
			subProperties.add(subPropertyID);
			this.subDataProperties.put(superPropertyID, subProperties);
		}

		if (this.superDataProperties.containsKey(subPropertyID)) {
			this.superDataProperties.get(subPropertyID).add(superPropertyID);
		} else {
			Set<String> superProperties = new HashSet<>();
			superProperties.add(superPropertyID);
			this.superDataProperties.put(subPropertyID, superProperties);
		}
	}

	@Override
	public void visit(SIA sia)
	{
		String i1ID = sia.geti1id();
		String i2ID = sia.geti2id();

		if (this.sameIndividual.containsKey(i1ID)) {
			this.sameIndividual.get(i1ID).add(i2ID);
		} else {
			Set<String> individuals = new HashSet<>();
			individuals.add(i2ID);
			this.sameIndividual.put(i1ID, individuals);
		}
	}

	@Override
	public void visit(IOPA iopa)
	{
		String p1ID = iopa.getp1id();
		String p2ID = iopa.getp2id();

		if (this.inverseObjectProperties.containsKey(p1ID)) {
			this.inverseObjectProperties.get(p1ID).add(p2ID);
		} else {
			Set<String> properties = new HashSet<>();
			properties.add(p2ID);
			this.inverseObjectProperties.put(p1ID, properties);
		}
	}

	@Override
	public void visit(NOPAA nopa)
	{
		// An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
	}

	@Override
	public void visit(AOPA aopa)
	{
		// An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
	}

	@Override
	public void visit(NDPAA ndpaa)
	{
		// An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
	}

	@Override
	public void visit(FOPA fopa)
	{
		// An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
	}

	@Override
	public void visit(TOPA topa)
	{
		// An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
	}

	@Override
	public void visit(IROPA iropa)
	{
		// An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
	}

	@Override
	public void visit(SPA spa)
	{
		// An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
	}

	@Override
	public void visit(DPRA dpra)
	{
		// An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
	}

	@Override
	public void visit(FDPA fdpa)
	{
		// An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
	}

	@Override
	public void visit(IFOPA ifopa)
	{
		// An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
	}

	private void checkSubClassIDs(String... ceids)
	{
		for (String ceid : ceids) {
			if (!this.subClasses.containsKey(ceid)) {
				throw new TargetSWRLRuleEngineInternalException("No record of OWL class expression with ID " + ceid);
			}
		}
	}

	private void checkSubObjectPropertyIDs(String... opids)
	{
		for (String opid : opids) {
			if (!this.subObjectProperties.containsKey(opid)) {
				throw new TargetSWRLRuleEngineInternalException("No record of OWL object property expression with ID " + opid);
			}
		}
	}

	private void checkSubDataPropertyIDs(String... dpids)
	{
		for (String dpid : dpids) {
			if (!this.subDataProperties.containsKey(dpid)) {
				throw new TargetSWRLRuleEngineInternalException("No record of OWL data property expression with ID " + dpid);
			}
		}
	}
}
