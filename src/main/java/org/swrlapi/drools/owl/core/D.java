package org.swrlapi.drools.owl.core;

import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.swrlapi.builtins.arguments.SWRLBuiltInArgument;
import org.swrlapi.drools.extractors.DroolsOWLEntityExtractor;
import org.swrlapi.drools.extractors.DroolsSWRLBuiltInArgumentExtractor;
import org.swrlapi.drools.owl.dataranges.DR;
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

  public D(String datatypeID)
  {
    super(datatypeID);
  }

  /*
   * We have no way of anticipating the return types of built-ins in rules so we need to perform a runtime check.
   */
  public D(BA ba)
  {
    super("<InProcess>");

    if (ba instanceof D) {
      D d = (D)ba;
      setId(d.getName());
    } else
      throw new TargetSWRLRuleEngineInternalException("expecting OWL datatype from bound built-in argument, got "
          + ba.getClass().getCanonicalName());
  }

  @Override
  public String getrid()
  {
    return getName();
  }

  @Override
  public OWLDatatype extract(DroolsOWLEntityExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override
  public SWRLBuiltInArgument extract(DroolsSWRLBuiltInArgumentExtractor extractor) throws TargetSWRLRuleEngineException
  {
    return extractor.extract(this);
  }

  @Override
  public String toString()
  {
    return super.toString();
  }

  public static D getTopDatatype()
  {
    return new D(OWLRDFVocabulary.RDFS_LITERAL.getPrefixedName());
  }
}
