package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractor;
import org.swrlapi.drools.owl.properties.AP;
import org.swrlapi.drools.owl.core.C;
import org.swrlapi.drools.owl.core.D;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl.core.OE;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface describes methods for converting the Drools representation of OWL properties represented by the class
 * {@link OE} to their OWLAPI representation.
 */
public interface DroolsOWLEntityExtractor extends TargetRuleEngineExtractor
{
	OWLClass extract(C c) throws TargetRuleEngineException;

	OWLNamedIndividual extract(I i) throws TargetRuleEngineException;

	OWLObjectProperty extract(OP p) throws TargetRuleEngineException;

	OWLDataProperty extract(DP p) throws TargetRuleEngineException;

	OWLAnnotationProperty extract(AP p) throws TargetRuleEngineException;

	OWLDatatype extract(D d) throws TargetRuleEngineException;
}
