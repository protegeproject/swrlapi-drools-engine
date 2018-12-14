package org.swrlapi.drools.extractors;

import org.checkerframework.checker.nullness.qual.NonNull;
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
 */
public interface DroolsOWLAxiomExtractor extends TargetRuleEngineExtractor
{
  @NonNull OWLDeclarationAxiom extract(@NonNull CDA da);

  @NonNull OWLSubClassOfAxiom extract(@NonNull SCA sca);

  @NonNull OWLDisjointClassesAxiom extract(@NonNull DCA dca);

  @NonNull OWLEquivalentClassesAxiom extract(@NonNull ECA eca);

  @NonNull OWLObjectPropertyDomainAxiom extract(@NonNull DOPA dopa);

  @NonNull OWLDataPropertyDomainAxiom extract(@NonNull DDPA ddpa);

  @NonNull OWLDeclarationAxiom extract(@NonNull IDA da);

  @NonNull OWLDataPropertyRangeAxiom extract(@NonNull DPRA DPRA);

  @NonNull OWLObjectPropertyRangeAxiom extract(@NonNull OPRA OPRA);

  @NonNull OWLDeclarationAxiom extract(@NonNull OPDA da);

  @NonNull OWLDeclarationAxiom extract(@NonNull DPDA da);

  @NonNull OWLDeclarationAxiom extract(@NonNull APDA da);

  @NonNull OWLClassAssertionAxiom extract(@NonNull CAA caa);

  @NonNull OWLObjectPropertyAssertionAxiom extract(@NonNull OPAA opaa);

  @NonNull OWLNegativeObjectPropertyAssertionAxiom extract(@NonNull NOPAA opaa);

  @NonNull OWLDataPropertyAssertionAxiom extract(@NonNull DPAA dpaa);

  @NonNull OWLNegativeDataPropertyAssertionAxiom extract(@NonNull NDPAA ndpaa);

  @NonNull OWLSameIndividualAxiom extract(@NonNull SIA sia);

  @NonNull OWLDifferentIndividualsAxiom extract(@NonNull DIA dia);

  @NonNull OWLSubObjectPropertyOfAxiom extract(@NonNull SOPA sopa);

  @NonNull OWLSubDataPropertyOfAxiom extract(@NonNull SDPA sdpa);

  @NonNull OWLEquivalentObjectPropertiesAxiom extract(@NonNull EOPA eopa);

  @NonNull OWLEquivalentDataPropertiesAxiom extract(@NonNull EDPA edpa);

  @NonNull OWLDisjointObjectPropertiesAxiom extract(@NonNull DJOPA jdopa);

  @NonNull OWLDisjointDataPropertiesAxiom extract(@NonNull DJDPA djdpa);

  @NonNull OWLFunctionalObjectPropertyAxiom extract(@NonNull FOPA fopa);

  @NonNull OWLFunctionalDataPropertyAxiom extract(@NonNull FDPA fopa);

  @NonNull OWLInverseFunctionalObjectPropertyAxiom extract(@NonNull IFOPA ifopa);

  @NonNull OWLIrreflexiveObjectPropertyAxiom extract(@NonNull IROPA iropa);

  @NonNull OWLAsymmetricObjectPropertyAxiom extract(@NonNull AOPA aopa);

  @NonNull OWLSymmetricObjectPropertyAxiom extract(@NonNull SPA spa);

  @NonNull OWLTransitiveObjectPropertyAxiom extract(@NonNull TOPA topa);

  @NonNull OWLInverseObjectPropertiesAxiom extract(@NonNull IOPA iopa);
}
