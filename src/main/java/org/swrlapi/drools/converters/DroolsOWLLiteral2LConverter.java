package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLLiteralConverter;
import org.swrlapi.drools.owl.L;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * Class to convert OWL literal to instances of the {@link L} class, which represents literals in Drools.
 */
public class DroolsOWLLiteral2LConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineOWLLiteralConverter<L>
{
	public DroolsOWLLiteral2LConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	@Override
	public L convert(OWLLiteral literal) throws TargetRuleEngineException
	{
		String literalValue = literal.getLiteral();
		IRI datatypeIRI = literal.getDatatype().getIRI();
		String datatypePrefixedName = getOWLIRIResolver().iri2PrefixedName(datatypeIRI);
		return new L(literalValue, datatypePrefixedName);
	}
}
