package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLClassExpressionConverter;
import org.swrlapi.drools.core.DroolsCEResolver;
import org.swrlapi.drools.owl.classexpressions.CE;
import org.swrlapi.drools.owl.classexpressions.DAVFCE;
import org.swrlapi.drools.owl.classexpressions.DCCE;
import org.swrlapi.drools.owl.classexpressions.DHVCE;
import org.swrlapi.drools.owl.classexpressions.DMaxCCE;
import org.swrlapi.drools.owl.classexpressions.DMaxQCCE;
import org.swrlapi.drools.owl.classexpressions.DMinCCE;
import org.swrlapi.drools.owl.classexpressions.DSVFCE;
import org.swrlapi.drools.owl.classexpressions.OAVFCE;
import org.swrlapi.drools.owl.classexpressions.OCCE;
import org.swrlapi.drools.owl.classexpressions.OHVCE;
import org.swrlapi.drools.owl.classexpressions.OIOCE;
import org.swrlapi.drools.owl.classexpressions.OMaxCCE;
import org.swrlapi.drools.owl.classexpressions.OMaxQCCE;
import org.swrlapi.drools.owl.classexpressions.OMinCCE;
import org.swrlapi.drools.owl.classexpressions.OOCOCE;
import org.swrlapi.drools.owl.classexpressions.OOOCE;
import org.swrlapi.drools.owl.classexpressions.OSVFCE;
import org.swrlapi.drools.owl.classexpressions.OUOCE;
import org.swrlapi.drools.owl.core.C;

import java.util.HashSet;
import java.util.Set;

/**
 * This class converts OWLAPI OWL class expressions to their Drools representation.
 *
 * @see org.semanticweb.owlapi.model.OWLClassExpression
 * @see org.swrlapi.drools.owl.classexpressions.CE
 */
