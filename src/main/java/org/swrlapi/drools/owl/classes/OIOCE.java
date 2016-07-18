package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL object intersection of class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectIntersectionOf
 */
public class OIOCE extends DroolsBinaryObject<String, String> implements CE
{
  private static final long serialVersionUID = 1L;

  public OIOCE(@NonNull String ceid, @NonNull String c1)
  {
    super(ceid, c1);
  }

  @NonNull @Override public String getceid()
  {
    return getT1();
  }

  @NonNull public String getC1()
  {
    return getT2();
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "OIOCE" + super.toString();
  }

  @NonNull @Override public SWRLClassExpressionBuiltInArgument extract(
    @NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }
}
