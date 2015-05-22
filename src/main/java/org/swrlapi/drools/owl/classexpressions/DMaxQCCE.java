package org.swrlapi.drools.owl.classexpressions;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.core.DroolsQuadObject;

/**
 * This class represents an OWL data maximum qualified cardinality class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataMaxCardinality
 */
public class DMaxQCCE extends DroolsQuadObject<String, String, String, Integer> implements CE
{
  private static final long serialVersionUID = 1L;

  public DMaxQCCE(@NonNull String ceid, @NonNull String propertyID, @NonNull String fillerID, @NonNull Integer card)
  {
    super(ceid, propertyID, fillerID, card);
  }

  @NonNull @Override
  public String getceid()
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

  @NonNull @Override
  public String toString()
  {
    return "DMaxQCCE" + super.toString();
  }
}
