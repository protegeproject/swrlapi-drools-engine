package org.swrlapi.drools.owl.classexpressions;

import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.properties.OP;

/**
 * This class represents an OWL object exact cardinality class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectExactCardinality
 */
public class OCCE extends DroolsTernaryObject<String, OP, Integer> implements CE
{
  private static final long serialVersionUID = 1L;

  public OCCE(String ceid, OP p, Integer card)
  {
    super(ceid, p, card);
  }

  public OCCE(String ceid, String propertyID, Integer card)
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
    return "OCE" + super.toString();
  }
}
