package org.swrlapi.drools.converters.oo;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.converters.TargetRuleEngineConverterBase;
import org.swrlapi.bridge.converters.TargetRuleEngineOWLLiteralConverter;
import org.swrlapi.drools.owl.literals.L;

/**
 * Class to convert an OWLAPI OWL literal to instances of the {@link L} class, which
 * represents literals in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLLiteral
 * @see L
 */
public class DroolsOWLLiteral2LConverter extends DroolsOOConverterBase implements
TargetRuleEngineOWLLiteralConverter<L>
{
  public DroolsOWLLiteral2LConverter(@NonNull SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull @Override
  public L convert(@NonNull OWLLiteral literal)
  {
    String literalValue = literal.getLiteral();
    IRI datatypeIRI = literal.getDatatype().getIRI();
    String datatypePrefixedName = getIRIResolver().iri2PrefixedName(datatypeIRI);

    return new L(literalValue, datatypePrefixedName);
  }
}
