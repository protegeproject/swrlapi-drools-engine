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

  @NonNull @Override public String getceid()
  {
    return getT1();
  }

  @NonNull public String getP()
  {
    return getT2();
  }

  @NonNull @SideEffectFree @Override public String toString()
  {
    return "OOHSCE" + super.toString();
  }

  @NonNull @Override public SWRLClassExpressionBuiltInArgument extract(
    @NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }
}
