package org.swrlapi.drools.extractors;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.*;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.drools.owl.axioms.*;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class defined methods for converting the Drools representation of OWL axioms to their OWLAPI representation.
 *
 * @see org.semanticweb.owlapi.model.OWLAxiom
 * @see org.swrlapi.drools.owl.axioms.A
 */
public class DefaultDroolsOWLAxiomExtractor extends DroolsExtractorBase implements DroolsOWLAxiomExtractor
{
	public DefaultDroolsOWLAxiomExtractor(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	@Override
	public OWLDeclarationAxiom extract(CDA da) throws TargetRuleEngineException
	{
		OWLClass cls = getOWLDataFactory().getOWLClass(prefixedName2IRI(da.getE().getName()));

		return getSWRLAPIOWLDataFactory().getOWLClassDeclarationAxiom(cls);
	}

	@Override
	public OWLDeclarationAxiom extract(IDA da) throws TargetRuleEngineException
	{
		OWLNamedIndividual individual = getOWLDataFactory().getOWLNamedIndividual(prefixedName2IRI(da.getE().getName()));

		return getSWRLAPIOWLDataFactory().getOWLIndividualDeclarationAxiom(individual);
	}

	@Override
	public OWLDeclarationAxiom extract(OPDA da) throws TargetRuleEngineException
	{
		OWLObjectProperty property = getOWLDataFactory().getOWLObjectProperty(prefixedName2IRI(da.getE().getName()));

		return getSWRLAPIOWLDataFactory().getOWLObjectPropertyDeclarationAxiom(property);
	}

	@Override
	public OWLDeclarationAxiom extract(DPDA da) throws TargetRuleEngineException
	{
		OWLDataProperty property = getOWLDataFactory().getOWLDataProperty(prefixedName2IRI(da.getE().getName()));

		return getSWRLAPIOWLDataFactory().getOWLDataPropertyDeclarationAxiom(property);
	}

	@Override
	public OWLDeclarationAxiom extract(APDA da) throws TargetRuleEngineException
	{
		OWLAnnotationProperty property = getOWLDataFactory()
				.getOWLAnnotationProperty(prefixedName2IRI(da.getE().getName()));

		return getSWRLAPIOWLDataFactory().getOWLAnnotationPropertyDeclarationAxiom(property);
	}

	@Override
	public OWLClassAssertionAxiom extract(CAA caa) throws TargetRuleEngineException
	{
		OWLClassExpression cls = getOWLClassExpressionResolver().resolveOWLClassExpression(caa.getcid());
		OWLIndividual individual = caa.getI().extract(getDroolsOWLIndividualExtractor());

		return getOWLDataFactory().getOWLClassAssertionAxiom(cls, individual);
	}

	@Override
	public OWLObjectPropertyAssertionAxiom extract(OPAA opaa) throws TargetRuleEngineException
	{
		OWLIndividual subject = opaa.getT1().extract(getDroolsOWLIndividualExtractor());
		OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(opaa.getpid());
		OWLIndividual object = opaa.getT3().extract(getDroolsOWLIndividualExtractor());

		return getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(property, subject, object);
	}

	@Override
	public OWLNegativeObjectPropertyAssertionAxiom extract(NOPAA nopaa) throws TargetRuleEngineException
	{
		OWLIndividual subject = nopaa.gets().extract(getDroolsOWLIndividualExtractor());
		OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(nopaa.getpid());
		OWLIndividual object = nopaa.geto().extract(getDroolsOWLIndividualExtractor());

		return getOWLDataFactory().getOWLNegativeObjectPropertyAssertionAxiom(property, subject, object);
	}

	@Override
	public OWLDataPropertyAssertionAxiom extract(DPAA dpaa) throws TargetRuleEngineException
	{
		OWLIndividual subject = dpaa.gets().extract(getDroolsOWLIndividualExtractor());
		OWLDataPropertyExpression property = getOWLDataPropertyExpressionResolver().resolve(dpaa.getpid());
		OWLLiteral literal = getDroolsOWLLiteralExtractor().extract(dpaa.geto());

		return getOWLDataFactory().getOWLDataPropertyAssertionAxiom(property, subject, literal);
	}

	@Override
	public OWLNegativeDataPropertyAssertionAxiom extract(NDPAA ndpaa) throws TargetRuleEngineException
	{
		OWLIndividual subject = ndpaa.gets().extract(getDroolsOWLIndividualExtractor());
		OWLDataPropertyExpression property = getOWLDataPropertyExpressionResolver().resolve(ndpaa.getpid());
		OWLLiteral literal = getDroolsOWLLiteralExtractor().extract(ndpaa.geto());

		return getOWLDataFactory().getOWLNegativeDataPropertyAssertionAxiom(property, subject, literal);
	}

	@Override
	public OWLSameIndividualAxiom extract(SIA sia) throws TargetRuleEngineException
	{
		OWLIndividual individual1 = sia.geti1().extract(getDroolsOWLIndividualExtractor());
		OWLIndividual individual2 = sia.geti2().extract(getDroolsOWLIndividualExtractor());
		Set<OWLIndividual> individuals = new HashSet<OWLIndividual>();

		individuals.add(individual1);
		individuals.add(individual2);

		return getOWLDataFactory().getOWLSameIndividualAxiom(individuals);
	}

	@Override
	public OWLDifferentIndividualsAxiom extract(DIA dia) throws TargetRuleEngineException
	{
		OWLIndividual individual1 = dia.geti1().extract(getDroolsOWLIndividualExtractor());
		OWLIndividual individual2 = dia.geti2().extract(getDroolsOWLIndividualExtractor());
		Set<OWLIndividual> individuals = new HashSet<OWLIndividual>();

		individuals.add(individual1);
		individuals.add(individual2);

		return getOWLDataFactory().getOWLDifferentIndividualsAxiom(individuals);
	}

	@Override
	public OWLSubClassOfAxiom extract(SCA sca) throws TargetRuleEngineException
	{
		OWLClassExpression superClass = getOWLClassExpressionResolver().resolveOWLClassExpression(sca.getsupercid());
		OWLClassExpression subClass = getOWLClassExpressionResolver().resolveOWLClassExpression(sca.getsubcid());

		return getOWLDataFactory().getOWLSubClassOfAxiom(subClass, superClass);
	}

	@Override
	public OWLDisjointClassesAxiom extract(DCA dca) throws TargetRuleEngineException
	{
		OWLClassExpression class1 = getOWLClassExpressionResolver().resolveOWLClassExpression(dca.getc1id());
		OWLClassExpression class2 = getOWLClassExpressionResolver().resolveOWLClassExpression(dca.getc2id());
		Set<OWLClassExpression> classes = new HashSet<OWLClassExpression>();
		classes.add(class1);
		classes.add(class2);

		return getOWLDataFactory().getOWLDisjointClassesAxiom(classes);
	}

	@Override
	public OWLEquivalentClassesAxiom extract(ECA eca) throws TargetRuleEngineException
	{
		OWLClassExpression class1 = getOWLClassExpressionResolver().resolveOWLClassExpression(eca.getc1id());
		OWLClassExpression class2 = getOWLClassExpressionResolver().resolveOWLClassExpression(eca.getc2id());
		Set<OWLClassExpression> classes = new HashSet<OWLClassExpression>();
		classes.add(class1);
		classes.add(class2);

		return getOWLDataFactory().getOWLEquivalentClassesAxiom(classes);
	}

	@Override
	public OWLObjectPropertyDomainAxiom extract(DOPA dopa) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(dopa.getpid());
		OWLClassExpression domain = getOWLClassExpressionResolver().resolveOWLClassExpression(dopa.getdid());

		return getOWLDataFactory().getOWLObjectPropertyDomainAxiom(property, domain);
	}

