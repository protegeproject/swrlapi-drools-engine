package org.swrlapi.drools.owl.classes;

import checkers.nullness.quals.NonNull;
import dataflow.quals.SideEffectFree;
import org.swrlapi.drools.owl.core.DroolsTernaryObject;
import org.swrlapi.drools.owl.properties.DP;

/**
 * This class represents an OWL data minimum cardinality class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLDataMinCardinality
 */
public class DMinCCE extends DroolsTernaryObject<String, DP, Integer> implements CE
{
  private static final long serialVersionUID = 1L;

  public DMinCCE(@NonNull String ceid, @NonNull DP p, @NonNull Integer card)
  {
    super(ceid, p, card);
  }

  public DMinCCE(@NonNull String ceid, @NonNull String propertyID, @NonNull Integer card)
  {
    super(ceid, new DP(propertyID), card);
  }

  @NonNull @Override public String getceid()
  {
    return getT1();
  }

  @NonNull public DP getP()
  {
    return getT2();
  }

  @NonNull public Integer getCard()
  {
    return getT3();
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "DMinCCE" + super.toString();
  }
}
