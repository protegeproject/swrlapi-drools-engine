package org.swrlapi.drools.owl.dataranges;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.builtins.arguments.SWRLDatatypeBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.OE;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

/**
 * This class represents an OWL datatype (e.g., xsd:int).
 *
 * @see org.semanticweb.owlapi.model.OWLDatatype
 */
public class D implements DR, OE
{
  private static final long serialVersionUID = 1L;

  private final String id;

  public D(@NonNull String name)
  {
    this.id = name;
  }

  @NonNull public String getid()
  {
    return this.id;
  }

  /*
   * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
   */
  public D(@NonNull BA ba)
  {
    if (ba instanceof D) {
      D d = (D)ba;
      this.id = d.getid();
    } else
      throw new TargetSWRLRuleEngineInternalException(
        "expecting OWL datatype from bound built-in argument, got " + ba.getClass().getCanonicalName());
  }

  @NonNull @Override public String getdrid()
  {
    return getid();
  }

  @NonNull @Override public OWLDatatype extract(@NonNull DroolsOWLEntityExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override public @NonNull SWRLDatatypeBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @NonNull public static D getTopDatatype()
  {
    return new D(OWLRDFVocabulary.RDFS_LITERAL.getPrefixedName());
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return super.toString();
  }
}
