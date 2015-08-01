package org.swrlapi.drools.owl.dataranges;

import checkers.nullness.quals.NonNull;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
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
public class D extends OE implements DR
{
  private static final long serialVersionUID = 1L;

  public D(@NonNull String datatypeID)
  {
    super(datatypeID);
  }

  /*
   * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
   */
  public D(@NonNull BA ba)
  {
    super("<InProcess>");

    if (ba instanceof D) {
      D d = (D)ba;
      setId(d.getName());
    } else
      throw new TargetSWRLRuleEngineInternalException("expecting OWL datatype from bound built-in argument, got "
          + ba.getClass().getCanonicalName());
  }

  @NonNull @Override
  public String getrid()
  {
    return getName();
  }

  @NonNull @Override
  public OWLDatatype extract(@NonNull DroolsOWLEntityExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @NonNull @Override
  public SWRLBuiltInArgument extract(@NonNull DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @NonNull @Override
  public String toString()
  {
    return super.toString();
  }

  @NonNull public static D getTopDatatype()
  {
    return new D(OWLRDFVocabulary.RDFS_LITERAL.getPrefixedName());
  }
}
