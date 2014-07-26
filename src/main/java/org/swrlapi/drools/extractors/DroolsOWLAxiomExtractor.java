package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.*;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractor;
import org.swrlapi.drools.owl.axioms.*;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface list methods for converting the Drools representation of OWL axioms to their OWLAPI representation.
 * 
 * @see org.semanticweb.owlapi.model.OWLAxiom
 * @see org.swrlapi.drools.owl.axioms.A
 * @see org.swrlapi.drools.extractors.DefaultDroolsOWLAxiomExtractor
 */
public interface DroolsOWLAxiomExtractor extends TargetRuleEngineExtractor
{
	OWLDeclarationAxiom extract(CDA da) throws TargetRuleEngineException;

	OWLSubClassOfAxiom extract(SCA sca) throws TargetRuleEngineException;

	OWLDisjointClassesAxiom extract(DCA dca) throws TargetRuleEngineException;

	OWLEquivalentClassesAxiom extract(ECA eca) throws TargetRuleEngineException;

	OWLObjectPropertyDomainAxiom extract(DOPA dopa) throws TargetRuleEngineException;

	OWLDataPropertyDomainAxiom extract(DDPA ddpa) throws TargetRuleEngineException;

	OWLDeclarationAxiom extract(IDA da) throws TargetRuleEngineException;

	OWLDataPropertyRangeAxiom extract(DPRA DPRA) throws TargetRuleEngineException;

	OWLObjectPropertyRangeAxiom extract(OPRA OPRA) throws TargetRuleEngineException;

	OWLDeclarationAxiom extract(OPDA da) throws TargetRuleEngineException;

	OWLDeclarationAxiom extract(DPDA da) throws TargetRuleEngineException;

	OWLDeclarationAxiom extract(APDA da) throws TargetRuleEngineException;

	OWLClassAssertionAxiom extract(CAA caa) throws TargetRuleEngineException;

	OWLObjectPropertyAssertionAxiom extract(OPAA opaa) throws TargetRuleEngineException;

	OWLNegativeObjectPropertyAssertionAxiom extract(NOPAA opaa) throws TargetRuleEngineException;

	OWLDataPropertyAssertionAxiom extract(DPAA dpaa) throws TargetRuleEngineException;

	OWLNegativeDataPropertyAssertionAxiom extract(NDPAA ndpaa) throws TargetRuleEngineException;

	OWLSameIndividualAxiom extract(SIA sia) throws TargetRuleEngineException;

	OWLDifferentIndividualsAxiom extract(DIA dia) throws TargetRuleEngineException;

	OWLSubObjectPropertyOfAxiom extract(SOPA sopa) throws TargetRuleEngineException;

	OWLSubDataPropertyOfAxiom extract(SDPA sdpa) throws TargetRuleEngineException;

	OWLEquivalentObjectPropertiesAxiom extract(EOPA eopa) throws TargetRuleEngineException;

	OWLEquivalentDataPropertiesAxiom extract(EDPA edpa) throws TargetRuleEngineException;

	OWLDisjointObjectPropertiesAxiom extract(DJOPA edpa) throws TargetRuleEngineException;

	OWLDisjointDataPropertiesAxiom extract(DJDPA edpa) throws TargetRuleEngineException;

	OWLFunctionalObjectPropertyAxiom extract(FOPA fopa) throws TargetRuleEngineException;

	OWLFunctionalDataPropertyAxiom extract(FDPA fopa) throws TargetRuleEngineException;

	OWLInverseFunctionalObjectPropertyAxiom extract(IFOPA IFOPA) throws TargetRuleEngineException;

	OWLIrreflexiveObjectPropertyAxiom extract(IROPA IROPA) throws TargetRuleEngineException;

	OWLAsymmetricObjectPropertyAxiom extract(AOPA AOPA) throws TargetRuleEngineException;

	OWLSymmetricObjectPropertyAxiom extract(SPA spa) throws TargetRuleEngineException;

	OWLTransitiveObjectPropertyAxiom extract(TOPA TOPA) throws TargetRuleEngineException;

	OWLInverseObjectPropertiesAxiom extract(IOPA iopa) throws TargetRuleEngineException;
}
