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
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLAxiomConverter;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.drools.DroolsSWRLRuleEngine;
import org.swrlapi.drools.owl.core.L;
import org.swrlapi.drools.owl.axioms.A;
import org.swrlapi.drools.owl.axioms.APA;
import org.swrlapi.drools.owl.axioms.APDA;
import org.swrlapi.drools.owl.axioms.CAA;
import org.swrlapi.drools.owl.axioms.CDA;
import org.swrlapi.drools.owl.axioms.DCA;
import org.swrlapi.drools.owl.axioms.DDPA;
import org.swrlapi.drools.owl.axioms.DIA;
import org.swrlapi.drools.owl.axioms.DOPA;
import org.swrlapi.drools.owl.axioms.DPAA;
import org.swrlapi.drools.owl.axioms.DPDA;
import org.swrlapi.drools.owl.axioms.ECA;
import org.swrlapi.drools.owl.axioms.EDPA;
import org.swrlapi.drools.owl.axioms.EOPA;
import org.swrlapi.drools.owl.axioms.FDPA;
import org.swrlapi.drools.owl.axioms.FOPA;
import org.swrlapi.drools.owl.axioms.IDA;
import org.swrlapi.drools.owl.axioms.IOPA;
import org.swrlapi.drools.owl.axioms.IPA;
import org.swrlapi.drools.owl.axioms.IRPA;
import org.swrlapi.drools.owl.axioms.OPAA;
import org.swrlapi.drools.owl.axioms.OPDA;
import org.swrlapi.drools.owl.axioms.DPRA;
import org.swrlapi.drools.owl.axioms.OPRA;
import org.swrlapi.drools.owl.axioms.SCA;
import org.swrlapi.drools.owl.axioms.SDPA;
import org.swrlapi.drools.owl.axioms.SIA;
import org.swrlapi.drools.owl.axioms.SOPA;
import org.swrlapi.drools.owl.axioms.SPA;
import org.swrlapi.drools.owl.axioms.TPA;
import org.swrlapi.drools.owl.core.I;
import org.swrlapi.drools.owl.classexpressions.CE;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class converts OWL axioms to their Drools representation.
 * <p>
 * The following axioms are not currently supported and are ignored:
 * <p>
 * OWLDatatypeDefinitionAxiom
 * OWLNegativeDataPropertyAssertionAxiom, OWLNegativeObjectPropertyAssertionAxiom
 * OWLReflexiveObjectPropertyAxiom OWLDisjointUnionAxiom
 * OWLSubPropertyChainOfAxiom OWLHasKeyAxiom
 * <p>
 * The following annotation axioms are ignored because they are not involved in reasoning:
 * <p>
 * OWLAnnotationAssertionAxiom OWLAnnotationPropertyRangeAxiom OWLAnnotationPropertyDomainAxiom OWLSubAnnotationPropertyOfAxiom
 *
 * <p>
 * Note that SWRL rules are also a type of OWL axiom so are also converted here.
 * 
 * @see OWLAxiom, A
 */
public class DroolsOWLAxiomConverter extends DroolsConverterBase implements TargetRuleEngineOWLAxiomConverter
{
	private final DroolsSWRLRuleConverter swrlRuleConverter;
	private final DroolsOWLClassExpressionConverter classExpressionConverter;
	private final DroolsOWLPropertyExpressionConverter propertyExpressionConverter;

	private final Set<A> assertedOWLAxioms;

	public DroolsOWLAxiomConverter(SWRLRuleEngineBridge bridge, DroolsSWRLRuleEngine droolsEngine)
	{
		super(bridge);

		this.swrlRuleConverter = new DroolsSWRLRuleConverter(bridge, droolsEngine);
		this.classExpressionConverter = new DroolsOWLClassExpressionConverter(bridge);
		this.propertyExpressionConverter = new DroolsOWLPropertyExpressionConverter(bridge);

		this.assertedOWLAxioms = new HashSet<A>();
	}

	public void reset()
	{
		this.assertedOWLAxioms.clear();

		getDroolsOWLClassExpressionConverter().reset();
		getDroolsOWLPropertyExpressionConverter().reset();
		getDroolsOWLDataRangeConverter().reset();
	}

	public Set<A> getAssertedOWLAxioms()
	{
		return new HashSet<A>(this.assertedOWLAxioms);
	}

	public Set<CE> getOWLClassExpressions()
	{
		return this.classExpressionConverter.getOWLClassExpressions();
	}

