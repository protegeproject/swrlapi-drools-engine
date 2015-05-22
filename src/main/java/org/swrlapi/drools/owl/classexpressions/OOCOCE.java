package org.swrlapi.drools.owl.classexpressions;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;

/**
 * This class represents an OWL object complement of class expression in Drools. Drools is supplied with an exhaustive
 * pairwise set of classes from the list in an OWL one of class expression.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectComplementOf
 */
public class OOCOCE extends DroolsBinaryObject<String, String> implements CE
{
  private static final long serialVersionUID = 1L;

  public OOCOCE(@NonNull String ceid, @NonNull String c)
  {
    super(ceid, c);
  }

  @NonNull @Override
  public String getceid()
  {
    return getT1();
  }

  @NonNull public String getC()
  {
    return getT2();
  }

  @NonNull @Override
  public String toString()
  {
    return "OOCOCE" + super.toString();
  }
}
