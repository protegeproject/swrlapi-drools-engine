package org.swrlapi.drools.converters;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
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
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
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
import org.semanticweb.owlapi.model.SWRLAtom;
import org.swrlapi.converters.TargetRuleEngineOWLAxiomConverter;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.drools.DroolsSWRLRuleEngine;
import org.swrlapi.drools.owl.L;
import org.swrlapi.drools.owl.axioms.A;
import org.swrlapi.drools.owl.axioms.APA;
import org.swrlapi.drools.owl.axioms.CAA;
import org.swrlapi.drools.owl.axioms.CDA;
import org.swrlapi.drools.owl.axioms.DCA;
import org.swrlapi.drools.owl.axioms.DDPA;
import org.swrlapi.drools.owl.axioms.DIA;
import org.swrlapi.drools.owl.axioms.DOPA;
import org.swrlapi.drools.owl.axioms.DPAA;
import org.swrlapi.drools.owl.axioms.ECA;
import org.swrlapi.drools.owl.axioms.EDPA;
import org.swrlapi.drools.owl.axioms.EOPA;
import org.swrlapi.drools.owl.axioms.FDPA;
import org.swrlapi.drools.owl.axioms.FOPA;
import org.swrlapi.drools.owl.axioms.IOPA;
import org.swrlapi.drools.owl.axioms.IPA;
import org.swrlapi.drools.owl.axioms.IRPA;
import org.swrlapi.drools.owl.axioms.OPAA;
import org.swrlapi.drools.owl.axioms.RDPA;
import org.swrlapi.drools.owl.axioms.ROPA;
import org.swrlapi.drools.owl.axioms.SCA;
import org.swrlapi.drools.owl.axioms.SDPA;
import org.swrlapi.drools.owl.axioms.SIA;
import org.swrlapi.drools.owl.axioms.SOPA;
import org.swrlapi.drools.owl.axioms.SPA;
import org.swrlapi.drools.owl.axioms.TPA;
import org.swrlapi.drools.owl.entities.I;
import org.swrlapi.drools.owl.expressions.CE;
import org.swrlapi.exceptions.TargetRuleEngineException;
import org.swrlapi.ext.SWRLAPIRule;

/**
 * This class converts OWL axioms to their Drools representation, which is the class {@link A}. Not all OWL axioms are
 * currently supported. These axioms are ignored. Supported axioms are targeted to the requirements of an OWL 2 RL
 * reasoner. Some axioms (e.g., annotation axioms) may never be reasoned with so are unlikely to require conversion to
 * Drools.
 */
public class DroolsOWLAxiom2AConverter extends DroolsConverterBase implements TargetRuleEngineOWLAxiomConverter
{
	private final DroolsSWRLBodyAtom2DRLConverter bodyAtomConverter;
	private final DroolsSWRLHeadAtom2DRLConverter headAtomConverter;
	private final DroolsOWLClassExpressionConverter classExpressionConverter;
	private final DroolsOWLPropertyExpressionConverter propertyExpressionConverter;
	private final DroolsSWRLRuleEngine droolsEngine;

	private final Set<A> nonRuleAssertedOWLAxioms;

	public DroolsOWLAxiom2AConverter(SWRLRuleEngineBridge bridge, DroolsSWRLRuleEngine droolsEngine)
	{
		super(bridge);

		this.bodyAtomConverter = new DroolsSWRLBodyAtom2DRLConverter(bridge);
		this.headAtomConverter = new DroolsSWRLHeadAtom2DRLConverter(bridge);

		this.classExpressionConverter = new DroolsOWLClassExpressionConverter(bridge);
		this.propertyExpressionConverter = new DroolsOWLPropertyExpressionConverter(bridge);

		this.droolsEngine = droolsEngine;

		this.nonRuleAssertedOWLAxioms = new HashSet<A>();
	}

	public void reset()
	{
		this.nonRuleAssertedOWLAxioms.clear();
		getSWRLBodyAtomConverter().reset();
		getSWRLHeadAtomConverter().reset();
		getDroolsOWLClassExpressionConverter().reset();
		getDroolsOWLPropertyExpressionConverter().reset();
	}