	public void convert(OWLAxiom axiom) throws TargetRuleEngineException
	{ // TODO Get rid of these instanceof with visitor
		if (axiom instanceof SWRLAPIRule) {
			convert((SWRLAPIRule)axiom);
		} else if (axiom instanceof OWLDeclarationAxiom) {
			convert((OWLDeclarationAxiom)axiom);
		} else if (axiom instanceof OWLClassAssertionAxiom) {
			convert((OWLClassAssertionAxiom)axiom);
		} else if (axiom instanceof OWLObjectPropertyAssertionAxiom) {
			convert((OWLObjectPropertyAssertionAxiom)axiom);
		} else if (axiom instanceof OWLDataPropertyAssertionAxiom) {
			convert((OWLDataPropertyAssertionAxiom)axiom);
		} else if (axiom instanceof OWLSameIndividualAxiom) {
			convert((OWLSameIndividualAxiom)axiom);
		} else if (axiom instanceof OWLDifferentIndividualsAxiom) {
			convert((OWLDifferentIndividualsAxiom)axiom);
		} else if (axiom instanceof OWLSubClassOfAxiom) {
			convert((OWLSubClassOfAxiom)axiom);
		} else if (axiom instanceof OWLDisjointClassesAxiom) {
			convert((OWLDisjointClassesAxiom)axiom);
		} else if (axiom instanceof OWLEquivalentClassesAxiom) {
			convert((OWLEquivalentClassesAxiom)axiom);
		} else if (axiom instanceof OWLSubObjectPropertyOfAxiom) {
			convert((OWLSubObjectPropertyOfAxiom)axiom);
		} else if (axiom instanceof OWLSubDataPropertyOfAxiom) {
			convert((OWLSubDataPropertyOfAxiom)axiom);
		} else if (axiom instanceof OWLEquivalentObjectPropertiesAxiom) {
			convert((OWLEquivalentObjectPropertiesAxiom)axiom);
		} else if (axiom instanceof OWLEquivalentDataPropertiesAxiom) {
			convert((OWLEquivalentDataPropertiesAxiom)axiom);
		} else if (axiom instanceof OWLDisjointObjectPropertiesAxiom) {
			convert((OWLDisjointObjectPropertiesAxiom)axiom);
		} else if (axiom instanceof OWLDisjointDataPropertiesAxiom) {
			convert((OWLDisjointDataPropertiesAxiom)axiom);
		} else if (axiom instanceof OWLObjectPropertyDomainAxiom) {
			convert((OWLObjectPropertyDomainAxiom)axiom);
		} else if (axiom instanceof OWLDataPropertyDomainAxiom) {
			convert((OWLDataPropertyDomainAxiom)axiom);
		} else if (axiom instanceof OWLObjectPropertyRangeAxiom) {
			convert((OWLObjectPropertyRangeAxiom)axiom);
		} else if (axiom instanceof OWLDataPropertyRangeAxiom) {
			convert((OWLDataPropertyRangeAxiom)axiom);
		} else if (axiom instanceof OWLTransitiveObjectPropertyAxiom) {
			convert((OWLTransitiveObjectPropertyAxiom)axiom);
		} else if (axiom instanceof OWLSymmetricObjectPropertyAxiom) {
			convert((OWLSymmetricObjectPropertyAxiom)axiom);
		} else if (axiom instanceof OWLFunctionalObjectPropertyAxiom) {
			convert((OWLFunctionalObjectPropertyAxiom)axiom);
		} else if (axiom instanceof OWLFunctionalDataPropertyAxiom) {
			convert((OWLFunctionalDataPropertyAxiom)axiom);
		} else if (axiom instanceof OWLInverseFunctionalObjectPropertyAxiom) {
			convert((OWLInverseFunctionalObjectPropertyAxiom)axiom);
		} else if (axiom instanceof OWLInverseObjectPropertiesAxiom) {
			convert((OWLInverseObjectPropertiesAxiom)axiom);
		} else if (axiom instanceof OWLIrreflexiveObjectPropertyAxiom) {
			convert((OWLIrreflexiveObjectPropertyAxiom)axiom);
		} else if (axiom instanceof OWLAsymmetricObjectPropertyAxiom) {
			convert((OWLAsymmetricObjectPropertyAxiom)axiom);
		} else if (axiom instanceof OWLNegativeDataPropertyAssertionAxiom) {
			convert((OWLNegativeDataPropertyAssertionAxiom)axiom);
		} else if (axiom instanceof OWLNegativeObjectPropertyAssertionAxiom) {
			convert((OWLNegativeObjectPropertyAssertionAxiom)axiom);
		} else if (axiom instanceof OWLReflexiveObjectPropertyAxiom) {
			convert((OWLReflexiveObjectPropertyAxiom)axiom);
		} else if (axiom instanceof OWLDisjointUnionAxiom) {
			convert((OWLDisjointUnionAxiom)axiom);
		} else if (axiom instanceof OWLAnnotationAssertionAxiom) {
			convert((OWLAnnotationAssertionAxiom)axiom);
		} else if (axiom instanceof OWLSubPropertyChainOfAxiom) {
			convert((OWLSubPropertyChainOfAxiom)axiom);
		} else if (axiom instanceof OWLHasKeyAxiom) {
			convert((OWLHasKeyAxiom)axiom);
		} else if (axiom instanceof OWLDatatypeDefinitionAxiom) {
			convert((OWLDatatypeDefinitionAxiom)axiom);
		} else if (axiom instanceof OWLAnnotationPropertyRangeAxiom) {
			convert((OWLAnnotationPropertyRangeAxiom)axiom);
		} else if (axiom instanceof OWLAnnotationPropertyDomainAxiom) {
			convert((OWLAnnotationPropertyDomainAxiom)axiom);
		} else if (axiom instanceof OWLSubAnnotationPropertyOfAxiom) {
			convert((OWLSubAnnotationPropertyOfAxiom)axiom);
		} else
			throw new RuntimeException("unknown OWL axiom type " + axiom.getClass().getCanonicalName());
	}

