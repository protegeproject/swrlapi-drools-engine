package org.protege.swrlapi.drools.extractors;

import org.protege.swrlapi.drools.owl.entities.AP;
import org.protege.swrlapi.drools.owl.entities.C;
import org.protege.swrlapi.drools.owl.entities.D;
import org.protege.swrlapi.drools.owl.entities.DP;
import org.protege.swrlapi.drools.owl.entities.I;
import org.protege.swrlapi.drools.owl.entities.OE;
import org.protege.swrlapi.drools.owl.entities.OP;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.protege.swrlapi.extractors.TargetRuleEngineExtractor;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * This interface converts the Drools representation of OWL entities represented by the class {@link OE} to their
 * Portability API representation.
 */
public interface DroolsOE2OWLEntityExtractor extends TargetRuleEngineExtractor
{
	OWLClass extract(C c) throws TargetRuleEngineException;

	OWLNamedIndividual extract(I i) throws TargetRuleEngineException;

	OWLObjectProperty extract(OP p) throws TargetRuleEngineException;

	OWLDataProperty extract(DP p) throws TargetRuleEngineException;

	OWLAnnotationProperty extract(AP p) throws TargetRuleEngineException;

	OWLDatatype extract(D d) throws TargetRuleEngineException;
}
