package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
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

  public OMinCCE(@NonNull String ceid, @NonNull OP p, @NonNull Integer card)
  {
    super(ceid, p, card);
  }

  public OMinCCE(@NonNull String ceid, @NonNull String propertyID, @NonNull Integer card)
  {
    super(ceid, new OP(propertyID), card);
  }

  @NonNull @Override public String getceid()
  {
    return getT1();
  }

  @NonNull public OP getP()
  {
    return getT2();
  }

  @NonNull public Integer getCard()
  {
    return getT3();
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "OMinCE" + super.toString();
  }
}
