package org.swrlapi.drools.converters;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
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
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLAxiomConverter;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.visitors.SWRLAPIOWLAxiomVisitor;
import org.swrlapi.drools.core.DroolsSWRLRuleEngine;
import org.swrlapi.drools.owl.axioms.A;
import org.swrlapi.drools.owl.axioms.AOPA;
import org.swrlapi.drools.owl.axioms.APDA;
import org.swrlapi.drools.owl.axioms.CAA;
import org.swrlapi.drools.owl.axioms.CDA;
import org.swrlapi.drools.owl.axioms.DCA;
import org.swrlapi.drools.owl.axioms.DDPA;
import org.swrlapi.drools.owl.axioms.DIA;
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
import org.swrlapi.drools.owl.classexpressions.CE;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl.core.L;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

/**
 * This class converts OWLAPI OWL axioms to their Drools representation.
 * <p>
 * Note that SWRL rules are also a type of OWL axiom so are also converted here.
 *
 * @see org.semanticweb.owlapi.model.OWLAxiom
 * @see org.swrlapi.drools.owl.axioms.A
 * @see org.swrlapi.drools.owl2rl.DroolsOWL2RLEngine
 */
public class DroolsOWLAxiomConverter extends DroolsConverterBase implements TargetRuleEngineOWLAxiomConverter,
SWRLAPIOWLAxiomVisitor
{
  private final DroolsSWRLRuleConverter swrlRuleConverter;
  private final DroolsOWLClassExpressionConverter classExpressionConverter;
  private final DroolsOWLPropertyExpressionConverter propertyExpressionConverter;

  private final Set<A> assertedOWLAxioms;

  public DroolsOWLAxiomConverter(SWRLRuleEngineBridge bridge, DroolsSWRLRuleEngine droolsEngine,
      DroolsOWLClassExpressionConverter classExpressionConverter,
      DroolsOWLPropertyExpressionConverter propertyExpressionConverter)
  {
    super(bridge);

    this.swrlRuleConverter = new DroolsSWRLRuleConverter(bridge, droolsEngine, classExpressionConverter,
        propertyExpressionConverter);
    this.classExpressionConverter = classExpressionConverter;
    this.propertyExpressionConverter = propertyExpressionConverter;

    this.assertedOWLAxioms = new HashSet<>();
  }

  public void reset()
  {
    this.assertedOWLAxioms.clear();
  }

  public Set<A> getAssertedOWLAxioms()
  {
    return new HashSet<>(this.assertedOWLAxioms);
  }

  public Set<CE> getOWLClassExpressions()
  {
    return this.classExpressionConverter.getCEs();
  }

  @Override
  public void convert(SWRLAPIRule rule)
  {
    getDroolsSWRLRuleConverter().convert(rule);
  }

  @Override
  public void convert(OWLDeclarationAxiom axiom)
  {
    OWLEntity entity = axiom.getEntity();

    if (entity.isOWLClass()) {
      OWLClass cls = axiom.getEntity().asOWLClass();
      String classPrefixedName = getDroolsOWLNamedObject2DRLConverter().convert(cls);
      recordOWLAxiom(new CDA(classPrefixedName));
      getOWLClassExpressionResolver().record(classPrefixedName, cls);
    } else if (entity.isOWLNamedIndividual()) {
      OWLNamedIndividual individual = entity.asOWLNamedIndividual();
      String individualPrefixedName = getDroolsOWLNamedObject2DRLConverter().convert(individual);
      recordOWLAxiom(new IDA(individualPrefixedName));
      recordOWLAxiom(new CAA("owl:Thing", individualPrefixedName));
    } else if (entity.isOWLObjectProperty()) {
      OWLObjectProperty property = entity.asOWLObjectProperty();
      String propertyPrefixedName = getDroolsOWLPropertyExpressionConverter().convert(property);
      recordOWLAxiom(new OPDA(propertyPrefixedName));
    } else if (entity.isOWLDataProperty()) {
      OWLDataProperty property = entity.asOWLDataProperty();
      String propertyPrefixedName = getDroolsOWLPropertyExpressionConverter().convert(property);
      recordOWLAxiom(new DPDA(propertyPrefixedName));
    } else if (entity.isOWLAnnotationProperty()) {
      OWLAnnotationProperty property = entity.asOWLAnnotationProperty();
      String propertyPrefixedName = getDroolsOWLNamedObject2DRLConverter().convert(property);
      recordOWLAxiom(new APDA(propertyPrefixedName));
    } else
      throw new TargetSWRLRuleEngineInternalException("unknown entity type " + entity.getClass().getCanonicalName()
          + " in OWL declaration axiom");
  }

  @Override
  public void convert(OWLClassAssertionAxiom axiom)
  {
    OWLClassExpression cls = axiom.getClassExpression();
    OWLIndividual individual = axiom.getIndividual();
    String classID = getDroolsOWLClassExpressionConverter().convert(cls);
    I i = getDroolsOWLIndividual2IConverter().convert(individual);
    CAA caa = new CAA(classID, i);

    recordOWLAxiom(caa);
  }

  @Override
  public void convert(OWLObjectPropertyAssertionAxiom axiom)
  {
    OWLObjectPropertyExpression property = axiom.getProperty();
    OWLIndividual subjectIndividual = axiom.getSubject();
    OWLIndividual objectIndividual = axiom.getObject();
    String propertyID = getDroolsOWLPropertyExpressionConverter().convert(property);
    I subjectI = getDroolsOWLIndividual2IConverter().convert(subjectIndividual);
    I objectI = getDroolsOWLIndividual2IConverter().convert(objectIndividual);
    OPAA opaa = new OPAA(subjectI, propertyID, objectI);

    recordOWLAxiom(opaa);
  }

  @Override
  public void convert(OWLDataPropertyAssertionAxiom axiom)
  {
    OWLDataPropertyExpression property = axiom.getProperty();
    OWLIndividual subjectIndividual = axiom.getSubject();
    OWLLiteral objectLiteral = axiom.getObject();
    String propertyID = getDroolsOWLPropertyExpressionConverter().convert(property);
    I subjectI = getDroolsOWLIndividual2IConverter().convert(subjectIndividual);
    L literal = getDroolsOWLLiteral2LConverter().convert(objectLiteral);
    DPAA dpaa = new DPAA(subjectI, propertyID, literal);

    recordOWLAxiom(dpaa);
  }

  @Override
  public void convert(OWLSameIndividualAxiom axiom)
  {
    if (!axiom.getIndividuals().isEmpty()) {
      for (OWLIndividual individual1 : axiom.getIndividuals()) {
        Set<OWLIndividual> sameIndividuals = new HashSet<>(axiom.getIndividuals());
        I i1 = getDroolsOWLIndividual2IConverter().convert(individual1);
        SIA sia = new SIA(i1, i1);
        sameIndividuals.remove(individual1);

        recordOWLAxiom(sia);
        for (OWLIndividual individual2 : sameIndividuals) {
          I i2 = getDroolsOWLIndividual2IConverter().convert(individual2);
          sia = new SIA(i1, i2);
          recordOWLAxiom(sia);
        }
      }
    }
  }

  @Override
  public void convert(OWLDifferentIndividualsAxiom axiom)
  {
    if (!axiom.getIndividuals().isEmpty()) {
      for (OWLIndividual individual1 : axiom.getIndividuals()) {
        Set<OWLIndividual> differentIndividuals = new HashSet<>(axiom.getIndividuals());
        I i1 = getDroolsOWLIndividual2IConverter().convert(individual1);
        differentIndividuals.remove(individual1);
        for (OWLIndividual individual2 : differentIndividuals) {
          I i2 = getDroolsOWLIndividual2IConverter().convert(individual2);
          DIA dia = new DIA(i1, i2);
          recordOWLAxiom(dia);
        }
      }
    }
  }

  @Override
  public void convert(OWLSubDataPropertyOfAxiom axiom)
  {
    OWLDataPropertyExpression subProperty = axiom.getSubProperty();
    OWLDataPropertyExpression superProperty = axiom.getSuperProperty();
    SDPA a = new SDPA(getDroolsOWLPropertyExpressionConverter().convert(subProperty),
        getDroolsOWLPropertyExpressionConverter().convert(superProperty));

    recordOWLAxiom(a);
  }

  @Override
  public void convert(OWLSubObjectPropertyOfAxiom axiom)
  {
    OWLObjectPropertyExpression subProperty = axiom.getSubProperty();
    OWLObjectPropertyExpression superProperty = axiom.getSuperProperty();
    SOPA a = new SOPA(getDroolsOWLPropertyExpressionConverter().convert(subProperty),
        getDroolsOWLPropertyExpressionConverter().convert(superProperty));

    recordOWLAxiom(a);
  }

  @Override
  public void convert(OWLInverseObjectPropertiesAxiom axiom)
  {
    OWLObjectPropertyExpression property1 = axiom.getFirstProperty();
    OWLObjectPropertyExpression property2 = axiom.getSecondProperty();
    IOPA a = new IOPA(getDroolsOWLPropertyExpressionConverter().convert(property1),
        getDroolsOWLPropertyExpressionConverter().convert(property2));

    recordOWLAxiom(a);
  }

  @Override
  public void convert(OWLSubClassOfAxiom axiom)
  {
    OWLClassExpression subClass = axiom.getSubClass();
    OWLClassExpression superClass = axiom.getSuperClass();
    SCA a = new SCA(getDroolsOWLClassExpressionConverter().convert(subClass), getDroolsOWLClassExpressionConverter()
        .convert(superClass));

    recordOWLAxiom(a);
  }

  @Override
  public void convert(OWLDisjointClassesAxiom axiom)
  {
    if (!axiom.getClassExpressions().isEmpty()) {
      for (OWLClassExpression class1 : axiom.getClassExpressions()) {
        Set<OWLClassExpression> disjointClasses = new HashSet<>(axiom.getClassExpressions());
        String class1ID = getDroolsOWLClassExpressionConverter().convert(class1);
        disjointClasses.remove(class1);
        for (OWLClassExpression class2 : disjointClasses) {
          String class2ID = getDroolsOWLClassExpressionConverter().convert(class2);
          DCA a = new DCA(class1ID, class2ID);
          recordOWLAxiom(a);
          a = new DCA(class2ID, class1ID);
          recordOWLAxiom(a);
        }
      }
    }
  }

  @Override
  public void convert(OWLEquivalentClassesAxiom axiom)
  {
    if (!axiom.getClassExpressions().isEmpty()) {
      for (OWLClassExpression class1 : axiom.getClassExpressions()) {
        Set<OWLClassExpression> equivalentClasses = new HashSet<>(axiom.getClassExpressions());
        String class1ID = getDroolsOWLClassExpressionConverter().convert(class1);
        equivalentClasses.remove(class1);
        for (OWLClassExpression class2 : equivalentClasses) {
          String class2ID = getDroolsOWLClassExpressionConverter().convert(class2);
          ECA a = new ECA(class1ID, class2ID);
          recordOWLAxiom(a);
        }
      }
    }
  }

  @Override
  public void convert(OWLEquivalentObjectPropertiesAxiom axiom)
  {
    if (!axiom.getProperties().isEmpty()) {
      for (OWLObjectPropertyExpression property1 : axiom.getProperties()) {
        Set<OWLObjectPropertyExpression> equivalentProperties = new HashSet<>(axiom.getProperties());
        String property1ID = getDroolsOWLPropertyExpressionConverter().convert(property1);
        equivalentProperties.remove(property1);
        for (OWLObjectPropertyExpression property2 : equivalentProperties) {
          String property2ID = getDroolsOWLPropertyExpressionConverter().convert(property2);
          EOPA a = new EOPA(property1ID, property2ID);
          recordOWLAxiom(a);
        }
      }
    }
  }

  @Override
  public void convert(OWLEquivalentDataPropertiesAxiom axiom)
  {
    if (!axiom.getProperties().isEmpty()) {
      for (OWLDataPropertyExpression property1 : axiom.getProperties()) {
        Set<OWLDataPropertyExpression> equivalentProperties = new HashSet<>(axiom.getProperties());
        String property1ID = getDroolsOWLPropertyExpressionConverter().convert(property1);
        equivalentProperties.remove(property1);
        for (OWLDataPropertyExpression property2 : equivalentProperties) {
          String property2ID = getDroolsOWLPropertyExpressionConverter().convert(property2);
          EDPA a = new EDPA(property1ID, property2ID);
          recordOWLAxiom(a);
        }
      }
    }
  }

  @Override
  public void convert(OWLDisjointObjectPropertiesAxiom axiom)
  {
    if (!axiom.getProperties().isEmpty()) {
      for (OWLObjectPropertyExpression property1 : axiom.getProperties()) {
        Set<OWLObjectPropertyExpression> disjointProperties = new HashSet<>(axiom.getProperties());
        String property1ID = getDroolsOWLPropertyExpressionConverter().convert(property1);
        disjointProperties.remove(property1);
        for (OWLObjectPropertyExpression property2 : disjointProperties) {
          String property2ID = getDroolsOWLPropertyExpressionConverter().convert(property2);
          EOPA a = new EOPA(property1ID, property2ID);
          recordOWLAxiom(a);
        }
      }
    }
  }

  @Override
  public void convert(OWLDisjointDataPropertiesAxiom axiom)
  {
    if (!axiom.getProperties().isEmpty()) {
      for (OWLDataPropertyExpression property1 : axiom.getProperties()) {
        Set<OWLDataPropertyExpression> disjointProperties = new HashSet<>(axiom.getProperties());
        String property1ID = getDroolsOWLPropertyExpressionConverter().convert(property1);
        disjointProperties.remove(property1);
        for (OWLDataPropertyExpression property2 : disjointProperties) {
          String property2ID = getDroolsOWLPropertyExpressionConverter().convert(property2);
          EOPA a = new EOPA(property1ID, property2ID);
          recordOWLAxiom(a);
        }
      }
    }
  }

  @Override
  public void convert(OWLObjectPropertyDomainAxiom axiom)
  {
    OWLObjectPropertyExpression property = axiom.getProperty();
    OWLClassExpression domain = axiom.getDomain();
    DOPA a = new DOPA(getDroolsOWLPropertyExpressionConverter().convert(property),
        getDroolsOWLClassExpressionConverter().convert(domain));

    recordOWLAxiom(a);
  }

  @Override
  public void convert(OWLDataPropertyDomainAxiom axiom)
  {
    OWLDataPropertyExpression property = axiom.getProperty();
    OWLClassExpression domain = axiom.getDomain();
    DDPA a = new DDPA(getDroolsOWLPropertyExpressionConverter().convert(property),
        getDroolsOWLClassExpressionConverter().convert(domain));

    recordOWLAxiom(a);
  }

  @Override
  public void convert(OWLObjectPropertyRangeAxiom axiom)
  {
    OWLObjectPropertyExpression property = axiom.getProperty();
    OWLClassExpression domain = axiom.getRange();
    OPRA a = new OPRA(getDroolsOWLPropertyExpressionConverter().convert(property),
        getDroolsOWLClassExpressionConverter().convert(domain));

    recordOWLAxiom(a);
  }

  @Override
  public void convert(OWLDataPropertyRangeAxiom axiom)
  {
    OWLDataPropertyExpression property = axiom.getProperty();
    OWLDataRange range = axiom.getRange();
    DPRA a = new DPRA(getDroolsOWLPropertyExpressionConverter().convert(property), getDroolsOWLDataRangeConverter()
        .convert(range));

    recordOWLAxiom(a);
  }

  @Override
  public void convert(OWLFunctionalObjectPropertyAxiom axiom)
  {
    OWLObjectPropertyExpression property = axiom.getProperty();
    FOPA fopa = new FOPA(getDroolsOWLPropertyExpressionConverter().convert(property));

    recordOWLAxiom(fopa);
  }

  @Override
  public void convert(OWLFunctionalDataPropertyAxiom axiom)
  {
    OWLDataPropertyExpression property = axiom.getProperty();
    FDPA fdpa = new FDPA(getDroolsOWLPropertyExpressionConverter().convert(property));

    recordOWLAxiom(fdpa);
  }

  @Override
  public void convert(OWLInverseFunctionalObjectPropertyAxiom axiom)
  {
    OWLObjectPropertyExpression property = axiom.getProperty();
    IFOPA ifopa = new IFOPA(getDroolsOWLPropertyExpressionConverter().convert(property));

    recordOWLAxiom(ifopa);
  }

  @Override
  public void convert(OWLIrreflexiveObjectPropertyAxiom axiom)
  {
    OWLObjectPropertyExpression property = axiom.getProperty();
    IROPA iropa = new IROPA(getDroolsOWLPropertyExpressionConverter().convert(property));

    recordOWLAxiom(iropa);
  }

  @Override
  public void convert(OWLTransitiveObjectPropertyAxiom axiom)
  {
    OWLObjectPropertyExpression property = axiom.getProperty();
    TOPA topa = new TOPA(getDroolsOWLPropertyExpressionConverter().convert(property));

    recordOWLAxiom(topa);
  }

  @Override
  public void convert(OWLSymmetricObjectPropertyAxiom axiom)
  {
    OWLObjectPropertyExpression property = axiom.getProperty();
    SPA spa = new SPA(getDroolsOWLPropertyExpressionConverter().convert(property));

    recordOWLAxiom(spa);
  }

  @Override
  public void convert(OWLAsymmetricObjectPropertyAxiom axiom)
  {
    OWLObjectPropertyExpression property = axiom.getProperty();
    AOPA aopa = new AOPA(getDroolsOWLPropertyExpressionConverter().convert(property));

    recordOWLAxiom(aopa);
  }

  @Override
  public void convert(OWLNegativeObjectPropertyAssertionAxiom axiom)
  {
    OWLObjectPropertyExpression property = axiom.getProperty();
    OWLIndividual subjectIndividual = axiom.getSubject();
    OWLIndividual objectIndividual = axiom.getObject();
    String propertyID = getDroolsOWLPropertyExpressionConverter().convert(property);
    I subjectI = getDroolsOWLIndividual2IConverter().convert(subjectIndividual);
    I objectI = getDroolsOWLIndividual2IConverter().convert(objectIndividual);
    NOPAA nopaa = new NOPAA(subjectI, propertyID, objectI);

    recordOWLAxiom(nopaa);
  }

  @Override
  public void convert(OWLNegativeDataPropertyAssertionAxiom axiom)
  {
    OWLDataPropertyExpression property = axiom.getProperty();
    OWLIndividual subjectIndividual = axiom.getSubject();
    OWLLiteral objectLiteral = axiom.getObject();
    String propertyID = getDroolsOWLPropertyExpressionConverter().convert(property);
    I subjectI = getDroolsOWLIndividual2IConverter().convert(subjectIndividual);
    L literal = getDroolsOWLLiteral2LConverter().convert(objectLiteral);
    NDPAA ndpaa = new NDPAA(subjectI, propertyID, literal);

    recordOWLAxiom(ndpaa);
  }

  @Override
  public void convert(OWLSubPropertyChainOfAxiom axiom)
  {
    // TODO sub property chain of axiom not implemented in Drools
  }

  @Override
  public void convert(OWLHasKeyAxiom axiom)
  {
    // TODO has key axiom not implemented in Drools
  }

  @Override
  public void convert(OWLReflexiveObjectPropertyAxiom axiom)
  {
    // We ignore because the OWL 2 RL reasoner does not reason with this axiom.
  }

  @Override
  public void convert(OWLDisjointUnionAxiom axiom)
  {
    // We ignore because the OWL 2 RL reasoner does not reason with this axiom.
  }

  @Override
  public void convert(OWLDatatypeDefinitionAxiom axiom)
  {
    // We ignore because the OWL 2 RL reasoner does not reason with this axiom.
  }

  @Override
  public void convert(OWLAnnotationAssertionAxiom axiom)
  {
    // We ignore because the OWL 2 RL reasoner does not reason with this axiom.
  }

  @Override
  public void convert(OWLAnnotationPropertyRangeAxiom axiom)
  {
    // We ignore because the OWL 2 RL reasoner does not reason with this axiom.
  }

  @Override
  public void convert(OWLAnnotationPropertyDomainAxiom axiom)
  {
    // We ignore because the OWL 2 RL reasoner does not reason with this axiom.
  }

  @Override
  public void convert(OWLSubAnnotationPropertyOfAxiom axiom)
  {
    // We ignore because we do not reason with this axiom.
  }

  public void convert(OWLAxiom axiom)
  {
    axiom.accept(this);
  }

  @Override
  public void visit(SWRLRule swrlRule)
  {
    if (swrlRule instanceof SWRLAPIRule)
      convert((SWRLAPIRule)swrlRule);
    else
      throw new TargetSWRLRuleEngineInternalException("Unexpected SWRL rule " + swrlRule + " - expecting SWRLAPIRule");
  }

  @Override
  public void visit(SWRLAPIRule swrlapiRule)
  {
    convert(swrlapiRule);
  }

  @Override
  public void visit(OWLDeclarationAxiom axiom)
  {
    convert(axiom);
  }

  @Override
  public void visit(OWLSubClassOfAxiom owlSubClassOfAxiom)
  {
    convert(owlSubClassOfAxiom);
  }

  @Override
  public void visit(OWLNegativeObjectPropertyAssertionAxiom owlNegativeObjectPropertyAssertionAxiom)
  {
    convert(owlNegativeObjectPropertyAssertionAxiom);
  }

  @Override
  public void visit(OWLAsymmetricObjectPropertyAxiom owlAsymmetricObjectPropertyAxiom)
  {
    convert(owlAsymmetricObjectPropertyAxiom);
  }

  @Override
  public void visit(OWLReflexiveObjectPropertyAxiom owlReflexiveObjectPropertyAxiom)
  {
    convert(owlReflexiveObjectPropertyAxiom);
  }

  @Override
  public void visit(OWLDisjointClassesAxiom owlDisjointClassesAxiom)
  {
    convert(owlDisjointClassesAxiom);
  }

  @Override
  public void visit(OWLDataPropertyDomainAxiom owlDataPropertyDomainAxiom)
  {
    convert(owlDataPropertyDomainAxiom);
  }

  @Override
  public void visit(OWLObjectPropertyDomainAxiom owlObjectPropertyDomainAxiom)
  {
    convert(owlObjectPropertyDomainAxiom);
  }

  @Override
  public void visit(OWLEquivalentObjectPropertiesAxiom owlEquivalentObjectPropertiesAxiom)
  {
    convert(owlEquivalentObjectPropertiesAxiom);
  }

  @Override
  public void visit(OWLNegativeDataPropertyAssertionAxiom owlNegativeDataPropertyAssertionAxiom)
  {
    convert(owlNegativeDataPropertyAssertionAxiom);
  }

  @Override
  public void visit(OWLDifferentIndividualsAxiom owlDifferentIndividualsAxiom)
  {
    convert(owlDifferentIndividualsAxiom);
  }

  @Override
  public void visit(OWLDisjointDataPropertiesAxiom owlDisjointDataPropertiesAxiom)
  {
    convert(owlDisjointDataPropertiesAxiom);
  }

  @Override
  public void visit(OWLDisjointObjectPropertiesAxiom owlDisjointObjectPropertiesAxiom)
  {
    convert(owlDisjointObjectPropertiesAxiom);
  }

  @Override
  public void visit(OWLObjectPropertyRangeAxiom owlObjectPropertyRangeAxiom)
  {
    convert(owlObjectPropertyRangeAxiom);
  }

  @Override
  public void visit(OWLObjectPropertyAssertionAxiom owlObjectPropertyAssertionAxiom)
  {
    convert(owlObjectPropertyAssertionAxiom);
  }

  @Override
  public void visit(OWLFunctionalObjectPropertyAxiom owlFunctionalObjectPropertyAxiom)
  {
    convert(owlFunctionalObjectPropertyAxiom);
  }

  @Override
  public void visit(OWLSubObjectPropertyOfAxiom owlSubObjectPropertyOfAxiom)
  {
    convert(owlSubObjectPropertyOfAxiom);
  }

  @Override
  public void visit(OWLDisjointUnionAxiom owlDisjointUnionAxiom)
  {
    convert(owlDisjointUnionAxiom);
  }

  @Override
  public void visit(OWLSymmetricObjectPropertyAxiom owlSymmetricObjectPropertyAxiom)
  {
    convert(owlSymmetricObjectPropertyAxiom);
  }

  @Override
  public void visit(OWLDataPropertyRangeAxiom owlDataPropertyRangeAxiom)
  {
    convert(owlDataPropertyRangeAxiom);
  }

  @Override
  public void visit(OWLFunctionalDataPropertyAxiom owlFunctionalDataPropertyAxiom)
  {
    convert(owlFunctionalDataPropertyAxiom);
  }

  @Override
  public void visit(OWLEquivalentDataPropertiesAxiom owlEquivalentDataPropertiesAxiom)
  {
    convert(owlEquivalentDataPropertiesAxiom);
  }

  @Override
  public void visit(OWLClassAssertionAxiom owlClassAssertionAxiom)
  {
    convert(owlClassAssertionAxiom);
  }

  @Override
  public void visit(OWLEquivalentClassesAxiom owlEquivalentClassesAxiom)
  {
    convert(owlEquivalentClassesAxiom);
  }

  @Override
  public void visit(OWLDataPropertyAssertionAxiom owlDataPropertyAssertionAxiom)
  {
    convert(owlDataPropertyAssertionAxiom);
  }

  @Override
  public void visit(OWLTransitiveObjectPropertyAxiom owlTransitiveObjectPropertyAxiom)
  {
    convert(owlTransitiveObjectPropertyAxiom);
  }

  @Override
  public void visit(OWLIrreflexiveObjectPropertyAxiom owlIrreflexiveObjectPropertyAxiom)
  {
    convert(owlIrreflexiveObjectPropertyAxiom);
  }

  @Override
  public void visit(OWLSubDataPropertyOfAxiom owlSubDataPropertyOfAxiom)
  {
    convert(owlSubDataPropertyOfAxiom);
  }

  @Override
  public void visit(OWLInverseFunctionalObjectPropertyAxiom owlInverseFunctionalObjectPropertyAxiom)
  {
    convert(owlInverseFunctionalObjectPropertyAxiom);
  }

  @Override
  public void visit(OWLSameIndividualAxiom owlSameIndividualAxiom)
  {
    convert(owlSameIndividualAxiom);
  }

  @Override
  public void visit(OWLSubPropertyChainOfAxiom owlSubPropertyChainOfAxiom)
  {
    convert(owlSubPropertyChainOfAxiom);
  }

  @Override
  public void visit(OWLInverseObjectPropertiesAxiom owlInverseObjectPropertiesAxiom)
  {
    convert(owlInverseObjectPropertiesAxiom);
  }

  @Override
  public void visit(OWLHasKeyAxiom owlHasKeyAxiom)
  {
    convert(owlHasKeyAxiom);
  }

  @Override
  public void visit(OWLDatatypeDefinitionAxiom owlDatatypeDefinitionAxiom)
  {
    convert(owlDatatypeDefinitionAxiom);
  }

  @Override
  public void visit(OWLAnnotationAssertionAxiom owlAnnotationAssertionAxiom)
  {

  }

  @Override
  public void visit(OWLSubAnnotationPropertyOfAxiom owlSubAnnotationPropertyOfAxiom)
  {

  }

  @Override
  public void visit(OWLAnnotationPropertyDomainAxiom owlAnnotationPropertyDomainAxiom)
  {

  }

  @Override
  public void visit(OWLAnnotationPropertyRangeAxiom owlAnnotationPropertyRangeAxiom)
  {

  }

  private void recordOWLAxiom(A a)
  {
    if (!this.assertedOWLAxioms.contains(a)) {
      // System.err.println("Axiom: " + a);
      this.assertedOWLAxioms.add(a);
    }
  }

  private DroolsOWLClassExpressionConverter getDroolsOWLClassExpressionConverter()
  {
    return this.classExpressionConverter;
  }

  private DroolsOWLPropertyExpressionConverter getDroolsOWLPropertyExpressionConverter()
  {
    return this.propertyExpressionConverter;
  }

  private DroolsSWRLRuleConverter getDroolsSWRLRuleConverter()
  {
    return this.swrlRuleConverter;
  }
}