	public Set<A> getNonRuleAssertedOWLAxioms()
	{
		return this.nonRuleAssertedOWLAxioms;
	}

	public Set<CE> getOWLClassExpressions()
	{
		return this.classExpressionConverter.getOWLClassExpressions();
	}

	// TODO Get rid of these instanceof with visitor
	public void convert(OWLAxiom axiom) throws TargetRuleEngineException
	{
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
		swrlRule2DRL(rule);
	}

	@Override
	public void convert(OWLDeclarationAxiom axiom) throws TargetRuleEngineException
	{
		String classID = getDroolsOWLNamedObject2DRLConverter().convert(axiom.getEntity());

		createNonRuleOWLAxiom(new CDA(classID));

		getOWLClassExpressionResolver().recordOWLClassExpression(classID, axiom.getEntity().asOWLClass());
	}

	@Override
	public void convert(OWLClassAssertionAxiom axiom) throws TargetRuleEngineException
	{
		OWLClassExpression cls = axiom.getClassExpression();
		OWLIndividual individual = axiom.getIndividual();
		String classID = getDroolsOWLClassExpressionConverter().convert(cls);
		String individualID = getDroolsOWLIndividual2DRLConverter().convert(individual);
		CAA caa = new CAA(classID, new I(individualID));

		createNonRuleOWLAxiom(caa);
	}

	@Override
	public void convert(OWLObjectPropertyAssertionAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		OWLIndividual subjectIndividual = axiom.getSubject();
		OWLIndividual objectIndividual = axiom.getObject();
		String propertyID = getDroolsOWLPropertyExpressionConverter().convert(property);
		String subjectIndividualID = getDroolsOWLIndividual2DRLConverter().convert(subjectIndividual);
		String objectIndividualID = getDroolsOWLIndividual2DRLConverter().convert(objectIndividual);
		OPAA opaa = new OPAA(subjectIndividualID, propertyID, objectIndividualID);

		createNonRuleOWLAxiom(opaa);
	}

	@Override
	public void convert(OWLDataPropertyAssertionAxiom axiom) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression property = axiom.getProperty();
		OWLIndividual subjectIndividual = axiom.getSubject();
		OWLLiteral objectLiteral = axiom.getObject();
		String propertyID = getDroolsOWLPropertyExpressionConverter().convert(property);
		String subjectIndividualID = getDroolsOWLIndividual2DRLConverter().convert(subjectIndividual);
		L literal = getDroolsOWLLiteral2LConverter().convert(objectLiteral);
		DPAA dpaa = new DPAA(subjectIndividualID, propertyID, literal);

