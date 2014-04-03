package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.converters.TargetRuleEngineConverterBase;
import org.swrlapi.converters.TargetRuleEngineOWLLiteralConverter;
import org.swrlapi.core.SWRLRuleEngineBridge;
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
		return "new " + DroolsNames.LiteralClassName + "(\"" + literal.getLiteral() + "\", \""
				+ getOWLIRIResolver().iri2ShortName(literal.getDatatype().getIRI()) + "\")";
	}
}
