package org.swrlapi.drools.converters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLPropertyExpressionConverter;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class converts OWLAPI OWL property expressions to their Drools representation. Its basic function is to generate a
 * unique identifier for each property expressions. This identifier can be used in rules and in generating knowledge
 * base objects.
 *
 * @see org.semanticweb.owlapi.model.OWLDataPropertyExpression
 * @see org.semanticweb.owlapi.model.OWLObjectPropertyExpression
 */
public class DroolsOWLPropertyExpressionConverter extends DroolsConverterBase implements
		TargetRuleEngineOWLPropertyExpressionConverter<String>
{
	private final Map<OWLObjectPropertyExpression, String> objectPropertyExpression2IDMap;
	private final Map<OWLDataPropertyExpression, String> dataPropertyExpression2IDMap;
	private final Set<String> convertedPropertyExpressionIDs;
	private int objectPropertyExpressionIndex;
	private int dataPropertyExpressionIndex;

	public DroolsOWLPropertyExpressionConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);

		this.objectPropertyExpression2IDMap = new HashMap<OWLObjectPropertyExpression, String>();
		this.dataPropertyExpression2IDMap = new HashMap<OWLDataPropertyExpression, String>();
		this.objectPropertyExpressionIndex = 0;
		this.dataPropertyExpressionIndex = 0;
		this.convertedPropertyExpressionIDs = new HashSet<String>();
		getOWLObjectPropertyExpressionResolver().reset();
		getOWLDataPropertyExpressionResolver().reset();
	}

	public void reset()
	{
		this.objectPropertyExpressionIndex = 0;
		this.dataPropertyExpressionIndex = 0;
		this.objectPropertyExpression2IDMap.clear();
		this.dataPropertyExpression2IDMap.clear();
		this.convertedPropertyExpressionIDs.clear();
		getOWLObjectPropertyExpressionResolver().reset();
		getOWLDataPropertyExpressionResolver().reset();
	}

	@Override
	public String convert(OWLObjectPropertyExpression propertyExpression) throws TargetRuleEngineException
	{
		if (propertyExpression.isAnonymous())
			return generateAnonymousObjectPropertyExpressionID(propertyExpression);
		else {
			OWLObjectProperty objectProperty = propertyExpression.asOWLObjectProperty();
			IRI propertyIRI = objectProperty.getIRI();
			String prefixedName = getIRIResolver().iri2PrefixedName(propertyIRI);
			recordObjectPropertyExpressionID(prefixedName, propertyExpression);
			return prefixedName;
		}
	}

	@Override
	public String convert(OWLDataPropertyExpression propertyExpression) throws TargetRuleEngineException
	{
		if (propertyExpression.isAnonymous())
			return generateAnonymousDataPropertyExpressionID(propertyExpression);
		else {
			OWLDataProperty objectProperty = propertyExpression.asOWLDataProperty();
			IRI propertyIRI = objectProperty.getIRI();
			String prefixedName = getIRIResolver().iri2PrefixedName(propertyIRI);
			recordDataPropertyExpressionID(prefixedName, propertyExpression);
			return prefixedName;
		}
	}

	private String generateAnonymousObjectPropertyExpressionID(OWLObjectPropertyExpression propertyExpression)
	{
		if (this.objectPropertyExpression2IDMap.containsKey(propertyExpression))
			return this.objectPropertyExpression2IDMap.get(propertyExpression);
		else {
			String propertyExpressionID = "OPEID" + this.objectPropertyExpressionIndex++;
			recordObjectPropertyExpressionID(propertyExpressionID, propertyExpression);
			return propertyExpressionID;
		}
	}

	private String generateAnonymousDataPropertyExpressionID(OWLDataPropertyExpression propertyExpression)
	{
		if (this.dataPropertyExpression2IDMap.containsKey(propertyExpression))
			return this.dataPropertyExpression2IDMap.get(propertyExpression);
		else {
			String propertyExpressionID = "DPEID" + this.dataPropertyExpressionIndex++;
			recordDataPropertyExpressionID(propertyExpressionID, propertyExpression);
			return propertyExpressionID;
		}
	}

	private void recordObjectPropertyExpressionID(String propertyExpressionID, OWLObjectPropertyExpression propertyExpression)
	{
		if (!this.objectPropertyExpression2IDMap.containsKey(propertyExpression)) {
			this.objectPropertyExpression2IDMap.put(propertyExpression, propertyExpressionID);
			getOWLObjectPropertyExpressionResolver().record(propertyExpressionID, propertyExpression);
		}
	}

	private void recordDataPropertyExpressionID(String propertyExpressionID, OWLDataPropertyExpression propertyExpression)
	{
		if (!this.dataPropertyExpression2IDMap.containsKey(propertyExpression)) {
			this.dataPropertyExpression2IDMap.put(propertyExpression, propertyExpressionID);
			getOWLDataPropertyExpressionResolver().record(propertyExpressionID, propertyExpression);
		}
	}
}