package org.protege.swrlapi.drools.converters;

import org.protege.swrlapi.converters.TargetRuleEngineOWLLiteralConverter;
import org.protege.swrlapi.core.OWLNamedObjectResolver;
import org.protege.swrlapi.drools.DroolsNames;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLLiteral;

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
