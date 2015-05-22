package org.swrlapi.drools.owl.classexpressions;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.core.I;

/**
 * This class represents an OWL object one of class expression in Drools. Drools is supplied with an exhaustive pairwise
 * set of classes from the list in an OWL one of class expression.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectOneOf
 */
public class OOOCE extends DroolsTernaryObject<String, I, I> implements CE
{
  private static final long serialVersionUID = 1L;

  public OOOCE(@NonNull String ceid, @NonNull I i1, @NonNull I i2)
  {
    super(ceid, i1, i2);
  }

  public OOOCE(@NonNull String ceid, @NonNull String individual1ID, @NonNull String individual2ID)
  {
    super(ceid, new I(individual1ID), new I(individual2ID));
  }

  @NonNull @Override
  public String getceid()
  {
    return getT1();
  }

  @NonNull public I getI1()
  {
    return getT2();
  }

  @NonNull public I getI2()
  {
    return getT3();
  }

  @NonNull @Override
  public String toString()
  {
    return "OOOCE" + super.toString();
  }
}
