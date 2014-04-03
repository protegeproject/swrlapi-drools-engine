package org.swrlapi.drools.converters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.swrlapi.converters.TargetRuleEngineOWLPropertyExpressionConverter;
import org.swrlapi.core.SWRLRuleEngineBridge;
import org.swrlapi.drools.owl.entities.P;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class converts OWL property expressions to their Drools DRL representation for use in rules or to the {@link P}
 * class for knowledge base storage.
 */
public class DroolsOWLPropertyExpressionConverter extends DroolsConverterBase implements
		TargetRuleEngineOWLPropertyExpressionConverter<String>
{
	private final Map<OWLPropertyExpression<?, ?>, String> propertyExpression2IDMap;
	private final Set<String> convertedPropertyExpressionIDs;
	private int propertyExpressionIndex;
	private final Set<P> propertyExpressions;

	public DroolsOWLPropertyExpressionConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);

		this.propertyExpression2IDMap = new HashMap<OWLPropertyExpression<?, ?>, String>();
		this.propertyExpressionIndex = 0;
		this.convertedPropertyExpressionIDs = new HashSet<String>();
		this.propertyExpressions = new HashSet<P>();
		getOWLPropertyExpressionResolver().reset();
	}

	public void reset()
	{
		this.propertyExpressions.clear();
		this.propertyExpressionIndex = 0;
		this.propertyExpression2IDMap.clear();
		this.convertedPropertyExpressionIDs.clear();
		getOWLPropertyExpressionResolver().reset();
	}

	// TODO this is a bit rough and ready - see if we can be more principled
	@Override
	public String convert(OWLObjectPropertyExpression propertyExpression) throws TargetRuleEngineException
	{
		if (propertyExpression instanceof OWLNamedObject)
			return getOWLIRIResolver().iri2ShortName(((OWLNamedObject)propertyExpression).getIRI());
		else
			return getPropertyExpressionID(propertyExpression);
	}

	// TODO this is a bit rough and ready - see if we can be more principled
	@Override
	public String convert(OWLDataPropertyExpression propertyExpression) throws TargetRuleEngineException
	{
		if (propertyExpression instanceof OWLNamedObject)
			return getOWLIRIResolver().iri2ShortName(((OWLNamedObject)propertyExpression).getIRI());
		else
			return getPropertyExpressionID(propertyExpression);
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