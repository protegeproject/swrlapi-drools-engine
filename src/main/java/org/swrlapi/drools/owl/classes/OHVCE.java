package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL object has value class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectHasValue
 */
public class OHVCE extends DroolsTernaryObject<String, String, I> implements CE
{
  private static final long serialVersionUID = 1L;

  public OHVCE(@NonNull String ceid, @NonNull String propertyID, @NonNull String individualID)
  {
    super(ceid, propertyID, new I(individualID));
  }

  @NonNull @Override public String getceid()
  {
    return getT1();
  }

  @NonNull public String getpid()
  {
    return getT2();
  }

  @NonNull public I getV()
  {
    return getT3();
  }

  @NonNull @SideEffectFree @Override public String toString()
  {
    return "OHVCE" + super.toString();
  }

  @NonNull @Override public SWRLClassExpressionBuiltInArgument extract(
    @NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }
}
