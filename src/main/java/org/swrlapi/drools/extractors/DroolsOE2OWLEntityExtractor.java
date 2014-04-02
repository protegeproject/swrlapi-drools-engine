package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.drools.owl.entities.AP;
import org.swrlapi.drools.owl.entities.C;
import org.swrlapi.drools.owl.entities.D;
import org.swrlapi.drools.owl.entities.DP;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.drools.owl.entities.OE;
import org.swrlapi.drools.owl.entities.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.extractors.TargetRuleEngineExtractor;

/**
 * This interface describes methods for converting the Drools representation of OWL entities represented by the class
 * {@link OE} to their OWLAPI representation.
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
