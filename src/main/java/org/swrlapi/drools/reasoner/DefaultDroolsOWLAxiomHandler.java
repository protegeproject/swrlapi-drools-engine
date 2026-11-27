package org.swrlapi.drools.reasoner;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.kie.api.runtime.KieSession;
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
import org.swrlapi.drools.owl.literals.L;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;
import org.swrlapi.owl2rl.OWL2RLInconsistencyDescription;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
  private final @NonNull Set<@NonNull A> inferredOWLAxioms;
  private final @NonNull Set<@NonNull A> assertedOWLAxioms;

  private final @NonNull Set<@NonNull String> declaredClassIDs;
  private final @NonNull Set<@NonNull String> declaredIndividualIDs;
  private final @NonNull Set<@NonNull String> declaredObjectPropertyIDs;
  private final @NonNull Set<@NonNull String> declaredDataPropertyIDs;
  private final @NonNull Set<@NonNull String> declaredAnnotationPropertyIDs;

  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> classAssertions;
  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> subClasses;
  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> superClasses;
  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> disjointClasses;
  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> equivalentClasses;

  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> sameIndividual;
  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> differentIndividuals;

  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> subObjectProperties;
  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> superObjectProperties;
  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> disjointObjectProperties;
  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> equivalentObjectProperties;
  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> inverseObjectProperties;
  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> objectPropertyRanges;
  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> objectPropertyDomains;
  private final @NonNull Map<@NonNull String, @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>>> objectPropertyAssertions;

  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> subDataProperties;
  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> superDataProperties;
  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> disjointDataProperties;
  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> equivalentDataProperties;
  private final @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> dataPropertyDomains;
  private final @NonNull Map<@NonNull String, @NonNull Map<@NonNull String, @NonNull Set<@NonNull L>>> dataPropertyAssertions;

  private final @NonNull Set<@NonNull String> inconsistentMessages;

  private boolean isInconsistent;

  private @MonotonicNonNull KieSession knowledgeSession;

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

  public void reset(KieSession knowledgeSession)
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
  @Override public boolean isInconsistent()
  {
    return this.isInconsistent;
  }

  // Axioms

  /**
   * Supply the asserted OWL axioms.
   */
  @Override public void addAssertOWLAxioms(@NonNull Set<@NonNull A> newAssertedOWLAxioms)
  {
    this.assertedOWLAxioms.addAll(newAssertedOWLAxioms);

    for (A a : newAssertedOWLAxioms)
      a.visit(this);
  }

  /**
   * This method is called by Drools rules at runtime.
   */
  @Override public void infer(@NonNull A... newInferredOWLAxioms)
  {
    if (this.knowledgeSession == null)
      throw new TargetSWRLRuleEngineInternalException("knowledge session not initialized in axiom inferrer");

    for (A newInferredOWLAxiom : newInferredOWLAxioms) {
      if (!this.inferredOWLAxioms.contains(newInferredOWLAxiom) && (!this.assertedOWLAxioms
        .contains(newInferredOWLAxiom))) {
        this.inferredOWLAxioms.add(newInferredOWLAxiom);
        if (this.knowledgeSession != null) {
          this.knowledgeSession.insert(newInferredOWLAxiom);
          newInferredOWLAxiom.visit(this);
        } else
          throw new TargetSWRLRuleEngineInternalException("No knowledge session!");
      }
    }
  }

  @Override public @NonNull Set<@NonNull A> getAssertedOWLAxioms()
  {
    return Collections.unmodifiableSet(this.assertedOWLAxioms);
  }

  /**
   * This method can be called after the rule engine has finished executing to get all the OWL axioms that have been
   * inferred.
   */
  @Override public @NonNull Set<@NonNull A> getInferredOWLAxioms()
  {
    return Collections.unmodifiableSet(this.inferredOWLAxioms);
  }

  @Override public boolean isEntailed(@NonNull A a)
  {
    return this.assertedOWLAxioms.contains(a) || this.inferredOWLAxioms.contains(a);
  }

  @Override public boolean isEntailed(@NonNull Set<? extends @NonNull A> axioms)
  {
    for (A a : axioms)
      if (!isEntailed(a))
        return false;
    return true;
  }

  // Classes

  @Override public boolean isDeclaredClass(@NonNull String classID)
  {
    return this.declaredClassIDs.contains(classID);
  }

  @Override public @NonNull Set<@NonNull String> getClassAssertions(@NonNull String classID)
  {
    if (this.classAssertions.get(classID) != null)
      return this.classAssertions.get(classID);
    else
      return Collections.emptySet();
  }

  @Override public @NonNull Set<@NonNull String> getSubClasses(@NonNull String classID, boolean direct)
  {
    Set<@NonNull String> subClasses = new HashSet<>();

    if (this.subClasses.get(classID) != null) {
      for (String subClassID : this.subClasses.get(classID)) {
        if (direct) {
          if (directSubClassOf(subClassID, classID))
            subClasses.add(subClassID);
        } else {
          if (strictSubClassOf(subClassID, classID))
            subClasses.add(subClassID);
        }
      }
    }
    return subClasses;
  }

  @Override public @NonNull Set<@NonNull String> getSuperClasses(@NonNull String classID, boolean direct)
  {
    Set<@NonNull String> superClasses = new HashSet<>();

    if (this.superClasses.get(classID) != null) {
      for (String superClassID : this.superClasses.get(classID)) {
        if (direct) {
          if (directSubClassOf(classID, superClassID))
            superClasses.add(superClassID);
        } else {
          if (strictSubClassOf(classID, superClassID))
            superClasses.add(superClassID);
        }
      }
    }
    return superClasses;
  }

  @Override public @NonNull Set<@NonNull String> getDisjointClasses(@NonNull String classID)
  {
    if (this.disjointClasses.get(classID) != null)
      return this.disjointClasses.get(classID);
    else
      return Collections.emptySet();
  }

  @Override public @NonNull Set<@NonNull String> getEquivalentClasses(@NonNull String classID)
  {
    if (this.equivalentClasses.get(classID) != null)
      return this.equivalentClasses.get(classID);
    else
      return Collections.emptySet();
  }

  /**
   * Given two class expressions CE1 and CE2 and an ontology O, CE1 is a strict subclass of CE2, written
   * StrictSubClassOf(CE1 CE2) if O entails SubClassOf(CE1 CE2) and O does not entail SubClassOf(CE2 CE1)
   */
  @Override public boolean strictSubClassOf(@NonNull String ceid1, @NonNull String ceid2)
  {
    return this.superClasses.getOrDefault(ceid1, Collections.emptySet()).contains(ceid2)
      && !this.superClasses.getOrDefault(ceid2, Collections.emptySet()).contains(ceid1);
  }

  /**
   * Given two class expressions CE1 and CE2 and an ontology O, CE1 is a direct subclass of CE2, written
   * DirectSubClassOf(CE1 CE2), with respect to O if O entails StrictSubClassOf(CE1 CE2) and there is no class name C in
   * the signature of O such that O entails StrictSubClassOf(CE1 C) and O entails StrictSubClassOf(C CE2).
   */
  @Override public boolean directSubClassOf(@NonNull String ceid1, @NonNull String ceid2)
  {
    if (!strictSubClassOf(ceid1, ceid2))
      return false;
    for (String middle : this.superClasses.getOrDefault(ceid1, Collections.emptySet()))
      if (strictSubClassOf(ceid1, middle) && strictSubClassOf(middle, ceid2))
        return false;
    return true;
  }

  // Individuals

  @Override public boolean isDeclaredIndividual(@NonNull String individualID)
  {
    return this.declaredIndividualIDs.contains(individualID);
  }

  @Override public @NonNull Set<@NonNull String> getSameIndividual(@NonNull String individualID)
  {
    if (this.sameIndividual.get(individualID) != null)
      return this.sameIndividual.get(individualID);
    else
      return Collections.emptySet();
  }

  @Override public @NonNull Set<@NonNull String> getDifferentIndividuals(@NonNull String individualID)
  {
    if (this.differentIndividuals.get(individualID) != null)
      return this.differentIndividuals.get(individualID);
    else
      return Collections.emptySet();
  }

  // Object properties

  @Override public boolean isDeclaredObjectProperty(@NonNull String propertyID)
  {
    return this.declaredObjectPropertyIDs.contains(propertyID);
  }

  @Override public @NonNull Set<@NonNull String> getSubObjectProperties(@NonNull String propertyID, boolean direct)
  {
    Set<@NonNull String> subProperties = new HashSet<>();

    if (this.subObjectProperties.get(propertyID) != null) {
      for (String subPropertyID : this.subObjectProperties.get(propertyID)) {
        if (direct) {
          if (directSubObjectPropertyOf(subPropertyID, propertyID))
            subProperties.add(subPropertyID);
        } else {
          if (strictSubObjectPropertyOf(subPropertyID, propertyID))
            subProperties.add(subPropertyID);
        }
      }
    }
    return subProperties;
  }

  @Override public @NonNull Set<@NonNull String> getSuperObjectProperties(@NonNull String propertyID, boolean direct)
  {
    Set<@NonNull String> superProperties = new HashSet<>();

    if (this.superObjectProperties.get(propertyID) != null) {
      for (String superPropertyID : this.superObjectProperties.get(propertyID)) {
        if (direct) {
          if (directSubObjectPropertyOf(propertyID, superPropertyID))
            superProperties.add(superPropertyID);
        } else {
          if (strictSubObjectPropertyOf(propertyID, superPropertyID))
            superProperties.add(superPropertyID);
        }
      }
    }
    return superProperties;
  }

  @Override public @NonNull Set<@NonNull String> getObjectPropertyRanges(@NonNull String propertyID, boolean direct)
  {
    return classDomains(this.objectPropertyRanges.getOrDefault(propertyID, Collections.emptySet()), direct);
  }

  @Override public @NonNull Set<@NonNull String> getObjectPropertyDomains(@NonNull String propertyID, boolean direct)
  {
    return classDomains(this.objectPropertyDomains.getOrDefault(propertyID, Collections.emptySet()), direct);
  }

  @Override public @NonNull Set<@NonNull String> getDisjointObjectProperties(@NonNull String propertyID)
  {
    if (this.disjointObjectProperties.get(propertyID) != null)
      return this.disjointObjectProperties.get(propertyID);
    else
      return Collections.emptySet();
  }

  @Override public @NonNull Set<@NonNull String> getEquivalentObjectProperties(@NonNull String propertyID)
  {
    if (this.equivalentObjectProperties.get(propertyID) != null)
      return this.equivalentObjectProperties.get(propertyID);
    else
      return Collections.emptySet();
  }

  @Override public @NonNull Set<@NonNull String> getInverseObjectProperties(@NonNull String propertyID)
  {
    if (this.inverseObjectProperties.get(propertyID) != null)
      return this.inverseObjectProperties.get(propertyID);
    else
      return Collections.emptySet();
  }

  @Override public @NonNull Map<@NonNull String, @NonNull Set<@NonNull String>> getObjectPropertyAssertions(
    @NonNull String propertyID)
  {
    Map<String, Set<String>> result = new HashMap<>();
    for (Map.Entry<String, Map<String, Set<String>>> entry : this.objectPropertyAssertions.entrySet()) {
      Set<String> values = entry.getValue().get(propertyID);
      if (values != null)
        result.put(entry.getKey(), new HashSet<>(values));
    }
    return result;
  }

  @Override public @NonNull Set<@NonNull String> getObjectPropertyValuesForIndividual(@NonNull String individualID,
    @NonNull String propertyID)
  {
    return new HashSet<>(this.objectPropertyAssertions.getOrDefault(individualID, Collections.emptyMap())
      .getOrDefault(propertyID, Collections.emptySet()));
  }

  /**
   * Given two object property expressions OPE1 and OPE2 and an ontology O, OPE1 is a strict sub-property of OPE2,
   * written StrictSubObjectPropertyOf(OPE1 OPE2) if O entails SubObjectPropertyOf(OPE1 OPE2) and O does not entail
   * SubObjectPropertyOf(OPE2 OPE1)
   */
  @Override public boolean strictSubObjectPropertyOf(@NonNull String opid1, @NonNull String opid2)
  {
    return this.superObjectProperties.getOrDefault(opid1, Collections.emptySet()).contains(opid2)
      && !this.superObjectProperties.getOrDefault(opid2, Collections.emptySet()).contains(opid1);
  }

  /**
   * Given two object property expressions OPE1 and OPE2 and an ontology O, OPE1 is a direct sub-property of OPE2,
   * written DirectSubObjectPropertyOf(OPE1 OPE2), with respect to O if O entails StrictSubObjectPropertyOf(OPE1 OPE2)
   * and there is no object property name P in the signature of O such that O entails StrictSubObjectPropertyOf(OPE1 P)
   * and O entails StrictSubObjectPropertyOf(P OPE2).
   */
  @Override public boolean directSubObjectPropertyOf(@NonNull String opid1, @NonNull String opid2)
  {
    if (!strictSubObjectPropertyOf(opid1, opid2))
      return false;
    for (String middle : this.superObjectProperties.getOrDefault(opid1, Collections.emptySet()))
      if (strictSubObjectPropertyOf(opid1, middle) && strictSubObjectPropertyOf(middle, opid2))
        return false;
    return true;
  }

  // Data properties

  @Override public boolean isDeclaredDataProperty(@NonNull String propertyID)
  {
    return this.declaredDataPropertyIDs.contains(propertyID);
  }

  @Override public @NonNull Set<@NonNull String> getSubDataProperties(@NonNull String propertyID, boolean direct)
  {
    Set<@NonNull String> subProperties = new HashSet<>();

    if (this.subDataProperties.get(propertyID) != null) {
      for (String subPropertyID : this.subDataProperties.get(propertyID)) {
        if (direct) {
          if (directSubDataPropertyOf(subPropertyID, propertyID))
            subProperties.add(subPropertyID);
        } else {
          if (strictSubDataPropertyOf(subPropertyID, propertyID))
            subProperties.add(subPropertyID);
        }
      }
    }
    return subProperties;
  }

  @Override public @NonNull Set<@NonNull String> getSuperDataProperties(@NonNull String propertyID, boolean direct)
  {
    Set<@NonNull String> superProperties = new HashSet<>();

    if (this.superDataProperties.get(propertyID) != null) {
      for (String superPropertyID : this.superDataProperties.get(propertyID)) {
        if (direct) {
          if (directSubDataPropertyOf(propertyID, superPropertyID))
            superProperties.add(superPropertyID);
        } else {
          if (strictSubDataPropertyOf(propertyID, superPropertyID))
            superProperties.add(superPropertyID);
        }
      }
    }
    return superProperties;
  }

  @Override public @NonNull Set<@NonNull String> getDataPropertyDomains(@NonNull String propertyID, boolean direct)
  {
    return classDomains(this.dataPropertyDomains.getOrDefault(propertyID, Collections.emptySet()), direct);
  }

  @Override public @NonNull Set<@NonNull String> getDisjointDataProperties(@NonNull String propertyID)
  {
    if (this.disjointDataProperties.get(propertyID) != null)
      return this.disjointDataProperties.get(propertyID);
    else
      return Collections.emptySet();
  }

  @Override public @NonNull Set<@NonNull String> getEquivalentDataProperties(@NonNull String propertyID)
  {
    if (this.equivalentDataProperties.get(propertyID) != null)
      return this.equivalentDataProperties.get(propertyID);
    else
      return Collections.emptySet();
  }

  @Override public @NonNull Map<@NonNull String, @NonNull Set<@NonNull L>> getDataPropertyAssertions(
    @NonNull String propertyID)
  {
    Map<String, Set<L>> result = new HashMap<>();
    for (Map.Entry<String, Map<String, Set<L>>> entry : this.dataPropertyAssertions.entrySet()) {
      Set<L> values = entry.getValue().get(propertyID);
      if (values != null)
        result.put(entry.getKey(), new HashSet<>(values));
    }
    return result;
  }

  @Override public @NonNull Set<@NonNull L> getDataPropertyValuesForIndividual(@NonNull String individualID,
    @NonNull String propertyID)
  {
    return new HashSet<>(this.dataPropertyAssertions.getOrDefault(individualID, Collections.emptyMap())
      .getOrDefault(propertyID, Collections.emptySet()));
  }

  /**
   * Given two data property expressions DPE1 and DPE2 and an ontology O, DPE1 is a strict sub-property of DPE2, written
   * StrictSubDataPropertyOf(DPE1 DPE2) if O entails SubDataPropertyOf(DPE1 DPE2) and O does not entail
   * SubDataPropertyOf(DPE2 DPE1)
   */
  @Override public boolean strictSubDataPropertyOf(@NonNull String dpid1, @NonNull String dpid2)
  {
    return this.superDataProperties.getOrDefault(dpid1, Collections.emptySet()).contains(dpid2)
      && !this.superDataProperties.getOrDefault(dpid2, Collections.emptySet()).contains(dpid1);
  }

  /**
   * Given two object property expressions DPE1 and DPE2 and an ontology O, DPE1 is a direct sub-property of DPE2,
   * written DirectSubDataPropertyOf(DPE1 DPE2), with respect to O if O entails StrictSubDataPropertyOf(DPE1 DPE2) and
   * there is no data property name P in the signature of O such that O entails StrictSubDataPropertyOf(DPE1 P) and O
   * entails StrictSubDataPropertyOf(P DPE2).
   */
  @Override public boolean directSubDataPropertyOf(@NonNull String dpid1, @NonNull String dpid2)
  {
    if (!strictSubDataPropertyOf(dpid1, dpid2))
      return false;
    for (String middle : this.superDataProperties.getOrDefault(dpid1, Collections.emptySet()))
      if (strictSubDataPropertyOf(dpid1, middle) && strictSubDataPropertyOf(middle, dpid2))
        return false;
    return true;
  }

  // Annotations

  @Override public boolean isDeclaredAnnotation(@NonNull String propertyID)
  {
    return this.declaredAnnotationPropertyIDs.contains(propertyID);
  }

  public @NonNull Set<@NonNull String> getInconsistentMessages()
  {
    return Collections.unmodifiableSet(this.inconsistentMessages);
  }

  /**
   * This method is called by an OWL 2 RL inconsistency detection rule when an inconsistency is detected. The parameters
   * contains details of the offending rule and the OWL entities involved in the detected inconsistency.
   */
  private Set<String> classDomains(Set<String> asserted, boolean direct)
  {
    Set<String> result = new HashSet<>(asserted);
    result.add("owl:Thing");
    for (String cls : asserted)
      result.addAll(this.superClasses.getOrDefault(cls, Collections.emptySet()));
    if (direct) {
      Set<String> candidates = new HashSet<>(result);
      result.removeIf(parent -> candidates.stream().anyMatch(child -> strictSubClassOf(child, parent)));
    }
    return result;
  }

  @Override public void inferFalse(@NonNull String owl2RLRuleName, @NonNull String... arguments)
  {
    String inconsistentMessage = "OWL 2 RL rule detected an inconsistency in the ontology.\n "
      + "See http://www.w3.org/TR/owl-profiles/#Reasoning_in_OWL_2_RL_and_RDF_Graphs_using_Rules for a list of inconsistency detection rules.\n"
      + "Rule that detected an inconsistency: " + owl2RLRuleName;
    Iterator<String> argumentsIterator = Arrays.asList(arguments).iterator();

    if (OWL2RLInconsistencyDescription.hasInconsistencyRuleArgumentsDescription(owl2RLRuleName)) {
      Optional<OWL2RLInconsistencyDescription.OWL2RLRuleArguments> ruleArguments = OWL2RLInconsistencyDescription
        .getRuleArguments(owl2RLRuleName);

      if (ruleArguments.isPresent()) {
        if (ruleArguments.get().hasClassArguments()) {
          inconsistentMessage += "\n Classes:";
          for (int argumentCount = 0;
               (argumentCount < ruleArguments.get().getNumberOfClassArguments()) && argumentsIterator
                 .hasNext(); argumentCount++) {
            inconsistentMessage += " " + argumentsIterator.next();
          }
        }

        if (ruleArguments.get().hasIndividualArguments()) {
          inconsistentMessage += "\n Individuals:";
          for (int argumentCount = 0;
               (argumentCount < ruleArguments.get().getNumberOfIndividualArguments()) && argumentsIterator
                 .hasNext(); argumentCount++) {
            inconsistentMessage += " " + argumentsIterator.next();
          }
        }

        if (ruleArguments.get().hasObjectPropertyArguments()) {
          inconsistentMessage += "\n Object Properties:";
          for (int argumentCount = 0;
               (argumentCount < ruleArguments.get().getNumberOfObjectPropertyArguments()) && argumentsIterator
                 .hasNext(); argumentCount++) {
            inconsistentMessage += " " + argumentsIterator.next();
          }
        }

        if (ruleArguments.get().hasDataPropertyArguments()) {
          inconsistentMessage += "\n Data Properties:";
          for (int argumentCount = 0;
               (argumentCount < ruleArguments.get().getNumberOfObjectPropertyArguments()) && argumentsIterator
                 .hasNext(); argumentCount++) {
            inconsistentMessage += " " + argumentsIterator.next();
          }
        }
      }
    }
    this.isInconsistent = true;
    this.inconsistentMessages.add(inconsistentMessage);
  }

  @Override public void visit(@NonNull CDA cda)
  {
    this.declaredClassIDs.add(cda.getcid());
  }

  @Override public void visit(@NonNull OPDA opda)
  {
    this.declaredObjectPropertyIDs.add(opda.getpid());
  }

  @Override public void visit(@NonNull DPDA dpda)
  {
    this.declaredDataPropertyIDs.add(dpda.getpid());
  }

  @Override public void visit(@NonNull APDA apda)
  {
    this.declaredAnnotationPropertyIDs.add(apda.getpid());
  }

  @Override public void visit(@NonNull IDA ida)
  {
    this.declaredIndividualIDs.add(ida.getI().getid());
  }

  @Override public void visit(@NonNull SCA sca)
  {
    String subClassID = sca.getsubcid();
    String superClassID = sca.getsupercid();

    if (this.subClasses.containsKey(superClassID)) {
      this.subClasses.get(superClassID).add(subClassID);
    } else {
      Set<@NonNull String> subClasses = new HashSet<>();
      subClasses.add(subClassID);
      this.subClasses.put(superClassID, subClasses);
    }

    if (this.superClasses.containsKey(subClassID)) {
      this.superClasses.get(subClassID).add(superClassID);
    } else {
      Set<@NonNull String> superClasses = new HashSet<>();
      superClasses.add(superClassID);
      this.superClasses.put(subClassID, superClasses);
    }
  }

  @Override public void visit(@NonNull DCA dca)
  {
    String c1ID = dca.getc1id();
    String c2ID = dca.getc2id();

    if (this.disjointClasses.containsKey(c1ID)) {
      this.disjointClasses.get(c1ID).add(c2ID);
    } else {
      Set<@NonNull String> classIDs = new HashSet<>();
      classIDs.add(c2ID);
      this.disjointClasses.put(c1ID, classIDs);
    }
  }

  @Override public void visit(@NonNull DDPA ddpa)
  {
    String propertyID = ddpa.getpid();
    String domainID = ddpa.getdid();

    if (this.dataPropertyDomains.containsKey(propertyID)) {
      this.dataPropertyDomains.get(propertyID).add(domainID);
    } else {
      Set<@NonNull String> classIDs = new HashSet<>();
      classIDs.add(domainID);
      this.dataPropertyDomains.put(propertyID, classIDs);
    }
  }

  @Override public void visit(@NonNull DOPA dopa)
  {
    String propertyID = dopa.getpid();
    String domainID = dopa.getdid();

    if (this.objectPropertyDomains.containsKey(propertyID)) {
      this.objectPropertyDomains.get(propertyID).add(domainID);
    } else {
      Set<@NonNull String> classIDs = new HashSet<>();
      classIDs.add(domainID);
      this.objectPropertyDomains.put(propertyID, classIDs);
    }
  }

  @Override public void visit(@NonNull EOPA eopa)
  {
    String p1ID = eopa.getp1id();
    String p2ID = eopa.getp2id();

    if (this.equivalentObjectProperties.containsKey(p1ID)) {
      this.equivalentObjectProperties.get(p1ID).add(p2ID);
    } else {
      Set<@NonNull String> properties = new HashSet<>();
      properties.add(p2ID);
      this.equivalentObjectProperties.put(p1ID, properties);
    }
  }

  @Override public void visit(@NonNull DIA dia)
  {
    String i1ID = dia.geti1id();
    String i2ID = dia.geti2id();

    if (this.differentIndividuals.containsKey(i1ID)) {
      this.differentIndividuals.get(i1ID).add(i2ID);
    } else {
      Set<@NonNull String> individuals = new HashSet<>();
      individuals.add(i2ID);
      this.differentIndividuals.put(i1ID, individuals);
    }
  }

  @Override public void visit(@NonNull DJDPA djdpa)
  {
    String p1ID = djdpa.getp1id();
    String p2ID = djdpa.getp2id();

    if (this.disjointDataProperties.containsKey(p1ID)) {
      this.disjointDataProperties.get(p1ID).add(p2ID);
    } else {
      Set<@NonNull String> properties = new HashSet<>();
      properties.add(p2ID);
      this.disjointDataProperties.put(p1ID, properties);
    }
  }

  @Override public void visit(@NonNull DJOPA djopa)
  {
    String p1ID = djopa.getp1id();
    String p2ID = djopa.getp2id();

    if (this.disjointObjectProperties.containsKey(p1ID)) {
      this.disjointObjectProperties.get(p1ID).add(p2ID);
    } else {
      Set<@NonNull String> properties = new HashSet<>();
      properties.add(p2ID);
      this.disjointObjectProperties.put(p1ID, properties);
    }
  }

  @Override public void visit(@NonNull OPRA opra)
  {
    String propertyID = opra.getpid();
    String rangeID = opra.getrid();

    if (this.objectPropertyRanges.containsKey(propertyID)) {
      this.objectPropertyRanges.get(propertyID).add(rangeID);
    } else {
      Set<@NonNull String> classIDs = new HashSet<>();
      classIDs.add(rangeID);
      this.objectPropertyRanges.put(propertyID, classIDs);
    }
  }

  @Override public void visit(@NonNull OPAA opaa)
  {
    String subjectID = opaa.getsid();
    String propertyID = opaa.getpid();
    String objectID = opaa.getoid();

    if (this.objectPropertyAssertions.containsKey(subjectID)) {
      Map<@NonNull String, Set<@NonNull String>> property2Values = this.objectPropertyAssertions.get(subjectID);
      if (property2Values.containsKey(propertyID)) {
        Set<@NonNull String> values = property2Values.get(propertyID);
        values.add(objectID);
      } else {
        Set<@NonNull String> values = new HashSet<>();
        values.add(objectID);
        property2Values.put(propertyID, values);
      }
    } else {
      Map<@NonNull String, Set<@NonNull String>> property2Values = new HashMap<>();
      Set<@NonNull String> values = new HashSet<>();
      values.add(objectID);
      property2Values.put(propertyID, values);
      this.objectPropertyAssertions.put(subjectID, property2Values);
    }
  }

  @Override public void visit(@NonNull SOPA sopa)
  {
    String subPropertyID = sopa.getsubpid();
    String superPropertyID = sopa.getsuperpid();

    if (this.subObjectProperties.containsKey(superPropertyID)) {
      this.subObjectProperties.get(superPropertyID).add(subPropertyID);
    } else {
      Set<@NonNull String> subProperties = new HashSet<>();
      subProperties.add(subPropertyID);
      this.subObjectProperties.put(superPropertyID, subProperties);
    }

    if (this.superObjectProperties.containsKey(subPropertyID)) {
      this.superObjectProperties.get(subPropertyID).add(superPropertyID);
    } else {
      Set<@NonNull String> superProperties = new HashSet<>();
      superProperties.add(superPropertyID);
      this.superObjectProperties.put(subPropertyID, superProperties);
    }
  }

  @Override public void visit(@NonNull EDPA edpa)
  {
    String p1ID = edpa.getp1id();
    String p2ID = edpa.getp2id();

    if (this.equivalentDataProperties.containsKey(p1ID)) {
      this.equivalentDataProperties.get(p1ID).add(p2ID);
    } else {
      Set<@NonNull String> properties = new HashSet<>();
      properties.add(p2ID);
      this.equivalentDataProperties.put(p1ID, properties);
    }
  }

  @Override public void visit(@NonNull CAA caa)
  {
    String classID = caa.getcid();
    String individualID = caa.getiid();

    if (this.classAssertions.containsKey(classID)) {
      this.classAssertions.get(classID).add(individualID);
    } else {
      Set<@NonNull String> individuals = new HashSet<>();
      individuals.add(individualID);
      this.classAssertions.put(classID, individuals);
    }
  }

  @Override public void visit(@NonNull ECA eca)
  {
    String c1ID = eca.getc1id();
    String c2ID = eca.getc2id();

    if (this.equivalentClasses.containsKey(c1ID)) {
      this.equivalentClasses.get(c1ID).add(c2ID);
    } else {
      Set<@NonNull String> classIDs = new HashSet<>();
      classIDs.add(c2ID);
      this.equivalentClasses.put(c1ID, classIDs);
    }
  }

  @Override public void visit(@NonNull DPAA dpaa)
  {
    String subjectID = dpaa.getsid();
    String propertyID = dpaa.getpid();
    L object = dpaa.geto();

    if (this.dataPropertyAssertions.containsKey(subjectID)) {
      Map<@NonNull String, @NonNull Set<@NonNull L>> property2Values = this.dataPropertyAssertions.get(subjectID);
      if (property2Values.containsKey(propertyID)) {
        Set<@NonNull L> values = property2Values.get(propertyID);
        values.add(object);
      } else {
        Set<@NonNull L> values = new HashSet<>();
        values.add(object);
        property2Values.put(propertyID, values);
      }
    } else {
      Map<@NonNull String, @NonNull Set<@NonNull L>> property2Values = new HashMap<>();
      Set<@NonNull L> values = new HashSet<>();
      values.add(object);
      property2Values.put(propertyID, values);
      this.dataPropertyAssertions.put(subjectID, property2Values);
    }
  }

  @Override public void visit(@NonNull SDPA sdpa)
  {
    String subPropertyID = sdpa.getsubpid();
    String superPropertyID = sdpa.getsuperpid();

    if (this.subDataProperties.containsKey(superPropertyID)) {
      this.subDataProperties.get(superPropertyID).add(subPropertyID);
    } else {
      Set<@NonNull String> subProperties = new HashSet<>();
      subProperties.add(subPropertyID);
      this.subDataProperties.put(superPropertyID, subProperties);
    }

    if (this.superDataProperties.containsKey(subPropertyID)) {
      this.superDataProperties.get(subPropertyID).add(superPropertyID);
    } else {
      Set<@NonNull String> superProperties = new HashSet<>();
      superProperties.add(superPropertyID);
      this.superDataProperties.put(subPropertyID, superProperties);
    }
  }

  @Override public void visit(@NonNull SIA sia)
  {
    String i1ID = sia.geti1id();
    String i2ID = sia.geti2id();

    if (this.sameIndividual.containsKey(i1ID)) {
      this.sameIndividual.get(i1ID).add(i2ID);
    } else {
      Set<@NonNull String> individuals = new HashSet<>();
      individuals.add(i2ID);
      this.sameIndividual.put(i1ID, individuals);
    }
  }

  @Override public void visit(@NonNull IOPA iopa)
  {
    String p1ID = iopa.getp1id();
    String p2ID = iopa.getp2id();

    if (this.inverseObjectProperties.containsKey(p1ID)) {
      this.inverseObjectProperties.get(p1ID).add(p2ID);
    } else {
      Set<@NonNull String> properties = new HashSet<>();
      properties.add(p2ID);
      this.inverseObjectProperties.put(p1ID, properties);
    }
  }

  @Override public void visit(NOPAA nopa)
  {
    // An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
  }

  @Override public void visit(AOPA aopa)
  {
    // An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
  }

  @Override public void visit(NDPAA ndpaa)
  {
    // An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
  }

  @Override public void visit(FOPA fopa)
  {
    // An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
  }

  @Override public void visit(TOPA topa)
  {
    // An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
  }

  @Override public void visit(IROPA iropa)
  {
    // An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
  }

  @Override public void visit(SPA spa)
  {
    // An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
  }

  @Override public void visit(DPRA dpra)
  {
    // An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
  }

  @Override public void visit(FDPA fdpa)
  {
    // An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
  }

  @Override public void visit(IFOPA ifopa)
  {
    // An OWL 2 RL reasoner does not assert axioms of this type so we ignore.
  }

  private void checkSubClassIDs(@NonNull String... ceids)
  {
    for (String ceid : ceids) {
      if (!this.subClasses.containsKey(ceid)) {
        throw new TargetSWRLRuleEngineInternalException(
          "No recordOWLClassExpression of OWL class expression with ID " + ceid);
      }
    }
  }

  private void checkSubObjectPropertyIDs(@NonNull String... opids)
  {
    for (String opid : opids) {
      if (!this.subObjectProperties.containsKey(opid)) {
        throw new TargetSWRLRuleEngineInternalException(
          "No recordOWLClassExpression of OWL object property expression with ID " + opid);
      }
    }
  }

  private void checkSubDataPropertyIDs(@NonNull String... dpids)
  {
    for (String dpid : dpids) {
      if (!this.subDataProperties.containsKey(dpid)) {
        throw new TargetSWRLRuleEngineInternalException(
          "No recordOWLClassExpression of OWL data property expression with ID " + dpid);
      }
    }
  }
}
