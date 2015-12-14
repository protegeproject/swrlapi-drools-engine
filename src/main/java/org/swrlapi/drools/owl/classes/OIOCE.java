package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.drools.converters.drl.DroolsOWLClassExpression2DRLConverter;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;

/**
 * This class represents an OWL object intersection of class expression in Drools. Each element of the intersection
 * class list is broken up by the {@link DroolsOWLClassExpression2DRLConverter} class. These are linked together in the
 * Drools OWL 2 RL rules using the class expression ID.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectIntersectionOf
 */
public class OIOCE extends DroolsBinaryObject<String, String> implements CE
{
  private static final long serialVersionUID = 1L;

  public OIOCE(@NonNull String ceid, @NonNull String c1)
  {
    super(ceid, c1);
  }

  @NonNull @Override public String getceid()
  {
    return getT1();
  }

  @NonNull public String getC1()
  {
    return getT2();
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "OIOCE" + super.toString();
  }
}
