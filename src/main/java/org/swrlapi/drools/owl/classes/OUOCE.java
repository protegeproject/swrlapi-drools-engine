package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL object union of class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectUnionOf
 */
public class OUOCE extends DroolsBinaryObject<String, String> implements CE
{
  private static final long serialVersionUID = 1L;

  public OUOCE(@NonNull String ceid, @NonNull String c1)
  {
    super(ceid, c1);
  }

  @Override @NonNull public String getceid()
  {
    return getT1();
  }

  @NonNull public String getC1()
  {
    return getT2();
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "OUOCE" + super.toString();
  }

  @NonNull @Override public SWRLClassExpressionBuiltInArgument extract(
    @NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }
}
