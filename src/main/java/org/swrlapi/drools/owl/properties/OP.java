package org.swrlapi.drools.owl.properties;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.OE;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

/**
 * This class represents an OWL object property in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectProperty
 */
public class OP extends OE implements P, OPE
{
  private static final long serialVersionUID = 1L;

  public OP(String propertyName)
  {
    super(propertyName);
  }

  /*
   * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
   */
  public OP(BA ba)
  {
    super("<InProcess>");

    if (ba instanceof OP) {
      OP p = (OP)ba;
      setId(p.getName());
    } else
      throw new TargetSWRLRuleEngineInternalException(
          "expecting OWL object property from bound built-in argument, got " + ba.getClass().getCanonicalName());
  }

  @NonNull @Override
  public OWLObjectProperty extract(@NonNull DroolsOWLEntityExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @NonNull @Override
  public SWRLBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override
  public String toString()
  {
    return super.toString();
  }

  @NonNull public static OP getOWLTopObjectProperty()
  {
    return new OP(OWLRDFVocabulary.OWL_TOP_OBJECT_PROPERTY.getPrefixedName());
  }

  @NonNull public static OP getOWLBottomObjectProperty()
  {
    return new OP(OWLRDFVocabulary.OWL_BOTTOM_DATA_PROPERTY.getPrefixedName());
  }
}
