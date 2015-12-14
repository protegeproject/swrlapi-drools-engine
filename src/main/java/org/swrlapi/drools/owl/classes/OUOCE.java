package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.drools.converters.drl.DroolsOWLClassExpression2DRLConverter;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;

/**
 * This class represents an OWL object union of class expression in Drools. Each element of the union class list is
 * broken up by the {@link DroolsOWLClassExpression2DRLConverter} class. These are linked together in the Drools OWL 2 RL
 * rules using the class expression ID.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectUnionOf
 */
public class OUOCE extends DroolsBinaryObject<String, String> implements CE
{
  private static final long serialVersionUID = 1L;

  public OUOCE(@NonNull String ceid, @NonNull String c1)
  {
    super(ceid, c1);
  }

  @Override @NonNull public String getceid()
  {
    return getT1();
  }

  @NonNull public String getC1()
  {
    return getT2();
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "OUOCE" + super.toString();
  }
}
