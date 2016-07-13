package org.swrlapi.drools.owl.properties;

import org.swrlapi.drools.owl.core.OO;

/**
 * Class representing an OWL property expression
 *
 * @see org.semanticweb.owlapi.model.OWLPropertyExpression
 */
public class PE implements OO
{
  private String peid;

  public PE(String peid) { this.peid = peid; }

  public String getid()
  {
    return peid;
  }

  protected void setPEID(String peid) { this.peid = peid; }
}
