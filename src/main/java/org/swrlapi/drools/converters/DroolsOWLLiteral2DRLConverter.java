package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.converters.TargetRuleEngineOWLLiteralConverter;
import org.swrlapi.core.OWLNamedObjectResolver;
import org.swrlapi.drools.DroolsNames;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class converts OWL literals to their DRL representation for use in rules.
 */
public class DroolsOWLLiteral2DRLConverter implements TargetRuleEngineOWLLiteralConverter<String>
{
	private final OWLNamedObjectResolver resolver;

	public DroolsOWLLiteral2DRLConverter(OWLNamedObjectResolver resolver)
	{
		this.resolver = resolver;
	}

	@Override
	public String convert(OWLLiteral literal) throws TargetRuleEngineException
	{
		return "new " + DroolsNames.LiteralClassName + "(\"" + literal.getLiteral() + "\", \""
				+ resolver.iri2PrefixedName(literal.getDatatype().getIRI()) + "\")";
	}
}
