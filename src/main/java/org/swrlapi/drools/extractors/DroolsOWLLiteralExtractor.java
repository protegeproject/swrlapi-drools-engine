package org.swrlapi.drools.extractors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.swrlapi.bridge.SWRLRuleEngineBridge;
import org.swrlapi.bridge.extractors.TargetRuleEngineExtractorBase;
import org.swrlapi.bridge.extractors.TargetRuleEngineOWLLiteralExtractor;
import org.swrlapi.drools.owl.literals.L;


/**
 * This class converts a Drools OWL literal representation to its OWLAPI representation.
 *
 * @see org.semanticweb.owlapi.model.OWLLiteral
 * @see L
 */
public class DroolsOWLLiteralExtractor extends TargetRuleEngineExtractorBase
    implements TargetRuleEngineOWLLiteralExtractor<L>
{
  public DroolsOWLLiteralExtractor(SWRLRuleEngineBridge bridge)
  {
    super(bridge);
  }

  @NonNull @Override public OWLLiteral extract(@NonNull L literal)
  {
    // Preserve lexical form and datatype, including ill-typed literals whose
    // inconsistency is handled by the RL rules rather than the export path.
    if (!literal.languageTag.isEmpty())
      return getOWLDataFactory().getOWLLiteral(literal.value, literal.languageTag);
    OWLDatatype datatype = getOWLDatatypeFactory().getOWLDatatype(prefixedName2IRI(literal.datatypeName));
    return getOWLDataFactory().getOWLLiteral(literal.value, datatype);
  }
}
