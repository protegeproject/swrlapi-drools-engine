package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.properties.OP;

/**
 * This class represents an OWL object minimum cardinality class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectMinCardinality
 */
public class OMinCCE extends DroolsTernaryObject<String, OP, Integer> implements CE
{
  private static final long serialVersionUID = 1L;

  public OMinCCE(String ceid, OP p, Integer card)
  {
    super(ceid, p, card);
  }

  public OMinCCE(String ceid, String propertyID, Integer card)
  {
    super(ceid, new OP(propertyID), card);
  }

  @Override
  public String getceid()
  {
    return getT1();
  }

  public OP getP()
  {
    return getT2();
  }

  public Integer getCard()
  {
    return getT3();
  }

  @Override
  public String toString()
  {
    return "OMinCE" + super.toString();
  }
}
