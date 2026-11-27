package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL object has self class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectHasSelf
 */
public class OOHSCE extends DroolsBinaryObject<String, String> implements CE
{
  private static final long serialVersionUID = 1L;

  public OOHSCE(@NonNull String ceid, @NonNull String p)
  {
    super(ceid, p);
  }

  @Override public @NonNull String getceid()
  {
    return getT1();
  }

  public @NonNull String getP()
  {
    return getT2();
  }

  @SideEffectFree @Override public @NonNull String toString()
  {
    return "OOHSCE" + super.toString();
  }

  @Override public @NonNull SWRLClassExpressionBuiltInArgument extract(
    @NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }
}
