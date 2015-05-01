package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.core.DroolsTernaryObject;

/**
 * This class represents an OWL object all values from class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectAllValuesFrom
 */
public class OAVFCE extends DroolsTernaryObject<String, String, String> implements CE
{
  private static final long serialVersionUID = 1L;

  public OAVFCE(String ceid, String propertyID, String valueClassID)
  {
    super(ceid, propertyID, valueClassID);
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

  public String getV()
  {
    return getT3();
  }

  @Override
  public String toString()
  {
    return "OAVFCE" + super.toString();
  }
}
