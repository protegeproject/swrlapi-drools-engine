package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.DroolsQuadObject;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

/**
 * This class represents an OWL data maximum qualified cardinality class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataMaxCardinality
 */
public class OMaxQCCE extends DroolsQuadObject<String, String, String, Integer> implements CE
{
  private static final long serialVersionUID = 1L;

  public OMaxQCCE(@NonNull String ceid, @NonNull String propertyID, @NonNull String fillerID, Integer card)
  {
    super(ceid, propertyID, fillerID, card);
  }

  @NonNull @Override public String getceid()
  {
    return getT1();
  }

  @NonNull public String getpid()
  {
    return getT2();
  }

  @NonNull public String getf()
  {
    return getT3();
  }

  @NonNull public Integer getcard()
  {
    return getT4();
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "OMaxQCCE" + super.toString();
  }

  @Override public @NonNull SWRLClassExpressionBuiltInArgument extract(
    @NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }
}
