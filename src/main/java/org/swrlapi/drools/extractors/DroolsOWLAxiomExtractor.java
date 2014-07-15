package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.*;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLAxiomConverter;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractor;
import org.swrlapi.drools.owl.axioms.*;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This interface list methods for converting the Drools representation of OWL axioms to their OWLAPI representation.
 * Only OWL axioms currently supported by the Drools implementation will appear in this list. Supported axioms are
 * targeted to the requirements of an OWL 2 RL reasoner. For a complete list of possible OWL axioms see
 * {@link TargetRuleEngineOWLAxiomConverter}. Some axioms (e.g., annotation axioms) may never be reasoned with so are
 * unlikely to require extraction.
 * 
 * @see A, OWLAxiom
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

	OWLInverseFunctionalObjectPropertyAxiom extract(IPA ipa) throws TargetRuleEngineException;

	OWLIrreflexiveObjectPropertyAxiom extract(IRPA irpa) throws TargetRuleEngineException;

	OWLAsymmetricObjectPropertyAxiom extract(APA apa) throws TargetRuleEngineException;

	OWLSymmetricObjectPropertyAxiom extract(SPA spa) throws TargetRuleEngineException;

	OWLTransitiveObjectPropertyAxiom extract(TPA tpa) throws TargetRuleEngineException;

	OWLInverseObjectPropertiesAxiom extract(IOPA iopa) throws TargetRuleEngineException;
}
