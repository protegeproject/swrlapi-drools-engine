package org.swrlapi.drools.converters;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLLiteralConverter;
import org.swrlapi.drools.owl.core.L;

/**
 * Class to convert an OWLAPI OWL literal to instances of the {@link org.swrlapi.drools.owl.core.L} class, which
 * represents literals in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLLiteral
 * @see org.swrlapi.drools.owl.core.L
 */
public class DroolsOWLLiteral2LConverter extends TargetRuleEngineConverterBase implements
TargetRuleEngineOWLLiteralConverter<L>
{
  public DroolsOWLLiteral2LConverter(SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @Override
  public L convert(OWLLiteral literal)
  {
    String literalValue = literal.getLiteral();
    IRI datatypeIRI = literal.getDatatype().getIRI();
    String datatypePrefixedName = getIRIResolver().iri2PrefixedName(datatypeIRI);

    return new L(literalValue, datatypePrefixedName);
  }
}
