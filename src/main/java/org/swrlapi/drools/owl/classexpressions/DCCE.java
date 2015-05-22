package org.swrlapi.drools.owl.classexpressions;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;

/**
 * This class represents an OWL data exact cardinality class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataExactCardinality
 */
public class DCCE extends DroolsTernaryObject<String, String, Integer> implements CE
{
  private static final long serialVersionUID = 1L;

  public DCCE(@NonNull String ceid, @NonNull String propertyID, @NonNull Integer card)
  {
    super(ceid, propertyID, card);
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

  @NonNull public Integer getcard()
  {
    return getT3();
  }

  @NonNull @Override
  public String toString()
  {
    return "DCCE" + super.toString();
  }
}
