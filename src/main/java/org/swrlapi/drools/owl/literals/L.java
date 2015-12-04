package org.swrlapi.drools.owl.literals;

import checkers.nullness.quals.NonNull;
import dataflow.quals.Deterministic;
import dataflow.quals.SideEffectFree;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.OO;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

import java.io.Serializable;

/**
 * Class representing an OWL literal in Drools
 *
 * @see org.semanticweb.owlapi.model.OWLLiteral
 */
public class L implements OO, BA, Serializable
{
  private static final long serialVersionUID = 1L;

  @NonNull public final String value;
  @NonNull public final String datatypeName;

  public L(@NonNull String value, @NonNull String datatypeName)
  {
    this.value = value;
    this.datatypeName = datatypeName;
  }

  public L(@NonNull L l)
  {
    this.value = l.value;
    this.datatypeName = l.datatypeName;
  }

  /*
   * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
   */
  public L(@NonNull BA ba)
  {
    if (ba instanceof L) {
      L l = (L)ba;
      this.value = l.getValue();
      this.datatypeName = l.getTypeName();
    } else
      throw new TargetSWRLRuleEngineInternalException(
        "expecting OWL literal from bound built-in argument, got " + ba.getClass().getCanonicalName());
  }

  public boolean isInt()
  {
    return this.datatypeName.equals(XSDVocabulary.INT.getPrefixedName());
  }

  public boolean isLong()
  {
    return this.datatypeName.equals(XSDVocabulary.LONG.getPrefixedName());
  }

  public boolean isFloat()
  {
    return this.datatypeName.equals(XSDVocabulary.FLOAT.getPrefixedName());
  }

  public boolean isDouble()
  {
    return this.datatypeName.equals(XSDVocabulary.DOUBLE.getPrefixedName());
  }

  public boolean isShort()
  {
    return this.datatypeName.equals(XSDVocabulary.SHORT.getPrefixedName());
  }

  public boolean isBoolean()
  {
    return this.datatypeName.equals(XSDVocabulary.BOOLEAN.getPrefixedName());
  }

  public boolean isByte()
  {
    return this.datatypeName.equals(XSDVocabulary.BYTE.getPrefixedName());
  }

  public boolean isAnyURI()
  {
    return this.datatypeName.equals(XSDVocabulary.ANY_URI.getPrefixedName());
  }

  public boolean isTime()
  {
    return this.datatypeName.equals(XSDVocabulary.TIME.getPrefixedName());
  }

  public boolean isDate()
  {
    return this.datatypeName.equals(XSDVocabulary.DATE.getPrefixedName());
  }

  public boolean isDateTime()
  {
    return this.datatypeName.equals(XSDVocabulary.DATE_TIME.getPrefixedName());
  }

  public boolean isDuration()
  {
    return this.datatypeName.equals(XSDVocabulary.DURATION.getPrefixedName());
  }

  public boolean isString()
  {
    return this.datatypeName.equals(XSDVocabulary.STRING.getPrefixedName());
  }

  @NonNull @Override public SWRLBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "L(\"" + this.value + "\", " + this.datatypeName + ")";
  }

  @NonNull public String getTypeName()
  {
    return this.datatypeName;
  }

  @NonNull public String getValue()
  {
    return this.value;
  }

  // We consider literals to be equal if they have the same type name and value.
  // TODO This is a very simpleminded implementation of equals. Think about using the SWRLAPI's OWLLiteralComparator

  @SideEffectFree @Deterministic @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    L l = (L)o;

    if (!value.equals(l.value))
      return false;
    return datatypeName.equals(l.datatypeName);

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int result = value.hashCode();
    result = 31 * result + datatypeName.hashCode();
    return result;
  }
}
