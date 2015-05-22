package org.swrlapi.drools.owl.properties;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.OE;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

/**
 * This class represents an OWL annotation property.
 *
 * @see org.semanticweb.owlapi.model.OWLAnnotationProperty
 */
public class AP extends OE implements P
{
  private static final long serialVersionUID = 1L;

  public AP(String propertyName)
  {
    super(propertyName);
  }

  /*
   * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
   */
  public AP(BA ba)
  {
    super("<InProcess>");

    if (ba instanceof AP) {
      AP p = (AP)ba;
      setId(p.getName());
    } else
      throw new TargetSWRLRuleEngineInternalException(
          "expecting OWL annotation property for bound built-in argument, got " + ba.getClass().getCanonicalName());
  }

  @NonNull @Override
  public OWLAnnotationProperty extract(@NonNull DroolsOWLEntityExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @NonNull @Override
  public SWRLBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }
}
