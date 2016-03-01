package org.swrlapi.drools.owl.classes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.drools.owl.core.DroolsBinaryObject;

/**
 * This class represents an OWL object has self class expression in Drools.
 *
 * @see org.semanticweb.owlapi.model.OWLObjectHasSelf
 */
public class OOHS extends DroolsBinaryObject<String, String> implements CE
{
  private static final long serialVersionUID = 1L;

  public OOHS(@NonNull String ceid, @NonNull String p)
  {
    super(ceid, p);
  }

  @NonNull @Override public String getceid()
  {
    return getT1();
  }

  @NonNull public String getP()
  {
    return getT2();
  }

  @SideEffectFree @NonNull @Override public String toString()
  {
    return "OOHS" + super.toString();
  }
}
