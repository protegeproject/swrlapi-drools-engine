package org.swrlapi.drools.owl.properties;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.dataflow.qual.Deterministic;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.swrlapi.drools.owl.core.OO;

/**
 * Class representing an OWL property expression
 *
 * @see org.semanticweb.owlapi.model.OWLPropertyExpression
 */
public abstract class PE implements OO
{
  private String id;

  public PE(String id) { this.id = id; }

  public String getid()
  {
    return id;
  }

  protected void setPEID(String peid) { this.id = peid; }

  @SideEffectFree @Deterministic @Override public boolean equals(@Nullable Object obj)
  {
    if (this == obj)
      return true;
    if ((obj == null) || (obj.getClass() != this.getClass()))
      return false;
    PE e = (PE)obj;
    return (getid().equals(e.getid()) || (getid() != null && getid().equals(e.getid())));
  }

  @SideEffectFree @Deterministic @Override public int hashCode()
  {
    int hash = 731;

    hash = hash + (null == getid() ? 0 : getid().hashCode());

    return hash;
  }
}
