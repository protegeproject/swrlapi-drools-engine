package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLPropertyExpressionConverter;
import org.swrlapi.drools.core.DroolsDPEResolver;
import org.swrlapi.drools.core.DroolsOPEResolver;
import org.swrlapi.drools.owl.properties.DP;
import org.swrlapi.drools.owl.properties.OP;

/**
 * This class converts OWLAPI OWL property expressions to their Drools representation.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectPropertyExpression
 * @see org.semanticweb.owlapi.model.OWLDataPropertyExpression
 */
public class DroolsOWLPropertyExpressionConverter extends DroolsConverterBase
		implements TargetRuleEngineOWLPropertyExpressionConverter<String>
{
	private final DroolsOPEResolver opeResolver;
	private final DroolsDPEResolver dpeResolver;

	public DroolsOWLPropertyExpressionConverter(SWRLRuleEngineBridge bridge, DroolsOPEResolver opeResolver,
			DroolsDPEResolver dpeResolver)
	{
		super(bridge);

		this.opeResolver = opeResolver;
		this.dpeResolver = dpeResolver;

		reset();
	}

	public void reset()
	{
		getOWLObjectPropertyExpressionResolver().reset();
		getOWLDataPropertyExpressionResolver().reset();
		this.opeResolver.reset();
		this.dpeResolver.reset();
	}

	@Override
	public String convert(OWLObjectPropertyExpression propertyExpression)
	{
		if (!getOWLObjectPropertyExpressionResolver().records(propertyExpression)) {

			if (propertyExpression.isAnonymous()) {
				String propertyExpressionID = this.opeResolver.generatePEID();
				getOWLObjectPropertyExpressionResolver().record(propertyExpressionID, propertyExpression);

				return propertyExpressionID;
			} else {
				OWLObjectProperty objectProperty = propertyExpression.asOWLObjectProperty();
				IRI propertyIRI = objectProperty.getIRI();
				String prefixedName = getIRIResolver().iri2PrefixedName(propertyIRI);
				OP op = new OP(prefixedName);

				getOWLObjectPropertyExpressionResolver().record(prefixedName, propertyExpression);
				this.opeResolver.record(op);

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
				String propertyExpressionID = this.dpeResolver.generatePEID();
				getOWLDataPropertyExpressionResolver().record(propertyExpressionID, propertyExpression);

				return propertyExpressionID;
			} else {
				OWLDataProperty objectProperty = propertyExpression.asOWLDataProperty();
				IRI propertyIRI = objectProperty.getIRI();
				String prefixedName = getIRIResolver().iri2PrefixedName(propertyIRI);
				DP dp = new DP(prefixedName);

				getOWLDataPropertyExpressionResolver().record(prefixedName, propertyExpression);
				this.dpeResolver.record(dp);

				return prefixedName;
			}
		} else
			return getOWLDataPropertyExpressionResolver().resolve(propertyExpression);
	}
}