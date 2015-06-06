package org.swrlapi.drools.factory;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
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
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.drools.extractors.DroolsExtractorBase;
import org.swrlapi.drools.extractors.DroolsOWLAxiomExtractor;
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

import java.util.HashSet;
import java.util.Set;

/**
 * This class defines methods for converting the Drools representation of OWL axioms to their OWLAPI representation.
 *
 * @see org.swrlapi.drools.owl.axioms.A
 * @see org.semanticweb.owlapi.model.OWLAxiom
 */
class DefaultDroolsOWLAxiomExtractor extends DroolsExtractorBase implements DroolsOWLAxiomExtractor
{
  public DefaultDroolsOWLAxiomExtractor(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull @Override public OWLDeclarationAxiom extract(@NonNull CDA da)
  {
    OWLClass cls = getOWLDataFactory().getOWLClass(getIRIResolver().prefixedName2IRI(da.getcid()));

    return getSWRLAPIOWLDataFactory().getOWLClassDeclarationAxiom(cls);
  }

  @NonNull @Override public OWLDeclarationAxiom extract(@NonNull IDA da)
  {
    OWLNamedIndividual individual = getOWLDataFactory().getOWLNamedIndividual(getIRIResolver().prefixedName2IRI(
        da.getE().getName()));

    return getSWRLAPIOWLDataFactory().getOWLIndividualDeclarationAxiom(individual);
  }

  @NonNull @Override public OWLDeclarationAxiom extract(@NonNull OPDA da)
  {
    OWLObjectProperty property = getOWLDataFactory().getOWLObjectProperty(getIRIResolver().prefixedName2IRI(da.getpid()));

    return getSWRLAPIOWLDataFactory().getOWLObjectPropertyDeclarationAxiom(property);
  }

  @NonNull @Override public OWLDeclarationAxiom extract(@NonNull DPDA da)
  {
    OWLDataProperty property = getOWLDataFactory().getOWLDataProperty(getIRIResolver().prefixedName2IRI(da.getpid()));

    return getSWRLAPIOWLDataFactory().getOWLDataPropertyDeclarationAxiom(property);
  }

  @NonNull @Override public OWLDeclarationAxiom extract(@NonNull APDA da)
  {
    OWLAnnotationProperty property = getOWLDataFactory().getOWLAnnotationProperty(getIRIResolver().prefixedName2IRI(da.getpid()));

    return getSWRLAPIOWLDataFactory().getOWLAnnotationPropertyDeclarationAxiom(property);
  }

  @NonNull @Override public OWLClassAssertionAxiom extract(@NonNull CAA caa)
  {
    OWLClassExpression cls = getOWLObjectResolver().resolveOWLClassExpression(caa.getcid());
    OWLIndividual individual = caa.getI().extract(getDroolsOWLIndividualExtractor());

    return getOWLDataFactory().getOWLClassAssertionAxiom(cls, individual);
  }

  @NonNull @Override public OWLObjectPropertyAssertionAxiom extract(@NonNull OPAA opaa)
  {
    OWLIndividual subject = opaa.getT1().extract(getDroolsOWLIndividualExtractor());
    OWLObjectPropertyExpression property = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        opaa.getpid());
    OWLIndividual object = opaa.getT3().extract(getDroolsOWLIndividualExtractor());

    return getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(property, subject, object);
  }

  @NonNull @Override public OWLNegativeObjectPropertyAssertionAxiom extract(@NonNull NOPAA nopaa)
  {
    OWLIndividual subject = nopaa.gets().extract(getDroolsOWLIndividualExtractor());
    OWLObjectPropertyExpression property = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        nopaa.getpid());
    OWLIndividual object = nopaa.geto().extract(getDroolsOWLIndividualExtractor());