	@Override
	public void convert(SWRLAPIRule rule) throws TargetRuleEngineException
	{
		getDroolsSWRLRuleConverter().convert(rule);
	}

	@Override
	public void convert(OWLDeclarationAxiom axiom) throws TargetRuleEngineException
	{
		OWLEntity entity = axiom.getEntity();

		if (entity.isOWLClass()) {
			OWLClass cls = axiom.getEntity().asOWLClass();
			String classPrefixedName = getDroolsOWLNamedObject2DRLConverter().convert(cls);
			recordOWLAxiom(new CDA(classPrefixedName));
			getOWLClassExpressionResolver().recordOWLClassExpression(classPrefixedName, cls);
		} else if (entity.isOWLNamedIndividual()) {
			OWLNamedIndividual individual = entity.asOWLNamedIndividual();
			String individualPrefixedName = getDroolsOWLNamedObject2DRLConverter().convert(individual);
			recordOWLAxiom(new IDA(individualPrefixedName));
		} else if (entity.isOWLObjectProperty()) {
			OWLObjectProperty property = entity.asOWLObjectProperty();
			String propertyPrefixedName = getDroolsOWLNamedObject2DRLConverter().convert(property);
			recordOWLAxiom(new OPDA(propertyPrefixedName));
		} else if (entity.isOWLDataProperty()) {
			OWLDataProperty property = entity.asOWLDataProperty();
			String propertyPrefixedName = getDroolsOWLNamedObject2DRLConverter().convert(property);
			recordOWLAxiom(new DPDA(propertyPrefixedName));
		} else if (entity.isOWLAnnotationProperty()) {
			OWLAnnotationProperty property = entity.asOWLAnnotationProperty();
			String propertyPrefixedName = getDroolsOWLNamedObject2DRLConverter().convert(property);
			recordOWLAxiom(new APDA(propertyPrefixedName));
		} else
			throw new RuntimeException("unknown entity type " + entity.getClass().getCanonicalName()
					+ " in OWL declaration axiom");
	}

	@Override
	public void convert(OWLClassAssertionAxiom axiom) throws TargetRuleEngineException
	{
		OWLClassExpression cls = axiom.getClassExpression();
		OWLIndividual individual = axiom.getIndividual();
		String classID = getDroolsOWLClassExpressionConverter().convert(cls);
		I i = getDroolsOWLIndividual2IConverter().convert(individual);
		CAA caa = new CAA(classID, i);

		recordOWLAxiom(caa);
	}

