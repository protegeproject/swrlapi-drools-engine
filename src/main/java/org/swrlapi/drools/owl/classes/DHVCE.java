package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.literals.L;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL data has value class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataHasValue
 */
public class DHVCE extends DroolsTernaryObject<String, String, L> implements CE
{
  private static final long serialVersionUID = 1L;

  public DHVCE(@NonNull String ceid, @NonNull String propertyID, @NonNull L l)
  {
    super(ceid, propertyID, l);
  }

  @NonNull @Override public String getceid()
  {
    return getT1();
  }

  @NonNull public String getpid()
  {
    return getT2();
  }

  @NonNull public L getV()
  {
    return getT3();
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "DHVCE" + super.toString();
  }

  @NonNull @Override public SWRLClassExpressionBuiltInArgument extract(
    @NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }
}