    return getOWLDataFactory().getOWLNegativeObjectPropertyAssertionAxiom(property, subject, object);
  }

  @NonNull @Override public OWLDataPropertyAssertionAxiom extract(@NonNull DPAA dpaa)
  {
    OWLIndividual subject = dpaa.gets().extract(getDroolsOWLIndividualExtractor());
    OWLDataPropertyExpression property = getOWLObjectResolver().resolveOWLDataPropertyExpression(
        dpaa.getpid());
    OWLLiteral literal = getDroolsOWLLiteralExtractor().extract(dpaa.geto());

    return getOWLDataFactory().getOWLDataPropertyAssertionAxiom(property, subject, literal);
  }

  @NonNull @Override public OWLNegativeDataPropertyAssertionAxiom extract(@NonNull NDPAA ndpaa)
  {
    OWLIndividual subject = ndpaa.gets().extract(getDroolsOWLIndividualExtractor());
    OWLDataPropertyExpression property = getOWLObjectResolver().resolveOWLDataPropertyExpression(
        ndpaa.getpid());
    OWLLiteral literal = getDroolsOWLLiteralExtractor().extract(ndpaa.geto());

    return getOWLDataFactory().getOWLNegativeDataPropertyAssertionAxiom(property, subject, literal);
  }

  @NonNull @Override public OWLSameIndividualAxiom extract(@NonNull SIA sia)
  {
    OWLIndividual individual1 = sia.geti1().extract(getDroolsOWLIndividualExtractor());
    OWLIndividual individual2 = sia.geti2().extract(getDroolsOWLIndividualExtractor());
    Set<OWLIndividual> individuals = new HashSet<>();

    individuals.add(individual1);
    individuals.add(individual2);

    return getOWLDataFactory().getOWLSameIndividualAxiom(individuals);
  }

  @NonNull @Override public OWLDifferentIndividualsAxiom extract(@NonNull DIA dia)
  {
    OWLIndividual individual1 = dia.geti1().extract(getDroolsOWLIndividualExtractor());
    OWLIndividual individual2 = dia.geti2().extract(getDroolsOWLIndividualExtractor());
    Set<OWLIndividual> individuals = new HashSet<>();

    individuals.add(individual1);
    individuals.add(individual2);

    return getOWLDataFactory().getOWLDifferentIndividualsAxiom(individuals);
  }

  @NonNull @Override public OWLSubClassOfAxiom extract(@NonNull SCA sca)
  {
    OWLClassExpression superClass = getOWLObjectResolver().resolveOWLClassExpression(sca.getsupercid());
    OWLClassExpression subClass = getOWLObjectResolver().resolveOWLClassExpression(sca.getsubcid());

    return getOWLDataFactory().getOWLSubClassOfAxiom(subClass, superClass);
  }

  @NonNull @Override public OWLDisjointClassesAxiom extract(@NonNull DCA dca)
  {
    OWLClassExpression class1 = getOWLObjectResolver().resolveOWLClassExpression(dca.getc1id());
    OWLClassExpression class2 = getOWLObjectResolver().resolveOWLClassExpression(dca.getc2id());
    Set<OWLClassExpression> classes = new HashSet<>();
    classes.add(class1);
    classes.add(class2);

    return getOWLDataFactory().getOWLDisjointClassesAxiom(classes);
  }

  @NonNull @Override public OWLEquivalentClassesAxiom extract(@NonNull ECA eca)
  {
    OWLClassExpression class1 = getOWLObjectResolver().resolveOWLClassExpression(eca.getc1id());
    OWLClassExpression class2 = getOWLObjectResolver().resolveOWLClassExpression(eca.getc2id());
    Set<OWLClassExpression> classes = new HashSet<>();
    classes.add(class1);
    classes.add(class2);

    return getOWLDataFactory().getOWLEquivalentClassesAxiom(classes);
  }

  @NonNull @Override public OWLObjectPropertyDomainAxiom extract(@NonNull DOPA dopa)
  {
    OWLObjectPropertyExpression property = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        dopa.getpid());
    OWLClassExpression domain = getOWLObjectResolver().resolveOWLClassExpression(dopa.getdid());

    return getOWLDataFactory().getOWLObjectPropertyDomainAxiom(property, domain);
  }

  @NonNull @Override public OWLDataPropertyDomainAxiom extract(@NonNull DDPA ddpa)
  {
    OWLDataPropertyExpression property = getOWLObjectResolver().resolveOWLDataPropertyExpression(
        ddpa.getpid());
    OWLClassExpression domain = getOWLObjectResolver().resolveOWLClassExpression(ddpa.getdid());

    return getOWLDataFactory().getOWLDataPropertyDomainAxiom(property, domain);
  }

  @NonNull @Override public OWLObjectPropertyRangeAxiom extract(@NonNull OPRA opra)
  {
    OWLObjectPropertyExpression property = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        opra.getpid());
    OWLClassExpression range = getOWLObjectResolver().resolveOWLClassExpression(opra.getrid());

    return getOWLDataFactory().getOWLObjectPropertyRangeAxiom(property, range);
  }

  @NonNull @Override public OWLDataPropertyRangeAxiom extract(@NonNull DPRA dpra)
  {
    OWLDataPropertyExpression property = getOWLObjectResolver().resolveOWLDataPropertyExpression(
        dpra.getpid());
    OWLDataRange range = getOWLObjectResolver().resolveOWLDataRange(dpra.getrid());

    return getOWLDataFactory().getOWLDataPropertyRangeAxiom(property, range);
  }

  @NonNull @Override public OWLSubObjectPropertyOfAxiom extract(@NonNull SOPA sopa)
  {
    OWLObjectPropertyExpression superProperty = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        sopa.getsuperpid());
    OWLObjectPropertyExpression subProperty = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        sopa.getsubpid());

    return getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(subProperty, superProperty);
  }

  @NonNull @Override public OWLInverseObjectPropertiesAxiom extract(@NonNull IOPA iopa)
  {
    OWLObjectPropertyExpression property1 = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        iopa.getp1id());
    OWLObjectPropertyExpression property2 = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        iopa.getp2id());

    return getOWLDataFactory().getOWLInverseObjectPropertiesAxiom(property2, property1);
  }

  @NonNull @Override public OWLSubDataPropertyOfAxiom extract(@NonNull SDPA sdpa)
  {
    OWLDataPropertyExpression superProperty = getOWLObjectResolver().resolveOWLDataPropertyExpression(
        sdpa.getsuperpid());
    OWLDataPropertyExpression subProperty = getOWLObjectResolver().resolveOWLDataPropertyExpression(
        sdpa.getsubpid());

    return getOWLDataFactory().getOWLSubDataPropertyOfAxiom(subProperty, superProperty);
  }

  @NonNull @Override public OWLEquivalentObjectPropertiesAxiom extract(@NonNull EOPA eopa)
  {
    OWLObjectPropertyExpression property1 = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        eopa.getp1id());
    OWLObjectPropertyExpression property2 = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        eopa.getp1id());
    Set<OWLObjectPropertyExpression> properties = new HashSet<>();
    properties.add(property1);
    properties.add(property2);

    return getOWLDataFactory().getOWLEquivalentObjectPropertiesAxiom(properties);
  }

  @NonNull @Override public OWLEquivalentDataPropertiesAxiom extract(@NonNull EDPA edpa)
  {
    OWLDataPropertyExpression property1 = getOWLObjectResolver().resolveOWLDataPropertyExpression(
        edpa.getp1id());
    OWLDataPropertyExpression property2 = getOWLObjectResolver().resolveOWLDataPropertyExpression(
        edpa.getp2id());
    Set<OWLDataPropertyExpression> properties = new HashSet<>();
    properties.add(property1);
    properties.add(property2);

    return getOWLDataFactory().getOWLEquivalentDataPropertiesAxiom(properties);
  }

  @NonNull @Override public OWLDisjointObjectPropertiesAxiom extract(@NonNull DJOPA djopa)
  {
    OWLObjectPropertyExpression property1 = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        djopa.getp1id());
    OWLObjectPropertyExpression property2 = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        djopa.getp2id());
    Set<OWLObjectPropertyExpression> properties = new HashSet<>();
    properties.add(property1);
    properties.add(property2);

    return getOWLDataFactory().getOWLDisjointObjectPropertiesAxiom(properties);
  }

  @NonNull @Override public OWLDisjointDataPropertiesAxiom extract(@NonNull DJDPA djdpa)
  {
    OWLDataPropertyExpression property1 = getOWLObjectResolver().resolveOWLDataPropertyExpression(
        djdpa.getp1id());
    OWLDataPropertyExpression property2 = getOWLObjectResolver().resolveOWLDataPropertyExpression(
        djdpa.getp2id());
    Set<OWLDataPropertyExpression> properties = new HashSet<>();
    properties.add(property1);
    properties.add(property2);

    return getOWLDataFactory().getOWLDisjointDataPropertiesAxiom(properties);
  }

  @NonNull @Override public OWLFunctionalObjectPropertyAxiom extract(@NonNull FOPA fopa)
  {
    OWLObjectPropertyExpression property = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        fopa.getpid());

    return getOWLDataFactory().getOWLFunctionalObjectPropertyAxiom(property);
  }

  @NonNull @Override public OWLFunctionalDataPropertyAxiom extract(@NonNull FDPA fdpa)
  {
    OWLDataPropertyExpression property = getOWLObjectResolver().resolveOWLDataPropertyExpression(
        fdpa.getpid());

    return getOWLDataFactory().getOWLFunctionalDataPropertyAxiom(property);
  }

  @NonNull @Override public OWLInverseFunctionalObjectPropertyAxiom extract(@NonNull IFOPA ifopa)
  {
    OWLObjectPropertyExpression property = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        ifopa.getpid());

    return getOWLDataFactory().getOWLInverseFunctionalObjectPropertyAxiom(property);
  }

  @NonNull @Override public OWLIrreflexiveObjectPropertyAxiom extract(@NonNull IROPA iropa)
  {
    OWLObjectPropertyExpression property = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        iropa.getpid());

    return getOWLDataFactory().getOWLIrreflexiveObjectPropertyAxiom(property);
  }

  @NonNull @Override public OWLAsymmetricObjectPropertyAxiom extract(@NonNull AOPA aopa)
  {
    OWLObjectPropertyExpression property = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        aopa.getpid());

    return getOWLDataFactory().getOWLAsymmetricObjectPropertyAxiom(property);
  }

  @NonNull @Override public OWLSymmetricObjectPropertyAxiom extract(@NonNull SPA spa)
  {
    OWLObjectPropertyExpression property = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        spa.getpid());

    return getOWLDataFactory().getOWLSymmetricObjectPropertyAxiom(property);
  }

  @NonNull @Override public OWLTransitiveObjectPropertyAxiom extract(@NonNull TOPA topa)
  {
    OWLObjectPropertyExpression property = getOWLObjectResolver().resolveOWLObjectPropertyExpression(
        topa.getpid());

    return getOWLDataFactory().getOWLTransitiveObjectPropertyAxiom(property);
  }
}
