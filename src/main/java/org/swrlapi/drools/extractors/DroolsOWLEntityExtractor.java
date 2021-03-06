package org.swrlapi.drools.extractors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractor;
import org.swrlapi.drools.owl.classes.C;
import org.swrlapi.drools.owl.dataranges.D;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.drools.owl.core.OE;
import org.swrlapi.drools.owl.properties.AP;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This interface describes methods for converting the Drools representation of OWL entities to their OWLAPI
 * representation.
 *
 * @see org.semanticweb.owlapi.model.OWLEntity
 * @see OE
 */
public interface DroolsOWLEntityExtractor extends TargetRuleEngineExtractor
{
  @NonNull OWLClass extract(@NonNull C c) throws TargetSWRLRuleEngineException;

  @NonNull OWLNamedIndividual extract(@NonNull I i) throws TargetSWRLRuleEngineException;

  @NonNull OWLObjectProperty extract(@NonNull OP p) throws TargetSWRLRuleEngineException;

  @NonNull OWLDataProperty extract(@NonNull DP p) throws TargetSWRLRuleEngineException;

  @NonNull OWLAnnotationProperty extract(@NonNull AP p) throws TargetSWRLRuleEngineException;

  @NonNull OWLDatatype extract(@NonNull D d) throws TargetSWRLRuleEngineException;
}
