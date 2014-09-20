package org.swrlapi.drools.extractors;

import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractor;
import org.swrlapi.drools.owl.axioms.AOPA;
import org.swrlapi.drools.owl.axioms.APDA;
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

/**
 * This interface list methods for converting the Drools representation of OWL axioms to their OWLAPI representation.
 *
 * @see org.semanticweb.owlapi.model.OWLAxiom
 * @see org.swrlapi.drools.owl.axioms.A
 * @see org.swrlapi.drools.extractors.DefaultDroolsOWLAxiomExtractor
 */
public interface DroolsOWLAxiomExtractor extends TargetRuleEngineExtractor
{
	OWLDeclarationAxiom extract(CDA da);

	OWLSubClassOfAxiom extract(SCA sca);

	OWLDisjointClassesAxiom extract(DCA dca);

	OWLEquivalentClassesAxiom extract(ECA eca);

	OWLObjectPropertyDomainAxiom extract(DOPA dopa);

	OWLDataPropertyDomainAxiom extract(DDPA ddpa);

	OWLDeclarationAxiom extract(IDA da);

	OWLDataPropertyRangeAxiom extract(DPRA DPRA);

	OWLObjectPropertyRangeAxiom extract(OPRA OPRA);

	OWLDeclarationAxiom extract(OPDA da);

	OWLDeclarationAxiom extract(DPDA da);

	OWLDeclarationAxiom extract(APDA da);

	OWLClassAssertionAxiom extract(CAA caa);

	OWLObjectPropertyAssertionAxiom extract(OPAA opaa);

	OWLNegativeObjectPropertyAssertionAxiom extract(NOPAA opaa);

	OWLDataPropertyAssertionAxiom extract(DPAA dpaa);

	OWLNegativeDataPropertyAssertionAxiom extract(NDPAA ndpaa);

	OWLSameIndividualAxiom extract(SIA sia);

	OWLDifferentIndividualsAxiom extract(DIA dia);

	OWLSubObjectPropertyOfAxiom extract(SOPA sopa);

	OWLSubDataPropertyOfAxiom extract(SDPA sdpa);

	OWLEquivalentObjectPropertiesAxiom extract(EOPA eopa);

	OWLEquivalentDataPropertiesAxiom extract(EDPA edpa);

	OWLDisjointObjectPropertiesAxiom extract(DJOPA edpa);

	OWLDisjointDataPropertiesAxiom extract(DJDPA edpa);

	OWLFunctionalObjectPropertyAxiom extract(FOPA fopa);

	OWLFunctionalDataPropertyAxiom extract(FDPA fopa);

	OWLInverseFunctionalObjectPropertyAxiom extract(IFOPA IFOPA);

	OWLIrreflexiveObjectPropertyAxiom extract(IROPA IROPA);

	OWLAsymmetricObjectPropertyAxiom extract(AOPA AOPA);

	OWLSymmetricObjectPropertyAxiom extract(SPA spa);

	OWLTransitiveObjectPropertyAxiom extract(TOPA TOPA);

	OWLInverseObjectPropertiesAxiom extract(IOPA iopa);
}
