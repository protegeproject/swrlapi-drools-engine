package org.swrlapi.drools.converters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
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
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.swrlapi.converters.TargetRuleEngineOWLClassExpressionConverter;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.drools.owl.expressions.CE;
import org.swrlapi.drools.owl.expressions.DAVFCE;
import org.swrlapi.drools.owl.expressions.DCCE;
import org.swrlapi.drools.owl.expressions.DHVCE;
import org.swrlapi.drools.owl.expressions.DMaxCCE;
import org.swrlapi.drools.owl.expressions.DMinCCE;
import org.swrlapi.drools.owl.expressions.DSVFCE;
import org.swrlapi.drools.owl.expressions.OAVFCE;
import org.swrlapi.drools.owl.expressions.OCCE;
import org.swrlapi.drools.owl.expressions.OCOCE;
import org.swrlapi.drools.owl.expressions.OHVCE;
import org.swrlapi.drools.owl.expressions.OIOCE;
import org.swrlapi.drools.owl.expressions.OMaxCCE;
import org.swrlapi.drools.owl.expressions.OMinCCE;
import org.swrlapi.drools.owl.expressions.OOOCE;
import org.swrlapi.drools.owl.expressions.OSVFCE;
import org.swrlapi.drools.owl.expressions.OUOCE;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class converts OWL class expressions to their Drools DRL representation for use in rules or to the {@link CE}
 * class for knowledge base storage.
 */