		createNonRuleOWLAxiom(dpaa);
	}

	@Override
	public void convert(OWLSameIndividualAxiom axiom) throws TargetRuleEngineException
	{
		if (!axiom.getIndividuals().isEmpty()) {
			for (OWLIndividual individual1 : axiom.getIndividuals()) {
				Set<OWLIndividual> sameIndividuals = new HashSet<OWLIndividual>(axiom.getIndividuals());
				String individual1ID = getDroolsOWLIndividual2DRLConverter().convert(individual1);
				SIA sia = new SIA(individual1ID, individual1ID);
				sameIndividuals.remove(individual1);

				createNonRuleOWLAxiom(sia);
				for (OWLIndividual individual2 : sameIndividuals) {
					String individual2ID = getDroolsOWLIndividual2DRLConverter().convert(individual2);
					sia = new SIA(individual1ID, individual2ID);
					createNonRuleOWLAxiom(sia);
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
				String individual1ID = getDroolsOWLIndividual2DRLConverter().convert(individual1);
				differentIndividuals.remove(individual1);
				for (OWLIndividual individual2 : differentIndividuals) {
					String individual2ID = getDroolsOWLIndividual2DRLConverter().convert(individual2);
					DIA dia = new DIA(individual1ID, individual2ID);
					createNonRuleOWLAxiom(dia);
				}
			}
		}
	}

	@Override
	public void convert(OWLSubDataPropertyOfAxiom axiom) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression subProperty = axiom.getSubProperty();
		OWLDataPropertyExpression superProperty = axiom.getSuperProperty();
		SDPA a = new SDPA(getDroolsOWLPropertyExpressionConverter().convert(subProperty), getDroolsOWLPropertyExpressionConverter()
				.convert(superProperty));

		createNonRuleOWLAxiom(a);
	}

	@Override
	public void convert(OWLSubObjectPropertyOfAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression subProperty = axiom.getSubProperty();
		OWLObjectPropertyExpression superProperty = axiom.getSuperProperty();
		SOPA a = new SOPA(getDroolsOWLPropertyExpressionConverter().convert(subProperty), getDroolsOWLPropertyExpressionConverter()
				.convert(superProperty));

		createNonRuleOWLAxiom(a);
	}

	@Override
	public void convert(OWLInverseObjectPropertiesAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property1 = axiom.getFirstProperty();
		OWLObjectPropertyExpression property2 = axiom.getSecondProperty();
		IOPA a = new IOPA(getDroolsOWLPropertyExpressionConverter().convert(property1), getDroolsOWLPropertyExpressionConverter()
				.convert(property2));

		createNonRuleOWLAxiom(a);
	}

	@Override
	public void convert(OWLSubClassOfAxiom axiom) throws TargetRuleEngineException
	{
		OWLClassExpression subClass = axiom.getSubClass();
		OWLClassExpression superClass = axiom.getSuperClass();
		SCA a = new SCA(getDroolsOWLClassExpressionConverter().convert(subClass), getDroolsOWLClassExpressionConverter().convert(
				superClass));

		createNonRuleOWLAxiom(a);
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
					createNonRuleOWLAxiom(a);
					a = new DCA(class2ID, class1ID);
					createNonRuleOWLAxiom(a);
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
					createNonRuleOWLAxiom(a);
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
					createNonRuleOWLAxiom(a);
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
					createNonRuleOWLAxiom(a);
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
					createNonRuleOWLAxiom(a);
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
					createNonRuleOWLAxiom(a);
				}
			}
		}
	}

	@Override
	public void convert(OWLObjectPropertyDomainAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		OWLClassExpression domain = axiom.getDomain();
		DOPA a = new DOPA(getDroolsOWLPropertyExpressionConverter().convert(property), getDroolsOWLClassExpressionConverter().convert(
				domain));

		createNonRuleOWLAxiom(a);
	}

	@Override
	public void convert(OWLDataPropertyDomainAxiom axiom) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression property = axiom.getProperty();
		OWLClassExpression domain = axiom.getDomain();
		DDPA a = new DDPA(getDroolsOWLPropertyExpressionConverter().convert(property), getDroolsOWLClassExpressionConverter().convert(
				domain));

		createNonRuleOWLAxiom(a);
	}

	@Override
	public void convert(OWLObjectPropertyRangeAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		OWLClassExpression domain = axiom.getRange();
		ROPA a = new ROPA(getDroolsOWLPropertyExpressionConverter().convert(property), getDroolsOWLClassExpressionConverter().convert(
				domain));

		createNonRuleOWLAxiom(a);
	}

	@Override
	public void convert(OWLDataPropertyRangeAxiom axiom) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression property = axiom.getProperty();
		OWLDataRange range = axiom.getRange();
		RDPA a = new RDPA(getDroolsOWLPropertyExpressionConverter().convert(property), getDroolsOWLDataRange2DRLConverter()
				.convert(range));

		createNonRuleOWLAxiom(a);
	}

	@Override
	public void convert(OWLFunctionalObjectPropertyAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		FOPA fopa = new FOPA(getDroolsOWLPropertyExpressionConverter().convert(property));

		createNonRuleOWLAxiom(fopa);
	}

	@Override
	public void convert(OWLFunctionalDataPropertyAxiom axiom) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression property = axiom.getProperty();
		FDPA fdpa = new FDPA(getDroolsOWLPropertyExpressionConverter().convert(property));

		createNonRuleOWLAxiom(fdpa);
	}

	@Override
	public void convert(OWLInverseFunctionalObjectPropertyAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		IPA ipa = new IPA(getDroolsOWLPropertyExpressionConverter().convert(property));

		createNonRuleOWLAxiom(ipa);
	}

	@Override
	public void convert(OWLIrreflexiveObjectPropertyAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		IRPA irpa = new IRPA(getDroolsOWLPropertyExpressionConverter().convert(property));

		createNonRuleOWLAxiom(irpa);
	}

	@Override
	public void convert(OWLTransitiveObjectPropertyAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		TPA tpa = new TPA(getDroolsOWLPropertyExpressionConverter().convert(property));

		createNonRuleOWLAxiom(tpa);
	}

	@Override
	public void convert(OWLSymmetricObjectPropertyAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		SPA spa = new SPA(getDroolsOWLPropertyExpressionConverter().convert(property));

		createNonRuleOWLAxiom(spa);
	}

	@Override
	public void convert(OWLAsymmetricObjectPropertyAxiom axiom) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = axiom.getProperty();
		APA apa = new APA(getDroolsOWLPropertyExpressionConverter().convert(property));

		createNonRuleOWLAxiom(apa);
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
	public void convert(OWLAnnotationAssertionAxiom axiom) throws TargetRuleEngineException
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

	private void swrlRule2DRL(SWRLAPIRule rule) throws TargetRuleEngineException
	{
		String ruleName = rule.getRuleName();
		String drlRule = getRulePreamble(ruleName);
		Set<String> variableNames = new HashSet<String>();

		getSWRLBodyAtomConverter().reset();
		getSWRLHeadAtomConverter().reset();

		for (SWRLAtom atom : rule.getBodyAtoms())
			drlRule += "\n   " + getSWRLBodyAtomConverter().convert(atom, variableNames) + " ";

		drlRule = addRuleThenClause(drlRule);

		// TODO HACK!!! Need to reference these variables before use or will get null pointer error when invoking built-ins
		// for (String variableName : variableNames)
		// drlRule += "$" + variableName + ";";

		for (SWRLAtom atom : rule.getHeadAtoms())
			drlRule += "\n   " + getSWRLHeadAtomConverter().convert(atom) + " ";

		drlRule = addRuleEndClause(drlRule);

		// System.err.println("----------------------------------------------------------------------------------------------------");
		// System.err.println("SWRL: " + rule.getRuleText());
		// System.err.println("DRL:\n" + drlRule);
		getDroolsEngine().defineDRLRule(ruleName, drlRule);
	}

	private String getRulePreamble(String ruleName)
	{
		return "rule \"" + ruleName + "\" \nwhen ";
	}

	private String addRuleEndClause(String ruleText)
	{
		return ruleText + "\nend";
	}

	private String addRuleThenClause(String ruleText)
	{
		return ruleText + "\nthen ";
	}

	private DroolsSWRLBodyAtom2DRLConverter getSWRLBodyAtomConverter()
	{
		return this.bodyAtomConverter;
	}

	private DroolsSWRLHeadAtom2DRLConverter getSWRLHeadAtomConverter()
	{
		return this.headAtomConverter;
	}

	private void createNonRuleOWLAxiom(A a)
	{
		if (!this.nonRuleAssertedOWLAxioms.contains(a))
			this.nonRuleAssertedOWLAxioms.add(a);
	}

	private DroolsSWRLRuleEngine getDroolsEngine()
	{
		return this.droolsEngine;
	}

	private DroolsOWLClassExpressionConverter getDroolsOWLClassExpressionConverter()
	{
		return this.classExpressionConverter;
	}

	private DroolsOWLPropertyExpressionConverter getDroolsOWLPropertyExpressionConverter()
	{
		return this.propertyExpressionConverter;
	}
}
