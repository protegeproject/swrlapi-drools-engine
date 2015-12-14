package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.core.OE;
import org.swrlapi.drools.swrl.BA;
import org.swrlapi.exceptions.TargetSWRLRuleEngineException;
import org.swrlapi.exceptions.TargetSWRLRuleEngineInternalException;

/**
 * Class representing an OWL named class in Drools. A named class is a type of class expression in OWL.
 *
 * @see org.semanticweb.owlapi.model.OWLClass
 */
public class C extends OE implements CE
{
  private static final long serialVersionUID = 1L;

  public C(@NonNull String classID)
  {
    super(classID);
  }

  @NonNull @Override public String getceid()
  {
    return super.getName();
  }

  /*
   * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
   */
  public C(BA ba)
  {
    super("<InProcess>");

    if (ba instanceof C) {
      C c = (C)ba;
      this.id = c.getName();
    } else
      throw new TargetSWRLRuleEngineInternalException(
        "expecting OWL class from bound built-in argument, got " + ba.getClass().getCanonicalName());
  }

  @NonNull @Override public OWLClass extract(@NonNull DroolsOWLEntityExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @NonNull @Override public SWRLBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor)
    throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return super.toString();
  }

  @NonNull public static C getOWLThing()
  {
    return new C(OWLRDFVocabulary.OWL_THING.getPrefixedName());
  }

  @NonNull public static C getOWLNothing()
  {
    return new C(OWLRDFVocabulary.OWL_NOTHING.getPrefixedName());
  }
}