	@Override
	public OWLDataPropertyDomainAxiom extract(DDPA ddpa) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression property = getOWLDataPropertyExpressionResolver().resolve(ddpa.getpid());
		OWLClassExpression domain = getOWLClassExpressionResolver().resolveOWLClassExpression(ddpa.getdid());

		return getOWLDataFactory().getOWLDataPropertyDomainAxiom(property, domain);
	}

	@Override
	public OWLObjectPropertyRangeAxiom extract(OPRA opra) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(opra.getpid());
		OWLClassExpression range = getOWLClassExpressionResolver().resolveOWLClassExpression(opra.getrid());

		return getOWLDataFactory().getOWLObjectPropertyRangeAxiom(property, range);
	}

	@Override
	public OWLDataPropertyRangeAxiom extract(DPRA dpra) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression property = getOWLDataPropertyExpressionResolver().resolve(dpra.getpid());
		OWLDataRange range = getOWLDataRangeResolver().resolveOWLDataRange(dpra.getrid());

		return getOWLDataFactory().getOWLDataPropertyRangeAxiom(property, range);
	}

	@Override
	public OWLSubObjectPropertyOfAxiom extract(SOPA sopa) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression superProperty = getOWLObjectPropertyExpressionResolver().resolve(sopa.getsuperpid());
		OWLObjectPropertyExpression subProperty = getOWLObjectPropertyExpressionResolver().resolve(
				sopa.getsubpid());

		return getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(subProperty, superProperty);
	}

	@Override
	public OWLInverseObjectPropertiesAxiom extract(IOPA iopa) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property1 = getOWLObjectPropertyExpressionResolver().resolve(iopa.getp1id());
		OWLObjectPropertyExpression property2 = getOWLObjectPropertyExpressionResolver().resolve(iopa.getp2id());

		return getOWLDataFactory().getOWLInverseObjectPropertiesAxiom(property2, property1);
	}

	@Override
	public OWLSubDataPropertyOfAxiom extract(SDPA sdpa) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression superProperty = getOWLDataPropertyExpressionResolver().resolve(sdpa.getsuperpid());
		OWLDataPropertyExpression subProperty = getOWLDataPropertyExpressionResolver().resolve(sdpa.getsubpid());

		return getOWLDataFactory().getOWLSubDataPropertyOfAxiom(subProperty, superProperty);
	}

	@Override
	public OWLEquivalentObjectPropertiesAxiom extract(EOPA eopa) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property1 = getOWLObjectPropertyExpressionResolver().resolve(eopa.getp1id());
		OWLObjectPropertyExpression property2 = getOWLObjectPropertyExpressionResolver().resolve(eopa.getp1id());
		Set<OWLObjectPropertyExpression> properties = new HashSet<OWLObjectPropertyExpression>();
		properties.add(property1);
		properties.add(property2);

		return getOWLDataFactory().getOWLEquivalentObjectPropertiesAxiom(properties);
	}

	@Override
	public OWLEquivalentDataPropertiesAxiom extract(EDPA edpa) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression property1 = getOWLDataPropertyExpressionResolver().resolve(edpa.getp1id());
		OWLDataPropertyExpression property2 = getOWLDataPropertyExpressionResolver().resolve(edpa.getp2id());
		Set<OWLDataPropertyExpression> properties = new HashSet<OWLDataPropertyExpression>();
		properties.add(property1);
		properties.add(property2);

		return getOWLDataFactory().getOWLEquivalentDataPropertiesAxiom(properties);
	}

	@Override
	public OWLDisjointObjectPropertiesAxiom extract(DJOPA djopa) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property1 = getOWLObjectPropertyExpressionResolver().resolve(djopa.getp1id());
		OWLObjectPropertyExpression property2 = getOWLObjectPropertyExpressionResolver().resolve(djopa.getp2id());
		Set<OWLObjectPropertyExpression> properties = new HashSet<OWLObjectPropertyExpression>();
		properties.add(property1);
		properties.add(property2);

		return getOWLDataFactory().getOWLDisjointObjectPropertiesAxiom(properties);
	}

	@Override
	public OWLDisjointDataPropertiesAxiom extract(DJDPA djdpa) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression property1 = getOWLDataPropertyExpressionResolver().resolve(djdpa.getp1id());
		OWLDataPropertyExpression property2 = getOWLDataPropertyExpressionResolver().resolve(djdpa.getp2id());
		Set<OWLDataPropertyExpression> properties = new HashSet<OWLDataPropertyExpression>();
		properties.add(property1);
		properties.add(property2);

		return getOWLDataFactory().getOWLDisjointDataPropertiesAxiom(properties);
	}

	@Override
	public OWLFunctionalObjectPropertyAxiom extract(FOPA fopa) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(fopa.getpid());

		return getOWLDataFactory().getOWLFunctionalObjectPropertyAxiom(property);
	}

	@Override
	public OWLFunctionalDataPropertyAxiom extract(FDPA fdpa) throws TargetRuleEngineException
	{
		OWLDataPropertyExpression property = getOWLDataPropertyExpressionResolver().resolve(fdpa.getpid());

		return getOWLDataFactory().getOWLFunctionalDataPropertyAxiom(property);
	}

	@Override
	public OWLInverseFunctionalObjectPropertyAxiom extract(IFOPA IFOPA) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(IFOPA.getpid());

		return getOWLDataFactory().getOWLInverseFunctionalObjectPropertyAxiom(property);
	}

	@Override
	public OWLIrreflexiveObjectPropertyAxiom extract(IROPA IROPA) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(IROPA.getpid());

		return getOWLDataFactory().getOWLIrreflexiveObjectPropertyAxiom(property);
	}

	@Override
	public OWLAsymmetricObjectPropertyAxiom extract(AOPA AOPA) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(AOPA.getpid());

		return getOWLDataFactory().getOWLAsymmetricObjectPropertyAxiom(property);
	}

	@Override
	public OWLSymmetricObjectPropertyAxiom extract(SPA spa) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(spa.getpid());

		return getOWLDataFactory().getOWLSymmetricObjectPropertyAxiom(property);
	}

	@Override
	public OWLTransitiveObjectPropertyAxiom extract(TOPA TOPA) throws TargetRuleEngineException
	{
		OWLObjectPropertyExpression property = getOWLObjectPropertyExpressionResolver().resolve(TOPA.getpid());

		return getOWLDataFactory().getOWLTransitiveObjectPropertyAxiom(property);
	}
}
