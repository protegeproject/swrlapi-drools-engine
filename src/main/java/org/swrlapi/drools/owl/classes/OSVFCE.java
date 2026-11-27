package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL object some values from of class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom
 */
public class OSVFCE extends DroolsTernaryObject<String, String, String> implements CE
{
  private static final long serialVersionUID = 1L;

  public OSVFCE(@NonNull String ceid, @NonNull String propertyID, @NonNull String valueClassID)
  {
    super(ceid, propertyID, valueClassID);
  }

  @Override public @NonNull String getceid()
  {
    return getT1();
  }

  public @NonNull String getpid()
  {
    return getT2();
  }

  public @NonNull String getV()
  {
    return getT3();
  }

  @SideEffectFree @Override public @NonNull String toString()
  {
    return "OSVFCE" + super.toString();
  }

  @Override public @NonNull SWRLClassExpressionBuiltInArgument extract(
    @NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }
}
