package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLLiteralConverter;
import org.swrlapi.drools.DroolsNames;
import org.swrlapi.exceptions.TargetRuleEngineException;

/**
 * This class converts OWL literals to their DRL representation for use in rules.
 */
public class DroolsOWLLiteral2DRLConverter extends TargetRuleEngineConverterBase implements
		TargetRuleEngineOWLLiteralConverter<String>
{
	public DroolsOWLLiteral2DRLConverter(SWRLRuleEngineBridge bridge)
	{
		super(bridge);
	}

	@Override
	public String convert(OWLLiteral literal) throws TargetRuleEngineException
	{
		IRI datatypeIRI = literal.getDatatype().getIRI();
		String datatypePrefixedName = getOWLIRIResolver().iri2PrefixedName(datatypeIRI);

		return "new " + DroolsNames.LITERAL_CLASS_NAME + "(\"" + literal.getLiteral() + "\", \"" + datatypePrefixedName
				+ "\")";
	}
}
