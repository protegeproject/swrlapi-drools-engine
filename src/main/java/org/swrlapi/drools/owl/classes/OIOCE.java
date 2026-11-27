package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.builtins.arguments.SWRLClassExpressionBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.DroolsNarySet;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;

import java.util.Set;

/**
 * This class represents an OWL object intersection of class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectIntersectionOf
 */
public class OIOCE extends DroolsNarySet<String, String> implements CE
{
  private static final long serialVersionUID = 1L;

  public OIOCE(@NonNull String ceid, @NonNull Set<String> elements)
  {
    super(ceid, elements);
  }

  @Override public @NonNull String getceid()
  {
    return getID();
  }

  public @NonNull Set<String> getceids() { return getElements(); }

  @SideEffectFree @Override public @NonNull String toString()
  {
    return "OIOCE" + super.toString();
  }

  @Override public @NonNull SWRLClassExpressionBuiltInArgument extract(
    @NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }
}
