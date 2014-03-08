package org.protege.swrlapi.drools.converters;

import org.protege.swrlapi.converters.TargetRuleEngineOWLLiteralConverter;
import org.protege.swrlapi.core.OWLNamedObjectResolver;
import org.protege.swrlapi.drools.owl.L;
import org.protege.swrlapi.exceptions.TargetRuleEngineException;
import org.semanticweb.owlapi.model.OWLLiteral;

/**
 * Class to convert OWL literal to instances of the {@link L} class, which represents literals in Drools.
 */
public class DroolsOWLLiteral2LConverter implements TargetRuleEngineOWLLiteralConverter<L>
{
	private final OWLNamedObjectResolver resolver;

	public DroolsOWLLiteral2LConverter(OWLNamedObjectResolver resolver)
	{
		this.resolver = resolver;
	}

	@Override
	public L convert(OWLLiteral literal) throws TargetRuleEngineException
	{
		return new L(literal.getLiteral(), resolver.iri2PrefixedName(literal.getDatatype().getIRI()));
	}
}
