package org.swrlapi.drools.owl.classexpressions;

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

  public OHVCE(String ceid, String propertyID, String individualID)
  {
    super(ceid, propertyID, new I(individualID));
  }

  @Override
  public String getceid()
  {
    return getT1();
  }

  public String getpid()
  {
    return getT2();
  }

  public I getV()
  {
    return getT3();
  }

  @Override
  public String toString()
  {
    return "OHVCE" + super.toString();
  }
}
