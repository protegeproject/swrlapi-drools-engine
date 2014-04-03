package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.converters.TargetRuleEngineConverterBase;
import org.swrlapi.converters.TargetRuleEngineOWLLiteralConverter;
import org.swrlapi.core.SWRLRuleEngineBridge;
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
		String datatypeShortName = getOWLIRIResolver().iri2ShortName(datatypeIRI);
		return new L(literalValue, datatypeShortName);
	}
}