public class DroolsOWLClassExpressionConverter extends DroolsConverterBase
		implements TargetRuleEngineOWLClassExpressionConverter<String>, OWLClassExpressionVisitorEx<String>
{
	private final DroolsOWLPropertyExpressionConverter propertyExpressionConverter;
	private final DroolsCEResolver droolsCeResolver;

	public DroolsOWLClassExpressionConverter(SWRLRuleEngineBridge bridge, DroolsCEResolver droolsCeResolver,
			DroolsOWLPropertyExpressionConverter propertyExpressionConverter)
	{
		super(bridge);

		this.propertyExpressionConverter = propertyExpressionConverter;
		this.droolsCeResolver = droolsCeResolver;

		reset();
	}

	public void reset()
	{
		getOWLClassExpressionResolver().reset();
		getCEResolver().reset();
	}

	public String convert(OWLClassExpression owlClassExpression)
	{
		return owlClassExpression.accept(this);
	}

	@Override
	public String convert(OWLClass cls)
	{
		String classPrefixedName = getIRIResolver().iri2PrefixedName(cls.getIRI());

		if (!getCEResolver().recordsCEID(classPrefixedName)) {
			C c = new C(classPrefixedName);
			getOWLClassExpressionResolver().record(classPrefixedName, cls);
			getCEResolver().record(c);
		}
		return classPrefixedName;
	}

	@Override
	public String convert(OWLObjectOneOf classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			for (OWLIndividual individual1 : classExpression.getIndividuals()) {
				Set<OWLIndividual> individuals = new HashSet<>(classExpression.getIndividuals());
				String individual1ID = getDroolsOWLIndividual2DRLConverter().convert(individual1);

				individuals.remove(individual1);
				for (OWLIndividual individual2 : individuals) {
					String individual2ID = getDroolsOWLIndividual2DRLConverter().convert(individual2);
					OOOCE oooce = new OOOCE(classExpressionID, individual1ID, individual2ID);

					getOWLClassExpressionResolver().record(classExpressionID, classExpression);
					getCEResolver().record(oooce);
				}
			}
			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	@Override
	public String convert(OWLObjectIntersectionOf classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			for (OWLClassExpression ce : classExpression.getOperands()) {
				String class1ID = convert(ce);
				OIOCE oioce = new OIOCE(classExpressionID, class1ID);

				getOWLClassExpressionResolver().record(classExpressionID, classExpression);
				getCEResolver().record(oioce);
			}
			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	@Override
	public String convert(OWLObjectUnionOf classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			for (OWLClassExpression ce1 : classExpression.getOperands()) {
				String class1ID = convert(ce1);
				OUOCE ouoce = new OUOCE(classExpressionID, class1ID);

				getOWLClassExpressionResolver().record(classExpressionID, classExpression);
				getCEResolver().record(ouoce);
			}
			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	@Override
	public String convert(OWLObjectComplementOf classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			String complementClassID = convert(classExpression.getOperand());
			OOCOCE oooce = new OOCOCE(classExpressionID, complementClassID);

			getOWLClassExpressionResolver().record(classExpressionID, classExpression);
			getCEResolver().record(oooce);

			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	@Override
	public String convert(OWLObjectSomeValuesFrom classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			String someValuesFromClassID = convert(classExpression.getFiller());
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			OSVFCE osvfce = new OSVFCE(classExpressionID, propertyID, someValuesFromClassID);

			getOWLClassExpressionResolver().record(classExpressionID, classExpression);
			getCEResolver().record(osvfce);

			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	@Override
	public String convert(OWLDataSomeValuesFrom classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			String someValuesFromDataRangeID = getDroolsOWLDataRangeConverter().convert(classExpression.getFiller());
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			DSVFCE dsvfce = new DSVFCE(classExpressionID, propertyID, someValuesFromDataRangeID);

			getOWLClassExpressionResolver().record(classExpressionID, classExpression);
			getCEResolver().record(dsvfce);

			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	@Override
	public String convert(OWLDataExactCardinality classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			int cardinality = classExpression.getCardinality();
			DCCE dcce = new DCCE(classExpressionID, propertyID, cardinality);

			getOWLClassExpressionResolver().record(classExpressionID, classExpression);
			getCEResolver().record(dcce);

			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	@Override
	public String convert(OWLObjectExactCardinality classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			int cardinality = classExpression.getCardinality();
			OCCE occe = new OCCE(classExpressionID, propertyID, cardinality);

			getOWLClassExpressionResolver().record(classExpressionID, classExpression);
			getCEResolver().record(occe);

			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	@Override
	public String convert(OWLDataMinCardinality classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			int cardinality = classExpression.getCardinality();
			DMinCCE dmincce = new DMinCCE(classExpressionID, propertyID, cardinality);

			getOWLClassExpressionResolver().record(classExpressionID, classExpression);
			getCEResolver().record(dmincce);

			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	@Override
	public String convert(OWLObjectMinCardinality classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			int cardinality = classExpression.getCardinality();
			OMinCCE omincce = new OMinCCE(classExpressionID, propertyID, cardinality);

			getOWLClassExpressionResolver().record(classExpressionID, classExpression);
			getCEResolver().record(omincce);

			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	@Override
	public String convert(OWLDataMaxCardinality classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			int cardinality = classExpression.getCardinality();

			if (classExpression.isQualified()) {
				String fillerID = getDroolsOWLDataRangeConverter().convert(classExpression.getFiller());
				DMaxQCCE dmaxqcce = new DMaxQCCE(classExpressionID, propertyID, fillerID, cardinality);

				getOWLClassExpressionResolver().record(classExpressionID, classExpression);
				getCEResolver().record(dmaxqcce);
			} else {
				DMaxCCE dmaxcce = new DMaxCCE(classExpressionID, propertyID, cardinality);

				getOWLClassExpressionResolver().record(classExpressionID, classExpression);
				getCEResolver().record(dmaxcce);
			}
			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	@Override
	public String convert(OWLObjectMaxCardinality classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			int cardinality = classExpression.getCardinality();

			if (classExpression.isQualified()) {
				String fillerID = convert(classExpression.getFiller());
				OMaxQCCE omaxqcce = new OMaxQCCE(classExpressionID, propertyID, fillerID, cardinality);

				getOWLClassExpressionResolver().record(classExpressionID, classExpression);
				getCEResolver().record(omaxqcce);
			} else {
				OMaxCCE omaxcce = new OMaxCCE(classExpressionID, propertyID, cardinality);

				getOWLClassExpressionResolver().record(classExpressionID, classExpression);
				getCEResolver().record(omaxcce);
			}
			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	@Override
	public String convert(OWLDataHasValue classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			OWLLiteral valueLiteral = classExpression.getValue();
			DHVCE dhvce = new DHVCE(classExpressionID, propertyID, getDroolsOWLLiteral2LConverter().convert(valueLiteral));

			getOWLClassExpressionResolver().record(classExpressionID, classExpression);
			getCEResolver().record(dhvce);

			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	@Override
	public String convert(OWLObjectHasValue classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			String valueIndividualID = getDroolsOWLIndividual2DRLConverter().convert(classExpression.getValue());
			OHVCE ohvce = new OHVCE(classExpressionID, propertyID, valueIndividualID);

			getOWLClassExpressionResolver().record(classExpressionID, classExpression);
			getCEResolver().record(ohvce);

			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	@Override
	public String convert(OWLObjectAllValuesFrom classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			String allValuesFromClassID = convert(classExpression.getFiller());
			OAVFCE oavfce = new OAVFCE(classExpressionID, propertyID, allValuesFromClassID);

			getOWLClassExpressionResolver().record(classExpressionID, classExpression);
			getCEResolver().record(oavfce);

			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	@Override
	public String convert(OWLDataAllValuesFrom classExpression)
	{
		if (!getOWLClassExpressionResolver().records(classExpression)) {
			String classExpressionID = getCEResolver().generateCEID();
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			String allValuesFromDataRangeID = getDroolsOWLDataRangeConverter().convert(classExpression.getFiller());
			DAVFCE davfce = new DAVFCE(classExpressionID, propertyID, allValuesFromDataRangeID);

			getOWLClassExpressionResolver().record(classExpressionID, classExpression);
			getCEResolver().record(davfce);

			return classExpressionID;
		} else
			return getOWLClassExpressionResolver().resolve(classExpression);
	}

	public Set<CE> getCEs()
	{
		return getCEResolver().getCEs();
	}

	@Override public String visit(OWLClass owlClass)
	{
		return convert(owlClass);
	}

	@Override public String visit(OWLObjectIntersectionOf owlObjectIntersectionOf)
	{
		return convert(owlObjectIntersectionOf);
	}

	@Override public String visit(OWLObjectUnionOf owlObjectUnionOf)
	{
		return convert(owlObjectUnionOf);
	}

	@Override public String visit(OWLObjectComplementOf owlObjectComplementOf)
	{
		return convert(owlObjectComplementOf);
	}

	@Override public String visit(OWLObjectSomeValuesFrom owlObjectSomeValuesFrom)
	{
		return convert(owlObjectSomeValuesFrom);
	}

	@Override public String visit(OWLObjectAllValuesFrom owlObjectAllValuesFrom)
	{
		return convert(owlObjectAllValuesFrom);
	}

	@Override public String visit(OWLObjectHasValue owlObjectHasValue) { return convert(owlObjectHasValue); }

	@Override public String visit(OWLObjectMinCardinality owlObjectMinCardinality)
	{
		return convert(owlObjectMinCardinality);
	}

	@Override public String visit(OWLObjectExactCardinality owlObjectExactCardinality)
	{
		return convert(owlObjectExactCardinality);
	}

	@Override public String visit(OWLObjectMaxCardinality owlObjectMaxCardinality)
	{
		return convert(owlObjectMaxCardinality);
	}

	@Override public String visit(OWLObjectHasSelf owlObjectHasSelf)
	{
		return convert(owlObjectHasSelf);
	}

	@Override public String visit(OWLObjectOneOf owlObjectOneOf)
	{
		return convert(owlObjectOneOf);
	}

	@Override public String visit(OWLDataSomeValuesFrom owlDataSomeValuesFrom)
	{
		return convert(owlDataSomeValuesFrom);
	}

	@Override public String visit(OWLDataAllValuesFrom owlDataAllValuesFrom)
	{
		return convert(owlDataAllValuesFrom);
	}

	@Override public String visit(OWLDataHasValue owlDataHasValue)
	{
		return convert(owlDataHasValue);
	}

	@Override public String visit(OWLDataMinCardinality owlDataMinCardinality)
	{
		return convert(owlDataMinCardinality);
	}

	@Override public String visit(OWLDataExactCardinality owlDataExactCardinality)
	{
		return convert(owlDataExactCardinality);
	}

	@Override public String visit(OWLDataMaxCardinality owlDataMaxCardinality)
	{
		return convert(owlDataMaxCardinality);
	}

	private DroolsOWLPropertyExpressionConverter getOWLPropertyExpressionConverter()
	{
		return this.propertyExpressionConverter;
	}

	private DroolsCEResolver getCEResolver() { return this.droolsCeResolver; }
}
