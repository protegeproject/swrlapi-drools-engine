package org.swrlapi.drools.owl.classexpressions;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.core.I;

/**
 * This class represents an OWL object has value class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectHasValue
 */
public class OHVCE extends DroolsTernaryObject<String, String, I> implements CE
{
  private static final long serialVersionUID = 1L;

  public OHVCE(@NonNull String ceid, @NonNull String propertyID, @NonNull String individualID)
  {
    super(ceid, propertyID, new I(individualID));
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

  @NonNull public I getV()
  {
    return getT3();
  }

  @NonNull @Override
  public String toString()
  {
    return "OHVCE" + super.toString();
  }
}
