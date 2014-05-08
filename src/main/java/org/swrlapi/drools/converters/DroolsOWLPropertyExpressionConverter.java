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
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.swrlapi.converters.TargetRuleEngineOWLPropertyExpressionConverter;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class converts OWL property expressions to their Drools representation. Its basic function is to generate a
 * unique identifier for each property expressions. This identifier can be used in rules and in generating knowledge
 * base objects.
 */
public class DroolsOWLPropertyExpressionConverter extends DroolsConverterBase implements
		TargetRuleEngineOWLPropertyExpressionConverter<String>
{
	private final Map<OWLPropertyExpression<?, ?>, String> propertyExpression2IDMap;
	private final Set<String> convertedPropertyExpressionIDs;
	private int propertyExpressionIndex;

	public DroolsOWLPropertyExpressionConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);

		this.propertyExpression2IDMap = new HashMap<OWLPropertyExpression<?, ?>, String>();
		this.propertyExpressionIndex = 0;
		this.convertedPropertyExpressionIDs = new HashSet<String>();
		getOWLPropertyExpressionResolver().reset();
	}

	public void reset()
	{
		this.propertyExpressionIndex = 0;
		this.propertyExpression2IDMap.clear();
		this.convertedPropertyExpressionIDs.clear();
		getOWLPropertyExpressionResolver().reset();
	}

	@Override
	public String convert(OWLObjectPropertyExpression propertyExpression) throws TargetRuleEngineException
	{
		if (propertyExpression.isAnonymous())
			return getPropertyExpressionID(propertyExpression);
		else {
			OWLObjectProperty objectProperty = propertyExpression.asOWLObjectProperty();
			IRI propertyIRI = objectProperty.getIRI();
			return getOWLIRIResolver().iri2PrefixedName(propertyIRI);
		}
	}

	@Override
	public String convert(OWLDataPropertyExpression propertyExpression) throws TargetRuleEngineException
	{
		if (propertyExpression.isAnonymous())
			return getPropertyExpressionID(propertyExpression);
		else {
			OWLDataProperty objectProperty = propertyExpression.asOWLDataProperty();
			IRI propertyIRI = objectProperty.getIRI();
			return getOWLIRIResolver().iri2PrefixedName(propertyIRI);
		}
	}

	private String getPropertyExpressionID(OWLPropertyExpression<?, ?> propertyExpression)
	{
		if (this.propertyExpression2IDMap.containsKey(propertyExpression))
			return this.propertyExpression2IDMap.get(propertyExpression);
		else {
			String propertyExpressionID = "PEID" + this.propertyExpressionIndex++;
			this.propertyExpression2IDMap.put(propertyExpression, propertyExpressionID);
			getOWLPropertyExpressionResolver().record(propertyExpressionID, propertyExpression);
			return propertyExpressionID;
		}
	}
}