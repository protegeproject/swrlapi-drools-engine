package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;
import org.swrlapi.drools.owl.individuals.I;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL object one of class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectOneOf
 */
public class OOOCE extends DroolsBinaryObject<String, I> implements CE
{
  private static final long serialVersionUID = 1L;

  public OOOCE(@NonNull String ceid, @NonNull I i1)
  {
    super(ceid, i1);
  }

  public OOOCE(@NonNull String ceid, @NonNull String individual1ID)
  {
    super(ceid, new I(individual1ID));
  }

  @NonNull @Override public String getceid()
  {
    return getT1();
  }

  @NonNull public I getI()
  {
    return getT2();
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "OOOCE" + super.toString();
  }

  @Override public SWRLClassExpressionBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }
}