public class DroolsOWLClassExpressionConverter extends DroolsConverterBase implements
		TargetRuleEngineOWLClassExpressionConverter<String>
{
	private final DroolsOWLPropertyExpressionConverter propertyExpressionConverter;
	private final Map<OWLClassExpression, String> classExpression2IDMap;
	private final Set<String> convertedClassExpressionIDs;
	private int classExpressionIndex;
	private final Set<CE> classExpressions;

	public DroolsOWLClassExpressionConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);

		this.propertyExpressionConverter = new DroolsOWLPropertyExpressionConverter(bridge);

		this.classExpressions = new HashSet<CE>();
		this.classExpressionIndex = 0;
		this.classExpression2IDMap = new HashMap<OWLClassExpression, String>();
		this.convertedClassExpressionIDs = new HashSet<String>();

		getOWLClassExpressionResolver().reset();
	}

	public void reset()
	{
		this.classExpressions.clear();
		this.classExpressionIndex = 0;
		this.classExpression2IDMap.clear();
		this.convertedClassExpressionIDs.clear();
		getOWLClassExpressionResolver().reset();
	}

	public Set<CE> getOWLClassExpressions()
	{
		return this.classExpressions;
	}

	public String convert(OWLClassExpression ce) throws TargetRuleEngineException
	{ // TODO Use visitor to get rid of instanceof
		if (ce instanceof OWLClass) {
			return convert((OWLClass)ce);
		} else if (ce instanceof OWLObjectOneOf) {
			return convert((OWLObjectOneOf)ce);
		} else if (ce instanceof OWLObjectIntersectionOf) {
			return convert((OWLObjectIntersectionOf)ce);
		} else if (ce instanceof OWLObjectUnionOf) {
			return convert((OWLObjectUnionOf)ce);
		} else if (ce instanceof OWLObjectSomeValuesFrom) {
			return convert((OWLObjectSomeValuesFrom)ce);
		} else if (ce instanceof OWLObjectComplementOf) {
			return convert((OWLObjectComplementOf)ce);
		} else if (ce instanceof OWLObjectMinCardinality) {
			return convert((OWLObjectMinCardinality)ce);
		} else if (ce instanceof OWLObjectMaxCardinality) {
			return convert((OWLObjectMaxCardinality)ce);
		} else if (ce instanceof OWLObjectExactCardinality) {
			return convert((OWLObjectExactCardinality)ce);
		} else if (ce instanceof OWLObjectHasValue) {
			return convert((OWLObjectHasValue)ce);
		} else if (ce instanceof OWLObjectAllValuesFrom) {
			return convert((OWLObjectAllValuesFrom)ce);
		} else if (ce instanceof OWLDataSomeValuesFrom) {
			return convert((OWLDataSomeValuesFrom)ce);
		} else if (ce instanceof OWLDataAllValuesFrom) {
			return convert((OWLDataAllValuesFrom)ce);
		} else if (ce instanceof OWLDataHasValue) {
			return convert((OWLDataHasValue)ce);
		} else if (ce instanceof OWLDataExactCardinality) {
			return convert((OWLDataExactCardinality)ce);
		} else if (ce instanceof OWLDataMinCardinality) {
			return convert((OWLDataMinCardinality)ce);
		} else if (ce instanceof OWLDataMaxCardinality) {
			return convert((OWLDataMaxCardinality)ce);
		} else
			throw new RuntimeException("unknown OWL class expression type " + ce.getClass().getCanonicalName());
	}

	@Override
	public String convert(OWLClass cls) throws TargetRuleEngineException
	{
		String classExpressionID = getOWLIRIResolver().iri2ShortName(cls.getIRI());

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, cls);
			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLObjectOneOf classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			for (OWLIndividual individual1 : classExpression.getIndividuals()) {
				Set<OWLIndividual> individuals = new HashSet<OWLIndividual>(classExpression.getIndividuals());
				String individual1ID = getDroolsOWLIndividual2DRLConverter().convert(individual1);

				individuals.remove(individual1);
				for (OWLIndividual individual2 : individuals) {
					String individual2ID = getDroolsOWLIndividual2DRLConverter().convert(individual2);
					OOOCE oooce = new OOOCE(classExpressionID, individual1ID, individual2ID);

					addOWLClassExpression(oooce);
				}
			}
			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLObjectIntersectionOf classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			for (OWLClassExpression ce : classExpression.getOperands()) {
				String class1ID = convert(ce);
				OIOCE oioce = new OIOCE(classExpressionID, class1ID);
				addOWLClassExpression(oioce);
			}
			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLObjectUnionOf classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			for (OWLClassExpression ce1 : classExpression.getOperands()) {
				String class1ID = convert(ce1);
				OUOCE ouoce = new OUOCE(classExpressionID, class1ID);
				addOWLClassExpression(ouoce);
			}
			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLObjectComplementOf classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			String complementClassID = convert(classExpression.getOperand());
			OCOCE ouoce = new OCOCE(classExpressionID, complementClassID);

			getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);

			addOWLClassExpression(ouoce);

			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLObjectSomeValuesFrom classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			String someValuesFromClassID = convert(classExpression.getFiller());
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			OSVFCE osvfce = new OSVFCE(classExpressionID, propertyID, someValuesFromClassID);

			getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);

			addOWLClassExpression(osvfce);

			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLDataSomeValuesFrom classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			String someValuesFromDataRangeID = getDroolsOWLDataRange2DRLConverter().convert(classExpression.getFiller());
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			DSVFCE dsvfce = new DSVFCE(classExpressionID, propertyID, someValuesFromDataRangeID);

			getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);

			addOWLClassExpression(dsvfce);

			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLDataExactCardinality classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			int cardinality = classExpression.getCardinality();
			DCCE dcce = new DCCE(classExpressionID, propertyID, cardinality);

			getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);

			addOWLClassExpression(dcce);

			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLObjectExactCardinality classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			int cardinality = classExpression.getCardinality();
			OCCE occe = new OCCE(classExpressionID, propertyID, cardinality);

			getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);

			addOWLClassExpression(occe);

			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLDataMinCardinality classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			int cardinality = classExpression.getCardinality();
			DMinCCE dmincce = new DMinCCE(classExpressionID, propertyID, cardinality);

			getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);

			addOWLClassExpression(dmincce);

			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLObjectMinCardinality classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			int cardinality = classExpression.getCardinality();
			OMinCCE omincce = new OMinCCE(classExpressionID, propertyID, cardinality);

			getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);

			addOWLClassExpression(omincce);

			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLDataMaxCardinality classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			int cardinality = classExpression.getCardinality();
			DMaxCCE dmaxcce = new DMaxCCE(classExpressionID, propertyID, cardinality);

			getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);

			addOWLClassExpression(dmaxcce);

			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLObjectMaxCardinality classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			int cardinality = classExpression.getCardinality();
			OMaxCCE omaxcce = new OMaxCCE(classExpressionID, propertyID, cardinality);

			getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);

			addOWLClassExpression(omaxcce);

			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLDataHasValue classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			OWLLiteral valueLiteral = classExpression.getValue();
			DHVCE dhvce = new DHVCE(classExpressionID, propertyID, getDroolsOWLLiteral2LConverter().convert(valueLiteral));

			getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);

			addOWLClassExpression(dhvce);

			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLObjectHasValue classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			String valueIndividualID = getDroolsOWLIndividual2DRLConverter().convert(classExpression.getValue());
			OHVCE ohvce = new OHVCE(classExpressionID, propertyID, valueIndividualID);

			getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);

			addOWLClassExpression(ohvce);

			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLObjectAllValuesFrom classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			String allValuesFromClassID = convert(classExpression.getFiller());
			OAVFCE oavfce = new OAVFCE(classExpressionID, propertyID, allValuesFromClassID);

			getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);

			addOWLClassExpression(oavfce);

			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	@Override
	public String convert(OWLDataAllValuesFrom classExpression) throws TargetRuleEngineException
	{
		String classExpressionID = getClassExpressionID(classExpression);

		if (!this.convertedClassExpressionIDs.contains(classExpressionID)) {
			String propertyID = getOWLPropertyExpressionConverter().convert(classExpression.getProperty());
			String allValuesFromDataRangeID = getDroolsOWLDataRange2DRLConverter().convert(classExpression.getFiller());
			DAVFCE davfce = new DAVFCE(classExpressionID, propertyID, allValuesFromDataRangeID);

			getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);

			addOWLClassExpression(davfce);

			this.convertedClassExpressionIDs.add(classExpressionID);
		}
		return classExpressionID;
	}

	private String getClassExpressionID(OWLClassExpression classExpression)
	{
		if (this.classExpression2IDMap.containsKey(classExpression))
			return this.classExpression2IDMap.get(classExpression);
		else {
			String classExpressionID = "CEID" + this.classExpressionIndex++;
			this.classExpression2IDMap.put(classExpression, classExpressionID);
			getOWLClassExpressionResolver().recordOWLClassExpression(classExpressionID, classExpression);
			return classExpressionID;
		}
	}

	private void addOWLClassExpression(CE classExpression)
	{
		this.classExpressions.add(classExpression);
	}

	private DroolsOWLPropertyExpressionConverter getOWLPropertyExpressionConverter()
	{
		return this.propertyExpressionConverter;
	}
}
