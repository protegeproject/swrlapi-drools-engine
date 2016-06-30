package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL data maximum cardinality class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataMaxCardinality
 */
public class DMaxCCE extends DroolsTernaryObject<String, String, Integer> implements CE
{
  private static final long serialVersionUID = 1L;

  public DMaxCCE(@NonNull String ceid, @NonNull String propertyID, @NonNull Integer card)
  {
    super(ceid, propertyID, card);
  }

  @NonNull @Override public String getceid()
  {
    return getT1();
  }

  @NonNull public String getpid()
  {
    return getT2();
  }

  @NonNull public Integer getCard()
  {
    return getT3();
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "DMaxCCE" + super.toString();
  }

  @Override public @NonNull SWRLClassExpressionBuiltInArgument extract(
    @NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }
}
