package org.protege.swrlapi.drools.extractors;

import java.util.HashSet;
import java.util.Set;

import org.protege.swrlapi.core.SWRLRuleEngineBridge;
import org.protege.swrlapi.drools.owl.axioms.A;
import org.protege.swrlapi.drools.owl.axioms.APA;
import org.protege.swrlapi.drools.owl.axioms.APDA;
import org.protege.swrlapi.drools.owl.axioms.CAA;
import org.protege.swrlapi.drools.owl.axioms.CDA;
import org.protege.swrlapi.drools.owl.axioms.DCA;
import org.protege.swrlapi.drools.owl.axioms.DDPA;
import org.protege.swrlapi.drools.owl.axioms.DIA;
import org.protege.swrlapi.drools.owl.axioms.DJDPA;
import org.protege.swrlapi.drools.owl.axioms.DJOPA;
import org.protege.swrlapi.drools.owl.axioms.DOPA;
import org.protege.swrlapi.drools.owl.axioms.DPAA;
import org.protege.swrlapi.drools.owl.axioms.DPDA;
import org.protege.swrlapi.drools.owl.axioms.ECA;
import org.protege.swrlapi.drools.owl.axioms.EDPA;
import org.protege.swrlapi.drools.owl.axioms.EOPA;
import org.protege.swrlapi.drools.owl.axioms.FDPA;
import org.protege.swrlapi.drools.owl.axioms.FOPA;
import org.protege.swrlapi.drools.owl.axioms.IDA;
import org.protege.swrlapi.drools.owl.axioms.IOPA;
import org.protege.swrlapi.drools.owl.axioms.IPA;
import org.protege.swrlapi.drools.owl.axioms.IRPA;
import org.protege.swrlapi.drools.owl.axioms.OPAA;
import org.protege.swrlapi.drools.owl.axioms.OPDA;
import org.protege.swrlapi.drools.owl.axioms.RDPA;
import org.protege.swrlapi.drools.owl.axioms.ROPA;
import org.protege.swrlapi.drools.owl.axioms.SCA;
import org.protege.swrlapi.drools.owl.axioms.SDPA;
import org.protege.swrlapi.drools.owl.axioms.SIA;
import org.protege.swrlapi.drools.owl.axioms.SOPA;
import org.protege.swrlapi.drools.owl.axioms.SPA;
import org.protege.swrlapi.drools.owl.axioms.TPA;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.protege.swrlapi.extractors.TargetRuleEngineExtractorBase;
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
import org.semanticweb.owlapi.model.OWLDatatype;
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

/**
 * This class converts a Drools OWL axiom represented by the class {@link A} to Portability API OWL axioms.
 */
