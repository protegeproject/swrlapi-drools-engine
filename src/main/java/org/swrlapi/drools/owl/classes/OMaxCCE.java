package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL object maximum cardinality class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectMaxCardinality
 */
public class OMaxCCE extends DroolsTernaryObject<String, String, Integer> implements CE
{
  private static final long serialVersionUID = 1L;

  public OMaxCCE(@NonNull String ceid, @NonNull String propertyID, @NonNull Integer card)
  {
    super(ceid, propertyID, card);
  }

  @Override public @NonNull String getceid()
  {
    return getT1();
  }

  public @NonNull String getpid()
  {
    return getT2();
  }

  public @NonNull Integer getCard()
  {
    return getT3();
  }

  @SideEffectFree @Override public @NonNull String toString()
  {
    return "OMaxCE" + super.toString();
  }

  @Override public @NonNull SWRLClassExpressionBuiltInArgument extract(
    @NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }
}
