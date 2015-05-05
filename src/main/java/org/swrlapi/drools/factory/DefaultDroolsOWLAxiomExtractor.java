package org.swrlapi.drools.factory;

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
  public DefaultDroolsOWLAxiomExtractor(SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @Override public OWLDeclarationAxiom extract(CDA da)
  {
    OWLClass cls = getOWLDataFactory().getOWLClass(prefixedName2IRI(da.getcid()));

    return getSWRLAPIOWLDataFactory().getOWLClassDeclarationAxiom(cls);
  }

  @Override public OWLDeclarationAxiom extract(IDA da)
  {
    OWLNamedIndividual individual = getOWLDataFactory().getOWLNamedIndividual(prefixedName2IRI(da.getE().getName()));

    return getSWRLAPIOWLDataFactory().getOWLIndividualDeclarationAxiom(individual);
  }

  @Override public OWLDeclarationAxiom extract(OPDA da)
  {
    OWLObjectProperty property = getOWLDataFactory().getOWLObjectProperty(prefixedName2IRI(da.getpid()));

    return getSWRLAPIOWLDataFactory().getOWLObjectPropertyDeclarationAxiom(property);
  }

  @Override public OWLDeclarationAxiom extract(DPDA da)
  {
    OWLDataProperty property = getOWLDataFactory().getOWLDataProperty(prefixedName2IRI(da.getpid()));

    return getSWRLAPIOWLDataFactory().getOWLDataPropertyDeclarationAxiom(property);
  }

  @Override public OWLDeclarationAxiom extract(APDA da)
  {
    OWLAnnotationProperty property = getOWLDataFactory().getOWLAnnotationProperty(prefixedName2IRI(da.getpid()));

    return getSWRLAPIOWLDataFactory().getOWLAnnotationPropertyDeclarationAxiom(property);
  }

  @Override public OWLClassAssertionAxiom extract(CAA caa)
  {
    OWLClassExpression cls = getOWLClassExpressionResolver().resolve(caa.getcid());
    OWLIndividual individual = caa.getI().extract(getDroolsOWLIndividualExtractor());

    return getOWLDataFactory().getOWLClassAssertionAxiom(cls, individual);
  }

  @Override public OWLObjectPropertyAssertionAxiom extract(OPAA opaa)
  {
    OWLIndividual subject = opaa.getT1().extract(getDroolsOWLIndividualExtractor());
    OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(opaa.getpid());
    OWLIndividual object = opaa.getT3().extract(getDroolsOWLIndividualExtractor());

    return getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(property, subject, object);
  }

  @Override public OWLNegativeObjectPropertyAssertionAxiom extract(NOPAA nopaa)
  {
    OWLIndividual subject = nopaa.gets().extract(getDroolsOWLIndividualExtractor());
    OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(nopaa.getpid());
    OWLIndividual object = nopaa.geto().extract(getDroolsOWLIndividualExtractor());

    return getOWLDataFactory().getOWLNegativeObjectPropertyAssertionAxiom(property, subject, object);
  }

  @Override public OWLDataPropertyAssertionAxiom extract(DPAA dpaa)
  {
    OWLIndividual subject = dpaa.gets().extract(getDroolsOWLIndividualExtractor());
    OWLDataPropertyExpression property = getOWLDataPropertyExpressionResolver().resolve(dpaa.getpid());
    OWLLiteral literal = getDroolsOWLLiteralExtractor().extract(dpaa.geto());

    return getOWLDataFactory().getOWLDataPropertyAssertionAxiom(property, subject, literal);
  }

  @Override public OWLNegativeDataPropertyAssertionAxiom extract(NDPAA ndpaa)
  {
    OWLIndividual subject = ndpaa.gets().extract(getDroolsOWLIndividualExtractor());
    OWLDataPropertyExpression property = getOWLDataPropertyExpressionResolver().resolve(ndpaa.getpid());
    OWLLiteral literal = getDroolsOWLLiteralExtractor().extract(ndpaa.geto());

    return getOWLDataFactory().getOWLNegativeDataPropertyAssertionAxiom(property, subject, literal);
  }

  @Override public OWLSameIndividualAxiom extract(SIA sia)
  {
    OWLIndividual individual1 = sia.geti1().extract(getDroolsOWLIndividualExtractor());
    OWLIndividual individual2 = sia.geti2().extract(getDroolsOWLIndividualExtractor());
    Set<OWLIndividual> individuals = new HashSet<>();

    individuals.add(individual1);
    individuals.add(individual2);

    return getOWLDataFactory().getOWLSameIndividualAxiom(individuals);
  }

  @Override public OWLDifferentIndividualsAxiom extract(DIA dia)
  {
    OWLIndividual individual1 = dia.geti1().extract(getDroolsOWLIndividualExtractor());
    OWLIndividual individual2 = dia.geti2().extract(getDroolsOWLIndividualExtractor());
    Set<OWLIndividual> individuals = new HashSet<>();

    individuals.add(individual1);
    individuals.add(individual2);

    return getOWLDataFactory().getOWLDifferentIndividualsAxiom(individuals);
  }

  @Override public OWLSubClassOfAxiom extract(SCA sca)
  {
    OWLClassExpression superClass = getOWLClassExpressionResolver().resolve(sca.getsupercid());
    OWLClassExpression subClass = getOWLClassExpressionResolver().resolve(sca.getsubcid());

    return getOWLDataFactory().getOWLSubClassOfAxiom(subClass, superClass);
  }

  @Override public OWLDisjointClassesAxiom extract(DCA dca)
  {
    OWLClassExpression class1 = getOWLClassExpressionResolver().resolve(dca.getc1id());
    OWLClassExpression class2 = getOWLClassExpressionResolver().resolve(dca.getc2id());
    Set<OWLClassExpression> classes = new HashSet<>();
    classes.add(class1);
    classes.add(class2);

    return getOWLDataFactory().getOWLDisjointClassesAxiom(classes);
  }

  @Override public OWLEquivalentClassesAxiom extract(ECA eca)
  {
    OWLClassExpression class1 = getOWLClassExpressionResolver().resolve(eca.getc1id());
    OWLClassExpression class2 = getOWLClassExpressionResolver().resolve(eca.getc2id());
    Set<OWLClassExpression> classes = new HashSet<>();
    classes.add(class1);
    classes.add(class2);

    return getOWLDataFactory().getOWLEquivalentClassesAxiom(classes);
  }

  @Override public OWLObjectPropertyDomainAxiom extract(DOPA dopa)
  {
    OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(dopa.getpid());
    OWLClassExpression domain = getOWLClassExpressionResolver().resolve(dopa.getdid());

    return getOWLDataFactory().getOWLObjectPropertyDomainAxiom(property, domain);
  }

  @Override public OWLDataPropertyDomainAxiom extract(DDPA ddpa)
  {
    OWLDataPropertyExpression property = getOWLDataPropertyExpressionResolver().resolve(ddpa.getpid());
    OWLClassExpression domain = getOWLClassExpressionResolver().resolve(ddpa.getdid());

    return getOWLDataFactory().getOWLDataPropertyDomainAxiom(property, domain);
  }

  @Override public OWLObjectPropertyRangeAxiom extract(OPRA opra)
  {
    OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(opra.getpid());
    OWLClassExpression range = getOWLClassExpressionResolver().resolve(opra.getrid());

    return getOWLDataFactory().getOWLObjectPropertyRangeAxiom(property, range);
  }

  @Override public OWLDataPropertyRangeAxiom extract(DPRA dpra)
  {
    OWLDataPropertyExpression property = getOWLDataPropertyExpressionResolver().resolve(dpra.getpid());
    OWLDataRange range = getOWLDataRangeResolver().resolveOWLDataRange(dpra.getrid());

    return getOWLDataFactory().getOWLDataPropertyRangeAxiom(property, range);
  }

  @Override public OWLSubObjectPropertyOfAxiom extract(SOPA sopa)
  {
    OWLObjectPropertyExpression superProperty = getOWLObjectPropertyExpressionResolver().resolve(sopa.getsuperpid());
    OWLObjectPropertyExpression subProperty = getOWLObjectPropertyExpressionResolver().resolve(sopa.getsubpid());

    return getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(subProperty, superProperty);
  }

  @Override public OWLInverseObjectPropertiesAxiom extract(IOPA iopa)
  {
    OWLObjectPropertyExpression property1 = getOWLObjectPropertyExpressionResolver().resolve(iopa.getp1id());
    OWLObjectPropertyExpression property2 = getOWLObjectPropertyExpressionResolver().resolve(iopa.getp2id());

    return getOWLDataFactory().getOWLInverseObjectPropertiesAxiom(property2, property1);
  }

  @Override public OWLSubDataPropertyOfAxiom extract(SDPA sdpa)
  {
    OWLDataPropertyExpression superProperty = getOWLDataPropertyExpressionResolver().resolve(sdpa.getsuperpid());
    OWLDataPropertyExpression subProperty = getOWLDataPropertyExpressionResolver().resolve(sdpa.getsubpid());

    return getOWLDataFactory().getOWLSubDataPropertyOfAxiom(subProperty, superProperty);
  }

  @Override public OWLEquivalentObjectPropertiesAxiom extract(EOPA eopa)
  {
    OWLObjectPropertyExpression property1 = getOWLObjectPropertyExpressionResolver().resolve(eopa.getp1id());
    OWLObjectPropertyExpression property2 = getOWLObjectPropertyExpressionResolver().resolve(eopa.getp1id());
    Set<OWLObjectPropertyExpression> properties = new HashSet<>();
    properties.add(property1);
    properties.add(property2);

    return getOWLDataFactory().getOWLEquivalentObjectPropertiesAxiom(properties);
  }

  @Override public OWLEquivalentDataPropertiesAxiom extract(EDPA edpa)
  {
    OWLDataPropertyExpression property1 = getOWLDataPropertyExpressionResolver().resolve(edpa.getp1id());
    OWLDataPropertyExpression property2 = getOWLDataPropertyExpressionResolver().resolve(edpa.getp2id());
    Set<OWLDataPropertyExpression> properties = new HashSet<>();
    properties.add(property1);
    properties.add(property2);

    return getOWLDataFactory().getOWLEquivalentDataPropertiesAxiom(properties);
  }

  @Override public OWLDisjointObjectPropertiesAxiom extract(DJOPA djopa)
  {
    OWLObjectPropertyExpression property1 = getOWLObjectPropertyExpressionResolver().resolve(djopa.getp1id());
    OWLObjectPropertyExpression property2 = getOWLObjectPropertyExpressionResolver().resolve(djopa.getp2id());
    Set<OWLObjectPropertyExpression> properties = new HashSet<>();
    properties.add(property1);
    properties.add(property2);

    return getOWLDataFactory().getOWLDisjointObjectPropertiesAxiom(properties);
  }

  @Override public OWLDisjointDataPropertiesAxiom extract(DJDPA djdpa)
  {
    OWLDataPropertyExpression property1 = getOWLDataPropertyExpressionResolver().resolve(djdpa.getp1id());
    OWLDataPropertyExpression property2 = getOWLDataPropertyExpressionResolver().resolve(djdpa.getp2id());
    Set<OWLDataPropertyExpression> properties = new HashSet<>();
    properties.add(property1);
    properties.add(property2);

    return getOWLDataFactory().getOWLDisjointDataPropertiesAxiom(properties);
  }

  @Override public OWLFunctionalObjectPropertyAxiom extract(FOPA fopa)
  {
    OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(fopa.getpid());

    return getOWLDataFactory().getOWLFunctionalObjectPropertyAxiom(property);
  }

  @Override public OWLFunctionalDataPropertyAxiom extract(FDPA fdpa)
  {
    OWLDataPropertyExpression property = getOWLDataPropertyExpressionResolver().resolve(fdpa.getpid());

    return getOWLDataFactory().getOWLFunctionalDataPropertyAxiom(property);
  }

  @Override public OWLInverseFunctionalObjectPropertyAxiom extract(IFOPA ifopa)
  {
    OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(ifopa.getpid());

    return getOWLDataFactory().getOWLInverseFunctionalObjectPropertyAxiom(property);
  }

  @Override public OWLIrreflexiveObjectPropertyAxiom extract(IROPA iropa)
  {
    OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(iropa.getpid());

    return getOWLDataFactory().getOWLIrreflexiveObjectPropertyAxiom(property);
  }

  @Override public OWLAsymmetricObjectPropertyAxiom extract(AOPA aopa)
  {
    OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(aopa.getpid());

    return getOWLDataFactory().getOWLAsymmetricObjectPropertyAxiom(property);
  }

  @Override public OWLSymmetricObjectPropertyAxiom extract(SPA spa)
  {
    OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(spa.getpid());

    return getOWLDataFactory().getOWLSymmetricObjectPropertyAxiom(property);
  }

  @Override public OWLTransitiveObjectPropertyAxiom extract(TOPA topa)
  {
    OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(topa.getpid());

    return getOWLDataFactory().getOWLTransitiveObjectPropertyAxiom(property);
  }
}