public class DefaultDroolsA2OWLAxiomExtractor extends TargetRuleEngineExtractorBase implements
		DroolsA2OWLAxiomExtractor
{
	private final DroolsL2OWLLiteralExtractor literalExtractor;
	private final DroolsOE2OWLEntityExtractor namedObjectExtractor;
	private final DroolsOWLI2IndividualExtractor individualExtractor;

	public DefaultDroolsA2OWLAxiomExtractor(SWRLRuleEngineBridge bridge)
	{
		super(bridge);

		this.literalExtractor = new DroolsL2OWLLiteralExtractor(bridge);
		this.namedObjectExtractor = new DefaultDroolsOE2OWLEntityExtractor(bridge);
		this.individualExtractor = new DefaultDroolsI2OWLIndividualExtractor(bridge);
	}

	@Override
	public OWLDeclarationAxiom extract(CDA da) throws TargetRuleEngineException
	{
		OWLClass cls = getOWLDataFactory().getOWLClass(getIRI(da.getE().getid()));

		return getOWLDataFactory().getOWLClassDeclarationAxiom(cls);
	}

	@Override
	public OWLDeclarationAxiom extract(IDA da) throws TargetRuleEngineException
	{
		OWLNamedIndividual individual = getOWLDataFactory().getOWLNamedIndividual(getIRI(da.getE().getid()));

		return getOWLDataFactory().getOWLIndividualDeclarationAxiom(individual);
	}

	@Override
	public OWLDeclarationAxiom extract(OPDA da) throws TargetRuleEngineException
	{
		OWLObjectProperty property = getOWLDataFactory().getOWLObjectProperty(getIRI(da.getE().getid()));

		return getOWLDataFactory().getOWLObjectPropertyDeclarationAxiom(property);
	}

	@Override
	public OWLDeclarationAxiom extract(DPDA da) throws TargetRuleEngineException
	{
		OWLDataProperty property = getOWLDataFactory().getOWLDataProperty(getIRI(da.getE().getid()));

		return getOWLDataFactory().getOWLDataPropertyDeclarationAxiom(property);
	}

	@Override
	public OWLDeclarationAxiom extract(APDA da) throws TargetRuleEngineException
	{
		OWLAnnotationProperty property = getOWLDataFactory().getOWLAnnotationProperty(getIRI(da.getE().getid()));

		return getOWLDataFactory().getOWLAnnotationPropertyDeclarationAxiom(property);
	}

	@Override
	public OWLClassAssertionAxiom extract(CAA caa) throws TargetRuleEngineException
	{
		OWLClassExpression cls = getOWLClassExpressionResolver().resolveOWLClassExpression(caa.getC());
		OWLIndividual individual = caa.getI().extract(getOWLIndividualExtractor());

		return getOWLDataFactory().getOWLClassAssertionAxiom(cls, individual);
	}

	@Override
	public OWLObjectPropertyAssertionAxiom extract(OPAA opaa) throws TargetRuleEngineException
	{
		OWLIndividual subject = opaa.getT1().extract(getOWLIndividualExtractor());
		OWLObjectPropertyExpression property = opaa.getT2().extract(getOWLNamedObjectExtractor());
		OWLIndividual object = opaa.getT3().extract(getOWLIndividualExtractor());

		return getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(property, subject, object);
	}

	@Override
	public OWLDataPropertyAssertionAxiom extract(DPAA dpaa) throws TargetRuleEngineException
	{
		OWLIndividual subject = dpaa.getT1().extract(getOWLIndividualExtractor());
		OWLDataProperty property = dpaa.getT2().extract(getOWLNamedObjectExtractor());
		OWLLiteral literal = getOWLLiteralExtractor().extract(dpaa.getT3());

		return getOWLDataFactory().getOWLDataPropertyAssertionAxiom(property, subject, literal);
	}

	@Override
	public OWLSameIndividualAxiom extract(SIA sia) throws TargetRuleEngineException
	{
		OWLIndividual individual1 = sia.getI1().extract(getOWLIndividualExtractor());
		OWLIndividual individual2 = sia.getI2().extract(getOWLIndividualExtractor());
		Set<OWLIndividual> individuals = new HashSet<OWLIndividual>();

		individuals.add(individual1);
		individuals.add(individual2);

		return getOWLDataFactory().getOWLSameIndividualAxiom(individuals);
	}

	@Override
	public OWLDifferentIndividualsAxiom extract(DIA dia) throws TargetRuleEngineException
	{
		OWLIndividual individual1 = dia.getI1().extract(getOWLIndividualExtractor());
		OWLIndividual individual2 = dia.getI2().extract(getOWLIndividualExtractor());
		Set<OWLIndividual> individuals = new HashSet<OWLIndividual>();

		individuals.add(individual1);
		individuals.add(individual2);

		return getOWLDataFactory().getOWLDifferentIndividualsAxiom(individuals);
	}

	@Override
	public OWLSubClassOfAxiom extract(SCA sca) throws TargetRuleEngineException
	{
		OWLClassExpression superClass = getOWLClassExpressionResolver().resolveOWLClassExpression(sca.getSup());
		OWLClassExpression subClass = getOWLClassExpressionResolver().resolveOWLClassExpression(sca.getSub());

		return getOWLDataFactory().getOWLSubClassOfAxiom(subClass, superClass);
	}

	@Override
	public OWLDisjointClassesAxiom extract(DCA dca) throws TargetRuleEngineException
	{
		OWLClassExpression class1 = getOWLClassExpressionResolver().resolveOWLClassExpression(dca.getC1());
		OWLClassExpression class2 = getOWLClassExpressionResolver().resolveOWLClassExpression(dca.getC2());
		Set<OWLClassExpression> classes = new HashSet<OWLClassExpression>();
		classes.add(class1);
		classes.add(class2);

		return getOWLDataFactory().getOWLDisjointClassesAxiom(classes);
	}

	@Override
	public OWLEquivalentClassesAxiom extract(ECA eca) throws TargetRuleEngineException
	{
		OWLClassExpression class1 = getOWLClassExpressionResolver().resolveOWLClassExpression(eca.getC1());
		OWLClassExpression class2 = getOWLClassExpressionResolver().resolveOWLClassExpression(eca.getC2());
		Set<OWLClassExpression> classes = new HashSet<OWLClassExpression>();
		classes.add(class1);
		classes.add(class2);

		return getOWLDataFactory().getOWLEquivalentClassesAxiom(classes);
	}

	@Override
	public OWLObjectPropertyDomainAxiom extract(DOPA dopa) throws TargetRuleEngineException
	{
		OWLObjectProperty property = dopa.getP().extract(getOWLNamedObjectExtractor());
		OWLClassExpression domain = getOWLClassExpressionResolver().resolveOWLClassExpression(dopa.getD());

		return getOWLDataFactory().getOWLObjectPropertyDomainAxiom(property, domain);
	}

	@Override
	public OWLDataPropertyDomainAxiom extract(DDPA ddpa) throws TargetRuleEngineException
	{
		OWLDataProperty property = ddpa.getP().extract(getOWLNamedObjectExtractor());
		OWLClassExpression domain = getOWLClassExpressionResolver().resolveOWLClassExpression(ddpa.getD());

		return getOWLDataFactory().getOWLDataPropertyDomainAxiom(property, domain);
	}

	@Override
	public OWLObjectPropertyRangeAxiom extract(ROPA ropa) throws TargetRuleEngineException
	{
		OWLObjectProperty property = ropa.getP().extract(getOWLNamedObjectExtractor());
		OWLClassExpression range = getOWLClassExpressionResolver().resolveOWLClassExpression(ropa.getR());

		return getOWLDataFactory().getOWLObjectPropertyRangeAxiom(property, range);
	}

	@Override
	public OWLDataPropertyRangeAxiom extract(RDPA rdpa) throws TargetRuleEngineException
	{
		OWLDataProperty property = rdpa.getP().extract(getOWLNamedObjectExtractor());
		OWLDatatype range = rdpa.getR().extract(getOWLNamedObjectExtractor());

		return getOWLDataFactory().getOWLDataPropertyRangeAxiom(property, range);
	}

	@Override
	public OWLSubObjectPropertyOfAxiom extract(SOPA sopa) throws TargetRuleEngineException
	{
		OWLObjectProperty superProperty = sopa.getSup().extract(getOWLNamedObjectExtractor());
		OWLObjectProperty subProperty = sopa.getSub().extract(getOWLNamedObjectExtractor());

		return getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(subProperty, superProperty);
	}

	@Override
	public OWLInverseObjectPropertiesAxiom extract(IOPA iopa) throws TargetRuleEngineException
	{
		OWLObjectProperty property2 = iopa.getP1().extract(getOWLNamedObjectExtractor());
		OWLObjectProperty property1 = iopa.getP2().extract(getOWLNamedObjectExtractor());

		return getOWLDataFactory().getOWLInverseObjectPropertiesAxiom(property1, property2);
	}

	@Override
	public OWLSubDataPropertyOfAxiom extract(SDPA sdpa) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression superProperty = sdpa.getSup().extract(getOWLNamedObjectExtractor());
		OWLDataPropertyExpression subProperty = sdpa.getSub().extract(getOWLNamedObjectExtractor());

		return getOWLDataFactory().getOWLSubDataPropertyOfAxiom(subProperty, superProperty);
	}

	@Override
	public OWLEquivalentObjectPropertiesAxiom extract(EOPA eopa) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property1 = eopa.getP1().extract(getOWLNamedObjectExtractor());
		OWLObjectPropertyExpression property2 = eopa.getP2().extract(getOWLNamedObjectExtractor());
		Set<OWLObjectPropertyExpression> properties = new HashSet<OWLObjectPropertyExpression>();
		properties.add(property1);
		properties.add(property2);

		return getOWLDataFactory().getOWLEquivalentObjectPropertiesAxiom(properties);
	}

	@Override
	public OWLEquivalentDataPropertiesAxiom extract(EDPA edpa) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression property1 = edpa.getP1().extract(getOWLNamedObjectExtractor());
		OWLDataPropertyExpression property2 = edpa.getP2().extract(getOWLNamedObjectExtractor());
		Set<OWLDataPropertyExpression> properties = new HashSet<OWLDataPropertyExpression>();
		properties.add(property1);
		properties.add(property2);

		return getOWLDataFactory().getOWLEquivalentDataPropertiesAxiom(properties);
	}

	@Override
	public OWLDisjointObjectPropertiesAxiom extract(DJOPA djopa) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property1 = djopa.getP1().extract(getOWLNamedObjectExtractor());
		OWLObjectPropertyExpression property2 = djopa.getP2().extract(getOWLNamedObjectExtractor());
		Set<OWLObjectPropertyExpression> properties = new HashSet<OWLObjectPropertyExpression>();
		properties.add(property1);
		properties.add(property2);

		return getOWLDataFactory().getOWLDisjointObjectPropertiesAxiom(properties);
	}

	@Override
	public OWLDisjointDataPropertiesAxiom extract(DJDPA djdpa) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression property1 = djdpa.getP1().extract(getOWLNamedObjectExtractor());
		OWLDataPropertyExpression property2 = djdpa.getP2().extract(getOWLNamedObjectExtractor());
		Set<OWLDataPropertyExpression> properties = new HashSet<OWLDataPropertyExpression>();
		properties.add(property1);
		properties.add(property2);

		return getOWLDataFactory().getOWLDisjointDataPropertiesAxiom(properties);
	}

	@Override
	public OWLFunctionalObjectPropertyAxiom extract(FOPA fopa) throws TargetRuleEngineException
	{
		OWLObjectProperty property = fopa.getP().extract(getOWLNamedObjectExtractor());

		return getOWLDataFactory().getOWLFunctionalObjectPropertyAxiom(property);
	}

	@Override
	public OWLFunctionalDataPropertyAxiom extract(FDPA fdpa) throws TargetRuleEngineException
	{
		OWLDataProperty property = fdpa.getP().extract(getOWLNamedObjectExtractor());

		return getOWLDataFactory().getOWLFunctionalDataPropertyAxiom(property);
	}

	@Override
	public OWLInverseFunctionalObjectPropertyAxiom extract(IPA ipa) throws TargetRuleEngineException
	{
		OWLObjectProperty property = ipa.getP().extract(getOWLNamedObjectExtractor());

		return getOWLDataFactory().getOWLInverseFunctionalObjectPropertyAxiom(property);
	}

	@Override
	public OWLIrreflexiveObjectPropertyAxiom extract(IRPA irpa) throws TargetRuleEngineException
	{
		OWLObjectProperty property = irpa.getP().extract(getOWLNamedObjectExtractor());

		return getOWLDataFactory().getOWLIrreflexiveObjectPropertyAxiom(property);
	}

	@Override
	public OWLAsymmetricObjectPropertyAxiom extract(APA apa) throws TargetRuleEngineException
	{
		OWLObjectProperty property = apa.getP().extract(getOWLNamedObjectExtractor());

		return getOWLDataFactory().getOWLAsymmetricObjectPropertyAxiom(property);
	}

	@Override
	public OWLSymmetricObjectPropertyAxiom extract(SPA spa) throws TargetRuleEngineException
	{
		OWLObjectProperty property = spa.getP().extract(getOWLNamedObjectExtractor());

		return getOWLDataFactory().getOWLSymmetricObjectPropertyAxiom(property);
	}

	@Override
	public OWLTransitiveObjectPropertyAxiom extract(TPA tpa) throws TargetRuleEngineException
	{
		OWLObjectProperty property = tpa.getP().extract(getOWLNamedObjectExtractor());

		return getOWLDataFactory().getOWLTransitiveObjectPropertyAxiom(property);
	}

	private DroolsL2OWLLiteralExtractor getOWLLiteralExtractor()
	{
		return this.literalExtractor;
	}

	private DroolsOE2OWLEntityExtractor getOWLNamedObjectExtractor()
	{
		return this.namedObjectExtractor;
	}

	private DroolsOWLI2IndividualExtractor getOWLIndividualExtractor()
	{
		return this.individualExtractor;
	}
}
