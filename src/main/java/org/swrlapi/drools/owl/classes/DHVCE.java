package org.swrlapi.drools.owl.classes;

import checkers.nullness.quals.NonNull;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.literals.L;

/**
 * This class represents an OWL data has value class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataHasValue
 */
public class DHVCE extends DroolsTernaryObject<String, String, L> implements CE
{
  private static final long serialVersionUID = 1L;

  public DHVCE(@NonNull String ceid, @NonNull String propertyID, @NonNull L l)
  {
    super(ceid, propertyID, l);
  }

  @NonNull @Override public String getceid()
  {
    return getT1();
  }

  @NonNull public String getpid()
  {
    return getT2();
  }

  @NonNull public L getV()
  {
    return getT3();
  }

  @NonNull @Override public String toString()
  {
    return "DHVCE" + super.toString();
  }
}