	@Override
	public void convert(OWLObjectPropertyAssertionAxiom axiom) throws TargetRuleEngineException
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
	public void convert(OWLDataPropertyAssertionAxiom axiom) throws TargetRuleEngineException
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
	public void convert(OWLSameIndividualAxiom axiom) throws TargetRuleEngineException
	{
		if (!axiom.getIndividuals().isEmpty()) {
			for (OWLIndividual individual1 : axiom.getIndividuals()) {
				Set<OWLIndividual> sameIndividuals = new HashSet<OWLIndividual>(axiom.getIndividuals());
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
	public void convert(OWLDifferentIndividualsAxiom axiom) throws TargetRuleEngineException
	{
		if (!axiom.getIndividuals().isEmpty()) {
			for (OWLIndividual individual1 : axiom.getIndividuals()) {
				Set<OWLIndividual> differentIndividuals = new HashSet<OWLIndividual>(axiom.getIndividuals());
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
	public void convert(OWLSubDataPropertyOfAxiom axiom) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression subProperty = axiom.getSubProperty();
		OWLDataPropertyExpression superProperty = axiom.getSuperProperty();
		SDPA a = new SDPA(getDroolsOWLPropertyExpressionConverter().convert(subProperty),
				getDroolsOWLPropertyExpressionConverter().convert(superProperty));

		recordOWLAxiom(a);
	}

	@Override
	public void convert(OWLSubObjectPropertyOfAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression subProperty = axiom.getSubProperty();
		OWLObjectPropertyExpression superProperty = axiom.getSuperProperty();
		SOPA a = new SOPA(getDroolsOWLPropertyExpressionConverter().convert(subProperty),
				getDroolsOWLPropertyExpressionConverter().convert(superProperty));

		recordOWLAxiom(a);
	}

	@Override
	public void convert(OWLInverseObjectPropertiesAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property1 = axiom.getFirstProperty();
		OWLObjectPropertyExpression property2 = axiom.getSecondProperty();
		IOPA a = new IOPA(getDroolsOWLPropertyExpressionConverter().convert(property1),
				getDroolsOWLPropertyExpressionConverter().convert(property2));

		recordOWLAxiom(a);
	}

	@Override
	public void convert(OWLSubClassOfAxiom axiom) throws TargetRuleEngineException
	{
		OWLClassExpression subClass = axiom.getSubClass();
		OWLClassExpression superClass = axiom.getSuperClass();
		SCA a = new SCA(getDroolsOWLClassExpressionConverter().convert(subClass), getDroolsOWLClassExpressionConverter()
				.convert(superClass));

		recordOWLAxiom(a);
	}

	@Override
	public void convert(OWLDisjointClassesAxiom axiom) throws TargetRuleEngineException
	{
		if (!axiom.getClassExpressions().isEmpty()) {
			for (OWLClassExpression class1 : axiom.getClassExpressions()) {
				Set<OWLClassExpression> disjointClasses = new HashSet<OWLClassExpression>(axiom.getClassExpressions());
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
	public void convert(OWLEquivalentClassesAxiom axiom) throws TargetRuleEngineException
	{
		if (!axiom.getClassExpressions().isEmpty()) {
			for (OWLClassExpression class1 : axiom.getClassExpressions()) {
				Set<OWLClassExpression> equivalentClasses = new HashSet<OWLClassExpression>(axiom.getClassExpressions());
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
	public void convert(OWLEquivalentObjectPropertiesAxiom axiom) throws TargetRuleEngineException
	{
		if (!axiom.getProperties().isEmpty()) {
			for (OWLObjectPropertyExpression property1 : axiom.getProperties()) {
				Set<OWLObjectPropertyExpression> equivalentProperties = new HashSet<OWLObjectPropertyExpression>(
						axiom.getProperties());
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
	public void convert(OWLEquivalentDataPropertiesAxiom axiom) throws TargetRuleEngineException
	{
		if (!axiom.getProperties().isEmpty()) {
			for (OWLDataPropertyExpression property1 : axiom.getProperties()) {
				Set<OWLDataPropertyExpression> equivalentProperties = new HashSet<OWLDataPropertyExpression>(
						axiom.getProperties());
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
	public void convert(OWLDisjointObjectPropertiesAxiom axiom) throws TargetRuleEngineException
	{
		if (!axiom.getProperties().isEmpty()) {
			for (OWLObjectPropertyExpression property1 : axiom.getProperties()) {
				Set<OWLObjectPropertyExpression> disjointProperties = new HashSet<OWLObjectPropertyExpression>(
						axiom.getProperties());
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
	public void convert(OWLDisjointDataPropertiesAxiom axiom) throws TargetRuleEngineException
	{
		if (!axiom.getProperties().isEmpty()) {
			for (OWLDataPropertyExpression property1 : axiom.getProperties()) {
				Set<OWLDataPropertyExpression> disjointProperties = new HashSet<OWLDataPropertyExpression>(
						axiom.getProperties());
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
	public void convert(OWLObjectPropertyDomainAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		OWLClassExpression domain = axiom.getDomain();
		DOPA a = new DOPA(getDroolsOWLPropertyExpressionConverter().convert(property),
				getDroolsOWLClassExpressionConverter().convert(domain));

		recordOWLAxiom(a);
	}

	@Override
	public void convert(OWLDataPropertyDomainAxiom axiom) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression property = axiom.getProperty();
		OWLClassExpression domain = axiom.getDomain();
		DDPA a = new DDPA(getDroolsOWLPropertyExpressionConverter().convert(property),
				getDroolsOWLClassExpressionConverter().convert(domain));

		recordOWLAxiom(a);
	}

	@Override
	public void convert(OWLObjectPropertyRangeAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		OWLClassExpression domain = axiom.getRange();
		OPRA a = new OPRA(getDroolsOWLPropertyExpressionConverter().convert(property),
				getDroolsOWLClassExpressionConverter().convert(domain));

		recordOWLAxiom(a);
	}

	@Override
	public void convert(OWLDataPropertyRangeAxiom axiom) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression property = axiom.getProperty();
		OWLDataRange range = axiom.getRange();
		DPRA a = new DPRA(getDroolsOWLPropertyExpressionConverter().convert(property), getDroolsOWLDataRangeConverter()
				.convert(range));

		recordOWLAxiom(a);
	}

	@Override
	public void convert(OWLFunctionalObjectPropertyAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		FOPA fopa = new FOPA(getDroolsOWLPropertyExpressionConverter().convert(property));

		recordOWLAxiom(fopa);
	}

	@Override
	public void convert(OWLFunctionalDataPropertyAxiom axiom) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression property = axiom.getProperty();
		FDPA fdpa = new FDPA(getDroolsOWLPropertyExpressionConverter().convert(property));

		recordOWLAxiom(fdpa);
	}

	@Override
	public void convert(OWLInverseFunctionalObjectPropertyAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		IPA ipa = new IPA(getDroolsOWLPropertyExpressionConverter().convert(property));

		recordOWLAxiom(ipa);
	}

	@Override
	public void convert(OWLIrreflexiveObjectPropertyAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		IRPA irpa = new IRPA(getDroolsOWLPropertyExpressionConverter().convert(property));

		recordOWLAxiom(irpa);
	}

	@Override
	public void convert(OWLTransitiveObjectPropertyAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		TPA tpa = new TPA(getDroolsOWLPropertyExpressionConverter().convert(property));

		recordOWLAxiom(tpa);
	}

	@Override
	public void convert(OWLSymmetricObjectPropertyAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		SPA spa = new SPA(getDroolsOWLPropertyExpressionConverter().convert(property));

		recordOWLAxiom(spa);
	}

	@Override
	public void convert(OWLAsymmetricObjectPropertyAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		APA apa = new APA(getDroolsOWLPropertyExpressionConverter().convert(property));

		recordOWLAxiom(apa);
	}

	@Override
	public void convert(OWLNegativeDataPropertyAssertionAxiom axiom) throws TargetRuleEngineException
	{
		// We ignore because we do not currently reason with this axiom.
	}

	@Override
	public void convert(OWLNegativeObjectPropertyAssertionAxiom axiom) throws TargetRuleEngineException
	{
		// We ignore because we do not currently reason with this axiom.
	}

	@Override
	public void convert(OWLReflexiveObjectPropertyAxiom axiom) throws TargetRuleEngineException
	{
		// We ignore because we do not currently reason with this axiom.
	}

	@Override
	public void convert(OWLDisjointUnionAxiom axiom) throws TargetRuleEngineException
	{
		// We ignore because we do not currently reason with this axiom.
	}

	@Override
	public void convert(OWLSubPropertyChainOfAxiom axiom) throws TargetRuleEngineException
	{
		// We ignore because we do not currently reason with this axiom.
	}

	@Override
	public void convert(OWLHasKeyAxiom axiom) throws TargetRuleEngineException
	{
		// We ignore because we do not currently reason with this axiom.
	}

	@Override
	public void convert(OWLDatatypeDefinitionAxiom axiom) throws TargetRuleEngineException
	{
		// We ignore because we do not currently reason with this axiom.
	}

	@Override
	public void convert(OWLAnnotationAssertionAxiom axiom) throws TargetRuleEngineException
	{
		// We ignore because we do not currently reason with this axiom.
	}

	@Override
	public void convert(OWLAnnotationPropertyRangeAxiom axiom) throws TargetRuleEngineException
	{
		// We ignore because we do not currently reason with this axiom.
	}

	@Override
	public void convert(OWLAnnotationPropertyDomainAxiom axiom) throws TargetRuleEngineException
	{
		// We ignore because we do not currently reason with this axiom.
	}

	@Override
	public void convert(OWLSubAnnotationPropertyOfAxiom axiom) throws TargetRuleEngineException
	{
		// We ignore because we do not currently reason with this axiom.
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
