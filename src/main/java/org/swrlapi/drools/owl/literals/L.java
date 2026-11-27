package org.swrlapi.drools.owl.literals;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.OO;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

import java.io.Serializable;
import java.util.Locale;

/**
 * Class representing an OWL literal in Drools
 *
 * @see org.semanticweb.owlapi.model.OWLLiteral
 */
public class L implements OO, BA, Serializable
{
  private static final long serialVersionUID = 1L;

  public final @NonNull String value;
  public final @NonNull String datatypeName;
  public final @NonNull String languageTag;

  public L(@NonNull String value, @NonNull String datatypeName)
  {
    this(value, datatypeName, "");
  }

  public L(@NonNull String value, @NonNull String datatypeName, @NonNull String languageTag)
  {
    this.value = value;
    this.datatypeName = datatypeName;
    this.languageTag = languageTag.toLowerCase(Locale.ROOT);
  }

  public L(@NonNull L l)
  {
    this.value = l.value;
    this.datatypeName = l.datatypeName;
    this.languageTag = l.languageTag;
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
      this.languageTag = l.languageTag;
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

  @Override public @NonNull SWRLBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

   @SideEffectFree @Override public @NonNull String toString()
  {
    return "L(\"" + this.value + "\", " + this.datatypeName + ")";
  }

  public @NonNull String getTypeName()
  {
    return this.datatypeName;
  }

  public @NonNull String getValue()
  {
    return this.value;
  }

  // Literal identity includes datatype, lexical value, and language tag.
  // TODO This is a very simpleminded implementation of equals. Think about using the SWRLAPI's OWLLiteralComparator

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    L l = (L)o;

    if (!value.equals(l.value))
      return false;
    return datatypeName.equals(l.datatypeName) && languageTag.equals(l.languageTag);

  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int result = value.hashCode();
    result = 31 * result + datatypeName.hashCode();
    result = 31 * result + languageTag.hashCode();
    return result;
  }
}
