package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLPropertyExpressionConverter;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.OP;
import org.swrlapi.drools.resolvers.DroolsResolver;

/**
 * This class converts OWLAPI OWL property expressions to their Drools representation.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectPropertyExpression
 * @see org.semanticweb.owlapi.model.OWLDataPropertyExpression
 */
public class DroolsOWLPropertyExpressionConverter extends DroolsConverterBase implements
		TargetRuleEngineOWLPropertyExpressionConverter<String>
{
	private final DroolsResolver resolver;

	public DroolsOWLPropertyExpressionConverter(SWRLRuleEngineBridge bridge, DroolsResolver resolver)
	{
		super(bridge);

		this.resolver = resolver;

		reset();
	}

	public void reset()
	{
		getOWLObjectPropertyExpressionResolver().reset();
		getOWLDataPropertyExpressionResolver().reset();
		this.resolver.reset();
	}

	@Override
	public String convert(OWLObjectPropertyExpression propertyExpression)
	{
		if (!getOWLObjectPropertyExpressionResolver().records(propertyExpression)) {

			if (propertyExpression.isAnonymous()) {
				String propertyExpressionID = this.resolver.getDroolsOPEResolver().generatePEID();
				getOWLObjectPropertyExpressionResolver().record(propertyExpressionID, propertyExpression);

				return propertyExpressionID;
			} else {
				OWLObjectProperty objectProperty = propertyExpression.asOWLObjectProperty();
				IRI propertyIRI = objectProperty.getIRI();
				String prefixedName = getIRIResolver().iri2PrefixedName(propertyIRI);
				OP op = new OP(prefixedName);

				getOWLObjectPropertyExpressionResolver().record(prefixedName, propertyExpression);
				this.resolver.getDroolsOPEResolver().record(op);

				return prefixedName;
			}
		} else
			return getOWLObjectPropertyExpressionResolver().resolve(propertyExpression);
	}

	@Override
	public String convert(OWLDataPropertyExpression propertyExpression)
	{
		if (!getOWLDataPropertyExpressionResolver().records(propertyExpression)) {

			if (propertyExpression.isAnonymous()) {
				String propertyExpressionID = this.resolver.getDroolsDPEResolver().generatePEID();
				getOWLDataPropertyExpressionResolver().record(propertyExpressionID, propertyExpression);

				return propertyExpressionID;
			} else {
				OWLDataProperty objectProperty = propertyExpression.asOWLDataProperty();
				IRI propertyIRI = objectProperty.getIRI();
				String prefixedName = getIRIResolver().iri2PrefixedName(propertyIRI);
				DP dp = new DP(prefixedName);

				getOWLDataPropertyExpressionResolver().record(prefixedName, propertyExpression);
				this.resolver.getDroolsDPEResolver().record(dp);

				return prefixedName;
			}
		} else
			return getOWLDataPropertyExpressionResolver().resolve(propertyExpression);
	}
}